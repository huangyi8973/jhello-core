package com.jhello.core.handle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhello.core.pub.HttpConst;

/**
 * 资源文件处理器
 * @author Huangyi
 *
 */
public class ResourceHandler extends Handler {

	private final static Logger logger = LoggerFactory.getLogger(ResourceHandler.class);
	
	public ResourceHandler(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	@Override
	public void handle() throws Exception {
		logger.debug("============ResourceHandle");
		//检查文件是否存在
		File file = getResource();
		if(file != null){
			//获得文件的mineType
			String mineType = this.getRequest().getServletContext().getMimeType(file.getName());
			setHander(file,mineType);
			//判断浏览器中缓存是否过期
			if(checkIsModify(file)){
				writeContent(file);
			}
		}else{
			this.getResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	/**
	 * 判断浏览器中缓存是否过期
	 * @param file
	 * @return
	 */
	private boolean checkIsModify(File file) {
		long fileLastModify = file.lastModified();
		long ifModifiedSince = this.getRequest().getDateHeader(HttpConst.HEADER_IF_MODIFIED_SINCE);
		if(!this.getResponse().containsHeader(HttpConst.HEADER_LAST_MODIFIED)){
			//这里对文件最后修改时间进行计算是为了把时间弄到秒，太小的单位没意义
			if(ifModifiedSince >= (fileLastModify / 1000 * 1000)){
				this.getResponse().setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				logger.debug("resouse not modify ,response 304");
			}else{
				this.getResponse().setDateHeader(HttpConst.HEADER_LAST_MODIFIED, fileLastModify);
				return true;
			}
		}
		return false;
	}

	private void writeContent(File file) throws IOException {
		InputStream in = new FileInputStream(file);
		OutputStream out = this.getResponse().getOutputStream();
		byte[] buffer = new byte[4096];
		int byteRead = -1;
		while((byteRead = in.read(buffer)) != -1){
			out.write(buffer, 0, byteRead);
		}
		out.flush();
	}

	/**
	 * 设置文件长度和类型
	 * @param file
	 * @param mineType
	 * @throws IOException
	 */
	private void setHander(File file, String mineType) throws IOException {
		long length = file.length();
		if(length > Integer.MAX_VALUE){
			throw new IOException("file is too large(more than Integer.MAX_VALUE");
		}
		this.getResponse().setContentLength((int)length);
		this.getResponse().setContentType(mineType);
	}

	/**
	 * 获取资源文件
	 * @return
	 */
	private File getResource() {
		String path = this.getRequest().getServletContext().getRealPath("/");
		//去掉context的url，连开头的"\"都去掉
		String url = this.getRequest().getRequestURI().substring(this.getRequest().getContextPath().length()+1);
		url = url.replace("/", File.separator);
		logger.debug("resource path:"+path);
		logger.debug("resource request url:"+url);
		File file = new File(path + url);
		if(file != null && file.exists() && file.isFile() && file.canRead()){
			return file;
		}
		logger.debug(String.format("file not found!  --%s", url));
		return null;
	}

}
