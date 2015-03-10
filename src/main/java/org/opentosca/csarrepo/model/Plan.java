package org.opentosca.csarrepo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "plan")
public class Plan {

	public enum Type {
		BUILD, OTHERS
	}

	@EmbeddedId
	PlanId planId;

	@Column(name = "name")
	private String name;

	@Column(name = "reference")
	private String reference;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private Type type;

	public Plan() {
	}

	/**
	 * @param id
	 *            Id of the plan (from XML ... NOT the database id)
	 * @param name
	 * @param reference
	 * @param type
	 */
	public Plan(HashedFile hashedFile, String id, String name, String reference, Type type) {
		this.planId = new PlanId(hashedFile, id);
		this.name = name;
		this.reference = reference;
		this.type = type;
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

	public class PlanId implements Serializable {
		@ManyToOne
		@JoinColumn(name = "hashed_file_id")
		private HashedFile hashedFile;

		@Column(name = "id")
		private String id;

		public PlanId(HashedFile hashedFile, String id) {
			this.hashedFile = hashedFile;
			this.id = id;
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
		 * @return Id
		 */
		public String getId() {
			return id;
		}

		/**
		 * 
		 * @param Id
		 */
		public void setCsarPlanDatabaseId(String id) {
			this.id = id;
		}
	}

}
