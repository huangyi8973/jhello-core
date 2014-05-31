package com.hy.core.viewrender;

import com.hy.core.view.JsonView;
import com.hy.core.view.JspView;
import com.hy.core.view.View;

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
