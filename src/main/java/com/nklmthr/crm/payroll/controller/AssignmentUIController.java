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
import com.nklmthr.crm.payroll.service.OperationProficiencyService;

@Controller
@RequestMapping("/ui")
public class AssignmentUIController {

	private static final Logger logger = Logger.getLogger(AssignmentUIController.class);

	@Autowired
	AssignmentService assignmentService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	OperationProficiencyService operationProficiencyService;

	@GetMapping("/assignment")
	public String getAssignmentPage(Model m) {
		m.addAttribute("assignments", assignmentService.getAssignments());
		logger.info("Assignment page loaded successfully");
		return "assignment/assignment";
	}

	@GetMapping("/assignment/add")
	public String getAddAssignmentPage(Model m) {
		Assignment assignment = new Assignment();
		assignment.setDate(java.time.LocalDate.now());
		m.addAttribute("assignment", assignment);
		m.addAttribute("employees", employeeService.getEmployees());
		m.addAttribute("operationProficiencies", operationProficiencyService.getOperationProficiencies());
		m.addAttribute("workShifts", WorkShift.values());
		logger.info("Add Assignment page loaded successfully");
		return "assignment/addAssignment";
	}

	@PostMapping("/assignment/save")
	public String getSaveAssignmentPage(Model m, Assignment assignment) {
		logger.info("Saving assignment: " + assignment);
		assignmentService.saveAssignment(assignment);
		logger.info("Assignment saved successfully");
		m.addAttribute("assignments", assignmentService.getAssignments());
		return "assignment/assignment";
	}

	@GetMapping("/assignment/edit")
	public String getEditAssignmentPage(Model m, @RequestParam("assignmentId") String assignmentId) {
		m.addAttribute("assignment", assignmentService.getAssignment(assignmentId));
		m.addAttribute("employees", employeeService.getEmployees());
		m.addAttribute("operationProficiencies", operationProficiencyService.getOperationProficiencies());
		m.addAttribute("workShifts", WorkShift.values());
		logger.info("Edit Assignment page loaded successfully");
		return "assignment/editAssignment";
	}

	@PostMapping("/assignment/update")
	public String getUpdateAssignmentPage(Model m, @RequestParam("assignmentId") String assignmentId,
			Assignment assignment) {
		logger.info("Updating assignment: " + assignment);
		assignmentService.updateAssignment(assignmentId, assignment);
		logger.info("Assignment updated successfully");
		m.addAttribute("assignments", assignmentService.getAssignments());
		return "assignment/assignment";
	}
	
	@GetMapping("/assignment/delete")
	public String getDeleteAssignmentPage(Model m, @RequestParam("assignmentId") String assignmentId) {
		assignmentService.deleteAssignment(assignmentId);
		logger.info("Assignment deleted successfully");
		m.addAttribute("assignments", assignmentService.getAssignments());
		return "assignment/assignment";
	}

}
