package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
@Entity
@Table(name = "t_company_introduce")
public class TCompanyIntroduce implements Serializable {
    private static final long serialVersionUID = -2428194418693058665L;
    private int id;
    private int companyId;
    private String companyIntroduce;
    private String remark;
    private String createMan;
    private String createTime;
    private String updateMan;
    private String updateTime;

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
    @Column(name = "company_id")
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "company_introduce")
    public String getCompanyIntroduce() {
        return companyIntroduce;
    }

    public void setCompanyIntroduce(String companyIntroduce) {
        this.companyIntroduce = companyIntroduce;
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

        TCompanyIntroduce that = (TCompanyIntroduce) o;

        if (id != that.id) return false;
        if (companyId != that.companyId) return false;
        if (companyIntroduce != null ? !companyIntroduce.equals(that.companyIntroduce) : that.companyIntroduce != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + companyId;
        result = 31 * result + (companyIntroduce != null ? companyIntroduce.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
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
    @Column(name = "update_man")
    public String getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(String updateMan) {
        this.updateMan = updateMan;
    }

    @Basic
    @Column(name = "update_time")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
