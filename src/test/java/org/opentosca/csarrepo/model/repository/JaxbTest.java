/**
 *
 */
package org.opentosca.csarrepo.model.repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Assert;
import org.junit.Test;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.HashedFile;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Tests and validates the generated jaxb xml
 *
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
public class JaxbTest {

	private static final int NUMBER_OF_CSARFILES = 5;

	@Test
	public void testCsar() throws JAXBException, XPathExpressionException, ParserConfigurationException, SAXException,
	IOException {
		Csar csar = new Csar();
		csar.setId(1);
		csar.setName("csar1 name");

		HashedFile hash = new HashedFile();
		hash.setFileName("theOnlyOne.zip");
		hash.setHash("#1337");
		hash.setId(1);
		hash.setSize(1337L);

		for (int i = 0; i < NUMBER_OF_CSARFILES; i++) {
			CsarFile csarFile = new CsarFile();
			csarFile.setName("CsarFile No." + i);
			csarFile.setId(i);
			csarFile.setHashedFile(hash);
			csar.getCsarFiles().add(csarFile);
		}

		String marshalXml = marshalXml(csar);
		validateXML(marshalXml);

	}

	private String marshalXml(Object obj) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Csar.class, CsarFile.class, HashedFile.class);

		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(obj, baos);
		return baos.toString();
	}

	private void validateXML(String xml) throws XPathExpressionException, ParserConfigurationException, SAXException,
	IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		XPathExpression expr = null;
		builder = factory.newDocumentBuilder();
		StringReader sr = new StringReader(xml);
		InputSource inputSource = new InputSource(sr);
		doc = builder.parse(inputSource);

		// create an XPathFactory
		XPathFactory xFactory = XPathFactory.newInstance();

		// create an XPath object
		XPath xpath = xFactory.newXPath();

		// TODO: write additional tests
		// // compile the XPath expression
		// expr =
		// xpath.compile("//*[local-name()='csarfile' and id='1']/*[local-name()='filename']/text()");
		// // run the query and get a nodeset
		// Object result = expr.evaluate(doc, XPathConstants.NODESET);
		//
		// // cast the result to a DOM NodeList
		// NodeList nodes = (NodeList) result;
		// for (int i = 0; i < nodes.getLength(); i++) {
		// System.out.println(nodes.item(i).getNodeValue());
		// }

		// check if correct number of csarFiles with hash #1337 exist
		expr = xpath.compile("count(//*[local-name()='csarfile']/*[local-name()='hash' and text()='#1337']) ="
				+ NUMBER_OF_CSARFILES);
		// run the query and get the number of nodes

		boolean evaluate = (boolean) expr.evaluate(doc, XPathConstants.BOOLEAN);
		Assert.assertTrue("Number of Csars mismatched", evaluate);

	}
}
