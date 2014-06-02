package com.jhello.core.lang;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Datetime extends Date {
	
	private static final long serialVersionUID = -49340181088779786L;

	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	public static String getPattern(){
		return pattern;
	}
	public static void setPattern(String newPattern){
		pattern = newPattern;
	}
	
	public Datetime(long date){
		super(date);
	}
	
	public Datetime(){
		super();
	}
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(this);
	}
}
