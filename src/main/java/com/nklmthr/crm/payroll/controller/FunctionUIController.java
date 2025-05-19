package com.nklmthr.crm.payroll.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nklmthr.crm.payroll.dto.Operation;
import com.nklmthr.crm.payroll.dto.OperationProficiency;
import com.nklmthr.crm.payroll.service.FunctionCapabilityService;

@Controller
@RequestMapping("/ui")
public class FunctionUIController {

	private static final Logger logger = Logger.getLogger(FunctionUIController.class);

	@Autowired
	FunctionCapabilityService functionCapabilityService;

	@GetMapping("/function")
	public String getFunctionPage(Model m) {
		m.addAttribute("functions", functionCapabilityService.getFunctions());
		return "function/function";
	}

	@GetMapping("/function/add")
	public String getAddFunctionPage(Model m) {
		m.addAttribute("function", new Operation());
		return "function/addFunction";
	}

	@PostMapping("/function/save")
	public String getSaveFunctionPage(Model m, Operation operation) {
		functionCapabilityService.saveFunction(operation);
		logger.info("Operation saved successfully");
		m.addAttribute("functions", functionCapabilityService.getFunctions());
		logger.info("Operation saved successfully");
		return "function/function";
	}
	
	@GetMapping("/function/edit")
	public String getEditFunctionPage(Model m, @RequestParam("functionId") String functionId) {
		Operation operation = functionCapabilityService.getFunctionById(functionId);
		m.addAttribute("function", operation);
		logger.info("Operation fetched successfully");
		return "function/editFunction";
	}
	
	@PostMapping("/function/update")
	public String getUpdateFunctionPage(Model m, @RequestParam("functionId") String functionId, Operation operation) {
		functionCapabilityService.updateFunction(functionId, operation);
		logger.info("Operation updated successfully");
		m.addAttribute("functions", functionCapabilityService.getFunctions());
		return "function/function";
	}
	
	@GetMapping("/function/delete")
	public String getDeleteFunctionPage(Model m, @RequestParam("functionId") String functionId) {
		functionCapabilityService.deleteFunction(functionId);
		logger.info("Operation deleted successfully");
		m.addAttribute("functions", functionCapabilityService.getFunctions());
		return "function/function";
	}

	@GetMapping("/function/function-capability")
	public String getfunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId) {
		m.addAttribute("functionCapabilities", functionCapabilityService.getFunctionCapabilities(functionId));
		return "functionCapability/functionCapability";
	}

	@GetMapping("/function/function-capability/add")
	public String getAddFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId) {
		OperationProficiency operationProficiency = new OperationProficiency();
		operationProficiency.setStartDate(LocalDate.now().withDayOfMonth(1));
		operationProficiency.setEndDate(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
		m.addAttribute("functionCapability", operationProficiency);
		m.addAttribute("functionId", functionId);
		return "functionCapability/addFunctionCapability";
	}

	@PostMapping("/function/function-capability/save")
	public String getSaveFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId,
			OperationProficiency operationProficiency) {
		functionCapabilityService.saveFunctionCapability(functionId, operationProficiency);
		m.addAttribute("functionCapabilities", functionCapabilityService.getFunctionCapabilities(functionId));
		logger.info("Operation Capability saved successfully");
		return "functionCapability/functionCapability";
	}
	@GetMapping("/function/function-capability/edit")
	public String getEditFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId,
			@RequestParam("functionCapabilityId") String functionCapabilityId) {
		OperationProficiency operationProficiency = functionCapabilityService.getFunctionCapabilityById(functionId,
				functionCapabilityId);
		m.addAttribute("functionCapability", operationProficiency);
		m.addAttribute("functionId", functionId);
		logger.info("Operation Capability fetched successfully");
		return "functionCapability/editFunctionCapability";
	}
	@PostMapping("/function/function-capability/update")
	public String getUpdateFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId,
			@RequestParam("functionCapabilityId") String functionCapabilityId, OperationProficiency operationProficiency) {
		logger.info("Operation Capability ID: " + operationProficiency.getId());
		logger.info("Operation Capability Start Date: " + operationProficiency.getStartDate());
		logger.info("Operation Capability End Date: " + operationProficiency.getEndDate());
		logger.info("Operation Capability: " + operationProficiency.getCapability());
		
		functionCapabilityService.updateFunctionCapability(functionId, functionCapabilityId, operationProficiency);
		logger.info("Operation Capability updated successfully");
		m.addAttribute("functionCapabilities", functionCapabilityService.getFunctionCapabilities(functionId));
		return "functionCapability/functionCapability";
	}
	
	@GetMapping("/function/function-capability/delete")
	public String getDeleteFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId,
			@RequestParam("functionCapabilityId") String functionCapabilityId) {
		functionCapabilityService.deleteFunctionCapability(functionId, functionCapabilityId);
		logger.info("Operation Capability deleted successfully");
		m.addAttribute("functionCapabilities", functionCapabilityService.getFunctionCapabilities(functionId));
		return "functionCapability/functionCapability";
	}

}
