package org.opentosca.csarrepo.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.CsarPlan;
import org.opentosca.csarrepo.model.HashedFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.CsarPlanRepository;
import org.opentosca.csarrepo.model.repository.CsarRepository;
import org.opentosca.csarrepo.model.repository.FileSystemRepository;
import org.opentosca.csarrepo.util.Extractor;
import org.opentosca.csarrepo.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author eiselems (marcus.eisele@gmail.com), Dennis Przytarski
 *
 */
public class UploadCsarFileService extends AbstractService {

	private static final String ENTRY_DEFINITION_PATTERN = "Entry-Definitions: ([\\S]+)\\n";
	private static final String TOSCA_METADATA_FILEPATH = "TOSCA-Metadata/TOSCA.meta";

	private static final Logger LOGGER = LogManager.getLogger(UploadCsarFileService.class);
	private static final String XPATH_PLANS_FROM_SERVICETEMPLATE = "//*[local-name()='Plan']";
	private static final String XPATH_PLANMODELREFERENCE_REFERENCE = "//*[local-name()='PlanModelReference']/@*[name()='reference']";

	private CsarFile csarFile;

	// TODO: check if this is the newest one, or remove namespace checking by
	// replacing it with "*"
	private final String SERVICETEMPLATE_NS = "http://docs.oasis-open.org/tosca/ns/2011/12";

	private final String SERVICETEMPLATE_LOCALNAME = "ServiceTemplate";

	/**
	 * @param userId
	 * @param file
	 * @throws IOException
	 */
	public UploadCsarFileService(long userId, long csarId, InputStream inputStream, String name) {
		super(userId);

		if (!checkExtension(name, "csar")) {
			this.addError(String.format("Uploaded file %s does not contain required extension", name));
			return;
		}

		storeFile(csarId, inputStream, name);
	}

	/**
	 * Checks, if the name contains the given extension.
	 * 
	 * @param name
	 * @param extension
	 * @return true, if given name contains given extension
	 */
	private boolean checkExtension(String name, String extension) {
		int index = name.lastIndexOf('.');
		return 0 < index && name.substring(index + 1).equals(extension);
	}

	/**
	 * Moves the uploaded file to the filesystem and creates a csar file.
	 * 
	 * @param csarId
	 * @param inputStream
	 * @param name
	 */
	private void storeFile(long csarId, InputStream inputStream, String name) {
		CsarRepository csarRepository = new CsarRepository();
		CsarFileRepository csarFileRepository = new CsarFileRepository();
		FileSystemRepository fileSystemRepository = new FileSystemRepository();

		try {
			Csar csar = csarRepository.getbyId(csarId);
			if (null == csar) {
				String errorMsg = String.format("CSAR with ID: %d could not be found", csarId);
				this.addError(errorMsg);
				LOGGER.error(errorMsg);
				return;
			}

			FileSystem fileSystem = new FileSystem();
			File temporaryFile = fileSystem.saveTempFile(inputStream);

			String entryDefinition = Extractor.match(Extractor.unzip(temporaryFile, TOSCA_METADATA_FILEPATH),
					ENTRY_DEFINITION_PATTERN);
			String xmlData = Extractor.unzip(temporaryFile, entryDefinition);

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new ByteArrayInputStream(xmlData.getBytes()));

			NodeList elementsByTagNameNS = document.getElementsByTagNameNS(SERVICETEMPLATE_NS,
					SERVICETEMPLATE_LOCALNAME);
			Element serviceTemplate = (Element) elementsByTagNameNS.item(0);

			if (null == serviceTemplate) {
				throw new PersistenceException("Service Definition does not contain valid ServiceTemplate");
			}

			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expression = xpath.compile(XPATH_PLANS_FROM_SERVICETEMPLATE);
			XPathExpression referenceExpression = xpath.compile(XPATH_PLANMODELREFERENCE_REFERENCE);
			NodeList nodeList = (NodeList) expression.evaluate(serviceTemplate, XPathConstants.NODESET);

			for (int i = 0; i < nodeList.getLength(); i++) {
				Element item = (Element) nodeList.item(i);
				String planId = item.getAttribute("id");
				String fullZipPath = (String) referenceExpression.evaluate(item, XPathConstants.STRING);
				String extractedFileName = StringUtils.extractFilenameFromPath(fullZipPath);

				// FIXME: parse name and type
				CsarPlan csarPlan = new CsarPlan(csar, planId, "whateverName", extractedFileName, CsarPlan.Type.OTHERS);
				CsarPlanRepository csarPlanRepository = new CsarPlanRepository();
				csarPlanRepository.save(csarPlan);
				UploadCsarFileService.LOGGER.debug(
						"Extracted plan id: '{}' reference: '{}' from csar->id: '{}', ns: '{}' / name: '{}'", planId,
						extractedFileName, csar.getId(), csar.getNamespace(), csar.getName());

				csar.addPlan(planId, csarPlan);
			}

			String serviceTemplateId = serviceTemplate.getAttribute("id");
			String namespace = serviceTemplate.getAttribute("targetNamespace");

			if (null == csar.getServiceTemplateId()) {
				csar.setServiceTemplateId(serviceTemplateId);
				csar.setNamespace(namespace);
				LOGGER.info("csar: service template id ({}) and namespace ({}) set", serviceTemplateId, namespace);
			} else if (!csar.getServiceTemplateId().equals(serviceTemplateId)
					|| (null == csar.getNamespace() && null != namespace && !namespace.equals(null))
					|| (null != csar.getNamespace() && !csar.getNamespace().equals(namespace))) {
				throw new PersistenceException(String.format(
						"File does not match csar service template id (%s: %s) or namespace (%s: %s).",
						csar.getServiceTemplateId(), serviceTemplateId, csar.getNamespace(), namespace));
			}

			String hash = fileSystem.generateHash(temporaryFile);
			HashedFile hashedFile;

			if (!fileSystemRepository.containsHash(hash)) {
				hashedFile = new HashedFile();
				File newFile = fileSystem.saveToFileSystem(temporaryFile);
				hashedFile.setFilename(UUID.fromString(newFile.getName()));
				hashedFile.setHash(hash);
				hashedFile.setSize(newFile.length());
				fileSystemRepository.save(hashedFile);
			} else {
				hashedFile = fileSystemRepository.getByHash(hash);
			}

			this.csarFile = new CsarFile();
			this.csarFile.setCsar(csar);
			this.csarFile.setHashedFile(hashedFile);
			this.csarFile.setName(name);
			this.csarFile.setUploadDate(new Date());
			if (null != csarRepository.getLastCsarFile(csar)) {
				this.csarFile.setVersion(1 + csarRepository.getLastCsarFile(csar).getVersion());
			} else {
				this.csarFile.setVersion(1);
			}
			csarFileRepository.save(csarFile);

			csar.getCsarFiles().add(csarFile);

			csarRepository.save(csar);
		} catch (IllegalStateException | IOException | ParserConfigurationException | PersistenceException
				| SAXException | XPathExpressionException e) {
			this.addError(e.getMessage());
			LOGGER.error(e.getMessage());
			return;
		}
	}

	public CsarFile getResult() {
		super.logInvalidResultAccess("getResult");

		return this.csarFile;
	}

}
