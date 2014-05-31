package com.hy.core.handle;

import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 错误处理器
 * @author Huangyi
 *
 */
public class ExceptionHandler extends Handler {

	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	
	public ExceptionHandler(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	@Override
	public void handle() throws Exception {
		try{
			this.getNextHandle().handle();
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
			//写到页面上
			if(e.getCause() != null){
				this.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getCause().toString());
			}else{
				this.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.toString());
			}
		}
	}

}
