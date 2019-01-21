package com.ge.service.impl;


import com.common.exception.MyException;
import com.ge.dao.GeCertifiedProductMapper;
import com.ge.dao.GeProductCuserRelMapper;
import com.ge.po.GeCertifiedProduct;
import com.ge.service.GeCertifiedProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import utils.ObjectUtil;
import utils.PageModel;
import utils.Response;
import utils.ResultHttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ge_certified_product
 * Created by autoGen Tools
 */
@Service("geCertifiedProductService")
public class GeCertifiedProductServiceImpl implements GeCertifiedProductService {
    private static Logger logger = Logger.getLogger(GeCertifiedProductServiceImpl.class);

    //=============================================
    @Autowired
    private GeCertifiedProductMapper geCertifiedProductMapper;

    @Autowired
    private GeProductCuserRelMapper geProductCuserRelMapper;


    //=============================================

    /**
     * delete ge_certified_product
     *
     * @param id PKID
     * @throws MyException
     */
    @Override
    public void deleteGeCertifiedProductByPK(java.lang.Integer id) throws MyException {
        if (id == null) {
            throw new MyException("GeCertifiedProduct pk is not null");
        }

        try {
            geCertifiedProductMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("delete GeCertifiedProduct fail!");
        }

    }

    @Override
    public GeCertifiedProduct getGeCertifiedProductByPK(java.lang.Integer id) throws MyException {
        if (id == null) {
            throw new MyException("GeCertifiedProduct pk is not null");
        }

        try {
            return geCertifiedProductMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("get GeCertifiedProduct fail!");
        }

    }

    /**
     * add ge_certified_product
     *
     * @param geCertifiedProduct ge_certified_product
     * @throws MyException
     */
    @Override
    public void addGeCertifiedProduct(GeCertifiedProduct geCertifiedProduct) throws MyException {
        if (geCertifiedProduct == null) {
            throw new MyException("geCertifiedProduct is not null");
        }
        if (geCertifiedProductMapper.queryCount(null, geCertifiedProduct.getName()) > 0) {
            throw new MyException("geCertifiedProduct Already existed");
        }
        try {
            geCertifiedProductMapper.insertSelective(geCertifiedProduct);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("add catalog fail!");
        }

    }

    /**
     * edit ge_certified_product
     *
     * @param geCertifiedProduct ge_certified_product
     * @throws MyException
     */
    @Override
    public void updateGeCertifiedProduct(GeCertifiedProduct geCertifiedProduct) throws MyException {

        if (geCertifiedProduct == null) {
            throw new MyException("geCertifiedProduct is not null");
        }
        if (geCertifiedProductMapper.queryCount(geCertifiedProduct.getId(), geCertifiedProduct.getName()) > 0) {
            throw new MyException("geCertifiedProduct Already existed");
        }
        try {
            geCertifiedProductMapper.updateByPrimaryKeySelective(geCertifiedProduct);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("update catalog fail!");
        }
    }

    @SuppressWarnings("unchecked")
    public PageModel<GeCertifiedProduct> queryGeCertifiedProductBySearch(Map<String, Object> conditionMap, Integer pageNum, Integer pageSize) throws Exception {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        Page<GeCertifiedProduct> page = PageHelper.startPage(pageNum, pageSize);

        GeCertifiedProduct geCertifiedProduct = new GeCertifiedProduct();
        if (!CollectionUtils.isEmpty(conditionMap)) {
            for (String s : conditionMap.keySet()) {
                pageMap.put(s, conditionMap.get(s));
            }
            geCertifiedProduct = (GeCertifiedProduct) ObjectUtil.mapToObject(pageMap, GeCertifiedProduct.class);
        }
        List<GeCertifiedProduct> pageList = geCertifiedProductMapper.select(geCertifiedProduct);
        Long count = page.getTotal();

        PageModel<GeCertifiedProduct> pm = PageModel.newPageModel(pageSize, pageNum, count.intValue());
        pm.setDataList(pageList);
        return pm;

    }

    public Response<List<GeCertifiedProduct>> getProductByCategory(Integer category) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            response.setData(geCertifiedProductMapper.getProductByCategory(category));
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
        }
        return response;
    }

    public int checkProduct(Integer pid) {
        int count = geProductCuserRelMapper.queryCountByPid(pid);
        return  count;
    }

}

