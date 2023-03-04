/**
 * Class File: CrewMember.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store information about a crew member -these are the people who worked on the movies
 * Directors, Editors, Special Effects, cast 
 * 
 * 
 * @author Regal Cruz
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="crewmember")
public class CrewMember {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="firstName")
	private String firstName;
	@Column(name="lastName")
	private String lastName;
	@Column(name="dateOfBirth")
	Date dateOfBirth;
	@Column(name="nationality")
	private String nationality;
	@Column(name="award")
	private String award;
	@Column(name="createdAt")
	private Date createdAt;
	@Column(name="updatedAt")
	private Date updatedAt;
	@Column(name="deletedAt")
	private Date deletedAt;
	
	@OneToMany(mappedBy="crewMember",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private Set<ProductionCrew> productionCrews = new HashSet<>(); 
	
	public Set<ProductionCrew> getProductionCrews() {
		return productionCrews;
	}
	public void setProductionCrews(Set<ProductionCrew> productionCrews) {
		this.productionCrews = productionCrews;
	}
	public CrewMember() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CrewMember(String firstName, String lastName, Date dateOfBirth, String nationality, String award) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.nationality = nationality;
		this.award = award;
		this.createdAt = new Date();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	
	
	

}
