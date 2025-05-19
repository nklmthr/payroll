package com.nklmthr.crm.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class EmployeeSalary {

	@Id
	private String id;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "employee", referencedColumnName = "id")
	private Employee employee;


	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;

	@Column
	private BigDecimal salary;

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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(this);
	}

	public void update(EmployeeSalary employeeSalary) {
		this.startDate = employeeSalary.getStartDate();
		this.endDate = employeeSalary.getEndDate();
		this.salary = employeeSalary.getSalary();
	}

}
