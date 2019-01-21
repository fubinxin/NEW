package com.ge.service.impl;


import com.common.exception.MyException;
import com.ge.dao.GePromotionMapper;
import com.ge.po.GePromotion;
import com.ge.service.GeFileService;
import com.ge.service.GePromotionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import utils.ObjectUtil;
import utils.PageModel;
import utils.ResultHttpStatus;
import utils.TableRes;

import java.util.*;

/**
 * ge_news
 * Created by autoGen Tools
 */
@Service("gePromotionService")
public class GePromotionServiceImpl implements GePromotionService {
    private static Logger logger = Logger.getLogger(GePromotionServiceImpl.class);

    //=============================================
    @Autowired
    private GePromotionMapper gePromotionMapper;

    @Autowired
    private GeFileService geFileService;
    //=============================================

    /**
     * delete ge_news
     *
     * @param id PKID
     * @throws MyException
     */
    @Override
    public void deleteGeNewsByPK(java.lang.Integer id) throws MyException {
        if (id == null) {
            throw new MyException("GeNews pk is not null");
        }

        try {
            gePromotionMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("delete GeNews fail!");
        }

    }

    @Override
    public GePromotion getGeNewsByPK(java.lang.Integer id) throws MyException {
        if (id == null) {
            throw new MyException("GeNews pk is not null");
        }

        try {
            return gePromotionMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("get GeNews fail!");
        }

    }

    /**
     * add ge_news
     *
     * @param gePromotion ge_news
     * @throws MyException
     */
    @Override
    public void addGeNews(GePromotion gePromotion) throws MyException {
        if (gePromotion == null) {
            throw new MyException("geNews is not null");
        }

        try {
            gePromotionMapper.insertSelective(gePromotion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("add catalog fail!");
        }

    }

    /**
     * edit ge_news
     *
     * @param gePromotion ge_news
     * @throws MyException
     */
    @Override
    public void updateGeNews(GePromotion gePromotion) throws MyException {

        if (gePromotion == null) {
            throw new MyException("geNews is not null");
        }

        try {
            gePromotionMapper.updateByPrimaryKeySelective(gePromotion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("update catalog fail!");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageModel<GePromotion> queryGeNewsBySearch(Map<String, Object> conditionMap, Integer pageNum, Integer pageSize) throws Exception {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        GePromotion gePromotion = new GePromotion();
        if (!CollectionUtils.isEmpty(conditionMap)) {
            for (String s : conditionMap.keySet()) {
                pageMap.put(s, conditionMap.get(s));
            }
            gePromotion = (GePromotion) ObjectUtil.mapToObject(pageMap, GePromotion.class);
        }
        Page<GePromotion> page = PageHelper.startPage(pageNum, pageSize);
        List<GePromotion> pageList = gePromotionMapper.select(gePromotion);
        pageList.sort(Comparator.comparing(GePromotion::getCreateTime).reversed());
        Long count = page.getTotal();
        PageModel<GePromotion> pm = PageModel.newPageModel(pageSize, pageNum, count.intValue());
        pm.setDataList(pageList);

        return pm;

    }

    @Override
    public TableRes queryGeNews(Map<String, Object> conditionMapPage, Integer type, Boolean isAll) {
        TableRes response = new TableRes(0, ResultHttpStatus.OK.getName());
        try {
            Map<String, Object> conditionMap = (Map<String, Object>) conditionMapPage.get(
                    "conditionMap");
            if (conditionMap == null) {
                conditionMap = new HashMap<>();
            }
            int page = Integer.parseInt((String) conditionMapPage.get("page"));
            int pageSize = Integer.parseInt((String) conditionMapPage.get(
                    "limit"));
            //type=2图片滚动，type=1不滚动，settop=1的图片置顶和启用滚动
            conditionMap.put("type", type);
            if (!isAll) {
                conditionMap.put("settop", 1);
            }

            PageModel<GePromotion> queryData = queryGeNewsBySearch(conditionMap,
                    page, pageSize);
            response.setData(queryData.getDataList());
            response.setCount(queryData.getTotalRecord());

            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            return response;
        }
    }

}

