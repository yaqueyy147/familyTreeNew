package com.witkey.familyTree.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/1/6.
 */
@Entity
@Table(name = "t_company_photo")
public class TCompanyPhoto {
    private int id;
    private int companyId;
    private String publicityPhoto;
    private String photoDesc;
    private String remark;
    private Timestamp createTime;
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
    @Column(name = "company_id")
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "publicity_photo")
    public String getPublicityPhoto() {
        return publicityPhoto;
    }

    public void setPublicityPhoto(String publicityPhoto) {
        this.publicityPhoto = publicityPhoto;
    }

    @Basic
    @Column(name = "photo_desc")
    public String getPhotoDesc() {
        return photoDesc;
    }

    public void setPhotoDesc(String photoDesc) {
        this.photoDesc = photoDesc;
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
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCompanyPhoto that = (TCompanyPhoto) o;

        if (id != that.id) return false;
        if (companyId != that.companyId) return false;
        if (publicityPhoto != null ? !publicityPhoto.equals(that.publicityPhoto) : that.publicityPhoto != null)
            return false;
        if (photoDesc != null ? !photoDesc.equals(that.photoDesc) : that.photoDesc != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createMan != null ? !createMan.equals(that.createMan) : that.createMan != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + companyId;
        result = 31 * result + (publicityPhoto != null ? publicityPhoto.hashCode() : 0);
        result = 31 * result + (photoDesc != null ? photoDesc.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createMan != null ? createMan.hashCode() : 0);
        return result;
    }
}
