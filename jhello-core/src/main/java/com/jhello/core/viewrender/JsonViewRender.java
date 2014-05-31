package com.jhello.core.viewrender;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jhello.core.json.JsonParse;
import com.jhello.core.model.Model;
import com.jhello.core.view.JsonView;
import com.jhello.core.view.View;

public class JsonViewRender extends ViewRender {

	public JsonViewRender(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	public JsonViewRender(){}
	
	@Override
	public void render(View view, Model model) throws Exception {
		if(model !=null){
			JsonParse parse = new JsonParse();
			String result = null;
			if(model.containsKey(JsonView.JSON_KEY) && model.size() == 1){
				//包含json_key的，不输出key，直接输出value
				result = parse.toJson(model.get(JsonView.JSON_KEY));
			}else{
				result = parse.toJson(model);
			}
			
			//设置http 头部信息
			this.getResponse().setHeader("Cache-Control", "no-cache");
			String userAgent = this.getRequest().getHeader("User-Agent");
			// 处理IE返回json跳出下载框
			String mimeType = null;
			if(userAgent.contains("MSIE")){
				mimeType = "text/html";
			}else{
				mimeType = "application/json";
			}
			this.getResponse().setContentType(mimeType);
			//输出
			PrintWriter pw = this.getResponse().getWriter();
			pw.write(result);
			pw.flush();
			pw.close();
		}
	}

}
