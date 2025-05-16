package com.nklmthr.crm.payroll;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.nklmthr.crm.payroll.dao.UserRepository;
import com.nklmthr.crm.payroll.dto.AppUser;

@Component
public class MyUserDetailService implements UserDetailsService{
	private Logger logger = Logger.getLogger(MyUserDetailService.class);
	

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<AppUser> appUser = userRepository.findByUserName(username);
		if(appUser.isEmpty()) {
			logger.error("User not found: " + username);
			throw new UsernameNotFoundException("User not found");
		}
		logger.info("User found: " + appUser.get().getUsername());
		return org.springframework.security.core.userdetails.User.builder()
				.username(appUser.get().getUsername())
				.password(appUser.get().getPassword())
				.roles(appUser.get().getRoles().split(","))
				.build();
	}

}
