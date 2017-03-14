package com.witkey.familyTree.domain;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/3/12.
 */
@Entity
@Table(name = "t_meritocrat_attr", schema = "family_tree")
public class TMeritocratAttr {
    private int id;
    private String meritocratAttr;
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
    @Column(name = "meritocrat_attr")
    public String getMeritocratAttr() {
        return meritocratAttr;
    }

    public void setMeritocratAttr(String meritocratAttr) {
        this.meritocratAttr = meritocratAttr;
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

        TMeritocratAttr that = (TMeritocratAttr) o;

        if (id != that.id) return false;
        if (meritocratAttr != null ? !meritocratAttr.equals(that.meritocratAttr) : that.meritocratAttr != null)
            return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (meritocratAttr != null ? meritocratAttr.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
