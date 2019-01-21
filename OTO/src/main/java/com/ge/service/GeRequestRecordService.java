package com.ge.service;

import com.common.exception.MyException;
import com.ge.po.GeRequestRecord;
import org.springframework.stereotype.Service;
import utils.TableRes;

import java.util.Map;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/11 12:27
 */
@Service
public interface GeRequestRecordService {
    void addRequestRecord(GeRequestRecord geProductCuserRel) throws MyException;

    int countUserClick(String requestUser ,String requesturlSimple);

    TableRes queryRecord(Map<String, Object> conditionMap);
}
