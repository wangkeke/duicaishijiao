package com.duicaishijiao.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.duicaishijiao.base.entity.Anonymity;
import com.duicaishijiao.base.entity.User;
import com.duicaishijiao.web.common.UserAdapter;
import com.duicaishijiao.web.service.UserService;

/**
 * 生成匿名认证信息和访问认证信息的静态接口
 * @author k
 *
 */
public class CurrentUser implements Filter{
	
	private static final ThreadLocal<String> user = new ThreadLocal<String>();
	
	private static final String COOKIENAME_ANONYMITY = "anonymity";
	
	private UserService userService;
	
	
	public CurrentUser(UserService userService){
		this.userService = userService;
	}
	
	
	public static String getAnonymity() {
		return user.get();
	}
	
	public static User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null) {
			if(authentication.getName().contains("anonymousUser")) {
				return null;
			}
			Object principal = authentication.getPrincipal();
			if(principal instanceof UserAdapter) {
				return ((UserAdapter)principal).getUser();
			}
			return null;
		}
		return null;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(getUser()!=null) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest req = (HttpServletRequest) request;
		 HttpServletResponse res = (HttpServletResponse) response;
		 Cookie anonymity = null;
		 if(req.getCookies()!=null) {			 
			 for (Cookie cookie : req.getCookies()) {
				 if(COOKIENAME_ANONYMITY.equals(cookie.getName())) {
					 anonymity = cookie;
					 break;
				 }
			 }
		 }
		 if(anonymity!=null) {
			 if(userService.countAnonymity(anonymity.getValue())==0) {
				 anonymity = null;
			 }
		 }
		 if(anonymity==null) {
			 anonymity = createAnonymity(req);
			 res.addCookie(anonymity);
			 Anonymity amy = new Anonymity();
			 amy.setDomain(req.getServerName());
			 amy.setCreateTime(new Date());
			 amy.setIp(req.getRemoteAddr());
			 amy.setAnonymity(anonymity.getValue());
			 amy.setDevice(req.getHeader("User-Agent"));
			 userService.saveAnonymity(amy.getAnonymity(), amy);
			 res.addCookie(anonymity);
		 }
		 user.set(anonymity.getValue());
		 chain.doFilter(request, response);
	}
	
	private Cookie createAnonymity(HttpServletRequest req) {
		String anonymity = req.getSession(true).getId();
		Cookie cookie = new Cookie(COOKIENAME_ANONYMITY, anonymity);
		cookie.setMaxAge(Integer.MAX_VALUE);
		cookie.setPath("/");
		cookie.setHttpOnly(false);
		return cookie;
	}
	
}
