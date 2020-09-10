package com.duicaishijiao.web.common;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * spring security User适配
 * @author K
 *
 */
public class UserAdapter extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4371818362552859942L;
	
	private com.duicaishijiao.base.entity.User user;
	
	public com.duicaishijiao.base.entity.User getUser() {
		return user;
	}
	
	public UserAdapter(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities , com.duicaishijiao.base.entity.User user) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
	}

}
