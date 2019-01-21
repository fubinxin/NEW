package com.ge.vo;

import java.util.Date;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/19 15:46
 */
public class VcategoryUser {
    private String id;
    private String categoryUserName;
    private String area;
    private String headUrl;
    private String organiZation;
    private String mobile;
    private String productName;
    private String model;
    private Date validTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryUserName() {
        return categoryUserName;
    }

    public void setCategoryUserName(String categoryUserName) {
        this.categoryUserName = categoryUserName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getOrganiZation() {
        return organiZation;
    }

    public void setOrganiZation(String organiZation) {
        this.organiZation = organiZation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }
}
