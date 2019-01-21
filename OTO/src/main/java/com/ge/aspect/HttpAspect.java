package com.ge.aspect;

import com.ge.po.GeRequestRecord;
import com.ge.service.GeRequestRecordService;
import com.google.gson.JsonObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utils.ObjectUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/11 12:19
 */
@Aspect
@Component
public class HttpAspect {
    @Autowired
    public GeRequestRecordService geRequestRecordService;
    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);


    @Pointcut("execution(public * com.ge.controller.GeRequestRecordController.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object obj = joinPoint.getArgs()[0];
        Map map = ObjectUtil.objectToMap(obj);

        GeRequestRecord geRequestRecord = new GeRequestRecord();
        geRequestRecord.setRemoteaddr(request.getRemoteAddr());
        geRequestRecord.setRequesturl(map.get("requesturl").toString());
        geRequestRecord.setRequestUser(map.get("requestUser").toString());
        geRequestRecord.setRequesturlSimple(map.get("requesturlSimple").toString());

        geRequestRecord.setCreateTime(new Date());
        geRequestRecordService.addRequestRecord(geRequestRecord);
    }


}
