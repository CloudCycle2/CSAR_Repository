package org.opentosca.csarrepo.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.WineryServer;
import org.opentosca.csarrepo.model.repository.CsarRepository;
import org.opentosca.csarrepo.util.WineryApiClient;
import org.opentosca.csarrepo.util.WineryCsarFileObject;

public class ImportCsarFromWineryService extends AbstractService {

	CsarFile csarFile;

	public ImportCsarFromWineryService(long userId, long wineryId, Long csarId, String serviceTemplate) {
		super(userId);

		Csar csar = null;
		WineryServer winery = null;
		CsarRepository csarRepo = new CsarRepository();

		// load the winery
		ShowWineryServerService loadWinery = new ShowWineryServerService(userId, wineryId);
		if (loadWinery.hasErrors()) {
			this.addErrors(loadWinery.getErrors());
		} else {
			winery = loadWinery.getResult();
		}

		// load the csar
		if (csarId != null) {
			ShowCsarService loadCsar = new ShowCsarService(userId, csarId);
			if (loadCsar.hasErrors()) {
				this.addErrors(loadCsar.getErrors());
			} else {
				csar = loadCsar.getResult();

				// check if we can import the servicetemplate to our csar
				if (csar.getNamespace() != null || csar.getServiceTemplateId() != null) {
					// we can only import specific csars
					String csarServiceTemplate = "";
					try {
						String tmpNamespace = URLEncoder.encode(csar.getNamespace(), "UTF-8");
						String tmpId = URLEncoder.encode(csar.getServiceTemplateId(), "UTF-8");
						tmpNamespace = URLEncoder.encode(tmpNamespace, "UTF-8");
						tmpId = URLEncoder.encode(tmpId, "UTF-8");
						csarServiceTemplate = tmpNamespace + "/" + tmpId + "/";
					} catch (UnsupportedEncodingException e) {
						// can be empty - csarServiceTemplate is checked as
						// validation
					}

					if (!csarServiceTemplate.equals(serviceTemplate)) {
						this.addError("Servicetemplate does not match the CSAR");
					}
				}
			}
		} else {
			try {
				csar = new Csar();
				csarRepo.save(csar);
			} catch (PersistenceException e) {
				this.addError("Could not save the csar");
			}
		}

		if (this.hasErrors()) {
			if (csarId == null) {
				// csar has just been created --> delete it
				try {
					csarRepo.delete(csar);
				} catch (PersistenceException e) {
					this.addError("An error occured");
				}
			}

			return;
		}

		// no erros -> start import
		WineryApiClient client = new WineryApiClient(winery.getAddress());
		try {
			WineryCsarFileObject object = client.pullFromWinery(serviceTemplate);

			if (null == csarId) {
				csar.setName(object.getFilename().replace(".csar", ""));
				csarRepo.save(csar);
			}

			UploadCsarFileService uploadService = new UploadCsarFileService(userId, csar.getId(),
					object.getInputStream(), object.getFilename());

			if (uploadService.hasErrors()) {
				this.addErrors(uploadService.getErrors());
				if (csarId == null) {
					// we created the csar --> delete it
					csarRepo.delete(csar);
				}
			} else {
				this.csarFile = uploadService.getResult();
			}
		} catch (Exception e) {
			this.addError(e.getMessage());
		}
	}

	public ImportCsarFromWineryService(long userId, long wineryId, String serviceTemplate) {
		this(userId, wineryId, null, serviceTemplate);
	}

	public boolean getResult() {
		super.logInvalidResultAccess("getResult");

		return !this.hasErrors();
	}

	public CsarFile getCsarFile() {
		super.logInvalidResultAccess("getCsarFile");

		return this.csarFile;
	}
}
