package com.ge.service.impl;


import com.common.exception.MyException;
import com.ge.dao.GeCertifiedUserMapper;
import com.ge.po.GeCertifiedUser;
import com.ge.service.GeCertifiedUserService;
import com.ge.vo.VcategoryList;
import com.ge.vo.VcategoryUser;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import utils.ObjectUtil;
import utils.PageModel;
import utils.Response;
import utils.ResultHttpStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ge_certified_user
 * Created by autoGen Tools
 */
@Service("geCertifiedUserService")
public class GeCertifiedUserServiceImpl implements GeCertifiedUserService {
    private static Logger logger = Logger.getLogger(GeCertifiedUserServiceImpl.class);

    //=============================================
    @Autowired
    private GeCertifiedUserMapper geCertifiedUserMapper;


    //=============================================

    /**
     * delete ge_certified_user
     *
     * @param id PKID
     * @throws MyException
     */
    @Override
    public void deleteGeCertifiedUserByPK(java.lang.Integer id) throws MyException {
        if (id == null) {
            throw new MyException("GeCertifiedUser pk is not null");
        }

        try {
            geCertifiedUserMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("delete GeCertifiedUser fail!");
        }

    }

    @Override
    public GeCertifiedUser getGeCertifiedUserByPK(java.lang.Integer id) throws MyException {
        if (id == null) {
            throw new MyException("GeCertifiedUser pk is not null");
        }

        try {
            return geCertifiedUserMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("get GeCertifiedUser fail!");
        }

    }

    /**
     * add ge_certified_user
     *
     * @param geCertifiedUser ge_certified_user
     * @throws MyException
     */
    @Override
    public void addGeCertifiedUser(GeCertifiedUser geCertifiedUser) throws MyException {
        if (geCertifiedUser == null) {
            throw new MyException("geCertifiedUser is not null");
        }

        try {
            geCertifiedUser.setCreateTime(new Date());
            geCertifiedUserMapper.insertSelective(geCertifiedUser);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("add catalog fail!");
        }

    }

    /**
     * edit ge_certified_user
     *
     * @param geCertifiedUser ge_certified_user
     * @throws MyException
     */
    @Override
    public void updateGeCertifiedUser(GeCertifiedUser geCertifiedUser) throws MyException {

        if (geCertifiedUser == null) {
            throw new MyException("geCertifiedUser is not null");
        }
        try {
            geCertifiedUserMapper.updateByPrimaryKeySelective(geCertifiedUser);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("update catalog fail!");
        }
    }

    @SuppressWarnings("unchecked")
    public PageModel<GeCertifiedUser> queryGeCertifiedUserBySearch(Map<String, Object> conditionMap, Integer pageNum, Integer pageSize) throws Exception {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        Page<GeCertifiedUser> page = PageHelper.startPage(pageNum, pageSize);

        GeCertifiedUser geCertifiedUser = new GeCertifiedUser();
        if (!CollectionUtils.isEmpty(conditionMap)) {
            for (String s : conditionMap.keySet()) {
                pageMap.put(s, conditionMap.get(s));
            }
            geCertifiedUser = (GeCertifiedUser) ObjectUtil.mapToObject(pageMap, GeCertifiedUser.class);
        }
        List<GeCertifiedUser> pageList = geCertifiedUserMapper.select(geCertifiedUser);
        Long count = page.getTotal();

        PageModel<GeCertifiedUser> pm = PageModel.newPageModel(pageSize, pageNum, count.intValue());
        pm.setDataList(pageList);

        return pm;

    }

    @Override
    public Response queryVcategoryUser(String searchStr, String area, String model, Integer pageNum, Integer pageSize) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            if (!StringUtils.isEmpty(searchStr)) {
                searchStr = "%" + searchStr + "%";
            }
            List<String> areaList = StringUtils.isEmpty(area) ? null : Arrays.asList(area.split(","));
            List<String> modelList = StringUtils.isEmpty(model) ? null : Arrays.asList(model.split(","));
            //模糊查询结果
            Page page = PageHelper.startPage(pageNum, pageSize);
            List<VcategoryUser> list = geCertifiedUserMapper.queryVcategoryUser(searchStr, areaList, modelList);
            List<VcategoryUser> unique = list.stream().collect(
                    Collectors.collectingAndThen(Collectors.toCollection(
                            () -> new TreeSet<>(Comparator.comparing(o -> o.getId()))), ArrayList::new)
            );
            PageInfo pageResult = new PageInfo(unique);

            VcategoryList vcategoryList = new VcategoryList();
            vcategoryList.setCount(page.getTotal());
            vcategoryList.setPageInfo(pageResult);
            //结果中的区域
            List<String> areas = geCertifiedUserMapper.quearyArea(searchStr);
            //产品类型
            List<String> models = geCertifiedUserMapper.quearyModel(searchStr);
            vcategoryList.setAreas(areas);
            vcategoryList.setModels(models);
            response.setData(vcategoryList);
        } catch (Exception e) {
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * 通过id查询用户认证产品
     *
     * @param id
     * @return
     */
    @Override
    public Response queryProductById(String id) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        try {
            if (StringUtils.isBlank(id)) {
                throw new Exception("id is null");
            }
            List<VcategoryUser> list = geCertifiedUserMapper.queryProductById(id);
            Map<String, List<VcategoryUser>> group = list.stream().collect(Collectors.groupingBy(VcategoryUser::getModel));
            response.setData(group);
        } catch (Exception e) {
            response.setStatus(ResultHttpStatus.INTERNAL_ERROR.getValue());
            response.setMsg(e.getMessage());
        }
        return response;
    }

//    private static ArrayList<VcategoryUser> removeDuplicateUser(List<VcategoryUser> users) {
//        Set<VcategoryUser> set = new TreeSet<VcategoryUser>(new Comparator<VcategoryUser>() {
//            @Override
//            public int compare(VcategoryUser o1, VcategoryUser o2) {
//                return o1.getId().compareTo(o2.getId());
//            }
//        });
//        set.addAll(users);
//        return new ArrayList<VcategoryUser>(set);
//
//
//    }
}

