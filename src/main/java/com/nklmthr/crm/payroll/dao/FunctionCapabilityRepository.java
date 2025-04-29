package com.nklmthr.crm.payroll.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nklmthr.crm.payroll.dto.Function;
import com.nklmthr.crm.payroll.dto.FunctionCapability;
import com.nklmthr.crm.payroll.dto.FunctionCapabilityAssignment;


@Repository
public interface FunctionCapabilityRepository extends JpaRepository<FunctionCapability, String> {

	
	
	@Query("SELECT f FROM FunctionCapability f WHERE f.reportingPillar.id = :id")
	List<Function> findByReportingPillar(String id);

	@Query("SELECT f FROM FunctionCapability f WHERE f.function.id = :id")
	List<FunctionCapability> findByFunction(String id);
}