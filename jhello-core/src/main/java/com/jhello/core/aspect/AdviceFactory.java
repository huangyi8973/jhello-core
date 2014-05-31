package com.jhello.core.aspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jhello.core.action.Action;


public class AdviceFactory {

	private static AdviceFactory _instance;
	private static Object _lock = new Object();
	private AdviceFactory(){}
	
	public static AdviceFactory getInstance(){
		if(_instance == null){
			synchronized (_lock) {
				if(_instance == null){
					_instance = new AdviceFactory();
				}
			}
		}
		return _instance;
	}

	public Pointcut[] getBeforeAdvicesByAction(Action action) {
		Map<String,String> map = AdviceMapper.getInstance().getAdviceParttenAndInfoMap();
		return getAdvice(action, map, "before");
	}
	
	public Pointcut[] getAfterAdvicesByAction(Action action) {
		Map<String,String> map = AdviceMapper.getInstance().getAdviceParttenAndInfoMap();
		return getAdvice(action, map, "after");
	}

	private Pointcut[] getAdvice(Action action, Map<String, String> map,
			String methodName) {
		List<Pointcut> list = new ArrayList<Pointcut>();
		for(String pattern : map.keySet()){
			if(action.getClsAndMethod().matches(pattern)){
				String[] targetInfo = action.getClsAndMethod().split("#");
				String adviceClsName = map.get(pattern);
				Pointcut pointcut = new Pointcut(targetInfo[0],targetInfo[1],adviceClsName,methodName);
				list.add(pointcut);
			}
		}
		return list.toArray(new Pointcut[0]);
	}
}
