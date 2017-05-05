package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
@Entity
@Table(name = "t_user_1")
public class TUser1 implements Serializable {
    private static final long serialVersionUID = -1564664779967005273L;
    private int id;
    private String loginName;
    private String userName;
    private String password;
    private String idCard;
    private String phone;
    private String wechart;
    private String qqNum;
    private String remark;
    private String createMan;
    private String createTime;
    private String province;
    private String city;
    private String district;
    private String county;
    private String town;
    private String detailAddr;
    private Integer isVolunteer;
    private String userPhoto;
    private String idCardPhoto;
    private Integer state;
    private Integer isConsole;
    private Integer isFront;
    private Integer userFrom;

    private Double totalMoney;
    
    public TUser1() {
    }

    public TUser1(String loginName) {
        this.loginName = loginName;
    }

    public TUser1(String loginName, String password) {
        this.loginName = loginName;
        this.password = password;
    }


    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login_name")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    @Basic
    @Column(name = "create_man")
    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "county")
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Basic
    @Column(name = "town")
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Basic
    @Column(name = "detail_addr")
    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    @Basic
    @Column(name = "is_volunteer")
    public Integer getIsVolunteer() {
        return isVolunteer;
    }

    public void setIsVolunteer(Integer isVolunteer) {
        this.isVolunteer = isVolunteer;
    }

    @Basic
    @Column(name = "user_photo")
    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    @Basic
    @Column(name = "id_card_photo")
    public String getIdCardPhoto() {
        return idCardPhoto;
    }

    public void setIdCardPhoto(String idCardPhoto) {
        this.idCardPhoto = idCardPhoto;
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
    @Column(name = "is_console")
    public Integer getIsConsole() {
        return isConsole;
    }

    public void setIsConsole(Integer isConsole) {
        this.isConsole = isConsole;
    }

    @Basic
    @Column(name = "is_front")
    public Integer getIsFront() {
        return isFront;
    }

    public void setIsFront(Integer isFront) {
        this.isFront = isFront;
    }

    @Basic
    @Column(name = "user_from")
    public Integer getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(Integer userFrom) {
        this.userFrom = userFrom;
    }

    public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TUser1 tUser1 = (TUser1) o;

        if (id != tUser1.id) return false;
        if (loginName != null ? !loginName.equals(tUser1.loginName) : tUser1.loginName != null) return false;
        if (userName != null ? !userName.equals(tUser1.userName) : tUser1.userName != null) return false;
        if (password != null ? !password.equals(tUser1.password) : tUser1.password != null) return false;
        if (idCard != null ? !idCard.equals(tUser1.idCard) : tUser1.idCard != null) return false;
        if (phone != null ? !phone.equals(tUser1.phone) : tUser1.phone != null) return false;
        if (wechart != null ? !wechart.equals(tUser1.wechart) : tUser1.wechart != null) return false;
        if (qqNum != null ? !qqNum.equals(tUser1.qqNum) : tUser1.qqNum != null) return false;
        if (remark != null ? !remark.equals(tUser1.remark) : tUser1.remark != null) return false;
        if (createMan != null ? !createMan.equals(tUser1.createMan) : tUser1.createMan != null) return false;
        if (createTime != null ? !createTime.equals(tUser1.createTime) : tUser1.createTime != null) return false;
        if (province != null ? !province.equals(tUser1.province) : tUser1.province != null) return false;
        if (city != null ? !city.equals(tUser1.city) : tUser1.city != null) return false;
        if (district != null ? !district.equals(tUser1.district) : tUser1.district != null) return false;
        if (county != null ? !county.equals(tUser1.county) : tUser1.county != null) return false;
        if (town != null ? !town.equals(tUser1.town) : tUser1.town != null) return false;
        if (detailAddr != null ? !detailAddr.equals(tUser1.detailAddr) : tUser1.detailAddr != null) return false;
        if (isVolunteer != null ? !isVolunteer.equals(tUser1.isVolunteer) : tUser1.isVolunteer != null) return false;
        if (userPhoto != null ? !userPhoto.equals(tUser1.userPhoto) : tUser1.userPhoto != null) return false;
        if (idCardPhoto != null ? !idCardPhoto.equals(tUser1.idCardPhoto) : tUser1.idCardPhoto != null) return false;
        if (state != null ? !state.equals(tUser1.state) : tUser1.state != null) return false;
        if (isConsole != null ? !isConsole.equals(tUser1.isConsole) : tUser1.isConsole != null) return false;
        if (isFront != null ? !isFront.equals(tUser1.isFront) : tUser1.isFront != null) return false;
        if (userFrom != null ? !userFrom.equals(tUser1.userFrom) : tUser1.userFrom != null) return false;

        if (totalMoney != null ? !totalMoney.equals(tUser1.totalMoney) : tUser1.totalMoney != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (idCard != null ? idCard.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (wechart != null ? wechart.hashCode() : 0);
        result = 31 * result + (qqNum != null ? qqNum.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createMan != null ? createMan.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (county != null ? county.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        result = 31 * result + (detailAddr != null ? detailAddr.hashCode() : 0);
        result = 31 * result + (isVolunteer != null ? isVolunteer.hashCode() : 0);
        result = 31 * result + (userPhoto != null ? userPhoto.hashCode() : 0);
        result = 31 * result + (idCardPhoto != null ? idCardPhoto.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (isConsole != null ? isConsole.hashCode() : 0);
        result = 31 * result + (isFront != null ? isFront.hashCode() : 0);

        result = 31 * result + (userFrom != null ? userFrom.hashCode() : 0);
        
        result = 31 * result + (totalMoney != null ? totalMoney.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
