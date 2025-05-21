package com.nklmthr.crm.payroll.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.nklmthr.crm.payroll.dao.AssignmentRepository;
import com.nklmthr.crm.payroll.dao.EmployeePaymentRepository;
import com.nklmthr.crm.payroll.dto.Assignment;
import com.nklmthr.crm.payroll.dto.EmployeePayment;
import com.nklmthr.crm.payroll.dto.EmployeeSalary;

import jakarta.transaction.Transactional;

@Service
public class AssignmentService {

	private static final Logger logger = Logger.getLogger(AssignmentService.class);
	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private EmployeePaymentRepository paymentRepository;

	public List<Assignment> getAssignments() {
		Order by = new Order(Sort.Direction.DESC, "date");
		Order by2 = new Order(Sort.Direction.ASC, "employee.lastName");
		Sort sort = Sort.by(by, by2);
		List<Assignment> assignments = assignmentRepository.findAll(sort);
		if (!assignments.isEmpty()) {
			logger.info("Operation Proficiency Assignments: " + assignments.size());
			return assignments;
		}
		logger.info("No Operation Proficiency Assignments found");
		return null;
	}

	public Assignment getAssignment(String assignmentId) {
		Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);
		if (assignmentOpt.isPresent()) {
			logger.info("Operation Proficiency Assignment found with ID: " + assignmentId);
			return assignmentOpt.get();
		}
		logger.info("No Operation Proficiency Assignment found with ID: " + assignmentId);
		return null;
	}

	@Transactional
	public Assignment saveAssignment(Assignment assignment) {
		Assignment asg = assignmentRepository.save(assignment);
		assignmentRepository.flush();
		Assignment assignment1 = assignmentRepository.findById(asg.getId()).get();
		EmployeeSalary salary = assignment1.getEmployee().getEmployeeSalary().stream().filter(s -> (s.getEndDate() == null))
				.findFirst().get();
		EmployeePayment payment = new EmployeePayment();
		doRegulatoryDeductions(assignment1, payment, salary);
		assignment1.setEmployeeSalary(salary);
		assignment1.setEmployeePayment(payment);
		payment.setAssignment(assignment1);
		paymentRepository.save(payment);
		assignmentRepository.save(assignment1);
		return asg;

	}

	@Transactional
	public Assignment updateAssignment(String assignmentId, Assignment assignment) {
		Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);
		if (assignmentOpt.isPresent()) {
			Assignment assignment1 = assignmentOpt.get();
			Optional<EmployeeSalary> empSalary = assignment1.getEmployee().getEmployeeSalary().stream()
					.filter(s -> (s.getEndDate() == null)).findFirst();
			if (empSalary.isPresent()) {
				EmployeeSalary salary = empSalary.get();
				assignment1.update(assignment);
				
				EmployeePayment payment = assignment1.getEmployeePayment();
				payment = doRegulatoryDeductions(assignment1, payment, salary);
				assignment1.setEmployeeSalary(salary);
				assignment1.setEmployeePayment(payment);
				payment.setAssignment(assignment1);
				paymentRepository.save(payment);
				assignmentRepository.save(assignment1);
				return assignment1;
			}
		}
		return null;
	}

	private EmployeePayment doRegulatoryDeductions(Assignment assignment, EmployeePayment payment,
			EmployeeSalary salary) {
		payment.setEmployee(salary.getEmployee());
		payment.setAssignment(assignment);
		assignment.setEmployeeSalary(salary);
		payment.setAmount(salary.getSalary().multiply(
				new BigDecimal(assignment.getActualCapabilityAcheivedInPercent()).divide(new BigDecimal(100))));
		payment.setPaymentDate(assignment.getDate());
		payment.setPfEmployee(payment.getAmount().multiply(new java.math.BigDecimal(0.12)));
		payment.setPfEmployer(payment.getAmount().multiply(new java.math.BigDecimal(0.12)));
		payment.setTax(payment.getAmount().multiply(new java.math.BigDecimal(0.1)));
		payment.setTotalPf(payment.getPfEmployee().add(payment.getPfEmployer()));
		payment.setNetSalary(payment.getAmount().subtract(payment.getTax()).subtract(payment.getTotalPf()));
		return payment;
	}

	@Transactional
	public void deleteAssignment(String assignmentId) {
		Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);
		if (assignmentOpt.isPresent()) {
			EmployeePayment payment = assignmentOpt.get().getEmployeePayment();
			paymentRepository.delete(payment);
			assignmentRepository.delete(assignmentOpt.get());
			logger.info("Operation Proficiency Assignment deleted with ID: " + assignmentId);
		} else {
			logger.info("No Operation Proficiency Assignment found with ID: " + assignmentId);
		}
	}
}
