package com.jhello.db.convert;

public interface IConvert<T,K> {

	public abstract T convertToVO(K value);

	public abstract K convertToDb(T value);

}
