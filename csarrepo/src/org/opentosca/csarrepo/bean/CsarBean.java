package org.opentosca.csarrepo.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.service.DeleteCsarService;
import org.opentosca.csarrepo.service.DownloadCsarService;
import org.opentosca.csarrepo.service.ListCsarService;
import org.opentosca.csarrepo.service.UploadCsarService;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * Bean for the handling of csars
 * 
 * @author Fabian Toth
 */
@ManagedBean(name = "csarBean")
@SessionScoped
public class CsarBean {

	private static final Logger LOGGER = LogManager.getLogger(CsarBean.class);
	private UploadedFile file;
	private StreamedContent download;

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
				UploadCsarService upload = new UploadCsarService(1L, this.file.getInputstream(),
						this.file.getFileName());
				if (!upload.hasErrors()) {
					this.printMessage(FacesMessage.SEVERITY_INFO, "Success", "Uploaded file");
				} else {
					this.printErrors(upload.getErrors());
				}
			} catch (IOException e) {
				this.printMessage(FacesMessage.SEVERITY_ERROR, "An error occured", e.getMessage());
			}
		}
	}

	public List<Csar> getCsars() {
		ListCsarService list = new ListCsarService(0);
		return list.getResult();
	}

	public void prepareDownload(CsarFile csarFile) {
		DownloadCsarService download = new DownloadCsarService(0, csarFile.getFileId());
		if (!download.hasErrors()) {
			try {
				InputStream is = new FileInputStream(download.getResult());
				this.download = new DefaultStreamedContent(is);
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage(), e);
				this.printMessage(FacesMessage.SEVERITY_ERROR, "A error occured", e.getMessage());
			}
		} else {
			this.printErrors(download.getErrors());
		}
	}

	/**
	 * @return the download
	 */
	public StreamedContent getDownload() {
		return download;
	}
	
	public void deleteCsar(long id) {
		this.printMessage(FacesMessage.SEVERITY_INFO, "Info", "Working on CSAR " + id);
		DeleteCsarService service = new DeleteCsarService(0, id);
		if(service.hasErrors()) {
			this.printErrors(service.getErrors());
			return;
		}
		
		this.printMessage(FacesMessage.SEVERITY_INFO, "Success", "CSAR deleted");
	}

	private void printErrors(List<String> errors) {
		for (String error : errors) {
			if (error == null) {
				this.printMessage(FacesMessage.SEVERITY_ERROR, "A error occured", "NullPointer");
			} else {
				this.printMessage(FacesMessage.SEVERITY_ERROR, "A error occured", error);
			}
		}
	}

	private void printMessage(Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}
}
