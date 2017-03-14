package com.witkey.familyTree.domain;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/3/9.
 */
@Entity
@Table(name = "t_meritocrat", schema = "family_tree")
public class TMeritocrat {
    private int id;
    private String meritocratName;
    private String meritocratDesc;
    private String meritocratArea;
    private int meritocratAttrId;
    private String createMan;
    private String createTime;
    private String remark;

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
    @Column(name = "meritocrat_name")
    public String getMeritocratName() {
        return meritocratName;
    }

    public void setMeritocratName(String meritocratName) {
        this.meritocratName = meritocratName;
    }

    @Basic
    @Column(name = "meritocrat_desc")
    public String getMeritocratDesc() {
        return meritocratDesc;
    }

    public void setMeritocratDesc(String meritocratDesc) {
        this.meritocratDesc = meritocratDesc;
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
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "meritocrat_area")
    public String getMeritocratArea() {
        return meritocratArea;
    }

    public void setMeritocratArea(String meritocratArea) {
        this.meritocratArea = meritocratArea;
    }

    @Basic
    @Column(name = "meritocrat_attr_id")
    public int getMeritocratAttrId() {
        return meritocratAttrId;
    }

    public void setMeritocratAttrId(int meritocratAttrId) {
        this.meritocratAttrId = meritocratAttrId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TMeritocrat that = (TMeritocrat) o;

        if (id != that.id) return false;
        if (meritocratName != null ? !meritocratName.equals(that.meritocratName) : that.meritocratName != null)
            return false;
        if (meritocratDesc != null ? !meritocratDesc.equals(that.meritocratDesc) : that.meritocratDesc != null)
            return false;
        if (meritocratArea != null ? !meritocratArea.equals(that.meritocratArea) : that.meritocratArea != null)
            return false;
        if (meritocratAttrId != that.meritocratAttrId) return false;
        if (createMan != null ? !createMan.equals(that.createMan) : that.createMan != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (meritocratName != null ? meritocratName.hashCode() : 0);
        result = 31 * result + (meritocratDesc != null ? meritocratDesc.hashCode() : 0);
        result = 31 * result + (meritocratArea != null ? meritocratArea.hashCode() : 0);
        result = 31 * result + meritocratAttrId;
        result = 31 * result + (createMan != null ? createMan.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
