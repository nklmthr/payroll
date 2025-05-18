package com.nklmthr.crm.payroll.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nklmthr.crm.payroll.dao.EmployeeRepository;
import com.nklmthr.crm.payroll.dao.EmployeeSalaryRepository;
import com.nklmthr.crm.payroll.dao.FunctionCapabilityAssignmentRepository;
import com.nklmthr.crm.payroll.dao.FunctionCapabilityRepository;
import com.nklmthr.crm.payroll.dao.FunctionRepository;
import com.nklmthr.crm.payroll.dao.ReportingPillarRepository;
import com.nklmthr.crm.payroll.dto.Employee;
import com.nklmthr.crm.payroll.dto.EmployeeSalary;
import com.nklmthr.crm.payroll.dto.Function;
import com.nklmthr.crm.payroll.dto.FunctionCapability;
import com.nklmthr.crm.payroll.dto.FunctionCapabilityAssignment;
import com.nklmthr.crm.payroll.dto.ReportingPillar;
import com.nklmthr.crm.payroll.dto.ResultDTO;
import com.nklmthr.crm.payroll.dto.ResultEntity;

@RestController
@RequestMapping("/v1/payroll")
public class RestAPIService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private FunctionRepository functionRepository;

	@Autowired
	private FunctionCapabilityAssignmentRepository functionCapabilityAssignmentRepository;

	@Autowired
	private ReportingPillarRepository reportingPillarRepository;

	@Autowired
	private FunctionCapabilityRepository functionCapabilityRepository;
	
	@Autowired
	private EmployeeSalaryRepository employeeSalaryRepository;

	@GetMapping("/employee")
	public ResponseEntity<ResultEntity> getEmployees() {
		ResultEntity resultEntity = new ResultEntity();
		List<Employee> employees = employeeRepository.findAll();
		resultEntity.setCount((long) employees.size());
		if (employees.isEmpty()) {
			resultEntity.setErrors(List.of("No employees found"));
		} else {
			List<ResultDTO> employeeList = new ArrayList<>(employees.size());
			employeeList.addAll(employees);
			resultEntity.setResult(employeeList);
		}
		return ResponseEntity.ok(resultEntity);
	}

	@DeleteMapping("/employee/{employeeId}")
	public ResponseEntity<ResultEntity> deleteEmployee(String employeeId) {
		employeeRepository.deleteById(employeeId);
		return getEmployees();

	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<ResultEntity> getEmployeesById(@PathVariable(name = "id") String id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		ResultEntity resultEntity = new ResultEntity();
		if (employee.isPresent()) {
			List<ResultDTO> employeeList = new ArrayList<>(1);
			employeeList.add(employee.get());
			resultEntity.setResult(employeeList);
			resultEntity.setCount((long) employeeList.size());
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Employee not found for id:" + id));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@PutMapping("/employee")
	public ResponseEntity<ResultEntity> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<Employee> emp = employeeRepository.findById(id);
		if (emp.isPresent()) {
			Employee emp1 = emp.get();
			emp1.update(employee);
			employeeRepository.save(emp1);
			List<ResultDTO> employeeList = new ArrayList<>(1);
			employeeList.add(emp1);
			resultEntity.setCount((long) employeeList.size());
			resultEntity.setResult(employeeList);
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Employee not found for id:" + employee.getId()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@PostMapping("/employee")
	public ResponseEntity<ResultEntity> saveEmployee(@RequestBody List<Employee> employeeList) {
		ResultEntity resultEntity = new ResultEntity();
		employeeRepository.saveAll(employeeList);
		List<ResultDTO> resultList = new ArrayList<>(employeeList.size());
		resultEntity.setCount((long) employeeList.size());
		resultList.addAll(employeeList);
		resultEntity.setResult(resultList);
		return ResponseEntity.ok(resultEntity);

	}
	
	@PostMapping("/employee/{employeeId}/salary")
	public HttpEntity<ResultEntity> getEmployeeSalaries(String employeeId) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			List<ResultDTO> employeeSalaryList = new ArrayList<>(1);
			employeeSalaryList.addAll(employee.get().getEmployeeSalary());
			resultEntity.setCount((long) employeeSalaryList.size());
			resultEntity.setResult(employeeSalaryList);
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Employee not found for id:" + employeeId));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@GetMapping("/function")
	public ResponseEntity<ResultEntity> getFunctions() {
		ResultEntity resultEntity = new ResultEntity();
		List<Function> functions = functionRepository.findAll();
		resultEntity.setCount((long) functions.size());
		if (functions.isEmpty()) {
			resultEntity.setErrors(List.of("No functions found"));
		} else {
			List<ResultDTO> functionList = new ArrayList<>(functions.size());
			functionList.addAll(functions);
			resultEntity.setResult(functionList);
		}
		return ResponseEntity.ok(resultEntity);
	}

	@GetMapping("/function/{functionId}")
	public ResponseEntity<ResultEntity> getFunctionById(@PathVariable(name = "functionId") String functionId) {
		Optional<Function> function = functionRepository.findById(functionId);
		ResultEntity resultEntity = new ResultEntity();

		if (function.isPresent()) {
			List<ResultDTO> functionList = new ArrayList<>(1);
			functionList.add(function.get());
			resultEntity.setResult(functionList);
			resultEntity.setCount((long) functionList.size());
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Function not found for id:" + functionId));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@PostMapping("/function")
	public ResponseEntity<ResultEntity> saveFunction(@RequestBody List<Function> functionList) {
		ResultEntity resultEntity = new ResultEntity();
		List<Function> result = functionRepository.saveAll(functionList);
		List<ResultDTO> resultList = new ArrayList<>(result.size());
		resultEntity.setCount((long) result.size());
		resultList.addAll(result);
		resultEntity.setResult(resultList);
		return ResponseEntity.ok(resultEntity);

	}

	@PutMapping("/function")
	public ResponseEntity<ResultEntity> updateFunction(@RequestBody Function function) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<Function> func = functionRepository.findById(function.getId());
		if (func.isPresent()) {
			Function func1 = func.get();
			func1.update(function);
			List<ResultDTO> functionList = new ArrayList<>(1);
			functionList.add(func1);
			resultEntity.setResult(functionList);
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Function not found for id:" + function.getId()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@GetMapping("/function/{functionId}/function-capability")
	public ResponseEntity<ResultEntity> getFunctionCapabilities(@PathVariable(name = "functionId") String functionId) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<Function> function = functionRepository.findById(functionId);
		if (function.isPresent()) {
			List<FunctionCapability> functionCapabilities = function.get().getFunctionCapabilities();
			resultEntity.setCount((long) functionCapabilities.size());
			if (functionCapabilities.isEmpty()) {
				resultEntity.setErrors(List.of("No Function Capabilities found for Function:" + functionId));
			} else {
				List<ResultDTO> functionCapabilityList = new ArrayList<>(functionCapabilities.size());
				functionCapabilityList.addAll(functionCapabilities);
				resultEntity.setResult(functionCapabilityList);
			}
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Function not found for id:" + functionId));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@PostMapping("/function/{functionId}/function-capability")
	public ResponseEntity<ResultEntity> saveFunctionCapability(@PathVariable(name = "functionId") String functionId,
			@RequestBody FunctionCapability functionCapability) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<Function> function = functionRepository.findById(functionId);
		if (function.isPresent()) {
			functionCapability.setFunction(function.get());
			functionCapabilityRepository.save(functionCapability);
			List<ResultDTO> functionCapabilityList = new ArrayList<>(1);
			functionCapabilityList.add(functionCapability);
			resultEntity.setCount((long) functionCapabilityList.size());
			resultEntity.setResult(functionCapabilityList);
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Function not found for id:" + functionId));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);

	}

	@PutMapping("/function/{functionId}/function-capability")
	public ResponseEntity<ResultEntity> updateFunctionCapability(@PathVariable(name = "functionId") String functionId,
			@RequestBody FunctionCapability functionCapability) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<Function> function = functionRepository.findById(functionId);
		if (function.isPresent()) {
			Optional<FunctionCapability> funcCapability = functionCapabilityRepository
					.findById(functionCapability.getId());
			if (funcCapability.isPresent()) {
				FunctionCapability funcCapability1 = funcCapability.get();
				funcCapability1.update(functionCapability);
				functionCapabilityRepository.save(funcCapability1);
				List<ResultDTO> functionCapabilityList = new ArrayList<>(1);
				functionCapabilityList.add(funcCapability1);
				resultEntity.setCount((long) functionCapabilityList.size());
				resultEntity.setResult(functionCapabilityList);
				return ResponseEntity.ok(resultEntity);
			} else {
				resultEntity.setCount(0L);
				resultEntity.setErrors(List.of("FunctionCapability not found for Function:" + functionId
						+ ", FunctionCapability id provided is:" + functionCapability.getId()));
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
			}
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Function not found for id:" + functionId));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@GetMapping("/function/{functionId}/function-capability/{capId}")
	public ResponseEntity<ResultEntity> getFunctionCapabilityById(@PathVariable(name = "functionId") String functionId,
			@PathVariable(name = "capId") String capId) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<Function> function = functionRepository.findById(functionId);
		if (function.isPresent()) {
			Optional<FunctionCapability> functionCapability = functionCapabilityRepository.findById(capId);
			if (functionCapability.isPresent()) {

				List<ResultDTO> functionCapabilityList = new ArrayList<>(1);
				functionCapabilityList.add(functionCapability.get());
				resultEntity.setCount((long) functionCapabilityList.size());
				resultEntity.setResult(functionCapabilityList);
				return ResponseEntity.ok(resultEntity);
			} else {
				resultEntity.setCount(0L);
				resultEntity.setErrors(List.of("Function Capability not found for capId:" + capId));
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
			}

		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Function Capability not found for functionId:" + functionId));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@GetMapping("/function-capability-assignments")
	public ResponseEntity<ResultEntity> getFunctionCapabilityAssignments() {
		ResultEntity resultEntity = new ResultEntity();
		List<FunctionCapabilityAssignment> functionCapabilityAssignments = functionCapabilityAssignmentRepository
				.findAll();
		resultEntity.setCount((long) functionCapabilityAssignments.size());
		if (!functionCapabilityAssignments.isEmpty()) {
			List<ResultDTO> functionCapabilityAssignmentList = new ArrayList<>(functionCapabilityAssignments.size());
			functionCapabilityAssignmentList.addAll(functionCapabilityAssignments);
			resultEntity.setResult(functionCapabilityAssignmentList);
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("No Function Capability Assignments found"));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@GetMapping("/function-capabilities")
	public HttpEntity<ResultEntity> getFunctionCapabilities() {
		ResultEntity resultEntity = new ResultEntity();
		List<FunctionCapability> functionCapabilities = functionCapabilityRepository.findAll();
		resultEntity.setCount((long) functionCapabilities.size());
		if (!functionCapabilities.isEmpty()) {
			List<ResultDTO> functionCapabilityList = new ArrayList<>(functionCapabilities.size());
			functionCapabilityList.addAll(functionCapabilities);
			resultEntity.setResult(functionCapabilityList);
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Function Capability Assignment not found "));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@PostMapping("/function-capability-assignments")
	public ResponseEntity<ResultEntity> saveFunctionCapabilityAssignment(
			@RequestBody FunctionCapabilityAssignment functionCapabilityAssignment) {
		ResultEntity resultEntity = new ResultEntity();
		functionCapabilityAssignmentRepository.save(functionCapabilityAssignment);
		List<ResultDTO> functionCapabilityAssignmentList = new ArrayList<>(1);
		functionCapabilityAssignmentList.add(functionCapabilityAssignment);
		resultEntity.setCount((long) functionCapabilityAssignmentList.size());
		resultEntity.setResult(functionCapabilityAssignmentList);
		return ResponseEntity.ok(resultEntity);

	}

	@PutMapping("/function-capability-assignments")
	public ResponseEntity<ResultEntity> updateFunctionCapabilityAssignment(
			@RequestBody FunctionCapabilityAssignment functionCapabilityAssignment) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<FunctionCapabilityAssignment> functionCapabilityAssignmentOpt = functionCapabilityAssignmentRepository
				.findById(functionCapabilityAssignment.getId());
		if (functionCapabilityAssignmentOpt.isPresent()) {

			FunctionCapabilityAssignment functionCapabilityAssignment1 = functionCapabilityAssignmentOpt.get();
			functionCapabilityAssignment1.update(functionCapabilityAssignment);
			functionCapabilityAssignmentRepository.save(functionCapabilityAssignment1);
			List<ResultDTO> functionCapabilityAssignmentList = new ArrayList<>(1);
			functionCapabilityAssignmentList.add(functionCapabilityAssignment1);
			resultEntity.setCount((long) functionCapabilityAssignmentList.size());
			resultEntity.setResult(functionCapabilityAssignmentList);
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(
				List.of("Function Capability Assignment not found for id:" + functionCapabilityAssignment.getId()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);

	}

	@GetMapping("/reportingpillar")
	public ResponseEntity<ResultEntity> getReportingPillars() {
		ResultEntity resultEntity = new ResultEntity();
		List<ReportingPillar> reportingPillars = reportingPillarRepository.findAll();
		resultEntity.setCount((long) reportingPillars.size());
		if (reportingPillars.isEmpty()) {
			resultEntity.setErrors(List.of("No Reporting Pillars found"));
		} else {
			List<ResultDTO> reportingPillarList = new ArrayList<>(reportingPillars.size());
			reportingPillarList.addAll(reportingPillars);
			resultEntity.setResult(reportingPillarList);
		}
		return ResponseEntity.ok(resultEntity);
	}

	@GetMapping("/reportingpillar/{id}")
	public ResponseEntity<ResultEntity> getReportingPillarById(@PathVariable(name = "id") String id) {
		Optional<ReportingPillar> reportingPillar = reportingPillarRepository.findById(id);
		ResultEntity resultEntity = new ResultEntity();
		if (reportingPillar.isPresent()) {
			List<ResultDTO> reportingPillarList = new ArrayList<>(1);
			reportingPillarList.add(reportingPillar.get());
			resultEntity.setResult(reportingPillarList);
			resultEntity.setCount((long) reportingPillarList.size());
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Reporting Pillar not found for id:" + id));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@PostMapping("/reportingpillar")
	public ResponseEntity<ResultEntity> saveReportingPillar(@RequestBody ReportingPillar reportingPillar) {
		ResultEntity resultEntity = new ResultEntity();
		reportingPillarRepository.save(reportingPillar);
		List<ResultDTO> reportingPillarList = new ArrayList<>(1);
		reportingPillarList.add(reportingPillar);
		resultEntity.setCount((long) reportingPillarList.size());
		resultEntity.setResult(reportingPillarList);
		return ResponseEntity.ok(resultEntity);

	}

	@PutMapping("/reportingpillar")
	public ResponseEntity<ResultEntity> updateReportingPillar(@RequestBody ReportingPillar reportingPillar) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<ReportingPillar> reportingPillarOpt = reportingPillarRepository.findById(reportingPillar.getId());
		if (reportingPillarOpt.isPresent()) {
			ReportingPillar reportingPillar1 = reportingPillarOpt.get();
			reportingPillar1.update(reportingPillar);
			reportingPillarRepository.save(reportingPillar1);
			List<ResultDTO> reportingPillarList = new ArrayList<>(1);
			reportingPillarList.add(reportingPillar1);
			resultEntity.setCount((long) reportingPillarList.size());
			resultEntity.setResult(reportingPillarList);
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Reporting Pillar not found for id:" + reportingPillar.getId()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@GetMapping("/reportingpillar/{reportingPillarId}/function-capability")
	public ResponseEntity<ResultEntity> getFunctionsCapabilityByReportingPillar(
			@PathVariable(name = "reportingPillarId") String reportingPillarId) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<ReportingPillar> reportingPillar = reportingPillarRepository.findById(reportingPillarId);
		if (reportingPillar.isPresent()) {
			List<FunctionCapability> functionCapabilities = reportingPillar.get().getFunctionCapabilityList();
			resultEntity.setCount((long) functionCapabilities.size());
			if (functionCapabilities.isEmpty()) {
				resultEntity
						.setErrors(List.of("No Function Capabilities found for Reporting Pillar:" + reportingPillarId));
			} else {
				List<ResultDTO> functionCapabilityList = new ArrayList<>(functionCapabilities.size());
				functionCapabilityList.addAll(functionCapabilities);
				resultEntity.setResult(functionCapabilityList);
			}
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Reporting Pillar not found for id:" + reportingPillarId));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);

	}

	public void saveEmployeeSalary(String employeeId, EmployeeSalary employeeSalary) {
		// TODO Auto-generated method stub
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			employeeSalary.setEmployee(employee.get());
			employeeSalaryRepository.save(employeeSalary);
		}
		
	}

	

}
