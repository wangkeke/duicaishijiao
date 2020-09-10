package com.duicaishijiao.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.duicaishijiao.base.entity.Anonymity;
import com.duicaishijiao.base.entity.User;
import com.duicaishijiao.base.entity.UserType;
import com.duicaishijiao.base.repository.AnonymityRepository;
import com.duicaishijiao.base.repository.UserRepository;
import com.duicaishijiao.web.common.KeyConstants;
import com.duicaishijiao.web.common.UserAdapter;

@Service
@Transactional
public class UserService implements UserDetailsManager , UserDetailsPasswordService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AnonymityRepository anonymityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private UserCache userCache = new NullUserCache();
	
	private AuthenticationManager authenticationManager;
	
	
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findFirstByPhoneOrEmail(username, username);
		if(user==null)
			throw new UsernameNotFoundException("该账号不存在！");
	 	List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>(1);
	 	list.add(new SimpleGrantedAuthority(user.getType().name()));
	 	UserAdapter userAdapter = new UserAdapter(username, user.getPassword(), user.isEnable(), 
				true, true, true, list,user);
	 	return userAdapter;
	}
	
	@Override
	public void createUser(UserDetails userDetails) {
		Assert.hasText(userDetails.getUsername(), "用户名不能为空！");
		User user = new User();
		Date time = new Date();
		user.setUpdateTime(time);
		user.setCreateTime(time);
		user.setEnable(userDetails.isEnabled());
		if(userDetails.getUsername().indexOf("@")>-1) {
			user.setEmail(userDetails.getUsername());
		}else {
			user.setPhone(userDetails.getUsername());
		}
		user.setLastLogin(time);
		user.setPassword(userDetails.getPassword());
		user.setType(UserType.USER);
		repository.save(user);
	}

	@Override
	public void updateUser(UserDetails userDetails) {
		Assert.hasText(userDetails.getUsername(), "用户名不能为空！");
		repository.updateByPhoneOrEmail(userDetails.getPassword(), userDetails.isEnabled(), userDetails.getUsername(), userDetails.getUsername());
		userCache.removeUserFromCache(userDetails.getUsername());
	}

	@Override
	public void deleteUser(String username) {
		repository.disableByPhoneOrEmail(null, username, username);
		userCache.removeUserFromCache(username);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext()
				.getAuthentication();

		if (currentUser == null) {
			// This would indicate bad coding somewhere
			throw new AccessDeniedException(
					"用户不存在，不能进行修改密码操作！");
		}

		String username = currentUser.getName();
		if (authenticationManager != null) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					username, oldPassword));
		}
		repository.updatePassword(newPassword, username, username);
		SecurityContextHolder.getContext().setAuthentication(
				createNewAuthentication(currentUser, newPassword));

		userCache.removeUserFromCache(username);
	}
	
	protected Authentication createNewAuthentication(Authentication currentAuth,
			String newPassword) {
		UserDetails user = loadUserByUsername(currentAuth.getName());

		UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
				user, null, user.getAuthorities());
		newAuthentication.setDetails(currentAuth.getDetails());

		return newAuthentication;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean userExists(String username) {
		Assert.hasText(username, "用户名不能为空！");
		return repository.countByPhoneOrEmail(username, username)>0?true:false;
	}

	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		repository.updatePassword(newPassword, user.getUsername(), user.getUsername());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), newPassword, user.isEnabled(), 
				user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
	}
	
	@CacheEvict(value = KeyConstants.USER_ANONYMITY_COUNT,key = "#p0" , condition = "#p0!=null")
	public void saveAnonymity(String anonymityUser, Anonymity anonymity) {
		anonymityRepository.save(anonymity);
	}
	
	@Cacheable(value = KeyConstants.USER_ANONYMITY_COUNT,key = "#p0" , condition = "#p0!=null")
	@Transactional(readOnly = true)
	public int countAnonymity(String anonymity) {
		return anonymityRepository.countByAnonymity(anonymity);
	}
	
}
