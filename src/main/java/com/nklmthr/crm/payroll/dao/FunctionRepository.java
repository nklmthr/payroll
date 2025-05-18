package com.nklmthr.crm.payroll.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nklmthr.crm.payroll.dto.Function;


@Repository
public interface FunctionRepository extends JpaRepository<Function, String> {

	
	
}