package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by suyx on 2016/12/21 0021.
 */
@Entity
@Table(name = "t_user_family")
public class TUserFamily implements Serializable{
    private static final long serialVersionUID = -4662236187809073817L;
    private int id;
    private int userId;
    private int familyId;
    private String remark;

    public TUserFamily() {
    }

    public TUserFamily(int userId, int familyId) {
        this.userId = userId;
        this.familyId = familyId;
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
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "family_id")
    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
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

        TUserFamily tMate = (TUserFamily) o;

        if (id != tMate.id) return false;
        if (userId != tMate.userId) return false;
        if (familyId != tMate.familyId) return false;
        if (remark != null ? !remark.equals(tMate.remark) : tMate.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + familyId;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
