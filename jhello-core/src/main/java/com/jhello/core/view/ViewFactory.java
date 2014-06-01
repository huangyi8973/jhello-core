package com.jhello.core.view;

/**
 * 视图工厂
 * @author Huangyi
 *
 */
public class ViewFactory {

	public View createView(ViewType viewType,String viewPath){
		View view = null;
		switch(viewType){
			case JSP:
				view = new JspView(viewPath);
				break;
			default:view = new JspView(viewPath);
		}
		return view;
	}
}
