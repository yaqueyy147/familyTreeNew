package com.witkey.familyTree.domain;

import com.witkey.familyTree.util.CommonUtil;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by suyx on 2017/3/30 0030.
 */
@Entity
@Table(name = "t_log")
public class TLog implements Serializable {
    private static final long serialVersionUID = -4436397449360662590L;
    private int id;
    private Integer operateType;
    private String operateMan;
    private String operateTime;
    private String operateContent;
    private String operateContentOld;
    private String remark;

    public TLog() {
    }

    public TLog(Integer operateType, String operateMan, String operateContent, String operateContentOld) {
        this.operateType = operateType;
        this.operateMan = operateMan;
        this.operateContent = operateContent;
        this.operateContentOld = operateContentOld;
        this.operateTime = CommonUtil.getDateLong();
    }

    public TLog(Integer operateType, String operateMan, String operateContent) {
        this.operateType = operateType;
        this.operateMan = operateMan;
        this.operateContent = operateContent;
        this.operateTime = CommonUtil.getDateLong();
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
    @Column(name = "operate_type")
    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    @Basic
    @Column(name = "operate_man")
    public String getOperateMan() {
        return operateMan;
    }

    public void setOperateMan(String operateMan) {
        this.operateMan = operateMan;
    }

    @Basic
    @Column(name = "operate_time")
    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    @Basic
    @Column(name = "operate_content")
    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    @Basic
    @Column(name = "operate_content_old")
    public String getOperateContentOld() {
        return operateContentOld;
    }

    public void setOperateContentOld(String operateContentOld) {
        this.operateContentOld = operateContentOld;
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

        TLog tLog = (TLog) o;

        if (id != tLog.id) return false;
        if (operateType != null ? !operateType.equals(tLog.operateType) : tLog.operateType != null) return false;
        if (operateMan != null ? !operateMan.equals(tLog.operateMan) : tLog.operateMan != null) return false;
        if (operateTime != null ? !operateTime.equals(tLog.operateTime) : tLog.operateTime != null) return false;
        if (operateContent != null ? !operateContent.equals(tLog.operateContent) : tLog.operateContent != null)
            return false;
        if (operateContentOld != null ? !operateContentOld.equals(tLog.operateContentOld) : tLog.operateContentOld != null)
            return false;
        if (remark != null ? !remark.equals(tLog.remark) : tLog.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (operateType != null ? operateType.hashCode() : 0);
        result = 31 * result + (operateMan != null ? operateMan.hashCode() : 0);
        result = 31 * result + (operateTime != null ? operateTime.hashCode() : 0);
        result = 31 * result + (operateContent != null ? operateContent.hashCode() : 0);
        result = 31 * result + (operateContentOld != null ? operateContentOld.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
