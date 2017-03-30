package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/6.
 */
@Entity
@Table(name = "t_company_sponsor")
public class TCompanySponsor implements Serializable {
    private static final long serialVersionUID = -6879587329653235247L;
    private int id;
    private String companyLoginName;
    private String companyLoginPassword;
    private String companyName;
    private String companyArea;
    private String companyDesc;
    private String companyPhoto;
    private String businessLicense;
    private String companyMobilePhone;
    private String companyTelephone;
    private String companyFax;
    private String companyQq;
    private String companyWechart;
    private String companyEmail;
    private String remark;
    private Date createTime;
    private String createMan;
    private Integer state;

    public TCompanySponsor(){

    }

    public TCompanySponsor(String companyLoginName) {
        this.companyLoginName = companyLoginName;
    }

    public TCompanySponsor(String companyLoginName, String companyLoginPassword, String companyName) {
        this.companyLoginName = companyLoginName;
        this.companyLoginPassword = companyLoginPassword;
        this.companyName = companyName;
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
    @Column(name = "company_login_name")
    public String getCompanyLoginName() {
        return companyLoginName;
    }

    public void setCompanyLoginName(String companyLoginName) {
        this.companyLoginName = companyLoginName;
    }

    @Basic
    @Column(name = "company_login_password")
    public String getCompanyLoginPassword() {
        return companyLoginPassword;
    }

    public void setCompanyLoginPassword(String companyLoginPassword) {
        this.companyLoginPassword = companyLoginPassword;
    }

    @Basic
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "company_area")
    public String getCompanyArea() {
        return companyArea;
    }

    public void setCompanyArea(String companyArea) {
        this.companyArea = companyArea;
    }

    @Basic
    @Column(name = "company_desc")
    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    @Basic
    @Column(name = "company_photo")
    public String getCompanyPhoto() {
        return companyPhoto;
    }

    public void setCompanyPhoto(String companyPhoto) {
        this.companyPhoto = companyPhoto;
    }

    @Basic
    @Column(name = "business_license")
    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    @Basic
    @Column(name = "company_mobile_phone")
    public String getCompanyMobilePhone() {
        return companyMobilePhone;
    }

    public void setCompanyMobilePhone(String companyMobilePhone) {
        this.companyMobilePhone = companyMobilePhone;
    }

    @Basic
    @Column(name = "company_telephone")
    public String getCompanyTelephone() {
        return companyTelephone;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    @Basic
    @Column(name = "company_fax")
    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    @Basic
    @Column(name = "company_qq")
    public String getCompanyQq() {
        return companyQq;
    }

    public void setCompanyQq(String companyQq) {
        this.companyQq = companyQq;
    }

    @Basic
    @Column(name = "company_wechart")
    public String getCompanyWechart() {
        return companyWechart;
    }

    public void setCompanyWechart(String companyWechart) {
        this.companyWechart = companyWechart;
    }

    @Basic
    @Column(name = "company_email")
    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
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
    @Column(name = "create_man")
    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCompanySponsor that = (TCompanySponsor) o;

        if (id != that.id) return false;
        if (companyLoginName != null ? !companyLoginName.equals(that.companyLoginName) : that.companyLoginName != null)
            return false;
        if (companyLoginPassword != null ? !companyLoginPassword.equals(that.companyLoginPassword) : that.companyLoginPassword != null)
            return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (companyArea != null ? !companyArea.equals(that.companyArea) : that.companyArea != null) return false;
        if (companyDesc != null ? !companyDesc.equals(that.companyDesc) : that.companyDesc != null) return false;
        if (companyPhoto != null ? !companyPhoto.equals(that.companyPhoto) : that.companyPhoto != null) return false;
        if (businessLicense != null ? !businessLicense.equals(that.businessLicense) : that.businessLicense != null)
            return false;
        if (companyMobilePhone != null ? !companyMobilePhone.equals(that.companyMobilePhone) : that.companyMobilePhone != null)
            return false;
        if (companyTelephone != null ? !companyTelephone.equals(that.companyTelephone) : that.companyTelephone != null)
            return false;
        if (companyFax != null ? !companyFax.equals(that.companyFax) : that.companyFax != null) return false;
        if (companyQq != null ? !companyQq.equals(that.companyQq) : that.companyQq != null) return false;
        if (companyWechart != null ? !companyWechart.equals(that.companyWechart) : that.companyWechart != null)
            return false;
        if (companyEmail != null ? !companyEmail.equals(that.companyEmail) : that.companyEmail != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createMan != null ? !createMan.equals(that.createMan) : that.createMan != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (companyLoginName != null ? companyLoginName.hashCode() : 0);
        result = 31 * result + (companyLoginPassword != null ? companyLoginPassword.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (companyArea != null ? companyArea.hashCode() : 0);
        result = 31 * result + (companyDesc != null ? companyDesc.hashCode() : 0);
        result = 31 * result + (companyPhoto != null ? companyPhoto.hashCode() : 0);
        result = 31 * result + (businessLicense != null ? businessLicense.hashCode() : 0);
        result = 31 * result + (companyMobilePhone != null ? companyMobilePhone.hashCode() : 0);
        result = 31 * result + (companyTelephone != null ? companyTelephone.hashCode() : 0);
        result = 31 * result + (companyFax != null ? companyFax.hashCode() : 0);
        result = 31 * result + (companyQq != null ? companyQq.hashCode() : 0);
        result = 31 * result + (companyWechart != null ? companyWechart.hashCode() : 0);
        result = 31 * result + (companyEmail != null ? companyEmail.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createMan != null ? createMan.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
