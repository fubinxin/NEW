package com.ge.service.impl;

import com.common.exception.MyException;
import com.ge.dao.GeRequestRecordMapper;
import com.ge.po.GeRequestRecord;
import com.ge.service.GeRequestRecordService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.*;

import java.util.*;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/11 12:28
 */
@Service("GeRequestRecordService")
public class GeRequestRecordServiceImpl implements GeRequestRecordService {
    private static Logger logger = Logger.getLogger(GeRequestRecordServiceImpl.class);
    @Autowired
    private GeRequestRecordMapper geRequestRecordMapper;

    @Override
    public void addRequestRecord(GeRequestRecord geRequestRecord) throws MyException {
        if (geRequestRecord == null) {
            throw new MyException("geRequestRecord is not null");
        }
        try {
            geRequestRecordMapper.insertSelective(geRequestRecord);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("add geRequestRecord fail!");
        }

    }

    @Override
    public int countUserClick(String requestUser, String requesturlSimple) {
        int count = geRequestRecordMapper.queryCount(requestUser, requesturlSimple);
        return count;
    }

    @Override
    public TableRes queryRecord(Map<String, Object> conditionMap) {
        TableRes response = new TableRes(0, ResultHttpStatus.OK.getName());
        try {
            int pageNum = Integer.parseInt((String) conditionMap.get("page"));
            int pageSize = Integer.parseInt((String) conditionMap.get("limit"));
            String requesturlSimple = conditionMap.get("requesturlSimple").toString();
            Date startDate = conditionMap.get("startDate").toString() == "" ? null : DateTimeUtil.strToDate(conditionMap.get("startDate").toString(), "yyyy-MM-dd");
            Date endDate = conditionMap.get("endDate").toString() == "" ? null : DateTimeUtil.strToDate(conditionMap.get("endDate").toString(), "yyyy-MM-dd");
            Page page = PageHelper.startPage(pageNum, pageSize);
            if (endDate != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(endDate);
                cal.add(Calendar.DATE, 1);
                endDate = cal.getTime();
            }
            List<GeRequestRecord> pageList = geRequestRecordMapper.queryRecord(requesturlSimple, startDate, endDate);
            Long count = page.getTotal();
            PageModel<GeRequestRecord> pm = PageModel.newPageModel(pageSize, pageNum, count.intValue());
            pm.setDataList(pageList);
            response.setData(pm.getDataList());
            response.setCount(pm.getTotalRecord());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return response;
    }
}
