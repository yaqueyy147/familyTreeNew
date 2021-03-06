package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/9.
 */
@Entity
@Table(name = "t_user_points", schema = "family_tree")
public class TUserPoints implements Serializable {
    private static final long serialVersionUID = -5896603769275597189L;
    private int id;
    private int userId;
    private int points;
    private int userType;
    private String remark;
    private int inputCount;
    private Double totalMoney;

    public TUserPoints() {
    }

    public TUserPoints(int userId, int points) {
        this.userId = userId;
        this.points = points;
    }

    public TUserPoints(int userId, int points, int userType) {
        this.userId = userId;
        this.points = points;
        this.userType = userType;
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
    @Column(name = "user_type")
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "input_count")
    public int getInputCount() {
        return inputCount;
    }

    public void setInputCount(int inputCount) {
        this.inputCount = inputCount;
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

        TUserPoints that = (TUserPoints) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (points != that.points) return false;
        if (userType != that.userType) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        if (inputCount != that.inputCount) return false;
        
        if (totalMoney != null ? !totalMoney.equals(that.totalMoney) : that.totalMoney != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + points;
        result = 31 * result + userType;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + inputCount;
        
        result = 31 * result + (totalMoney != null ? totalMoney.hashCode() : 0);;
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
