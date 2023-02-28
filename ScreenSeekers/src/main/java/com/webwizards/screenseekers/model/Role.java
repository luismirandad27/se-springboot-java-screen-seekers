/**
 * Class File: Role.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the information of the different Role type
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public Role() {

	}

	public Role(ERole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}
