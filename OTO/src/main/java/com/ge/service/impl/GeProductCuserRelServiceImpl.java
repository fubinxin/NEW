package com.ge.service.impl;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.common.exception.MyException;
import com.ge.po.GeProductCuserRel;
import com.ge.service.GeProductCuserRelService;
import com.sun.javafx.scene.layout.region.Margins;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;
import utils.DateTimeUtil;
import utils.PageModel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.ge.dao.*;
import utils.Response;
import utils.ResultHttpStatus;

/**
 * ge_product_cuser_rel
 * Created by autoGen Tools
 */
@Service("geProductCuserRelService")
public class GeProductCuserRelServiceImpl implements GeProductCuserRelService {
    private static Logger logger = Logger.getLogger(GeProductCuserRelServiceImpl.class);

    //=============================================
    @Autowired
    private GeProductCuserRelMapper geProductCuserRelMapper;


    //=============================================

    /**
     * delete ge_product_cuser_rel
     *
     * @param id PKID
     * @throws MyException
     */
    @Override
    public void deleteGeProductCuserRelByPK(java.lang.Integer id) throws MyException {
        if (id == null) {
            throw new MyException("GeProductCuserRel pk is not null");
        }

        try {
            geProductCuserRelMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("delete GeProductCuserRel fail!");
        }

    }

    @Override
    public GeProductCuserRel getGeProductCuserRelByPK(java.lang.Integer id) throws MyException {
        if (id == null) {
            throw new MyException("GeProductCuserRel pk is not null");
        }

        try {
            return geProductCuserRelMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("get GeProductCuserRel fail!");
        }

    }

    /**
     * add ge_product_cuser_rel
     *
     * @param geProductCuserRel ge_product_cuser_rel
     * @throws MyException
     */
    @Override
    public void addGeProductCuserRel(GeProductCuserRel geProductCuserRel) throws MyException {
        if (geProductCuserRel == null) {
            throw new MyException("geProductCuserRel is not null");
        }

        try {
            geProductCuserRelMapper.insertSelective(geProductCuserRel);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("add catalog fail!");
        }

    }

    /**
     * edit ge_product_cuser_rel
     *
     * @param geProductCuserRel ge_product_cuser_rel
     * @throws MyException
     */
    @Override
    public void updateGeProductCuserRel(GeProductCuserRel geProductCuserRel) throws MyException {

        if (geProductCuserRel == null) {
            throw new MyException("geProductCuserRel is not null");
        }

        try {
            geProductCuserRelMapper.updateByPrimaryKeySelective(geProductCuserRel);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("update catalog fail!");
        }
    }

    @SuppressWarnings("unchecked")
    public PageModel<GeProductCuserRel> queryGeProductCuserRelBySearch(Map<String, Object> conditionMap, Integer pageNum, Integer pageSize) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        Page<GeProductCuserRel> page = PageHelper.startPage(pageNum, pageSize);

        List<GeProductCuserRel> pageList = geProductCuserRelMapper.select(null);
        Long count = page.getTotal();

        PageModel<GeProductCuserRel> pm = PageModel.newPageModel(pageSize, pageNum, count.intValue());
        //pm.setDataList( pagelist);
        //pageMap.put(PageModel.mStartRow, pm.getStartRows());
        //pageMap.put(PageModel.mPageSize, pageSize);

        //pageMap.put(PageModel.mCondition,conditionMap) ;

        pm.setDataList(pageList);

        return pm;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void relationProductAndUser(String cid, List<Map<String, String>> list) {
        if (StringUtils.isBlank(cid)) {
            throw new MyException("cid is null");
        }
        geProductCuserRelMapper.deleteByCid(Integer.valueOf(cid));
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        for (Map<String, String> s : list) {
            GeProductCuserRel geProductCuserRel = new GeProductCuserRel();
            try {
                geProductCuserRel.setValidTime(format1.parse(s.get("validTime").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            geProductCuserRel.setCreateTime(new Date());
            geProductCuserRel.setCid(Integer.valueOf(cid));
            geProductCuserRel.setPid(Integer.valueOf(s.get("pid")));
            geProductCuserRelMapper.insertSelective(geProductCuserRel);

        }
    }

    @Override
    public Response queryGeProductByCId(Integer cid) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            List<GeProductCuserRel> list = geProductCuserRelMapper.queryByCId(cid);
            response.setData(list);
        } catch (Exception e) {
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
        }
        return response;
    }
}

