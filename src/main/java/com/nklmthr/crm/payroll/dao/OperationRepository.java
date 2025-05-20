package com.nklmthr.crm.payroll.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nklmthr.crm.payroll.dto.Operation;


@Repository
public interface OperationRepository extends JpaRepository<Operation, String> {

	
	
}