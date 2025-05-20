package com.nklmthr.crm.payroll.dto;

import java.time.LocalDate;
import java.util.UUID;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Assignment {

	@Id
	private String id;

	@Column
	private LocalDate date;

	@Column
	private WorkShift workShift;

	@Column
	private Integer actualCapabilityAcheivedInPercent = Integer.valueOf(0);

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "employeeSalary", referencedColumnName = "id")
	private EmployeeSalary employeeSalary;

	@OneToOne
	@JoinColumn(name = "employeePayment", referencedColumnName = "id")
	private EmployeePayment employeePayment;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "OperationProficiency", referencedColumnName = "id")
	private OperationProficiency operationProficiency;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "Employee", referencedColumnName = "id")
	private Employee employee;

	@PrePersist
	protected void generateIdIfMissing() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}

	public void update(Assignment assignment) {
		this.date = assignment.getDate();
		this.operationProficiency = assignment.getOperationProficiency();
		this.workShift = assignment.getWorkShift();
		this.employee = assignment.getEmployee();
		this.actualCapabilityAcheivedInPercent = assignment.getActualCapabilityAcheivedInPercent();

	}

	public WorkShift getWorkShift() {
		return workShift;
	}

	public EmployeePayment getEmployeePayment() {
		return employeePayment;
	}

	public EmployeeSalary getEmployeeSalary() {
		return employeeSalary;
	}

	public void setEmployeeSalary(EmployeeSalary employeeSalary) {
		this.employeeSalary = employeeSalary;
	}

	public void setEmployeePayment(EmployeePayment employeePayment) {
		this.employeePayment = employeePayment;
	}

	public OperationProficiency getOperationProficiency() {
		return operationProficiency;
	}

	public void setOperationProficiency(OperationProficiency operationProficiency) {
		this.operationProficiency = operationProficiency;
	}

	public void setWorkShift(WorkShift workShift) {
		this.workShift = workShift;
	}

	public Integer getActualCapabilityAcheivedInPercent() {
		return actualCapabilityAcheivedInPercent;
	}

	public void setActualCapabilityAcheivedInPercent(Integer actualCapabilityAcheivedInPercent) {
		this.actualCapabilityAcheivedInPercent = actualCapabilityAcheivedInPercent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
		return ReflectionToStringBuilder.toStringExclude(this, "employee", "employeeSalary", "employeePayment");
	}
}
