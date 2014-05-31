package com.hy.core.handle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hy.core.action.Action;
import com.hy.core.action.ActionFactory;
import com.hy.core.action.ActionInvoker;
import com.hy.core.action.Params;
import com.hy.core.aspect.AbstractAdvice;
import com.hy.core.aspect.AdviceFactory;
import com.hy.core.aspect.AdviceMapper;
import com.hy.core.aspect.Pointcut;
import com.hy.core.model.Model;
import com.hy.core.modelview.ModelAndView;
import com.hy.core.utils.Utils;
import com.hy.core.view.JsonView;
import com.hy.core.viewrender.JsonViewRender;
import com.hy.core.viewrender.ViewRender;
import com.hy.core.viewrender.ViewRenderFactory;

/**
 * Action处理器
 * @author Huangyi
 *
 */
public class ActionHandler extends Handler {

	private final static Logger logger = LoggerFactory.getLogger(ActionHandler.class);
	
	//Map<切面类名，切面实例>
	private Map<String,AbstractAdvice> _adviceClsNameAndInstanceMap;
	
	public ActionHandler(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	
	/**
	 * 设置响应头
	 */
	private void setHeader(){
		this.getResponse().setCharacterEncoding("UTF-8");//设置响应流的编码方式
		this.getResponse().setHeader("ContentType", "text/html;charset=UTF-8");//设置浏览器的编码方式
	}

	@Override
	public void handle() throws Exception {
		this.setHeader();
		
		String url = this.getRequest().getRequestURI().substring(this.getRequest().getContextPath().length());
		String httpMethod = this.getRequest().getMethod();
		Action action = ActionFactory.getInstance().getAction(url,httpMethod);
		
		if(action != null){
			logger.debug(String.format("url:%s",action.getUrl()));
			logger.debug(String.format("params:%s", this.getRequest().getQueryString()));
			logger.debug(String.format("获得url映射:%s",action));
			action.setParams((Params)this.getRequest().getAttribute("params"));
			//实例化切面类
			prepareAdvice();
			//调用before方法
			executeBeforeAdvice(action);
			//调用action
			Object result = new ActionInvoker(action).invoke();
			//调用after方法
			executeAfterAdvice(action);
			if(result != null){
				if(result instanceof ModelAndView){
					//返回视图
					ModelAndView mv = (ModelAndView) result;
					ViewRender render = ViewRenderFactory.getInstance().createViewRender(mv.getView());
					render.setRequest(this.getRequest());
					render.setResponse(this.getResponse());
					render.render(mv.getView(),mv.getModel());
				}else{
					//不是返回ModelAndView的，全部看成是返回json
					ViewRender render = new JsonViewRender(this.getRequest(),this.getResponse());
					Model model = new Model();
					model.put(JsonView.JSON_KEY, result);
					render.render(null, model);
				}
			}
		}else{
			this.nextHandle();
		}
	}


	
	
	private void prepareAdvice() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		this._adviceClsNameAndInstanceMap = new HashMap<String, AbstractAdvice>();
		Map<String,String> adviceInfoMap = AdviceMapper.getInstance().getAdviceParttenAndInfoMap();
		for(Entry<String, String> entry : adviceInfoMap.entrySet()){
			Class<?> cls =Thread.currentThread().getContextClassLoader().loadClass(entry.getValue());
			AbstractAdvice adviceInstance = (AbstractAdvice) cls.newInstance();
			this._adviceClsNameAndInstanceMap.put(entry.getValue(), adviceInstance);
		}
	}


	private void executeBeforeAdvice(Action action) throws Exception {
		Pointcut[] pointcuts = AdviceFactory.getInstance().getBeforeAdvicesByAction(action);
		executeAdvice(pointcuts);
	}

	private void executeAfterAdvice(Action action) throws Exception {
		Pointcut[] pointcuts = AdviceFactory.getInstance().getAfterAdvicesByAction(action);
		executeAdvice(pointcuts);
	}


	private void executeAdvice(Pointcut[] pointcuts)
			throws ClassNotFoundException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		if(!Utils.isEmplyOrNull(pointcuts)){
			for(Pointcut pointcut : pointcuts){
				Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(pointcut.getAdviceClsName());
				Method method = cls.getMethod(pointcut.getAdviceMethodName(), Pointcut.class,HttpServletRequest.class,HttpServletResponse.class);
				Object obj = this._adviceClsNameAndInstanceMap.get(pointcut.getAdviceClsName());
				method.invoke(obj, pointcut,this.getRequest(),this.getResponse());
			}
		}
	}
}
