package com.jhello.core.view;

public class JsonView extends View{

	
	/**
	 * 用于action返回的不是ModelAndView类型，
	 * 在结果传递的时候，key就是__json__，直接返回值的json，没有key
	 */
	public final static String JSON_KEY = "__json__";
}
