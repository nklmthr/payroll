package com.nklmthr.crm.payroll.dto;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Function extends ResultDTO {
	@Id
	private String id;

	@Column
	private String name;

	@OneToMany(mappedBy = "function")
	@JsonIgnore
	private List<FunctionCapability> functionCapabilities;

	@PrePersist
	protected void generateIdIfMissing() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}

	public void update(Function function) {
		this.name = function.getName();

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

	public List<FunctionCapability> getFunctionCapabilities() {
		return functionCapabilities;
	}

	public void setFunctionCapabilities(List<FunctionCapability> functionCapabilities) {
		this.functionCapabilities = functionCapabilities;
	}

}
