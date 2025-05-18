package com.nklmthr.crm.payroll.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class FunctionCapabilityAssignment extends ResultDTO {

	@Id
	private String id;

	@Column
	private LocalDate date;

	@Column
	private WorkShift workShift;

	@Column
	private Integer actualCapabilityAcheivedInPercent = Integer.valueOf(0);

	public WorkShift getWorkShift() {
		return workShift;
	}

	public void setWorkShift(WorkShift workShift) {
		this.workShift = workShift;
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "FunctionCapability", referencedColumnName = "id")
	private FunctionCapability functionCapability;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "Employee", referencedColumnName = "id")
	private Employee employee;

	@PrePersist
	protected void generateIdIfMissing() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}

	public void update(FunctionCapabilityAssignment functionCapabilityAssignment) {
		this.date = functionCapabilityAssignment.getDate();
		this.functionCapability = functionCapabilityAssignment.getFunctionCapability();
		this.employee = functionCapabilityAssignment.getEmployee();

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

	public FunctionCapability getFunctionCapability() {
		return functionCapability;
	}

	public void setFunctionCapability(FunctionCapability functionCapability) {
		this.functionCapability = functionCapability;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(this);
	}
}
