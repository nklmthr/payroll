package com.nklmthr.crm.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Report {
	private String functionName;
	private String capabilty;
	private LocalDate date;
	private Integer assignmentCount = Integer.valueOf(0);
	private Integer averageActualCapabilityAcheivedInPercent;
	private BigDecimal totalSalary = BigDecimal.ZERO;
	private BigDecimal totalPf = BigDecimal.ZERO;
	private BigDecimal totalTax = BigDecimal.ZERO;
	private BigDecimal totalNetSalary = BigDecimal.ZERO;
	
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
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
	public Integer getAverageActualCapabilityAcheivedInPercent() {
		return averageActualCapabilityAcheivedInPercent;
	}
	public void setAverageActualCapabilityAcheivedInPercent(Integer averageActualCapabilityAcheivedInPercent) {
		this.averageActualCapabilityAcheivedInPercent = averageActualCapabilityAcheivedInPercent;
	}
	public BigDecimal getTotalSalary() {
		return totalSalary;
	}
	public void setTotalSalary(BigDecimal totalSalary) {
		this.totalSalary = totalSalary;
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
	public BigDecimal getTotalNetSalary() {
		return totalNetSalary;
	}
	public void setTotalNetSalary(BigDecimal totalNetSalary) {
		this.totalNetSalary = totalNetSalary;
	}
	
	
	
}