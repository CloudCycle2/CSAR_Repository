package org.opentosca.csarrepo.bean;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.service.ListCsarFileService;
import org.opentosca.csarrepo.service.UploadCsarService;
import org.primefaces.model.UploadedFile;

/**
 * Bean for the handling of csars
 * 
 * @author Fabian Toth
 */
@ManagedBean(name = "csarBean")
@SessionScoped
public class CsarBean {

	private UploadedFile file;

	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload() {
		if (this.file != null) {
			try {
				UploadCsarService upload = new UploadCsarService(1L, this.file.getInputstream());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Uploaded file"));
			} catch (IOException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", e.getMessage()));
			}
		}
	}

	/**
	 * @return List of CSARs
	 */
	public List<CsarFile> getCsars() {
		ListCsarFileService list = new ListCsarFileService(1L);
		return list.getResult();
	}
}
