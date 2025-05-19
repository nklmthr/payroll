package com.nklmthr.crm.payroll.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nklmthr.crm.payroll.dao.EmployeePaymentRepository;
import com.nklmthr.crm.payroll.dao.FunctionCapabilityAssignmentRepository;
import com.nklmthr.crm.payroll.dto.EmployeePayment;
import com.nklmthr.crm.payroll.dto.EmployeeSalary;
import com.nklmthr.crm.payroll.dto.FunctionCapabilityAssignment;

@Service
public class AssignmentService {

	private static final Logger logger = Logger.getLogger(AssignmentService.class);
	@Autowired
	private FunctionCapabilityAssignmentRepository functionCapabilityAssignmentRepository;

	@Autowired
	private EmployeePaymentRepository paymentRepository;

	public List<FunctionCapabilityAssignment> getFunctionCapabilityAssignments() {
		List<FunctionCapabilityAssignment> functionCapabilityAssignments = functionCapabilityAssignmentRepository
				.findAll();
		if (!functionCapabilityAssignments.isEmpty()) {
			logger.info("Function Capability Assignments: " + functionCapabilityAssignments.size());
			return functionCapabilityAssignments;
		}
		logger.info("No Function Capability Assignments found");
		return null;
	}
	
	public FunctionCapabilityAssignment getFunctionCapabilityAssignment(String id) {
		Optional<FunctionCapabilityAssignment> functionCapabilityAssignmentOpt = functionCapabilityAssignmentRepository
				.findById(id);
		if (functionCapabilityAssignmentOpt.isPresent()) {
			logger.info("Function Capability Assignment found with ID: " + id);
			return functionCapabilityAssignmentOpt.get();
		}
		logger.info("No Function Capability Assignment found with ID: " + id);
		return null;
	}

	public FunctionCapabilityAssignment saveFunctionCapabilityAssignment(
			FunctionCapabilityAssignment functionCapabilityAssignment) {
		FunctionCapabilityAssignment asg = functionCapabilityAssignmentRepository.save(functionCapabilityAssignment);
		EmployeeSalary salary = asg.getEmployee().getEmployeeSalary().stream().filter(s -> (s.getEndDate() == null))
				.findFirst().get();
		EmployeePayment payment = new EmployeePayment();
		payment.setEmployee(salary.getEmployee());
		payment.setAmount(salary.getSalary());
		payment.setPaymentDate(asg.getDate());
		payment.setPfEmployee(salary.getSalary().multiply(new java.math.BigDecimal(0.12)));
		payment.setPfEmployer(salary.getSalary().multiply(new java.math.BigDecimal(0.12)));
		payment.setTax(salary.getSalary().multiply(new java.math.BigDecimal(0.1)));
		payment.setTotalPf(payment.getPfEmployee().add(payment.getPfEmployer()));
		payment.setNetSalary(payment.getAmount().subtract(payment.getTax()).subtract(payment.getTotalPf()));
		paymentRepository.save(payment);
		return asg;

	}

	public FunctionCapabilityAssignment updateFunctionCapabilityAssignment(
			String assignmentId, FunctionCapabilityAssignment functionCapabilityAssignment) {
		Optional<FunctionCapabilityAssignment> functionCapabilityAssignmentOpt = functionCapabilityAssignmentRepository
				.findById(assignmentId);
		if (functionCapabilityAssignmentOpt.isPresent()) {
			FunctionCapabilityAssignment functionCapabilityAssignment1 = functionCapabilityAssignmentOpt.get();
			functionCapabilityAssignment1.update(functionCapabilityAssignment);
			functionCapabilityAssignmentRepository.save(functionCapabilityAssignment1);
			return functionCapabilityAssignment1;
		}
		return null;
	}

	public void deleteFunctionCapabilityAssignment(String assignmentId) {
		Optional<FunctionCapabilityAssignment> functionCapabilityAssignmentOpt = functionCapabilityAssignmentRepository
				.findById(assignmentId);
		if (functionCapabilityAssignmentOpt.isPresent()) {
			functionCapabilityAssignmentRepository.delete(functionCapabilityAssignmentOpt.get());
			logger.info("Function Capability Assignment deleted with ID: " + assignmentId);
		} else {
			logger.info("No Function Capability Assignment found with ID: " + assignmentId);
		}
	}
}
