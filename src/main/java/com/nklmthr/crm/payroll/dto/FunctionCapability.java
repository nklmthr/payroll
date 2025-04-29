package com.nklmthr.crm.payroll.dto;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class FunctionCapability  extends ResultDTO {
	@Id
	@UuidGenerator
	@Column
	private String id;

	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;

	@Column
	private String capability;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "Function", referencedColumnName = "id")
	private Function function;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "ReportingPillar", referencedColumnName = "id")
	private ReportingPillar reportingPillar;

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

	public String getCapability() {
		return capability;
	}

	public void setCapability(String capability) {
		this.capability = capability;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public ReportingPillar getReportingPillar() {
		return reportingPillar;
	}

	public void setReportingPillar(ReportingPillar reportingPillar) {
		this.reportingPillar = reportingPillar;
	}

	public void update(FunctionCapability functionCapability) {
		this.startDate = functionCapability.getStartDate();
		this.endDate = functionCapability.getEndDate();
		this.capability = functionCapability.getCapability();
		this.function = functionCapability.getFunction();
		this.reportingPillar = functionCapability.getReportingPillar();

	}

}
