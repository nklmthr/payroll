package com.nklmthr.crm.payroll;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private Logger logger = Logger.getLogger(WebSecurityConfig.class);

	@Autowired
	private MyUserDetailService myUserDetailService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers("/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll();
			authorize.requestMatchers("/ui/login", "/ui/error/**", "/ui/logout", "/ui/home").permitAll();
			authorize.requestMatchers("/ui/employee/**").hasRole("ADMIN");
			authorize.requestMatchers("/ui/function/**").hasRole("USER");
			authorize.requestMatchers("/ui/assignment/**").hasRole("USER");
			authorize.requestMatchers("/ui/report/**").hasRole("USER");
			authorize.anyRequest().authenticated();
		}).formLogin(formLogin -> {
			formLogin.loginPage("/ui/login").defaultSuccessUrl("/ui/home", true).permitAll();
		}).logout(logout -> {
			logout.logoutUrl("/ui/logout").logoutSuccessUrl("/ui/home").invalidateHttpSession(true)
					.deleteCookies("JSESSIONID").permitAll();
		}).csrf(csrf -> {
			csrf.ignoringRequestMatchers("/h2-console/**");
		}).headers().frameOptions().sameOrigin();
		logger.info("Security filter chain configured");
		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(myUserDetailService);
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		logger.info("Authentication provider configured");
		return daoAuthenticationProvider;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
