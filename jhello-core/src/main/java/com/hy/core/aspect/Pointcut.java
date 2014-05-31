package com.hy.core.aspect;

public class Pointcut {

	private String targetControllerName;
	private String targetMethodName;
	private String adviceClsName;
	private String adviceMethodName;
	public String getTargetControllerName() {
		return targetControllerName;
	}
	public void setTargetControllerName(String targetControllerName) {
		this.targetControllerName = targetControllerName;
	}
	public String getTargetMethodName() {
		return targetMethodName;
	}
	public void setTargetMethodName(String targetMethodName) {
		this.targetMethodName = targetMethodName;
	}
	public String getAdviceClsName() {
		return adviceClsName;
	}
	public void setAdviceClsName(String adviceClsName) {
		this.adviceClsName = adviceClsName;
	}
	public String getAdviceMethodName() {
		return adviceMethodName;
	}
	public void setAdviceMethodName(String adviceMethodName) {
		this.adviceMethodName = adviceMethodName;
	}
	public Pointcut(String targetControllerName, String targetMethodName,
			String adviceClsName, String adviceMethodName) {
		super();
		this.targetControllerName = targetControllerName;
		this.targetMethodName = targetMethodName;
		this.adviceClsName = adviceClsName;
		this.adviceMethodName = adviceMethodName;
	}
	
	
	
	
}
