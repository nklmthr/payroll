package com.nklmthr.crm.payroll.dto;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;

@Entity
public class Employee {
	@Id
	private String id;

	@Column
	private String firstName;

	@Column
	private String middleName;

	@Column
	private String lastName;

	@Column
	private String identity;

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	@Column
	private String mobile;

	@OneToMany(mappedBy = "employee")
	private List<EmployeeSalary> employeeSalary;

	@OneToMany(mappedBy = "employee")
	private List<EmployeePayment> employeePayments;

	@OneToMany(mappedBy = "employee")
	private List<Assignment> assignments;

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

	public List<EmployeeSalary> getEmployeeSalary() {
		return employeeSalary;
	}

	public void setEmployeeSalary(List<EmployeeSalary> employeeSalary) {
		this.employeeSalary = employeeSalary;
	}

	public List<EmployeePayment> getEmployeePayments() {
		return employeePayments;
	}

	public void setEmployeePayments(List<EmployeePayment> employeePayments) {
		this.employeePayments = employeePayments;
	}

	public List<Assignment> getFunctionCapabilityAssignments() {
		return assignments;
	}

	public void setFunctionCapabilityAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public void update(Employee employee) {
		this.firstName = employee.getFirstName();
		this.middleName = employee.getMiddleName();
		this.lastName = employee.getLastName();
		this.mobile = employee.getMobile();
	}

	@Override
	public String toString() {
		ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
		return ReflectionToStringBuilder.toStringExclude(this, "assignments");
	}

	@Transient
	public String getFullName() {
		return lastName + " " + firstName + " " + middleName;
	}

}
