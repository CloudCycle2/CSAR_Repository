package org.opentosca.csarrepo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ManagedBean
@SessionScoped
public class KeyboardBean {

	private String value;
	private static final Logger LOGGER = LogManager.getLogger(KeyboardBean.class);

	public String getValue() {
		LOGGER.info("KeyboardBean::reading value: " + value);

		return value;
	}

	public void setValue(String value) {
		LOGGER.info("KeyboardBean::setting value: " + value);

		this.value = value;
	}
}
