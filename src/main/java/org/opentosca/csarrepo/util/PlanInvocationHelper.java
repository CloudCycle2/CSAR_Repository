/**
 * 
 */
package org.opentosca.csarrepo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	// TODO: check which parameter is really needed to determine the CsarFile on
	// an OpenTosca Container
	public static List<HtmlLink> generateLinkToMngmtPlan(OpenToscaServer openToscaServer, String qnameServiceTemplate)
			throws PersistenceException {
		// TODO: get csarFileId from OT Container somehow
		long csarFileId = 14L;
		return generateLinkToPlan(openToscaServer, csarFileId, Plan.Type.OTHERS);
	}

	// TODO: check which parameter is really needed to determine the CsarFile on
	// an OpenTosca Container
	public static List<HtmlLink> generateLinkToBuildPlan(OpenToscaServer openToscaServer) throws PersistenceException {
		// TODO: get csarFileId from OT Container somehow
		long csarFileId = 14L;
		return generateLinkToPlan(openToscaServer, csarFileId, Plan.Type.BUILD);
	}

	// TODO: move this methode somewhere it belongs
	private static List<HtmlLink> generateLinkToPlan(OpenToscaServer openToscaServer, long csarFileId,
			Plan.Type planType) throws PersistenceException {

		List<Plan> matchedPlans = new ArrayList<Plan>();

		CsarFileRepository csarFileRepo = new CsarFileRepository();
		CsarFile csarFile = csarFileRepo.getbyId(csarFileId);
		Map<String, Plan> plansOfHashedFile = csarFile.getHashedFile().getPlans();

		for (Plan plan : plansOfHashedFile.values()) {
			if (planType.equals(plan.getType())) {
				matchedPlans.add(plan);
			}
		}

		// Build links
		List<HtmlLink> resultLinks = new ArrayList<HtmlLink>();
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
