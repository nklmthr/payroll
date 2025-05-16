package com.nklmthr.crm.payroll.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nklmthr.crm.payroll.dto.AppUser;

public interface UserRepository extends JpaRepository<AppUser, String>  {

	@Query("SELECT u FROM AppUser u WHERE u.username = :username")
	Optional<AppUser> findByUserName(String username);

}
