package com.jhello.core.controller.cmd;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.jhello.core.action.Params;

public class CmdFactory {

	private static Map<String,Class<? extends IControllerCmd>> cmdMap;
	static{
		cmdMap = new HashMap<String, Class<? extends IControllerCmd>>();
		cmdMap.put("redirect", CmdRedirect.class);
		cmdMap.put("view", CmdView.class);
	}
	
	public static IControllerCmd createCmd(String cmdName,String arg,Params params) throws Exception{
		if(cmdMap.containsKey(cmdName)){
			Class<? extends IControllerCmd> cmdCls = cmdMap.get(cmdName);
			Constructor<? extends IControllerCmd> constructor = cmdCls.getConstructor(String.class,Params.class);
			IControllerCmd cmd = constructor.newInstance(arg,params);
			return cmd;
		}
		return null;
		
	}
}
