package com.sinohealth.dscp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Auther: lj
 * @Date: 2018/5/19 16:38
 * @Description: 用户
 */
@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 8, nullable = false, unique = true)
    private String loginName;

    @Column(length = 40, nullable = false)
    private String loginPasswd;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 1)
    private Integer sex = 1;
    @Column(length = 11)
    private String telephone;
    @Column(length = 11)
    private Integer mobilePhone;
    @Column(length = 40)
    private String email;
    @Column(length = 80)
    private String address;
    @Column(length = 1)
    private Integer userStatus = 1;
    private Date lastLoginTime;
    @Column(length = 20)
    private String lastLoginIp;
    @Column(length = 20)
    private String lastLoginAddr;
    private Date createTime;
    @Column(length = 20)
    private String createUser;
    private Date updateTime;
    @Column(length = 20)
    private String updateUser;
    @Column(length = 80)
    private String logoUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(String loginName, String loginPasswd, String name, Integer sex, String telephone,
            Integer mobilePhone, String email, String address, Integer userStatus, Date lastLoginTime,
            String lastLoginIp, String lastLoginAddr, Date createTime, String createUser, Date updateTime,
            String updateUser, String logoUrl) {
        this.loginName = loginName;
        this.loginPasswd = loginPasswd;
        this.name = name;
        this.sex = sex;
        this.telephone = telephone;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.address = address;
        this.userStatus = userStatus;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginAddr = lastLoginAddr;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.logoUrl = logoUrl;
    }

    public User() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPasswd() {
        return loginPasswd;
    }

    public void setLoginPasswd(String loginPasswd) {
        this.loginPasswd = loginPasswd;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Integer mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginAddr() {
        return lastLoginAddr;
    }

    public void setLastLoginAddr(String lastLoginAddr) {
        this.lastLoginAddr = lastLoginAddr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", loginPasswd='" + loginPasswd + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", telephone='" + telephone + '\'' +
                ", mobilePhone=" + mobilePhone +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", userStatus=" + userStatus +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", lastLoginAddr='" + lastLoginAddr + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}
