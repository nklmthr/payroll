package com.nklmthr.crm.payroll.dto;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Employee extends ResultDTO {
	@Id
	private String id;

	@Column
	private String firstName;

	@Column
	private String middleName;

	@Column
	private String lastName;

	@Column
	private String email;

	@Column(length = 10)
	private String mobile;

	@OneToMany(mappedBy = "employee")
	private List<EmployeeSalary> employeeSalary;

	@PrePersist
	protected void generateIdIfMissing() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void update(Employee employee) {
		this.firstName = employee.getFirstName();
		this.middleName = employee.getMiddleName();
		this.lastName = employee.getLastName();
		this.mobile = employee.getMobile();
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", mobile=" + mobile + "]";

	}

}
