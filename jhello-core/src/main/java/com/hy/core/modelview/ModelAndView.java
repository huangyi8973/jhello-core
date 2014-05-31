package com.hy.core.modelview;

import com.hy.core.model.Model;
import com.hy.core.view.View;

public class ModelAndView {

	private View _view;
	private Model _model;
	
	public ModelAndView(){}

	public ModelAndView(View view, Model model){
		this._view = view;
		this._model = model;
	}
	
	public View getView() {
		return _view;
	}

	public void setView(View view) {
		this._view = view;
	}

	public Model getModel() {
		return _model;
	}

	public void setModel(Model model) {
		this._model = model;
	}
	
	
}
