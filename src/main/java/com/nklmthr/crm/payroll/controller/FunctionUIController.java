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

import com.nklmthr.crm.payroll.dto.Function;
import com.nklmthr.crm.payroll.dto.FunctionCapability;
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
		m.addAttribute("function", new Function());
		return "function/addFunction";
	}

	@PostMapping("/function/save")
	public String getSaveFunctionPage(Model m, Function function) {
		functionCapabilityService.saveFunction(function);
		logger.info("Function saved successfully");
		m.addAttribute("functions", functionCapabilityService.getFunctions());
		logger.info("Function saved successfully");
		return "function/function";
	}
	
	@GetMapping("/function/edit")
	public String getEditFunctionPage(Model m, @RequestParam("functionId") String functionId) {
		Function function = functionCapabilityService.getFunctionById(functionId);
		m.addAttribute("function", function);
		logger.info("Function fetched successfully");
		return "function/editFunction";
	}
	
	@PostMapping("/function/update")
	public String getUpdateFunctionPage(Model m, @RequestParam("functionId") String functionId, Function function) {
		functionCapabilityService.updateFunction(functionId, function);
		logger.info("Function updated successfully");
		m.addAttribute("functions", functionCapabilityService.getFunctions());
		return "function/function";
	}
	
	@GetMapping("/function/delete")
	public String getDeleteFunctionPage(Model m, @RequestParam("functionId") String functionId) {
		functionCapabilityService.deleteFunction(functionId);
		logger.info("Function deleted successfully");
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
		FunctionCapability functionCapability = new FunctionCapability();
		functionCapability.setStartDate(LocalDate.now().withDayOfMonth(1));
		functionCapability.setEndDate(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
		m.addAttribute("functionCapability", functionCapability);
		m.addAttribute("functionId", functionId);
		return "functionCapability/addFunctionCapability";
	}

	@PostMapping("/function/function-capability/save")
	public String getSaveFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId,
			FunctionCapability functionCapability) {
		functionCapabilityService.saveFunctionCapability(functionId, functionCapability);
		m.addAttribute("functionCapabilities", functionCapabilityService.getFunctionCapabilities(functionId));
		logger.info("Function Capability saved successfully");
		return "functionCapability/functionCapability";
	}
	@GetMapping("/function/function-capability/edit")
	public String getEditFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId,
			@RequestParam("functionCapabilityId") String functionCapabilityId) {
		FunctionCapability functionCapability = functionCapabilityService.getFunctionCapabilityById(functionId,
				functionCapabilityId);
		m.addAttribute("functionCapability", functionCapability);
		m.addAttribute("functionId", functionId);
		logger.info("Function Capability fetched successfully");
		return "functionCapability/editFunctionCapability";
	}
	@PostMapping("/function/function-capability/update")
	public String getUpdateFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId,
			@RequestParam("functionCapabilityId") String functionCapabilityId, FunctionCapability functionCapability) {
		logger.info("Function Capability ID: " + functionCapability.getId());
		logger.info("Function Capability Start Date: " + functionCapability.getStartDate());
		logger.info("Function Capability End Date: " + functionCapability.getEndDate());
		logger.info("Function Capability: " + functionCapability.getCapability());
		
		functionCapabilityService.updateFunctionCapability(functionId, functionCapabilityId, functionCapability);
		logger.info("Function Capability updated successfully");
		m.addAttribute("functionCapabilities", functionCapabilityService.getFunctionCapabilities(functionId));
		return "functionCapability/functionCapability";
	}
	
	@GetMapping("/function/function-capability/delete")
	public String getDeleteFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId,
			@RequestParam("functionCapabilityId") String functionCapabilityId) {
		functionCapabilityService.deleteFunctionCapability(functionId, functionCapabilityId);
		logger.info("Function Capability deleted successfully");
		m.addAttribute("functionCapabilities", functionCapabilityService.getFunctionCapabilities(functionId));
		return "functionCapability/functionCapability";
	}

}
