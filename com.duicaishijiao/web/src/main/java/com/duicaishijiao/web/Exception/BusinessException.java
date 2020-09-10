package com.duicaishijiao.web.Exception;

/**
 * 业务操作异常
 * @author k
 *
 */
public final class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3112603293157650840L;
	
	public BusinessException() {}
	
	public BusinessException(String message) {super(message);}
	
}
