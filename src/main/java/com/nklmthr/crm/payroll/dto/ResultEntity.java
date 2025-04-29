package com.nklmthr.crm.payroll.dto;

import java.util.List;

public class ResultEntity {
	
	private Long count;
	private List<String> errors;
	private List<String> warnings;
	private List<ResultDTO> result;
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public List<String> getWarnings() {
		return warnings;
	}
	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}
	public List<ResultDTO> getResult() {
		return result;
	}
	public void setResult(List<ResultDTO> result) {
		this.result = result;
	}
	
	

}
