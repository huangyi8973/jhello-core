package com.hy.core.utils;


/**
 * 加密工具
 * @version V5.0
 * @author huangy
 * @date   Apr 3, 2013
 */
public class EncryptUtils {
	private final static int ZERO=48;
	private final static int A=65;
	private final static int NINE=57;
	public static void main(String[] args) {
		String password="1";
		String regTime="2013-04-03 16:02:00";
		String three=encrypt(password,regTime);
		System.out.println(" 加密后:"+ three);
	}

	/**
	 *加密数据 
	 * @param strAry
	 * @return
	 * @author huangy
	 * @date 2013-4-29 下午9:34:46
	 */
	public static String encrypt(String... strAry) {
		String result=null;
		if(strAry.length>0){
			if(strAry.length==1){
				//只有一个字符串要加密的情况下，只返回MD5
				result=md5(strAry[0]);
			}else{
				byte[] bResult=md5(strAry[0]).getBytes();
				//循环加密
				for(int i=1;i<strAry.length;i++){
					byte[] b2=md5(strAry[i]).getBytes();
					for(int j=0;j<bResult.length;j++){
						int r= bResult[j]^b2[j];
						bResult[j]=getCharByte(r);
					}
				}
				result=new String(bResult);
			}
		}
		return result;
	}

	private static String md5(String str) {
		return MD5Utils.Parse(str);
	}

	/**把任意数值转换成A-Z0-9
	 * @param i
	 * @return
	 * @author huangy
	 * @date Apr 3, 2013 4:58:28 PM
	 */
	private static byte getCharByte(int i) {
		int c=i%36+ZERO;
		if(c>NINE && c<A){
			c+=7;
		}
		return (byte) c;
	}
}
