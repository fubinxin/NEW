package com.mahindra.util;

import java.util.UUID;

/**
 * uuid生成工具类
 * Created by weican on 2017/10/11.
 */
public class UUIDUtil {

    /**
     * 生成uuid
     * @return uuid
     */
    public synchronized static String creatUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
