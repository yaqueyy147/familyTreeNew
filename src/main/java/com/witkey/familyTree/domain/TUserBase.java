package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
@Entity
@Table(name = "t_user_base")
public class TUserBase implements Serializable {
    private static final long serialVersionUID = 7263106389088249810L;
    private int id;
    private String userCode;
    private String userName;
    private String userPassword;
    private String userDesc;
    private Integer userType;
    private String userNickName;
    private Integer state;
    private String remark;
    private String userPhone;
    private String userEmail;
    private String userQq;
    private String userWechart;
    private Date createTime;
    private String createMan;

    @Id
    @Column(name = "id",unique = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_code")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "user_password")
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "user_desc")
    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    @Basic
    @Column(name = "user_type")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "user_nick_name")
    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "user_phone")
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TUserBase tUserBase = (TUserBase) o;

        if (id != tUserBase.id) return false;
        if (userCode != null ? !userCode.equals(tUserBase.userCode) : tUserBase.userCode != null) return false;
        if (userName != null ? !userName.equals(tUserBase.userName) : tUserBase.userName != null) return false;
        if (userPassword != null ? !userPassword.equals(tUserBase.userPassword) : tUserBase.userPassword != null)
            return false;
        if (userDesc != null ? !userDesc.equals(tUserBase.userDesc) : tUserBase.userDesc != null) return false;
        if (userType != null ? !userType.equals(tUserBase.userType) : tUserBase.userType != null) return false;
        if (userNickName != null ? !userNickName.equals(tUserBase.userNickName) : tUserBase.userNickName != null)
            return false;
        if (state != null ? !state.equals(tUserBase.state) : tUserBase.state != null) return false;
        if (remark != null ? !remark.equals(tUserBase.remark) : tUserBase.remark != null) return false;
        if (userEmail != null ? !userEmail.equals(tUserBase.userEmail) : tUserBase.userEmail != null) return false;
        if (userQq != null ? !userQq.equals(tUserBase.userQq) : tUserBase.userQq != null) return false;
        if (userWechart != null ? !userWechart.equals(tUserBase.userWechart) : tUserBase.userWechart != null) return false;
        if (createTime != null ? !createTime.equals(tUserBase.createTime) : tUserBase.createTime != null) return false;
        if (createMan != null ? !createMan.equals(tUserBase.userPhone) : tUserBase.createMan != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userCode != null ? userCode.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (userDesc != null ? userDesc.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (userNickName != null ? userNickName.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (userQq != null ? userQq.hashCode() : 0);
        result = 31 * result + (userWechart != null ? userWechart.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createMan != null ? createMan.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "user_email")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "user_qq")
    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq;
    }

    @Basic
    @Column(name = "user_wechart")
    public String getUserWechart() {
        return userWechart;
    }

    public void setUserWechart(String userWechart) {
        this.userWechart = userWechart;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "create_man")
    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
