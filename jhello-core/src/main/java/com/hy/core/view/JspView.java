package com.hy.core.view;

public class JspView extends View {

	protected String viewPath;

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	public JspView(String viewPath) {
		this.viewPath = viewPath;
	}
}
