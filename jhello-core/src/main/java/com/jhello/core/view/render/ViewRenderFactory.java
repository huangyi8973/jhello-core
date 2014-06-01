package com.jhello.core.view.render;

import com.jhello.core.view.JsonView;
import com.jhello.core.view.JspView;
import com.jhello.core.view.View;

public class ViewRenderFactory {

	private static ViewRenderFactory _instance;
	private static Object lock = new Object();
	
	private ViewRenderFactory(){};
	
	public static ViewRenderFactory getInstance(){
		if(null == _instance){
			synchronized (lock) {
				if(null == _instance){
					_instance = new ViewRenderFactory();
				}
			}
		}
		return _instance;
	}
	
	public ViewRender createViewRender(View view){
		if(view instanceof JspView){
			return new JspViewRender();
		}else if(view instanceof JsonView){
			return new JsonViewRender();
		}
		return null;
	}
}
