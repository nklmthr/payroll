package com.nklmthr.crm.payroll.dto;

public enum AssignmentStatus {

	NEW("New"), COMPLETED("Completed"), CANCELED("Canceled");

	private String status;

	private AssignmentStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
