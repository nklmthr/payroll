package com.nklmthr.crm.payroll.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nklmthr.crm.payroll.dto.FunctionCapabilityAssignment;


@Repository
public interface FunctionCapabilityAssignmentRepository extends JpaRepository<FunctionCapabilityAssignment, String> {
	@Query("SELECT fca FROM FunctionCapabilityAssignment fca WHERE fca.functionCapability.id = :functionCapabilityId")
	List<FunctionCapabilityAssignment> findAssignmentsByFunctionCapability(String functionCapabilityId);

	

}