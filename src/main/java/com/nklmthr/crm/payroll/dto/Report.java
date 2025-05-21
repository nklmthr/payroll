package com.nklmthr.crm.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Report {
	private String operationName;
	private String capabilty;
	private LocalDate date;
	private Integer assignmentCount = Integer.valueOf(0);
	private BigDecimal grossSalary = BigDecimal.ZERO;
	private BigDecimal totalPf = BigDecimal.ZERO;
	private BigDecimal totalTax = BigDecimal.ZERO;
	private BigDecimal netSalary = BigDecimal.ZERO;

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getCapabilty() {
		return capabilty;
	}

	public void setCapabilty(String capabilty) {
		this.capabilty = capabilty;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getAssignmentCount() {
		return assignmentCount;
	}

	public void setAssignmentCount(Integer assignmentCount) {
		this.assignmentCount = assignmentCount;
	}

	public BigDecimal getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}

	public BigDecimal getTotalPf() {
		return totalPf;
	}

	public void setTotalPf(BigDecimal totalPf) {
		this.totalPf = totalPf;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public BigDecimal getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}