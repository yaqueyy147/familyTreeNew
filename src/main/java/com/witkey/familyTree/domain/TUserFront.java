package com.witkey.familyTree.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/18.
 */
@Entity
@Table(name = "t_user_front")
public class TUserFront implements Serializable {
    private static final long serialVersionUID = -7274629203921879941L;
    private int id;
    private String userName;
    private String password;
    private String idCard;
    private String nickName;
    private String phone;
    private String wechart;
    private String qqNum;
    private String remark;

    public TUserFront() {
    }

    public TUserFront(int id, String userName, String password, String idCard, String nickName, String phone, String wechart, String qqNum, String remark) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.idCard = idCard;
        this.nickName = nickName;
        this.phone = phone;
        this.wechart = wechart;
        this.qqNum = qqNum;
        this.remark = remark;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "id_card")
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Basic
    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "wechart")
    public String getWechart() {
        return wechart;
    }

    public void setWechart(String wechart) {
        this.wechart = wechart;
    }

    @Basic
    @Column(name = "qq_num")
    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TUserFront that = (TUserFront) o;

        if (id != that.id) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (idCard != null ? !idCard.equals(that.idCard) : that.idCard != null) return false;
        if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (wechart != null ? !wechart.equals(that.wechart) : that.wechart != null) return false;
        if (qqNum != null ? !qqNum.equals(that.qqNum) : that.qqNum != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (idCard != null ? idCard.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (wechart != null ? wechart.hashCode() : 0);
        result = 31 * result + (qqNum != null ? qqNum.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
