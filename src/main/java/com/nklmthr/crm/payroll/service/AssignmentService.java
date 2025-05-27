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
		Assignment assignment1 = assignmentRepository.findById(asg.getId()).get();
		Optional<EmployeeSalary> salOpt = assignment1.getEmployee().getEmployeeSalary().stream()
				.filter(s -> (s.getEndDate() == null)).findFirst();
		if (salOpt.isPresent()) {
			EmployeeSalary salary = salOpt.get();
			EmployeePayment payment = doRegulatoryDeductions(assignment1);
			payment.setEmployeeSalary(salary);
			payment.setAssignment(assignment1);
			payment.setEmployee(assignment1.getEmployee());
			assignment1.setEmployeePayment(payment);
			paymentRepository.save(payment);
			assignmentRepository.save(assignment1);
			return asg;
		}
		logger.info("No Employee Salary found for assignment: " + assignment.getId());
		return null;

	}

	@Transactional
	public Assignment updateAssignment(String assignmentId, Assignment assignment) {
		Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);
		if (assignmentOpt.isPresent()) {
			Assignment assignment1 = assignmentOpt.get();
			assignment1.update(assignment);
			EmployeePayment payment = doRegulatoryDeductions(assignment1);
			if (payment != null) {
				payment.setAssignment(assignment1);
				paymentRepository.save(payment);
				assignment1.setEmployeePayment(payment);
				assignmentRepository.save(assignment1);
				return assignment1;
			}

		}
		return null;
	}

	private EmployeePayment doRegulatoryDeductions(Assignment assignment) {
		Optional<EmployeeSalary> empSalary = assignment.getEmployee().getEmployeeSalary().stream()
				.filter(s -> (s.getEndDate() == null)).findFirst();
		if (empSalary.isPresent()) {
			EmployeeSalary salary = empSalary.get();
			EmployeePayment payment = new EmployeePayment();
			payment.setEmployeeSalary(salary);
			payment.setAssignment(assignment);
			payment.setEmployee(assignment.getEmployee());
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
		return null;
	}

	@Transactional
	public void deleteAssignment(String assignmentId) {
		Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);
		if (assignmentOpt.isPresent()) {
			Assignment assignment = assignmentOpt.get();
			assignment.setEmployeePayment(null);
			assignmentRepository.delete(assignment);
			logger.info("Assignment deleted with ID: " + assignmentId);
		} else {
			logger.info("No Assignment found with ID: " + assignmentId);
		}
	}
}
