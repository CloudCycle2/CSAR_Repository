/**
 * 
 */
package org.opentosca.csarrepo.util;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.DeploymentException;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.Plan;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;

/**
 * This class generates links to specific BPS URLs
 * 
 * Used to implement the usage of the try-it links
 * 
 * @author eiselems
 *
 */
public class PlanInvocationHelper {

	private static final Logger LOGGER = LogManager.getLogger(PlanInvocationHelper.class);

	public static List<HtmlLink> generateLinkToMngmtPlan(OpenToscaServer openToscaServer, String openToscaCsarId)
			throws PersistenceException, URISyntaxException, DeploymentException {
		ContainerApiClient client = new ContainerApiClient(openToscaServer);
		Long csarFileId = client.getRepositoryCsarFileId(openToscaCsarId);
		if (csarFileId == null) {
			return new ArrayList<HtmlLink>();
		}
		return generateLinkToPlan(openToscaServer, csarFileId, Plan.Type.OTHERS);
	}

	public static List<HtmlLink> generateLinkToBuildPlan(OpenToscaServer openToscaServer, String openToscaCsarId)
			throws PersistenceException, DeploymentException, URISyntaxException {
		ContainerApiClient client = new ContainerApiClient(openToscaServer);
		Long csarFileId = client.getRepositoryCsarFileId(openToscaCsarId);
		// no mapping was possible, maybe the meta files were not there
		if (csarFileId == null) {
			return new ArrayList<HtmlLink>();
		}
		return generateLinkToPlan(openToscaServer, csarFileId, Plan.Type.BUILD);
	}

	private static List<HtmlLink> generateLinkToPlan(OpenToscaServer openToscaServer, long csarFileId,
			Plan.Type planType) throws PersistenceException {

		List<Plan> matchedPlans = new ArrayList<Plan>();

		CsarFileRepository csarFileRepo = new CsarFileRepository();
		CsarFile csarFile = csarFileRepo.getbyId(csarFileId);
		List<HtmlLink> resultLinks = new ArrayList<HtmlLink>();

		if (null == csarFile) {
			LOGGER.warn("CSAR file was {} for CSAR file id {}. Build-Plans could not be matched.", csarFile, csarFileId);
			return resultLinks;
		}

		Map<String, Plan> plansOfHashedFile = csarFile.getHashedFile().getPlans();
		for (Plan plan : plansOfHashedFile.values()) {
			if (planType.equals(plan.getType())) {
				matchedPlans.add(plan);
			}
		}

		// Build links
		String host = openToscaServer.getAddress().getHost();

		for (Plan plan : matchedPlans) {
			String planName = matchedPlans.get(0).getName();
			String href = "http://" + host + ":9763/services/" + planName + "Service?tryit#";
			resultLinks.add(new HtmlLink(planName, href));
		}

		return resultLinks;

	}

	public static class HtmlLink {
		private String text;
		private String href;

		public HtmlLink(String text, String href) {
			this.text = text;
			this.href = href;
		}

		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}

		/**
		 * @param text
		 *            the text to set
		 */
		public void setText(String text) {
			this.text = text;
		}

		/**
		 * @return the href
		 */
		public String getHref() {
			return href;
		}

		/**
		 * @param href
		 *            the href to set
		 */
		public void setHref(String href) {
			this.href = href;
		}

	}
}
