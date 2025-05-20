package com.nklmthr.crm.payroll.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nklmthr.crm.payroll.dao.OperationProficiencyRepository;
import com.nklmthr.crm.payroll.dao.OperationRepository;
import com.nklmthr.crm.payroll.dto.Operation;
import com.nklmthr.crm.payroll.dto.OperationProficiency;

@Service
public class OperationProficiencyService {
	private static final Logger logger = Logger.getLogger(OperationProficiencyService.class);

	@Autowired
	private OperationRepository operationRepository;

	@Autowired
	private OperationProficiencyRepository operationProficiencyRepository;

	public List<Operation> getOperations() {
		List<Operation> operations = operationRepository.findAll();
		logger.info("Operations: " + operations.size());
		return operations;
	}

	public Operation getOperationById(String operationId) {
		Optional<Operation> operation = operationRepository.findById(operationId);
		if (operation.isPresent()) {
			return operation.get();
		}
		return null;
	}

	public Operation saveOperation(Operation operation) {
		Operation func = operationRepository.save(operation);
		return func;

	}

	public Operation updateOperation(String operationId, Operation operation) {
		Optional<Operation> func = operationRepository.findById(operationId);
		if (func.isPresent()) {
			Operation func1 = func.get();
			func1.update(operation);
			operationRepository.save(func1);
			return func1;
		}
		return null;
	}

	public void deleteOperation(String operationId) {
		logger.info("Deleting operation with ID: " + operationId);
		operationRepository.deleteById(operationId);
	}

	public OperationProficiency getOperationProficiencyById(String operationId, String capabilityId) {
		Optional<Operation> operation = operationRepository.findById(operationId);
		if (operation.isPresent()) {
			Optional<OperationProficiency> operationProficiency = operationProficiencyRepository.findById(capabilityId);
			if (operationProficiency.isPresent()) {
				return operationProficiency.get();
			}
		}
		return null;
	}

	public OperationProficiency saveOperationProficiency(String operationId,
			OperationProficiency operationProficiency) {
		Optional<Operation> operation = operationRepository.findById(operationId);
		if (operation.isPresent()) {
			operationProficiency.setOperation(operation.get());
			operationProficiencyRepository.save(operationProficiency);
			return operationProficiency;
		}
		return null;
	}

	public OperationProficiency updateOperationProficiency(String operationId, String operationProficiencyId,
			OperationProficiency operationProficiency) {
		logger.info("Updating operation capability with ID: " + operationProficiencyId);
		logger.info("Operation ID: " + operationId);
		logger.info("Operation Proficiency: " + operationProficiency);
		Optional<Operation> operation = operationRepository.findById(operationId);
		if (operation.isPresent()) {
			Optional<OperationProficiency> funcProficiency = operationProficiencyRepository
					.findById(operationProficiencyId);
			if (funcProficiency.isPresent()) {
				OperationProficiency funcProficiency1 = funcProficiency.get();
				funcProficiency1.update(operationProficiency);
				operationProficiencyRepository.save(funcProficiency1);
				return funcProficiency1;
			}
		}
		return null;
	}

	public List<OperationProficiency> getOperationProficiencies() {
		List<OperationProficiency> operationProficiencies = operationProficiencyRepository.findAll();
		logger.info("operationProficiencies: " + operationProficiencies.size());
		return operationProficiencies;
	}

	public void deleteOperationProficiency(String operationId, String operationProficiencyId) {
		Optional<Operation> operation = operationRepository.findById(operationId);
		if (operation.isPresent()) {
			Optional<OperationProficiency> operationProficiency = operationProficiencyRepository
					.findById(operationProficiencyId);
			if (operationProficiency.isPresent()) {
				operationProficiencyRepository.delete(operationProficiency.get());
				logger.info("Operation Proficiency Assignment deleted for operationId:" + operationId
						+ ", capabilityId:" + operationProficiencyId);
			}
		}

	}

	public List<OperationProficiency> getOperationProficiencies(String operationId) {
		Optional<Operation> operation = operationRepository.findById(operationId);
		if (operation.isPresent()) {
			List<OperationProficiency> operationProficiencies = operation.get().getOperationProficiencies();
			if (!operationProficiencies.isEmpty()) {
				logger.info("Operation Proficiency Assignment found for operationId:" + operationId);
				return operationProficiencies;
			}
		}
		logger.info("Operation Proficiency Assignment not found for operationId:" + operationId);
		return null;

	}

}
