package com.swarm.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.swarm.base.dao.SysUserDao;

@Configuration
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/favicon.ico","/login/captcha","/actuator","/actuator/**","/html/**").permitAll()
				.anyRequest().fullyAuthenticated()
//			.and()
			.and()
			.exceptionHandling()
				.accessDeniedPage("/login/unauthorized")
			.and()
			.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/login/unauthorized")
				.failureUrl("/login/failure")
				.defaultSuccessUrl("/login/success")
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/login/logout")
				.logoutSuccessUrl("/login/logout")
				.invalidateHttpSession(true)
//			.and()
//			.httpBasic()
				;
	}
	
	@Bean
	public UserDetailsService customUserDetailsService(SysUserDao sysUserDao) {
		return new CustomUserDetailsService(sysUserDao);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
