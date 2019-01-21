package com.mahindra.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @class description :
 * 
 * 
 * @author Andrew Qian
 * @time 2017年7月17日 下午2:33:08
 */
public class Util {
	private static Logger logger = Logger.getLogger(Util.class);

	public String getTime() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);

		logger.info("getTime ： " + time);
		return time;
	}
	public String getTimeforName() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);

		logger.info("getTime ： " + time);
		return time;
	}

	public String getMD5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值			
			String MD5=new BigInteger(1, md.digest()).toString(16);
			
			logger.info("getMD5 success");
			return MD5;
		} catch (Exception e) {
			logger.error("getMD5 发生错误" + e);
			return str;
		}
	}
	
	
}
