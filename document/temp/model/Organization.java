package com.sinohealth.dscp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

import javax.persistence.Entity;

/**
 * @Auther: Administrator
 * @Date: 2018/5/19 16:49
 * @Description: 组织架构/部门
 */
@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Organization {

    private Integer id;
    private String organizationName;
    private Integer parentId;
    private Integer organizationStatus;
    private Date createTime;
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrganizationStatus() {
        return organizationStatus;
    }

    public void setOrganizationStatus(Integer organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Organization() {
    }

    public Organization(Integer id, String organizationName, Integer parentId, Integer organizationStatus,
            Date createTime, Integer createUser, Date updateTime, Integer updateUser) {
        this.id = id;
        this.organizationName = organizationName;
        this.parentId = parentId;
        this.organizationStatus = organizationStatus;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", organizationName='" + organizationName + '\'' +
                ", parentId=" + parentId +
                ", organizationStatus=" + organizationStatus +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
