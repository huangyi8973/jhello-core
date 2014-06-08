package com.jhello.db.convert;

import java.sql.Timestamp;

import com.jhello.core.lang.Datetime;

public class Datetime2TimestampConvert implements IConvert<Datetime,Timestamp> {

	public Timestamp convertToDb(Datetime date){
		Timestamp ts = new Timestamp(date.getTime());
		return ts;
	}
	
	public Datetime convertToVO(Timestamp ts){
		Datetime date = new Datetime(ts.getTime());
		return date;
	}
}
