package com.ge.service.impl;


import java.util.*;

import com.common.Const;
import com.ge.po.GeUser;
import com.ge.service.GeUserService;
import org.apache.commons.lang3.StringUtils;
import utils.MD5Util;
import utils.PageModel;
import com.common.exception.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.ge.dao.*;
import utils.Response;
import utils.ResultHttpStatus;

/**
 * ge_user
 * Created by autoGen Tools
 */
@Service("geUserService")
public class GeUserServiceImpl implements GeUserService {
    private static Logger logger = Logger.getLogger(GeUserServiceImpl.class);

    //=============================================
    @Autowired
    private GeUserMapper geUserMapper;


    //=============================================

    /**
     * delete ge_user
     *
     * @param id PKID
     * @throws MyException
     */
    @Override
    public void deleteGeUserByPK(java.lang.Integer id) throws MyException {
        if (id == null) {
            throw new MyException("GeUser pk is not null");
        }

        try {
            geUserMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("delete GeUser fail!");
        }

    }

    @Override
    public GeUser getGeUserByPK(java.lang.Integer id) throws MyException {
        if (id == null) {
            throw new MyException("GeUser pk is not null");
        }

        try {
            return geUserMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("get GeUser fail!");
        }

    }

    /**
     * add ge_user
     *
     * @param geUser ge_user
     * @throws MyException
     */
    @Override
    public void addGeUser(GeUser geUser) throws MyException {
        if (geUser == null) {
            throw new MyException("geUser is not null");
        }
        if (geUserMapper.checkUsername(geUser.getLoginName()) > 0) {
            throw new MyException("用户名已存在");
        }
        try {
            geUser.setRole(Const.Role.ROLE_CUSTOMER);
            //MD5加密
            geUser.setPassword(MD5Util.MD5EncodeUtf8(geUser.getPassword()));
            geUser.setCreateTime(new Date());
            geUserMapper.insertSelective(geUser);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("add catalog fail!");
        }

    }

    /**
     * edit ge_user
     *
     * @param geUser ge_user
     * @throws MyException
     */
    @Override
    public void updateGeUser(GeUser geUser) throws MyException {

        if (geUser == null) {
            throw new MyException("geUser is not null");
        }

        try {
            geUserMapper.updateByPrimaryKeySelective(geUser);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MyException("update catalog fail!");
        }
    }

    @SuppressWarnings("unchecked")
    public PageModel<GeUser> queryGeUserBySearch(Map<String, Object> conditionMap, Integer pageNum, Integer pageSize) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        Page<GeUser> page = PageHelper.startPage(pageNum, pageSize);

        List<GeUser> pageList = geUserMapper.select(null);
        Long count = page.getTotal();

        PageModel<GeUser> pm = PageModel.newPageModel(pageSize, pageNum, count.intValue());
        //pm.setDataList( pagelist);
        //pageMap.put(PageModel.mStartRow, pm.getStartRows());
        //pageMap.put(PageModel.mPageSize, pageSize);

        //pageMap.put(PageModel.mCondition,conditionMap) ;

        pm.setDataList(pageList);

        return pm;

    }

    @Override
    public Response login(String loginName, String password) {
        Response response = new Response(ResultHttpStatus.OK.getValue(), ResultHttpStatus.OK.getName());
        int resultCount = geUserMapper.checkUsername(loginName);
        if (resultCount == 0) {
            response.setStatus(ResultHttpStatus.LOGIN_TIMEOUT.getValue());
            response.setMsg("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        GeUser user = geUserMapper.selectLogin(loginName, md5Password);
        if (user == null) {
            response.setStatus(ResultHttpStatus.LOGIN_TIMEOUT.getValue());
            response.setMsg("密码错误");
        } else {
            user.setPassword(StringUtils.EMPTY);
            response.setData(user);
            response.setMsg("登陆成功");
        }

        return response;
    }

    /**
     * 检测当前用户是否管理员
     *
     * @param user
     * @return
     */
    @Override
    public Response checkAdminRole(GeUser user) {
        Response response = new Response(ResultHttpStatus.INTERNAL_ERROR.getValue(), ResultHttpStatus.INTERNAL_ERROR.getName());
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
            response.setStatus(ResultHttpStatus.OK.getValue());
        }
        return response;
    }
}

