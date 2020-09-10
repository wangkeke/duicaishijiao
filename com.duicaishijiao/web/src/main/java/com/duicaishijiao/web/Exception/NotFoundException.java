package com.duicaishijiao.web.Exception;

/**
 * 资源未找到异常
 * @author k
 *
 */
public final class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3112603293157650840L;
	
	public NotFoundException() {}
	
	public NotFoundException(String message) {super(message);}
	
}
