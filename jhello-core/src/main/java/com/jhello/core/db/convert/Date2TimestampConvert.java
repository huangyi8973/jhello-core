package com.jhello.core.db.convert;

import java.sql.Timestamp;
import java.util.Date;

public class Date2TimestampConvert implements IConvert<Date,Timestamp> {

	public Timestamp convertToDb(Date date){
		Timestamp ts = new Timestamp(date.getTime());
		return ts;
	}
	
	public Date convertToVO(Timestamp ts){
		Date date = new java.sql.Date(ts.getTime());
		return date;
	}
	
}
