package com.witkey.familyTree.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
@Entity
@Table(name = "t_family")
public class TFamily implements Serializable {
    private static final long serialVersionUID = 836392985203284743L;
    private int id;
    private String familyFirstName;
    private String familyName;
    private String photoUrl;
    private String createMan;
    private Integer visitStatus;
    private String visitPassword;
    private Date createTime;
    private Integer state;
    private String remark;
    private String familyDesc;
    private int familyArea;

    public TFamily() {
    }

    public TFamily(int id, String familyFirstName, String familyName, String photoUrl, String createMan, Integer visitStatus, String visitPassword, Date createTime, Integer state, String remark, String familyDesc, int familyArea) {
        this.id = id;
        this.familyFirstName = familyFirstName;
        this.familyName = familyName;
        this.photoUrl = photoUrl;
        this.createMan = createMan;
        this.visitStatus = visitStatus;
        this.visitPassword = visitPassword;
        this.createTime = createTime;
        this.state = state;
        this.remark = remark;
        this.familyDesc = familyDesc;
        this.familyArea = familyArea;
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
    @Column(name = "family_first_name")
    public String getFamilyFirstName() {
        return familyFirstName;
    }

    public void setFamilyFirstName(String familyFirstName) {
        this.familyFirstName = familyFirstName;
    }

    @Basic
    @Column(name = "family_name")
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Basic
    @Column(name = "photo_url")
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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
    @Column(name = "visit_status")
    public Integer getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(Integer visitStatus) {
        this.visitStatus = visitStatus;
    }

    @Basic
    @Column(name = "visit_password")
    public String getVisitPassword() {
        return visitPassword;
    }

    public void setVisitPassword(String visitPassword) {
        this.visitPassword = visitPassword;
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
    @Column(name = "family_desc")
    public String getFamilyDesc() {
        return familyDesc;
    }

    public void setFamilyDesc(String familyDesc) {
        this.familyDesc = familyDesc;
    }

    @Basic
    @Column(name = "family_area")
    public int getFamilyArea() {
        return familyArea;
    }

    public void setFamilyArea(int familyArea) {
        this.familyArea = familyArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TFamily tFamily = (TFamily) o;

        if (id != tFamily.id) return false;
        if (familyFirstName != null ? !familyFirstName.equals(tFamily.familyFirstName) : tFamily.familyFirstName != null)
            return false;
        if (familyName != null ? !familyName.equals(tFamily.familyName) : tFamily.familyName != null) return false;
        if (photoUrl != null ? !photoUrl.equals(tFamily.photoUrl) : tFamily.photoUrl != null) return false;
        if (createMan != null ? !createMan.equals(tFamily.createMan) : tFamily.createMan != null) return false;
        if (visitStatus != null ? !visitStatus.equals(tFamily.visitStatus) : tFamily.visitStatus != null) return false;
        if (visitPassword != null ? !visitPassword.equals(tFamily.visitPassword) : tFamily.visitPassword != null)
            return false;
        if (createTime != null ? !createTime.equals(tFamily.createTime) : tFamily.createTime != null) return false;
        if (state != null ? !state.equals(tFamily.state) : tFamily.state != null) return false;
        if (remark != null ? !remark.equals(tFamily.remark) : tFamily.remark != null) return false;
        if (familyDesc != null ? !familyDesc.equals(tFamily.familyDesc) : tFamily.familyDesc != null) return false;
        if (familyArea != tFamily.familyArea) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (familyFirstName != null ? familyFirstName.hashCode() : 0);
        result = 31 * result + (familyName != null ? familyName.hashCode() : 0);
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        result = 31 * result + (createMan != null ? createMan.hashCode() : 0);
        result = 31 * result + (visitStatus != null ? visitStatus.hashCode() : 0);
        result = 31 * result + (visitPassword != null ? visitPassword.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (familyDesc != null ? familyDesc.hashCode() : 0);
        result = 31 * result + familyArea;
        return result;
    }
}
