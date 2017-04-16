package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/9.
 */
@Entity
@Table(name = "t_company_points", schema = "family_tree")
public class TCompanyPoints implements Serializable {
    private static final long serialVersionUID = -4930288309905753230L;
    private int id;
    private int companyId;
    private int points;
    private String remark;
    private Double totalMoney;

    public TCompanyPoints() {
    }

    public TCompanyPoints(int companyId, int points) {
        this.companyId = companyId;
        this.points = points;
    }

    public TCompanyPoints(int companyId, int points, Double totalMoney) {
        this.companyId = companyId;
        this.points = points;
        this.totalMoney = totalMoney;
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
    @Column(name = "company_id")
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "points")
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
    @Column(name = "total_money")
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

        TCompanyPoints that = (TCompanyPoints) o;

        if (id != that.id) return false;
        if (companyId != that.companyId) return false;
        if (points != that.points) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        if (totalMoney != null ? !totalMoney.equals(that.totalMoney) : that.totalMoney != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + companyId;
        result = 31 * result + points;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (totalMoney != null ? totalMoney.hashCode() : 0);;
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
