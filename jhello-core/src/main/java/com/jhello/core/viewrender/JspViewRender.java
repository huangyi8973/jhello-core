package com.jhello.core.viewrender;

import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhello.core.model.Model;
import com.jhello.core.view.JspView;
import com.jhello.core.view.View;

public class JspViewRender extends ViewRender {

	private final static Logger logger = LoggerFactory.getLogger(JspViewRender.class);
	
	public JspViewRender(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}
	
	public JspViewRender(){}

	@Override
	public void render(View view, Model model) throws Exception {
		String viewPath = ((JspView)view).getViewPath();
		RequestDispatcher dispatcher = this.getRequest().getRequestDispatcher("/WEB-INF/view/"+viewPath);
		
		//处理model的值
		if(model != null){
			for(Entry<String, Object> entry : model.entrySet()){
				this.getRequest().setAttribute(entry.getKey(), entry.getValue());
			}
		}
		
		dispatcher.forward(this.getRequest(), this.getResponse());
	}

}
