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
	private long csarPlanDatabaseId;

	@ManyToOne
	@JoinColumn(name = "hashed_file_id")
	private HashedFile hashedFile;

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

	/**
	 * @param id
	 *            Id of the plan (from XML ... NOT the database id)
	 * @param name
	 * @param reference
	 * @param type
	 */
	public CsarPlan(HashedFile hashedFile, String id, String name, String reference, Type type) {
		this.setHashedFile(hashedFile);
		this.id = id;
		this.name = name;
		this.reference = reference;
		this.type = type;
	}

	/**
	 * @return the hashedFile
	 */
	public HashedFile getHashedFile() {
		return hashedFile;
	}

	/**
	 * @param hashedFile
	 *            the hashedFile to set
	 */
	public void setHashedFile(HashedFile hashedFile) {
		this.hashedFile = hashedFile;
	}

	/**
	 * 
	 * @return csarPlanId
	 */
	public long getCsarPlanDatabaseId() {
		return csarPlanDatabaseId;
	}

	/**
	 * 
	 * @param csarPlanId
	 */
	public void setCsarPlanDatabaseId(long csarPlanId) {
		this.csarPlanDatabaseId = csarPlanId;
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
