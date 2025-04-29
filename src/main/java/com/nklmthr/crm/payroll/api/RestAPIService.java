package com.nklmthr.crm.payroll.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nklmthr.crm.payroll.dao.EmployeeRepository;
import com.nklmthr.crm.payroll.dao.FunctionCapabilityAssignmentRepository;
import com.nklmthr.crm.payroll.dao.FunctionCapabilityRepository;
import com.nklmthr.crm.payroll.dao.FunctionRepository;
import com.nklmthr.crm.payroll.dao.ReportingPillarRepository;
import com.nklmthr.crm.payroll.dto.Employee;
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
	public ResponseEntity<ResultEntity> updateEmployee(@RequestBody Employee employee) {
		ResultEntity resultEntity = new ResultEntity();
		Optional<Employee> emp = employeeRepository.findById(employee.getId());
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

	@GetMapping("/function/{id}")
	public ResponseEntity<ResultEntity> getFunctionById(@PathVariable(name = "id") String id) {
		Optional<Function> function = functionRepository.findById(id);
		ResultEntity resultEntity = new ResultEntity();

		if (function.isPresent()) {
			List<ResultDTO> functionList = new ArrayList<>(1);
			functionList.add(function.get());
			resultEntity.setResult(functionList);
			resultEntity.setCount((long) functionList.size());
			return ResponseEntity.ok(resultEntity);
		}
		resultEntity.setCount(0L);
		resultEntity.setErrors(List.of("Function not found for id:" + id));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultEntity);
	}

	@PostMapping("/function")
	public ResponseEntity<ResultEntity> saveFunction(@RequestBody List<Function> functionList) {
		ResultEntity resultEntity = new ResultEntity();
		functionRepository.saveAll(functionList);
		List<ResultDTO> resultList = new ArrayList<>(functionList.size());
		resultEntity.setCount((long) functionList.size());
		resultList.addAll(functionList);
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

	@GetMapping("/function/{functionId}/capability")
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

	@PostMapping("/function/{id}/capability")
	public ResponseEntity<FunctionCapability> saveFunctionCapability(@PathVariable(name = "id") String id,
			@RequestBody FunctionCapability functionCapability) {
		Optional<Function> function = functionRepository.findById(id);
		if (function.isPresent()) {
			functionCapability.setFunction(function.get());
			return ResponseEntity.ok(functionCapabilityRepository.save(functionCapability));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/function/{id}/capability")
	public ResponseEntity<FunctionCapability> updateFunctionCapability(@PathVariable(name = "id") String id,
			@RequestBody FunctionCapability functionCapability) {
		Optional<Function> function = functionRepository.findById(id);
		if (function.isPresent()) {
			Optional<FunctionCapability> funcCapability = functionCapabilityRepository
					.findById(functionCapability.getId());
			if (funcCapability.isPresent()) {
				FunctionCapability funcCapability1 = funcCapability.get();
				funcCapability1.update(functionCapability);
				functionCapabilityRepository.save(funcCapability1);
				return ResponseEntity.ok(funcCapability1);
			} else {
				// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FunctionCapability
				// not found for Function:"+id+", FunctionCapability id provided
				// is:"+functionCapability.getId());
			}
		}
		// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Function not found
		// for id:"+id);
		return null;
	}

	@GetMapping("/function/{id}/capability/{capId}")
	public ResponseEntity<FunctionCapability> getFunctionCapabilityById(@PathVariable(name = "id") String id,
			@PathVariable(name = "capId") String capId) {
		Optional<FunctionCapability> functionCapability = functionCapabilityRepository.findById(capId);
		if (functionCapability.isPresent()) {
			return ResponseEntity.ok(functionCapability.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/function/{id}/capability/{capId}/employee")
	public ResponseEntity<List<FunctionCapabilityAssignment>> getFunctionCapabilityAssignmentsByEmployee(
			@PathVariable(name = "id") String id, @PathVariable(name = "capId") String capId) {
		Optional<FunctionCapability> functionCapability = functionCapabilityRepository.findById(capId);
		if (functionCapability.isPresent()) {
			List<FunctionCapabilityAssignment> functionCapabilityAssignments = functionCapabilityAssignmentRepository
					.findAssignmentsByFunctionCapability(capId);
			return ResponseEntity.ok(functionCapabilityAssignments);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/function/{id}/capability/{capId}/employee")
	public ResponseEntity<FunctionCapabilityAssignment> saveFunctionCapabilityAssignment(
			@PathVariable(name = "id") String id, @PathVariable(name = "capId") String capId,
			@RequestBody FunctionCapabilityAssignment functionCapabilityAssignment) {
		Optional<FunctionCapability> functionCapability = functionCapabilityRepository.findById(capId);
		if (functionCapability.isPresent()) {
			functionCapabilityAssignment.setFunctionCapability(functionCapability.get());
			return ResponseEntity.ok(functionCapabilityAssignmentRepository.save(functionCapabilityAssignment));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/function/{id}/capability/{capId}/employee")
	public ResponseEntity<FunctionCapabilityAssignment> updateFunctionCapabilityAssignment(
			@PathVariable(name = "id") String id, @PathVariable(name = "capId") String capId,
			@RequestBody FunctionCapabilityAssignment functionCapabilityAssignment) {
		Optional<FunctionCapabilityAssignment> func = functionCapabilityAssignmentRepository
				.findById(functionCapabilityAssignment.getId());
		if (func.isPresent()) {
			FunctionCapabilityAssignment func1 = func.get();
			func1.update(functionCapabilityAssignment);
			return ResponseEntity.ok(functionCapabilityAssignmentRepository.save(func1));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/function/{id}/capability/{capId}/employee/{empId}")
	public ResponseEntity<FunctionCapabilityAssignment> getFunctionCapabilityAssignmentById(
			@PathVariable(name = "id") String id, @PathVariable(name = "capId") String capId,
			@PathVariable(name = "empId") String empId) {
		Optional<FunctionCapabilityAssignment> functionCapabilityAssignment = functionCapabilityAssignmentRepository
				.findById(empId);
		if (functionCapabilityAssignment.isPresent()) {
			return ResponseEntity.ok(functionCapabilityAssignment.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/reportingpillar")
	public ResponseEntity<List<ReportingPillar>> getReportingPillars() {
		return ResponseEntity.ok(reportingPillarRepository.findAll());
	}

	@GetMapping("/reportingpillar/{id}")
	public ResponseEntity<ReportingPillar> getReportingPillarById(@PathVariable(name = "id") String id) {
		Optional<ReportingPillar> reportingPillar = reportingPillarRepository.findById(id);
		if (reportingPillar.isPresent()) {
			return ResponseEntity.ok(reportingPillar.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/reportingpillar")
	public ResponseEntity<ReportingPillar> saveReportingPillar(@RequestBody ReportingPillar reportingPillar) {
		return ResponseEntity.ok(reportingPillarRepository.save(reportingPillar));
	}

	@PutMapping("/reportingpillar")
	public ResponseEntity<ReportingPillar> updateReportingPillar(@RequestBody ReportingPillar reportingPillar) {
		Optional<ReportingPillar> rep = reportingPillarRepository.findById(reportingPillar.getId());
		if (rep.isPresent()) {
			ReportingPillar rep1 = rep.get();
			rep1.update(reportingPillar);
			return ResponseEntity.ok(reportingPillarRepository.save(rep1));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/reportingpillar/{id}/function-capability")
	public ResponseEntity<List<Function>> getFunctionsCapabilityByReportingPillar(
			@PathVariable(name = "id") String id) {
		Optional<ReportingPillar> reportingPillar = reportingPillarRepository.findById(id);
		if (reportingPillar.isPresent()) {
			List<Function> functions = functionCapabilityRepository.findByReportingPillar(id);
			return ResponseEntity.ok(functions);
		}
		return ResponseEntity.notFound().build();
	}

}
