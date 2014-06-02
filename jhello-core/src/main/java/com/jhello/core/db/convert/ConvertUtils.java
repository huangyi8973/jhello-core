package com.jhello.core.db.convert;

import java.sql.Timestamp;
import java.util.Date;

import com.jhello.core.lang.Datetime;

public class ConvertUtils {

	public static Object convertToDb(Object obj){
		if(obj instanceof Date){
			Date2TimestampConvert convert = new Date2TimestampConvert();
			return convert.convertToDb((Date)obj);
		}else if(obj instanceof Boolean){
			Boolean2CharConvert convert = new Boolean2CharConvert();
			return convert.convertToDb((Boolean)obj);
		}else if(obj instanceof Datetime){
			Datetime2TimestampConvert convert = new Datetime2TimestampConvert();
			return convert.convertToDb((Datetime)obj);
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public static Object convertToVO(Object obj,Class<?> targetCls){
		if(obj instanceof Timestamp){
			IConvert convert = null;
			if(targetCls.equals(Date.class)){
				convert = new Date2TimestampConvert();
			}else if(targetCls.equals(Datetime.class)){
				convert = new Datetime2TimestampConvert();
			}
			return convert.convertToVO((Timestamp)obj);
		}else if(obj instanceof String &&((String)obj).length() == 1 && ("Y".equals(obj) || "N".equals(obj))){
			Boolean2CharConvert convert = new Boolean2CharConvert();
			return convert.convertToVO((String)obj);
		}
		return obj;
	}
}
