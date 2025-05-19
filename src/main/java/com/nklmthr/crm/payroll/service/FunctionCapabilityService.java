package com.nklmthr.crm.payroll.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nklmthr.crm.payroll.dao.FunctionCapabilityRepository;
import com.nklmthr.crm.payroll.dao.FunctionRepository;
import com.nklmthr.crm.payroll.dto.Function;
import com.nklmthr.crm.payroll.dto.FunctionCapability;

@Service
public class FunctionCapabilityService {
	private static final Logger logger = Logger.getLogger(FunctionCapabilityService.class);

	@Autowired
	private FunctionRepository functionRepository;

	@Autowired
	private FunctionCapabilityRepository functionCapabilityRepository;

	public List<Function> getFunctions() {
		List<Function> functions = functionRepository.findAll();
		logger.info("Functions: " + functions.size());
		return functions;
	}

	public Function getFunctionById(String functionId) {
		Optional<Function> function = functionRepository.findById(functionId);
		if (function.isPresent()) {
			return function.get();
		}
		return null;
	}

	public Function saveFunction(Function function) {
		Function func = functionRepository.save(function);
		return func;

	}

	public Function updateFunction(String functionId, Function function) {
		Optional<Function> func = functionRepository.findById(functionId);
		if (func.isPresent()) {
			Function func1 = func.get();
			func1.update(function);
			functionRepository.save(func1);
			return func1;
		}
		return null;
	}

	public void deleteFunction(String functionId) {
		logger.info("Deleting function with ID: " + functionId);
		functionRepository.deleteById(functionId);
	}

	public List<FunctionCapability> getFunctionCapabilities(String functionId) {
		Optional<Function> function = functionRepository.findById(functionId);
		if (function.isPresent()) {
			List<FunctionCapability> functionCapabilities = function.get().getFunctionCapabilities();
			if (functionCapabilities.isEmpty()) {
				logger.info("Function Capability Assignment not found for functionId:" + functionId);
				return null;
			} else {
				logger.info("Function Capability Assignment found for functionId:" + functionId);
				return functionCapabilities;
			}
		}
		return null;
	}

	public FunctionCapability getFunctionCapabilityById(String functionId, String capabilityId) {
		Optional<Function> function = functionRepository.findById(functionId);
		if (function.isPresent()) {
			Optional<FunctionCapability> functionCapability = functionCapabilityRepository.findById(capabilityId);
			if (functionCapability.isPresent()) {
				return functionCapability.get();
			}
		}
		return null;
	}

	public FunctionCapability saveFunctionCapability(String functionId, FunctionCapability functionCapability) {
		Optional<Function> function = functionRepository.findById(functionId);
		if (function.isPresent()) {
			functionCapability.setFunction(function.get());
			functionCapabilityRepository.save(functionCapability);
			return functionCapability;
		}
		return null;
	}

	public FunctionCapability updateFunctionCapability(String functionId, String functionCapabilityId,
			FunctionCapability functionCapability) {
		logger.info("Updating function capability with ID: " + functionCapabilityId);
		logger.info("Function ID: " + functionId);
		logger.info("Function Capability: " + functionCapability);
		Optional<Function> function = functionRepository.findById(functionId);
		if (function.isPresent()) {
			Optional<FunctionCapability> funcCapability = functionCapabilityRepository
					.findById(functionCapabilityId);
			if (funcCapability.isPresent()) {
				FunctionCapability funcCapability1 = funcCapability.get();
				funcCapability1.update(functionCapability);
				functionCapabilityRepository.save(funcCapability1);
				return funcCapability1;
			}
		}
		return null;
	}

	public List<FunctionCapability> getAllFunctionCapabilities() {
		List<FunctionCapability> functionCapabilities = functionCapabilityRepository.findAll();
		logger.info("Function Capabilities: " + functionCapabilities.size());
		return functionCapabilities;
	}

	public void deleteFunctionCapability(String functionId, String functionCapabilityId) {
		Optional<Function> function = functionRepository.findById(functionId);
		if (function.isPresent()) {
			Optional<FunctionCapability> functionCapability = functionCapabilityRepository
					.findById(functionCapabilityId);
			if (functionCapability.isPresent()) {
				functionCapabilityRepository.delete(functionCapability.get());
				logger.info("Function Capability Assignment deleted for functionId:" + functionId + ", capabilityId:"
						+ functionCapabilityId);
			}
		}

	}

}
