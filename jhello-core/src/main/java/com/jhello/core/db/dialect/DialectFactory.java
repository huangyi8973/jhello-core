package com.jhello.core.db.dialect;

import com.jhello.core.config.ConfigConst;
import com.jhello.core.config.JHelloConfig;
import com.jhello.core.utils.StringUtils;

public abstract class DialectFactory {

	private static IDialect dialect;
	private static Object lock = new Object();
	
	public static IDialect getDialect(){
		if(dialect == null){
			synchronized (lock) {
				if(dialect == null){
					dialect = createDialect();
				}
			}
		}
		return dialect;
	}
	
	private static IDialect createDialect(){
		String dialect = JHelloConfig.getConfigValue(ConfigConst.DB_DIALECT);
		if(!StringUtils.isEmpty(dialect)){
			if("mysql".equals(dialect.toLowerCase())){
				return new MySqlDialect();
			}
		}
		return new MySqlDialect();
	}
	
}
