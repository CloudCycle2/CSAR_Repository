package org.opentosca.csarrepo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Hibernate annotated class for Plan
 * 
 * @author Dennis Przytarski
 *
 */
@Entity
@Table(name = "csar_plan")
public class CsarPlan {

	public enum Type {
		BUILD, OTHERS
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "csar_plan_id")
	private long csarPlanId;

	@ManyToOne
	@JoinColumn(name = "csar_id")
	private Csar csar;

	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "reference")
	private String reference;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private Type type;

	public CsarPlan() {

	}

	public CsarPlan(String id, String name, String reference, Type type) {
		this.id = id;
		this.name = name;
		this.reference = reference;
		this.type = type;
	}

	/**
	 * 
	 * @return csarPlanId
	 */
	public long getCsarPlanId() {
		return csarPlanId;
	}

	/**
	 * 
	 * @param csarPlanId
	 */
	public void setCsarPlanId(long csarPlanId) {
		this.csarPlanId = csarPlanId;
	}

	/**
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
	}

}
