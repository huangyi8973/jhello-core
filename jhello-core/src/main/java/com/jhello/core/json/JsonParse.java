package com.jhello.core.json;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * json解析器
 * @author Huangyi
 *
 */
public class JsonParse {

	public String toJson(Object obj) throws Exception{
		return valueToJson(obj);
	}
	
	private String valueToJson(Object obj) throws Exception{
		if(obj != null){
			if(obj instanceof String){
				return "\""+(String) obj +"\"";
			}else if(isNumbertValue(obj)){
				return String.valueOf(obj);
			}else if(obj instanceof Boolean){
				return String.valueOf(obj);
			}else if(obj.getClass().isArray()){
				return arrayToJson(obj);
			}else if(obj instanceof Map){
				return mapToJson(obj);
			}else if(obj instanceof Collection){
				return collectionToJson(obj);
//			}else if(obj instanceof IBaseVO){
//				return objectToJson(obj);
			}else{
				return "\""+obj.toString() +"\"";
			}
		}
		return "null";
	}
	
	private String mapToJson(Object obj) throws Exception {
		if(obj != null && obj instanceof Map){
			Map map = (Map) obj;
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			for(Object key : map.keySet()){
				sb.append(valueToJson(key));
				sb.append(":");
				sb.append(valueToJson(map.get(key)));
				sb.append(",");
			}
			if(sb.charAt(sb.length() - 1) == ','){
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("}");
			return sb.toString();
		}
		return "null";
	}

	private String arrayToJson(Object obj) throws Exception {
		if(obj != null && obj.getClass().isArray()){
			int length =  Array.getLength(obj);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(int i = 0; i < length ;i++){
				sb.append(valueToJson(Array.get(obj, i)));
				sb.append(",");
			}
			if(sb.charAt(sb.length() - 1) == ','){
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("]");
			return sb.toString();
		}
		return "null";
	}
	
	private String collectionToJson(Object obj) throws Exception {
		if(obj != null && obj instanceof Collection){
			Collection<Object> collection = (Collection<Object>) obj;
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			Iterator<Object> iterator = collection.iterator();
			while(iterator.hasNext()){
				sb.append(valueToJson(iterator.next()));
				sb.append(",");
			}
			if(sb.charAt(sb.length() - 1) == ','){
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("]");
			return sb.toString();
		}
		return "null";
	}

	private boolean isNumbertValue(Object obj) {
		if(obj instanceof Integer){
			return true;
		}else if(obj instanceof Double){
			return true;
		}else if(obj instanceof Float){
			return true;
		}else if(obj instanceof Long){
			return true;
		}
		return false;
	}
//
//	private String objectToJson(Object obj) throws Exception{
//		Map<String,Object> map = new HashMap<String, Object>();
//		Class<?> cls = obj.getClass();
//		Field[] fields = cls.getDeclaredFields();
//		Set<String> fieldsNameSet = new HashSet<String>();
//		for(Field f : fields){
//			fieldsNameSet.add(f.getName());
//		}
//		BeanInfo bi = Introspector.getBeanInfo(cls);
//		PropertyDescriptor[] pd = bi.getPropertyDescriptors();
//		for(PropertyDescriptor p : pd){
//			String attrName = p.getName();
//			if(fieldsNameSet.contains(attrName)){
//				Method readMethod = p.getReadMethod();
//				map.put(attrName, readMethod.invoke(obj));
//			}
//		}
//		return mapToJson(map);
//		
//	}
}
