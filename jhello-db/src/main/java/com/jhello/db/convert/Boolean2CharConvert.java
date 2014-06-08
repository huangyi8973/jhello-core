package com.jhello.db.convert;

import com.jhello.core.utils.StringUtils;

public class Boolean2CharConvert implements IConvert<Boolean, String> {

	public Boolean convertToVO(String value) {
		if(!StringUtils.isEmpty(value) && value.length() ==1 && "N".equals(value)){
			return false;
		}else if(!StringUtils.isEmpty(value) && value.length() == 1 && "Y".equals(value)){
			return true;
		}
		return false;
	}

	public String convertToDb(Boolean value) {
		return value ? "Y" : "N";
	}

}
