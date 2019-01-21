package com.mahindra.util;

/**
 * Created by weican
 * Date：2018/9/19 10:02
 * Description：
 */
public class StringUtil {

    public static String convertString(Integer i) {
        return i == null ? "" : i +"";
    }

    public static String convertString(String str) {
        return str == null ? "" : str +"";
    }
}
