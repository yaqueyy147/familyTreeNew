package com.witkey.familyTree.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
@Entity
@Table(name = "t_mate")
public class TMate implements Serializable {
    private static final long serialVersionUID = 1949041494717743989L;
    private int id;
    private int peopleId;
    private int mateId;
    private String remark;
    private Integer mateType;

    public TMate() {
    }

    public TMate(int peopleId, int mateId, String remark, Integer mateType) {
        this.peopleId = peopleId;
        this.mateId = mateId;
        this.remark = remark;
        this.mateType = mateType;
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
    @Column(name = "people_id")
    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }

    @Basic
    @Column(name = "mate_id")
    public int getMateId() {
        return mateId;
    }

    public void setMateId(int mateId) {
        this.mateId = mateId;
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
    @Column(name = "mate_type")
    public Integer getMateType() {
        return mateType;
    }

    public void setMateType(Integer mateType) {
        this.mateType = mateType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TMate tMate = (TMate) o;

        if (id != tMate.id) return false;
        if (peopleId != tMate.peopleId) return false;
        if (mateId != tMate.mateId) return false;
        if (remark != null ? !remark.equals(tMate.remark) : tMate.remark != null) return false;
        if (mateType != null ? !mateType.equals(tMate.mateType) : tMate.mateType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + peopleId;
        result = 31 * result + mateId;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (mateType != null ? mateType.hashCode() : 0);
        return result;
    }
}
