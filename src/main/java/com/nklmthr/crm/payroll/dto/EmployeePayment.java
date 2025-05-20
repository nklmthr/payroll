package com.nklmthr.crm.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class EmployeePayment {

	@Id
	private String id;
	
	@PrePersist
	protected void generateIdIfMissing() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "employee", referencedColumnName = "id")
	private Employee employee;
	
	
	@Column
	private LocalDate paymentDate;
	
	@Column
	private BigDecimal amount;
	
	@Column
	private BigDecimal tax;
	
	@Column
	private BigDecimal pfEmployee;
	
	@Column
	private BigDecimal pfEmployer;
	
	@Column
	private BigDecimal totalPf;
	
	@Column
	private BigDecimal netSalary;
	
	@Column
	private String paymentMode;

	public void update(EmployeePayment employeePayment) {
		this.paymentDate = employeePayment.getPaymentDate();
		this.amount = employeePayment.getAmount();
		this.tax = employeePayment.getTax();
		this.pfEmployee = employeePayment.getPfEmployee();
		this.pfEmployer = employeePayment.getPfEmployer();
		this.totalPf = employeePayment.getTotalPf();
		this.netSalary = employeePayment.getNetSalary();	
		this.paymentMode = employeePayment.getPaymentMode();
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

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getPfEmployee() {
		return pfEmployee;
	}

	public void setPfEmployee(BigDecimal pfEmployee) {
		this.pfEmployee = pfEmployee;
	}

	public BigDecimal getPfEmployer() {
		return pfEmployer;
	}

	public void setPfEmployer(BigDecimal pfEmployer) {
		this.pfEmployer = pfEmployer;
	}

	public BigDecimal getTotalPf() {
		return totalPf;
	}

	public void setTotalPf(BigDecimal totalPf) {
		this.totalPf = totalPf;
	}

	public BigDecimal getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
	}
	
	@Override
	public String toString() {
		ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
		return ReflectionToStringBuilder.toStringExclude(this, "employee");
	}

	
	
	
}
