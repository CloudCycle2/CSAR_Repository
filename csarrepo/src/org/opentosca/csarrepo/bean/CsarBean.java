package org.opentosca.csarrepo.bean;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.service.ListCsarService;
import org.opentosca.csarrepo.service.UploadCsarService;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
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

	/**
	 * @return List of CSARs
	 */
	public TreeNode getRoot() {
		TreeNode root = new DefaultTreeNode();
		ListCsarService list = new ListCsarService(1L);
		for (Csar csar : list.getResult()) {
			TreeNode csarNode = new DefaultTreeNode(new TableRow(csar), root);
			for (CsarFile csarFile : csar.getCsarFiles()) {
				new DefaultTreeNode(new TableRow(csarFile), csarNode);
			}
		}
		return root;
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
