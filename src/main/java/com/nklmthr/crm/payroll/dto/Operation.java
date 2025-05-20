package com.nklmthr.crm.payroll.dto;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Operation {
	@Id
	private String id;

	@Column
	private String name;

	@OneToMany(mappedBy = "operation")
	@JsonIgnore
	private List<OperationProficiency> operationProficiencies;

	@PrePersist
	protected void generateIdIfMissing() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}

	public void update(Operation operation) {
		this.name = operation.getName();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OperationProficiency> getFunctionCapabilities() {
		return operationProficiencies;
	}

	public void setFunctionCapabilities(List<OperationProficiency> operationProficiencies) {
		this.operationProficiencies = operationProficiencies;
	}

	@Override
	public String toString() {
		ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
		return ReflectionToStringBuilder.toString(this);
	}

}
