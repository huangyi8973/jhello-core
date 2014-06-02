package com.jhello.core.db.convert;

import com.jhello.core.utils.StringUtils;

public class Boolean2CharConvert implements IConvert<Boolean, String> {

	@Override
	public Boolean convertToVO(String value) {
		if(!StringUtils.isEmpty(value) && value.length() ==1 && "N".equals(value)){
			return false;
		}else if(!StringUtils.isEmpty(value) && value.length() == 1 && "Y".equals(value)){
			return true;
		}
		return false;
	}

	@Override
	public String convertToDb(Boolean value) {
		return value ? "Y" : "N";
	}

}
