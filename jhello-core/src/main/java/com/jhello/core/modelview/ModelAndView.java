package com.jhello.core.modelview;

import com.jhello.core.config.JHelloConfig;
import com.jhello.core.model.Model;
import com.jhello.core.view.View;
import com.jhello.core.view.ViewFactory;

public class ModelAndView {

	private View view;
	private Model model;
	
	public ModelAndView(){}

	public ModelAndView(View view, Model model){
		this.view = view;
		this.model = model;
	}
	
	public ModelAndView(String viewPath,Model model){
		this.model = model;
		ViewFactory viewFactory = new ViewFactory();
		this.view = viewFactory.createView(JHelloConfig.getInstance().getDefaultViewType(), viewPath);
	}
	
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	
}
