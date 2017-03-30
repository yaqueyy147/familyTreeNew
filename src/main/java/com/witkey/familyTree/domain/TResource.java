package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;

/**
 * Created by chenxiaowei on 2017/1/11.
 */
@Entity
@Table(name = "t_resource")
public class TResource {
    private int id;
    private String sourceCode;
    private String sourceName;
    private String sourceDesc;
    private Integer sourceLevel;
    private Integer sourceType;
    private String sourceUrl;
    private Integer parentSourceId;
    private String parentSourceCode;
    private Integer state;

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
    @Column(name = "source_code")
    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    @Basic
    @Column(name = "source_name")
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @Basic
    @Column(name = "source_desc")
    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
    }

    @Basic
    @Column(name = "source_level")
    public Integer getSourceLevel() {
        return sourceLevel;
    }

    public void setSourceLevel(Integer sourceLevel) {
        this.sourceLevel = sourceLevel;
    }

    @Basic
    @Column(name = "source_type")
    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    @Basic
    @Column(name = "source_url")
    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Basic
    @Column(name = "parent_source_id")
    public Integer getParentSourceId() {
        return parentSourceId;
    }

    public void setParentSourceId(Integer parentSourceId) {
        this.parentSourceId = parentSourceId;
    }

    @Basic
    @Column(name = "parent_source_code")
    public String getParentSourceCode() {
        return parentSourceCode;
    }

    public void setParentSourceCode(String parentSourceCode) {
        this.parentSourceCode = parentSourceCode;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TResource tResource = (TResource) o;

        if (id != tResource.id) return false;
        if (sourceCode != null ? !sourceCode.equals(tResource.sourceCode) : tResource.sourceCode != null) return false;
        if (sourceName != null ? !sourceName.equals(tResource.sourceName) : tResource.sourceName != null) return false;
        if (sourceDesc != null ? !sourceDesc.equals(tResource.sourceDesc) : tResource.sourceDesc != null) return false;
        if (sourceLevel != null ? !sourceLevel.equals(tResource.sourceLevel) : tResource.sourceLevel != null)
            return false;
        if (sourceType != null ? !sourceType.equals(tResource.sourceType) : tResource.sourceType != null) return false;
        if (sourceUrl != null ? !sourceUrl.equals(tResource.sourceUrl) : tResource.sourceUrl != null) return false;
        if (parentSourceId != null ? !parentSourceId.equals(tResource.parentSourceId) : tResource.parentSourceId != null)
            return false;
        if (parentSourceCode != null ? !parentSourceCode.equals(tResource.parentSourceCode) : tResource.parentSourceCode != null)
            return false;
        if (state != null ? !state.equals(tResource.state) : tResource.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (sourceCode != null ? sourceCode.hashCode() : 0);
        result = 31 * result + (sourceName != null ? sourceName.hashCode() : 0);
        result = 31 * result + (sourceDesc != null ? sourceDesc.hashCode() : 0);
        result = 31 * result + (sourceLevel != null ? sourceLevel.hashCode() : 0);
        result = 31 * result + (sourceType != null ? sourceType.hashCode() : 0);
        result = 31 * result + (sourceUrl != null ? sourceUrl.hashCode() : 0);
        result = 31 * result + (parentSourceId != null ? parentSourceId.hashCode() : 0);
        result = 31 * result + (parentSourceCode != null ? parentSourceCode.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
