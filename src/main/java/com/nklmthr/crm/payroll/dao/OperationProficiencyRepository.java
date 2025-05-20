package com.nklmthr.crm.payroll.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nklmthr.crm.payroll.dto.OperationProficiency;


@Repository
public interface OperationProficiencyRepository extends JpaRepository<OperationProficiency, String> {

}