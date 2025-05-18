package com.nklmthr.crm.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class EmployeeSalaryPF extends ResultDTO {

	@Id
	private String id;

	@Column
	private LocalDate date;

	@Column
	private BigDecimal employeeContribution;

	@Column
	private BigDecimal employerContribution;

	@Column
	private BigDecimal totalPFContribution;

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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getEmployeeContribution() {
		return employeeContribution;
	}

	public void setEmployeeContribution(BigDecimal employeeContribution) {
		this.employeeContribution = employeeContribution;
	}

	public BigDecimal getEmployerContribution() {
		return employerContribution;
	}

	public void setEmployerContribution(BigDecimal employerContribution) {
		this.employerContribution = employerContribution;
	}

	public BigDecimal getTotalPFContribution() {
		return totalPFContribution;
	}

	public void setTotalPFContribution(BigDecimal totalPFContribution) {
		this.totalPFContribution = totalPFContribution;
	}

}
