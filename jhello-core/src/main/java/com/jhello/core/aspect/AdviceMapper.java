package com.jhello.core.aspect;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhello.core.annotations.aspect.Aspect;
import com.jhello.core.config.JHelloConfig;

public class AdviceMapper {

	private final static Logger logger = LoggerFactory.getLogger(AdviceMapper.class);
	private static AdviceMapper _instance;
	private static Object _lock = new Object();
	/**
	 * map<joinPointPartten,类名>
	 */
	private Map<String,String> _adviceMapper = new HashMap<String, String>();
	
	public Map<String,String> getAdviceParttenAndInfoMap(){
		return this._adviceMapper;
	}
	/**
	 * controller包扫描路径
	 */
	private String _scanPackagePath = JHelloConfig.getInstance().getAspectScanPackage();
	
	
	public String getScanPackagePath() {
		return _scanPackagePath;
	}

	private AdviceMapper(){}
	
	public static AdviceMapper getInstance(){
		if(_instance == null){
			synchronized (_lock) {
				if(_instance == null){
					_instance = new AdviceMapper();
				}
			}
		}
		return _instance;
	}
	
	public void init() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException{
		logger.debug("aspect mapper init");
		long start = System.currentTimeMillis();
		String packagePath = getScanPackagePath().replace('.', File.separatorChar);
		if(packagePath != null){
			String[] packagePathAry = packagePath.split(",");
			for(String path : packagePathAry){
				Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(path);
				while(urls.hasMoreElements()){
					URL url = urls.nextElement();
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					addClassToMapper(filePath,path);
				}
			}
		logger.debug(String.format("aspect mapper end, spend:%dms",+System.currentTimeMillis() - start));
		}
	}
	
	private void addClassToMapper(String filePath, String packagePath) throws ClassNotFoundException, NoSuchMethodException, SecurityException{
		File dir = new File(filePath);
		if(!dir.exists() && !dir.isDirectory()){
			return;
		}
		//扫描java.class文件和文件夹
		File[] files = dir.listFiles(new FileFilter() {
			
			public boolean accept(File pathname) {
				return pathname.isDirectory() || (pathname.isFile() && pathname.getName().endsWith(".class"));
			}
		});
		
		if(files != null && files.length > 0){
			for(File file : files){
				if(file.isDirectory()){
					//递归查找所有目录
					addClassToMapper(file.getAbsolutePath(),packagePath);
				}else{
					Mapping(file,packagePath);
				}
			}
		}
	}

	/**
	 * 映射
	 * @param file
	 * @param packagePath 
	 * @throws ClassNotFoundException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private void Mapping(File file, String packagePath) throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		String rootPath =packagePath.replace('.', File.separatorChar);
		//获取类
		String className = file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(rootPath)).replace(File.separatorChar, '.');
		//去掉.class
		className = className.substring(0,className.length() - 6);
		Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(className);
		//获取注解
		Aspect aspect = (Aspect) cls.getAnnotation(Aspect.class);
		if(aspect != null){
			// 获取通知
			this._adviceMapper.put(aspect.joincutExpression(), className);
		}
	}
}
