package com.ge.po;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "ge_request_record")
public class GeRequestRecord implements Serializable {
    /**
     * null
     */
    private String id;

    /**
     * null
     */
    private String requesturl;

    /**
     * null
     */
    private String remoteaddr;

    /**
     * null
     */
    private String requestUser;

    /**
     * null
     */
    private String requesturlSimple;

    /**
     * null
     */
    private Date createTime;

    /**
     * GE_REQUEST_RECORD
     */
    private static final long serialVersionUID = 1L;

    /**
     * null
     * @return ID null
     */
    public String getId() {
        return id;
    }

    /**
     * null
     * @param id null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * null
     * @return REQUESTURL null
     */
    public String getRequesturl() {
        return requesturl;
    }

    /**
     * null
     * @param requesturl null
     */
    public void setRequesturl(String requesturl) {
        this.requesturl = requesturl == null ? null : requesturl.trim();
    }

    /**
     * null
     * @return REMOTEADDR null
     */
    public String getRemoteaddr() {
        return remoteaddr;
    }

    /**
     * null
     * @param remoteaddr null
     */
    public void setRemoteaddr(String remoteaddr) {
        this.remoteaddr = remoteaddr == null ? null : remoteaddr.trim();
    }

    /**
     * null
     * @return REQUEST_USER null
     */
    public String getRequestUser() {
        return requestUser;
    }

    /**
     * null
     * @param requestUser null
     */
    public void setRequestUser(String requestUser) {
        this.requestUser = requestUser == null ? null : requestUser.trim();
    }

    /**
     * null
     * @return REQUESTURL_SIMPLE null
     */
    public String getRequesturlSimple() {
        return requesturlSimple;
    }

    /**
     * null
     * @param requesturlSimple null
     */
    public void setRequesturlSimple(String requesturlSimple) {
        this.requesturlSimple = requesturlSimple == null ? null : requesturlSimple.trim();
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        GeRequestRecord other = (GeRequestRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRequesturl() == null ? other.getRequesturl() == null : this.getRequesturl().equals(other.getRequesturl()))
            && (this.getRemoteaddr() == null ? other.getRemoteaddr() == null : this.getRemoteaddr().equals(other.getRemoteaddr()))
            && (this.getRequestUser() == null ? other.getRequestUser() == null : this.getRequestUser().equals(other.getRequestUser()))
            && (this.getRequesturlSimple() == null ? other.getRequesturlSimple() == null : this.getRequesturlSimple().equals(other.getRequesturlSimple()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRequesturl() == null) ? 0 : getRequesturl().hashCode());
        result = prime * result + ((getRemoteaddr() == null) ? 0 : getRemoteaddr().hashCode());
        result = prime * result + ((getRequestUser() == null) ? 0 : getRequestUser().hashCode());
        result = prime * result + ((getRequesturlSimple() == null) ? 0 : getRequesturlSimple().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", requesturl=").append(requesturl);
        sb.append(", remoteaddr=").append(remoteaddr);
        sb.append(", requestUser=").append(requestUser);
        sb.append(", requesturlSimple=").append(requesturlSimple);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}