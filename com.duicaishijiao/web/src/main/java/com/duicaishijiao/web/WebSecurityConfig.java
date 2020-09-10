package com.duicaishijiao.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.duicaishijiao.web.filter.CurrentUser;
import com.duicaishijiao.web.service.UserService;

@EnableGlobalMethodSecurity(securedEnabled = true , prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.and()
		.anonymous()
		.and()
		.authorizeRequests()
//		.antMatchers("/css/**")
		.anyRequest().permitAll()
//		.anyRequest().fullyAuthenticated()
		.and()
		.formLogin()
//		.loginPage("/login").failureUrl("/login?error")
		.permitAll().and().logout().permitAll()
		.and()
		.logout().clearAuthentication(true)
		.logoutUrl("/logout")
		.invalidateHttpSession(true);
	}
	
	/**
	 * 注册用户认证信息过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<CurrentUser> currentUser(UserService userService){
		FilterRegistrationBean<CurrentUser> filter = new FilterRegistrationBean<CurrentUser>();
		filter.setFilter(new CurrentUser(userService));
		return filter;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
