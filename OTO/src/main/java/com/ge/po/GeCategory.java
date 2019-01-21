package com.ge.po;

import java.io.Serializable;
import java.util.Date;

public class GeCategory implements Serializable {
    /**
     * null
     */
    private Integer id;

    /**
     * null
     */
    private Integer parentId;

    /**
     * null
     */
    private String name;

    /**
     * null
     */
    private Integer sortOrder;

    /**
     * null
     */
    private Date createTime;

    /**
     * null
     */
    private Date updateTime;

    private Integer isdeleted;
    /**
     * GE_CATEGORY
     */
    private static final long serialVersionUID = 1L;

    /**
     * null
     * @return ID null
     */
    public Integer getId() {
        return id;
    }

    /**
     * null
     * @param id null
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * null
     * @return PARENT_ID null
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * null
     * @param parentId null
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * null
     * @return NAME null
     */
    public String getName() {
        return name;
    }

    /**
     * null
     * @param name null
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * null
     * @return SORT_ORDER null
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * null
     * @param sortOrder null
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * null
     * @return CREATE_TIME null
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * null
     * @param createTime null
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * null
     * @return UPDATE_TIME null
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * null
     * @param updateTime null
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeCategory)) return false;

        GeCategory that = (GeCategory) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}