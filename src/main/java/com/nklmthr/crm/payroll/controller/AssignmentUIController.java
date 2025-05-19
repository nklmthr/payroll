package com.nklmthr.crm.payroll.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nklmthr.crm.payroll.dto.Assignment;
import com.nklmthr.crm.payroll.dto.WorkShift;
import com.nklmthr.crm.payroll.service.AssignmentService;
import com.nklmthr.crm.payroll.service.EmployeeService;
import com.nklmthr.crm.payroll.service.FunctionCapabilityService;

@Controller
@RequestMapping("/ui")
public class AssignmentUIController {

	private static final Logger logger = Logger.getLogger(AssignmentUIController.class);

	@Autowired
	AssignmentService assignmentService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	FunctionCapabilityService functionCapabilityService;

	@GetMapping("/assignment")
	public String getAssignmentPage(Model m) {
		m.addAttribute("assignments", assignmentService.getFunctionCapabilityAssignments());
		return "assignment/assignment";
	}

	@GetMapping("/assignment/add")
	public String getAddAssignmentPage(Model m) {
		Assignment assignment = new Assignment();
		assignment.setDate(java.time.LocalDate.now());
		m.addAttribute("assignment", assignment);
		m.addAttribute("employees", employeeService.getEmployees());
		m.addAttribute("functionCapabilities", functionCapabilityService.getAllFunctionCapabilities());
		m.addAttribute("workShifts", WorkShift.values());
		return "assignment/addAssignment";
	}

	@PostMapping("/assignment/save")
	public String getSaveAssignmentPage(Model m, Assignment assignment) {
		assignmentService.saveFunctionCapabilityAssignment(assignment);
		logger.info("Assignment saved successfully");
		m.addAttribute("assignments", assignmentService.getFunctionCapabilityAssignments());
		return "assignment/assignment";
	}

	@GetMapping("/assignment/edit")
	public String getEditAssignmentPage(Model m, @RequestParam("assignmentId") String assignmentId) {
		m.addAttribute("assignment", assignmentService.getFunctionCapabilityAssignment(assignmentId));
		m.addAttribute("employees", employeeService.getEmployees());
		m.addAttribute("functionCapabilities", functionCapabilityService.getAllFunctionCapabilities());
		m.addAttribute("workShifts", WorkShift.values());
		return "assignment/editAssignment";
	}

	@PostMapping("/assignment/update")
	public String getUpdateAssignmentPage(Model m, @RequestParam("assignmentId") String assignmentId,
			Assignment assignment) {
		assignmentService.updateFunctionCapabilityAssignment(assignmentId, assignment);
		logger.info("Assignment updated successfully");
		m.addAttribute("assignments", assignmentService.getFunctionCapabilityAssignments());
		return "assignment/assignment";
	}
	
	@GetMapping("/assignment/delete")
	public String getDeleteAssignmentPage(Model m, @RequestParam("assignmentId") String assignmentId) {
		assignmentService.deleteFunctionCapabilityAssignment(assignmentId);
		logger.info("Assignment deleted successfully");
		m.addAttribute("assignments", assignmentService.getFunctionCapabilityAssignments());
		return "assignment/assignment";
	}

}
