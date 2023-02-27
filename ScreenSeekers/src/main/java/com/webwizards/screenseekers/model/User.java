/*
 * Class File: User.java (Testing for pulling/merge)
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the information of the user (customer, admins), including
 * the username and password. This class have 3 many to many relationships:
 * 
 * 1) User x Role (adding a new comment)
 * 2) User x Movie (for rating and review)
 * 3) User x WatchList (for storing the different watchlists the user could have)
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Column(name = "username")
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(name = "email")
	private String email;

	@NotBlank
	@Size(max = 120)
	@Column(name = "password")
	private String password;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "dateOfBirth")
	private Date dateOfBirth;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "province")
	private String province;
	
	@Column(name = "country")
	private String country;
	
	@Column(name= "profileImage")
	@Lob
	private byte[] profileImage;

  @Column(name = "createdAt")
	private Date createdAt;
	
	@Column(name = "updatedAt")
	private Date updatedAt;
	
	@Column(name = "deletedAt")
	private Date deletedAt;
	
	
	//Setting relation with Role table
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "userRole", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}
	
	//Setting relation with Rating table (comes from a Many to Many relationship)
	@OneToMany(mappedBy="user",
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY)
	private Set<Rating> ratings = new HashSet<>();
	
	//Setting relation with Watchlist table (comes from a One to Many relationship)
	@OneToMany(mappedBy="user",
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY)
	private Set<Watchlist> watchlists = new HashSet<>();
	
	
	//Constructor to create the User in the database
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.createdAt = new Date();
	}
	
	public User(Long id, String password, String firstName,
			String lastName, Date dateOfBirth,
			String phone, String address,
			String city, String province,
			String country, Date createdAt, Date updatedAt, Date deletedAt) {
		this.id = id;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phone = phone;
		this.address = address;
		this.city = city;
		this.province = province;
		this.country = country;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public Set<Watchlist> getWatchlists() {
		return watchlists;
	}

	public void setWatchlists(Set<Watchlist> watchlists) {
		this.watchlists = watchlists;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
	
}
