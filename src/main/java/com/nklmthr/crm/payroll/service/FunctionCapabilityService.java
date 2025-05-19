package com.nklmthr.crm.payroll.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nklmthr.crm.payroll.dao.FunctionCapabilityRepository;
import com.nklmthr.crm.payroll.dao.FunctionRepository;
import com.nklmthr.crm.payroll.dto.Operation;
import com.nklmthr.crm.payroll.dto.OperationProficiency;

@Service
public class FunctionCapabilityService {
	private static final Logger logger = Logger.getLogger(FunctionCapabilityService.class);

	@Autowired
	private FunctionRepository functionRepository;

	@Autowired
	private FunctionCapabilityRepository functionCapabilityRepository;

	public List<Operation> getFunctions() {
		List<Operation> operations = functionRepository.findAll();
		logger.info("Functions: " + operations.size());
		return operations;
	}

	public Operation getFunctionById(String functionId) {
		Optional<Operation> operation = functionRepository.findById(functionId);
		if (operation.isPresent()) {
			return operation.get();
		}
		return null;
	}

	public Operation saveFunction(Operation operation) {
		Operation func = functionRepository.save(operation);
		return func;

	}

	public Operation updateFunction(String functionId, Operation operation) {
		Optional<Operation> func = functionRepository.findById(functionId);
		if (func.isPresent()) {
			Operation func1 = func.get();
			func1.update(operation);
			functionRepository.save(func1);
			return func1;
		}
		return null;
	}

	public void deleteFunction(String functionId) {
		logger.info("Deleting function with ID: " + functionId);
		functionRepository.deleteById(functionId);
	}

	public List<OperationProficiency> getFunctionCapabilities(String functionId) {
		Optional<Operation> operation = functionRepository.findById(functionId);
		if (operation.isPresent()) {
			List<OperationProficiency> operationProficiencies = operation.get().getFunctionCapabilities();
			if (operationProficiencies.isEmpty()) {
				logger.info("Operation Capability Assignment not found for functionId:" + functionId);
				return null;
			} else {
				logger.info("Operation Capability Assignment found for functionId:" + functionId);
				return operationProficiencies;
			}
		}
		return null;
	}

	public OperationProficiency getFunctionCapabilityById(String functionId, String capabilityId) {
		Optional<Operation> operation = functionRepository.findById(functionId);
		if (operation.isPresent()) {
			Optional<OperationProficiency> operationProficiency = functionCapabilityRepository.findById(capabilityId);
			if (operationProficiency.isPresent()) {
				return operationProficiency.get();
			}
		}
		return null;
	}

	public OperationProficiency saveFunctionCapability(String functionId, OperationProficiency operationProficiency) {
		Optional<Operation> operation = functionRepository.findById(functionId);
		if (operation.isPresent()) {
			operationProficiency.setFunction(operation.get());
			functionCapabilityRepository.save(operationProficiency);
			return operationProficiency;
		}
		return null;
	}

	public OperationProficiency updateFunctionCapability(String functionId, String functionCapabilityId,
			OperationProficiency operationProficiency) {
		logger.info("Updating function capability with ID: " + functionCapabilityId);
		logger.info("Operation ID: " + functionId);
		logger.info("Operation Capability: " + operationProficiency);
		Optional<Operation> operation = functionRepository.findById(functionId);
		if (operation.isPresent()) {
			Optional<OperationProficiency> funcCapability = functionCapabilityRepository
					.findById(functionCapabilityId);
			if (funcCapability.isPresent()) {
				OperationProficiency funcCapability1 = funcCapability.get();
				funcCapability1.update(operationProficiency);
				functionCapabilityRepository.save(funcCapability1);
				return funcCapability1;
			}
		}
		return null;
	}

	public List<OperationProficiency> getAllFunctionCapabilities() {
		List<OperationProficiency> operationProficiencies = functionCapabilityRepository.findAll();
		logger.info("Operation Capabilities: " + operationProficiencies.size());
		return operationProficiencies;
	}

	public void deleteFunctionCapability(String functionId, String functionCapabilityId) {
		Optional<Operation> operation = functionRepository.findById(functionId);
		if (operation.isPresent()) {
			Optional<OperationProficiency> operationProficiency = functionCapabilityRepository
					.findById(functionCapabilityId);
			if (operationProficiency.isPresent()) {
				functionCapabilityRepository.delete(operationProficiency.get());
				logger.info("Operation Capability Assignment deleted for functionId:" + functionId + ", capabilityId:"
						+ functionCapabilityId);
			}
		}

	}

}
