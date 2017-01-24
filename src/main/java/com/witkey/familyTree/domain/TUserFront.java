package com.witkey.familyTree.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by suyx on 2016/12/20 0020.
 */
@Entity
@Table(name = "t_user_front")
public class TUserFront implements Serializable {
    private static final long serialVersionUID = 8877469314125403688L;
    private int id;
    private String userName;
    private String password;
    private String idCard;
    private String nickName;
    private String phone;
    private String wechart;
    private String qqNum;
    private String remark;
    private Date createTime;
    private String province;
    private String city;
    private String district;
    private String county;
    private String town;
    private String detailAddr;
    private int isVolunteer;

    public TUserFront() {
    }

    public TUserFront(int id, String userName, String password, String idCard, String nickName, String phone, String wechart, String qqNum, String remark, Date createTime, String province, String city, String district, String county, String town, String detailAddr, int isVolunteer) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.idCard = idCard;
        this.nickName = nickName;
        this.phone = phone;
        this.wechart = wechart;
        this.qqNum = qqNum;
        this.remark = remark;
        this.createTime = createTime;
        this.province = province;
        this.city = city;
        this.district = district;
        this.county = county;
        this.town = town;
        this.detailAddr = detailAddr;
        this.isVolunteer = isVolunteer;
    }

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

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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
    public int getIsVolunteer() {
        return isVolunteer;
    }

    public void setIsVolunteer(int isVolunteer) {
        this.isVolunteer = isVolunteer;
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
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (district != null ? !district.equals(that.district) : that.district != null) return false;
        if (county != null ? !county.equals(that.county) : that.county != null) return false;
        if (town != null ? !town.equals(that.town) : that.town != null) return false;
        if (detailAddr != null ? !detailAddr.equals(that.detailAddr) : that.detailAddr != null) return false;
        if (isVolunteer != that.isVolunteer) return false;

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
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (county != null ? county.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        result = 31 * result + (detailAddr != null ? detailAddr.hashCode() : 0);
        result = 31 * result + isVolunteer;
        return result;
    }
}
