package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/14.
 */
@Entity
@Table(name = "t_family_merge", schema = "family_tree")
public class TFamilyMerge {
    private int id;
    private Integer primaryFamilyId;
    private Integer targetFamilyId;
    private Integer state;
    private String rejectDesc;
    private String applyMan;
    private Date applyTime;
    private String auditMan;
    private Date auditTime;
    private String remark;

    public TFamilyMerge() {

    }

    public TFamilyMerge(Integer primaryFamilyId, Integer targetFamilyId, Integer state, String applyMan, Date applyTime) {
        this.primaryFamilyId = primaryFamilyId;
        this.targetFamilyId = targetFamilyId;
        this.state = state;
        this.applyMan = applyMan;
        this.applyTime = applyTime;
    }

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
    @Column(name = "primary_family_id")
    public Integer getPrimaryFamilyId() {
        return primaryFamilyId;
    }

    public void setPrimaryFamilyId(Integer primaryFamilyId) {
        this.primaryFamilyId = primaryFamilyId;
    }

    @Basic
    @Column(name = "target_family_id")
    public Integer getTargetFamilyId() {
        return targetFamilyId;
    }

    public void setTargetFamilyId(Integer targetFamilyId) {
        this.targetFamilyId = targetFamilyId;
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
    @Column(name = "apply_man")
    public String getApplyMan() {
        return applyMan;
    }

    public void setApplyMan(String applyMan) {
        this.applyMan = applyMan;
    }

    @Basic
    @Column(name = "apply_time")
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Basic
    @Column(name = "audit_man")
    public String getAuditMan() {
        return auditMan;
    }

    public void setAuditMan(String auditMan) {
        this.auditMan = auditMan;
    }

    @Basic
    @Column(name = "audit_time")
    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
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
    @Column(name = "reject_desc")
    public String getRejectDesc() {
        return rejectDesc;
    }

    public void setRejectDesc(String rejectDesc) {
        this.rejectDesc = rejectDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TFamilyMerge that = (TFamilyMerge) o;

        if (id != that.id) return false;
        if (primaryFamilyId != that.primaryFamilyId)
            return false;
        if (targetFamilyId != that.targetFamilyId)
            return false;
        if (rejectDesc != null ? !rejectDesc.equals(that.rejectDesc) : that.rejectDesc != null) return false;
        if (state != that.state) return false;
        if (applyMan != null ? !applyMan.equals(that.applyMan) : that.applyMan != null) return false;
        if (applyTime != null ? !applyTime.equals(that.applyTime) : that.applyTime != null) return false;
        if (auditMan != null ? !auditMan.equals(that.auditMan) : that.auditMan != null) return false;
        if (auditTime != null ? !auditTime.equals(that.auditTime) : that.auditTime != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + primaryFamilyId;
        result = 31 * result + targetFamilyId;
        result = 31 * result + (rejectDesc != null ? rejectDesc.hashCode() : 0);
        result = 31 * result + state;
        result = 31 * result + (applyMan != null ? applyMan.hashCode() : 0);
        result = 31 * result + (applyTime != null ? applyTime.hashCode() : 0);
        result = 31 * result + (auditMan != null ? auditMan.hashCode() : 0);
        result = 31 * result + (auditTime != null ? auditTime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
