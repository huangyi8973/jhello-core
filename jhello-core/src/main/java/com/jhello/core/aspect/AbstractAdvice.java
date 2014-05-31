package com.jhello.core.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAdvice {

	public abstract void before(Pointcut pointcut,HttpServletRequest req,HttpServletResponse resp) throws Exception;
	public abstract void after(Pointcut pointcut,HttpServletRequest req,HttpServletResponse resp) throws Exception;
}
