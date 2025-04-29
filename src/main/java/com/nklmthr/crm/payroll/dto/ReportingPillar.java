package com.nklmthr.crm.payroll.dto;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class ReportingPillar  extends ResultDTO  {
	@Id
	@UuidGenerator
	@Column
	private String id;

	@Column
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void update(ReportingPillar reportingPillar) {
		this.name = reportingPillar.getName();
	}

}
