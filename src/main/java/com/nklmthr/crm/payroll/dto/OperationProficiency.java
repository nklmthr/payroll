package com.nklmthr.crm.payroll.dto;

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
public class OperationProficiency {

	@Id
	private String id;

	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;

	@Column
	private String capability;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "Operation", referencedColumnName = "id")
	private Operation operation;

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

	public Operation getFunction() {
		return operation;
	}

	public void setFunction(Operation operation) {
		this.operation = operation;
	}


	public void update(OperationProficiency operationProficiency) {
		this.startDate = operationProficiency.getStartDate();
		this.endDate = operationProficiency.getEndDate();
		this.capability = operationProficiency.getCapability();

	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(this);
	}

}
