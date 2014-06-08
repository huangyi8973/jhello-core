package com.jhello.db.result;

import java.sql.ResultSet;

/**
 * 数据集结果处理类
 * @author huangy
 * @date   2013-4-12
 */
public interface IResultProcessor {

	public abstract Object getResult(ResultSet rs) throws Exception;

}