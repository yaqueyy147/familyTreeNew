package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;

/**
 * Created by chenxiaowei on 2017/1/11.
 */
@Entity
@Table(name = "t_user_resource", schema = "family_tree")
public class TUserResource {
    private int id;
    private int userId;
    private int resourceId;
    private Integer state;
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
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "resource_id")
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TUserResource that = (TUserResource) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (resourceId != that.resourceId) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + resourceId;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
