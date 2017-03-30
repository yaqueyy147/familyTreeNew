package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/9.
 */
@Entity
@Table(name = "t_points_dic", schema = "family_tree")
public class TPointsDic {
    private int id;
    private Integer pointsType;
    private Integer pointsNum;
    private Integer pointsValue;
    private Integer state;
    private String createMan;
    private Date createTime;
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
    @Column(name = "points_type")
    public Integer getPointsType() {
        return pointsType;
    }

    public void setPointsType(Integer pointsType) {
        this.pointsType = pointsType;
    }

    @Basic
    @Column(name = "points_num")
    public Integer getPointsNum() {
        return pointsNum;
    }

    public void setPointsNum(Integer pointsNum) {
        this.pointsNum = pointsNum;
    }

    @Basic
    @Column(name = "points_value")
    public Integer getPointsValue() {
        return pointsValue;
    }

    public void setPointsValue(Integer pointsValue) {
        this.pointsValue = pointsValue;
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

        TPointsDic that = (TPointsDic) o;

        if (id != that.id) return false;
        if (pointsType != null ? !pointsType.equals(that.pointsType) : that.pointsType != null) return false;
        if (pointsNum != null ? !pointsNum.equals(that.pointsNum) : that.pointsNum != null) return false;
        if (pointsValue != null ? !pointsValue.equals(that.pointsValue) : that.pointsValue != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (state != that.state) return false;
        if (createMan != null ? !createMan.equals(that.createMan) : that.createMan != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pointsType != null ? pointsType.hashCode() : 0);
        result = 31 * result + (pointsNum != null ? pointsNum.hashCode() : 0);
        result = 31 * result + (pointsValue != null ? pointsValue.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + state;
        result = 31 * result + (createMan != null ? createMan.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
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
    @Column(name = "create_man")
    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
