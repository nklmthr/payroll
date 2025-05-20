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
import com.nklmthr.crm.payroll.service.OperationProficiencyService;

@Controller
@RequestMapping("/ui")
public class OperationUIController {

	private static final Logger logger = Logger.getLogger(OperationUIController.class);

	@Autowired
	OperationProficiencyService operationProficiencyService;

	@GetMapping("/operation")
	public String getOperationPage(Model m) {
		m.addAttribute("operations", operationProficiencyService.getOperations());
		return "operation/operation";
	}

	@GetMapping("/operation/add")
	public String getAddOperationPage(Model m) {
		m.addAttribute("operation", new Operation());
		return "operation/addOperation";
	}

	@PostMapping("/operation/save")
	public String getSaveOperationPage(Model m, Operation operation) {
		operationProficiencyService.saveOperation(operation);
		logger.info("Operation saved successfully");
		m.addAttribute("operations", operationProficiencyService.getOperations());
		logger.info("Operation saved successfully");
		return "operation/operation";
	}

	@GetMapping("/operation/edit")
	public String getEditOperationPage(Model m, @RequestParam("operationId") String operationId) {
		Operation operation = operationProficiencyService.getOperationById(operationId);
		m.addAttribute("operation", operation);
		logger.info("Operation fetched successfully");
		return "operation/editOperation";
	}

	@PostMapping("/operation/update")
	public String getUpdateOperationPage(Model m, @RequestParam("operationId") String operationId,
			Operation operation) {
		operationProficiencyService.updateOperation(operationId, operation);
		logger.info("Operation updated successfully");
		m.addAttribute("operations", operationProficiencyService.getOperations());
		return "operation/operation";
	}

	@GetMapping("/operation/delete")
	public String getDeleteOperationPage(Model m, @RequestParam("operationId") String operationId) {
		operationProficiencyService.deleteOperation(operationId);
		logger.info("Operation deleted successfully");
		m.addAttribute("operations", operationProficiencyService.getOperations());
		return "operation/operation";
	}

	@GetMapping("/operation/operation-proficiency")
	public String getOperationProficiencyPage(Model m, @RequestParam("operationId") String operationId) {
		m.addAttribute("operationProficiencies", operationProficiencyService.getOperationProficiencies(operationId));
		return "operationProficiency/operationProficiency";
	}

	@GetMapping("/operation/operation-proficiency/add")
	public String getAddOperationProficiencyPage(Model m, @RequestParam("operationId") String operationId) {
		OperationProficiency operationProficiency = new OperationProficiency();
		operationProficiency.setStartDate(LocalDate.now().withDayOfMonth(1));
		operationProficiency.setEndDate(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
		m.addAttribute("operationProficiency", operationProficiency);
		m.addAttribute("operationId", operationId);
		return "operationProficiency/addOperationProficiency";
	}

	@PostMapping("/operation/operation-proficiency/save")
	public String getSaveOperationProficiencyPage(Model m, @RequestParam("operationId") String operationId,
			OperationProficiency operationProficiency) {
		operationProficiencyService.saveOperationProficiency(operationId, operationProficiency);
		m.addAttribute("operationProficiencies", operationProficiencyService.getOperationProficiencies(operationId));
		logger.info("Operation Proficiency saved successfully");
		return "operationProficiency/operationProficiency";
	}

	@GetMapping("/operation/operation-proficiency/edit")
	public String getEditOperationProficiencyPage(Model m, @RequestParam("operationId") String operationId,
			@RequestParam("operationProficiencyId") String operationProficiencyId) {
		OperationProficiency operationProficiency = operationProficiencyService.getOperationProficiencyById(operationId,
				operationProficiencyId);
		m.addAttribute("operationProficiency", operationProficiency);
		m.addAttribute("operationId", operationId);
		logger.info("Operation Proficiency fetched successfully");
		return "operationProficiency/editOperationProficiency";
	}

	@PostMapping("/operation/operation-proficiency/update")
	public String getUpdateOperationProficiencyPage(Model m, @RequestParam("operationId") String operationId,
			@RequestParam("operationProficiencyId") String operationProficiencyId,
			OperationProficiency operationProficiency) {
		logger.info("Operation Proficiency ID: " + operationProficiency.getId());
		logger.info("Operation Proficiency Start Date: " + operationProficiency.getStartDate());
		logger.info("Operation Proficiency End Date: " + operationProficiency.getEndDate());
		logger.info("Operation Proficiency: " + operationProficiency.getCapability());

		operationProficiencyService.updateOperationProficiency(operationId, operationProficiencyId,
				operationProficiency);
		logger.info("Operation Proficiency updated successfully");
		m.addAttribute("operationProficiencies", operationProficiencyService.getOperationProficiencies(operationId));
		return "operationProficiency/operationProficiency";
	}

	@GetMapping("/operation/operation-proficiency/delete")
	public String getDeleteOperationProficiencyPage(Model m, @RequestParam("operationId") String operationId,
			@RequestParam("operationProficiencyId") String operationProficiencyId) {
		operationProficiencyService.deleteOperationProficiency(operationId, operationProficiencyId);
		logger.info("Operation Proficiency deleted successfully");
		m.addAttribute("operationProficiencies", operationProficiencyService.getOperationProficiencies(operationId));
		return "operationProficiency/operationProficiency";
	}

}
