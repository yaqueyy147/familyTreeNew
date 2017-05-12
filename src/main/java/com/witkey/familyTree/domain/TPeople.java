package com.witkey.familyTree.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
@Entity
@Table(name = "t_people")
public class TPeople implements Serializable {
    private static final long serialVersionUID = 8603696737469498574L;
    private Integer id;
    private Integer familyId;
    private Integer generation;
    private String name;
    private String usedName;
    private Integer sex;
    private Integer familyGeneration;
    private Integer familyRank;
    private String generationActor;
    private String nationality;
    private String nation;
    private String education;
    private String job;
    private String idCard;
    private String phone;
    private String email;
    private Integer fatherId;
    private Integer motherId;
    private Date birthTime;
    private String birthAddr;
    private Date dieTime;
    private String dieAddr;
    private String liveAddr;
    private String specialRemark;
    private Integer state;
    private String xing;
    private String artName;
    private String cName;
    private String photoUrl;
    private String remark;
    private String createMan;
    private Date createTime;
    private Integer mateType;
    private Integer peopleType;
    private Integer peopleStatus;

    private Integer isSupplement;

    public TPeople() {
    }

    public TPeople(Integer id, Integer familyId, Integer generation, String name, String usedName, Integer sex, Integer familyGeneration, Integer familyRank, String generationActor, String nationality, String nation, String education, String job, String idCard, String phone, String email, Integer fatherId, Integer motherId, Date birthTime, String birthAddr, Date dieTime, String dieAddr, String liveAddr, String specialRemark, Integer state, String xing, String artName, String cName, String photoUrl, String remark, String createMan, Date createTime, Integer mateType, Integer peopleType, Integer isSupplement) {
        this.id = id;
        this.familyId = familyId;
        this.generation = generation;
        this.name = name;
        this.usedName = usedName;
        this.sex = sex;
        this.familyGeneration = familyGeneration;
        this.familyRank = familyRank;
        this.generationActor = generationActor;
        this.nationality = nationality;
        this.nation = nation;
        this.education = education;
        this.job = job;
        this.idCard = idCard;
        this.phone = phone;
        this.email = email;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.birthTime = birthTime;
        this.birthAddr = birthAddr;
        this.dieTime = dieTime;
        this.dieAddr = dieAddr;
        this.liveAddr = liveAddr;
        this.specialRemark = specialRemark;
        this.state = state;
        this.xing = xing;
        this.artName = artName;
        this.cName = cName;
        this.photoUrl = photoUrl;
        this.remark = remark;
        this.createMan = createMan;
        this.createTime = createTime;
        this.mateType = mateType;
        this.peopleType = peopleType;
        this.isSupplement = isSupplement;
    }

    @Id
    @Column(name = "id",unique = true)
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "family_id")
    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    @Basic
    @Column(name = "generation")
    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "used_name")
    public String getUsedName() {
        return usedName;
    }

    public void setUsedName(String usedName) {
        this.usedName = usedName;
    }

    @Basic
    @Column(name = "sex")
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "family_generation")
    public Integer getFamilyGeneration() {
        return familyGeneration;
    }

    public void setFamilyGeneration(Integer familyGeneration) {
        this.familyGeneration = familyGeneration;
    }

    @Basic
    @Column(name = "family_rank")
    public Integer getFamilyRank() {
        return familyRank;
    }

    public void setFamilyRank(Integer familyRank) {
        this.familyRank = familyRank;
    }

    @Basic
    @Column(name = "generation_actor")
    public String getGenerationActor() {
        return generationActor;
    }

    public void setGenerationActor(String generationActor) {
        this.generationActor = generationActor;
    }

    @Basic
    @Column(name = "nationality")
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Basic
    @Column(name = "nation")
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Basic
    @Column(name = "education")
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Basic
    @Column(name = "job")
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Basic
    @Column(name = "id_card")
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "father_id")
    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    @Basic
    @Column(name = "mother_id")
    public Integer getMotherId() {
        return motherId;
    }

    public void setMotherId(Integer motherId) {
        this.motherId = motherId;
    }

    @Basic
    @Column(name = "birth_time")
    public Date getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(Date birthTime) {
        this.birthTime = birthTime;
    }

    @Basic
    @Column(name = "birth_addr")
    public String getBirthAddr() {
        return birthAddr;
    }

    public void setBirthAddr(String birthAddr) {
        this.birthAddr = birthAddr;
    }

    @Basic
    @Column(name = "die_time")
    public Date getDieTime() {
        return dieTime;
    }

    public void setDieTime(Date dieTime) {
        this.dieTime = dieTime;
    }

    @Basic
    @Column(name = "die_addr")
    public String getDieAddr() {
        return dieAddr;
    }

    public void setDieAddr(String dieAddr) {
        this.dieAddr = dieAddr;
    }

    @Basic
    @Column(name = "live_addr")
    public String getLiveAddr() {
        return liveAddr;
    }

    public void setLiveAddr(String liveAddr) {
        this.liveAddr = liveAddr;
    }

    @Basic
    @Column(name = "special_remark")
    public String getSpecialRemark() {
        return specialRemark;
    }

    public void setSpecialRemark(String specialRemark) {
        this.specialRemark = specialRemark;
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
    @Column(name = "xing")
    public String getXing() {
        return xing;
    }

    public void setXing(String xing) {
        this.xing = xing;
    }

    @Basic
    @Column(name = "art_name")
    public String getArtName() {
        return artName;
    }

    public void setArtName(String artName) {
        this.artName = artName;
    }

    @Basic
    @Column(name = "c_name")
    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    @Basic
    @Column(name = "photo_url")
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    @Basic
    @Column(name = "mate_type")
    public Integer getMateType() {
        return mateType;
    }

    public void setMateType(Integer mateType) {
        this.mateType = mateType;
    }

    @Basic
    @Column(name = "people_type")
    public Integer getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(Integer peopleType) {
        this.peopleType = peopleType;
    }

    @Basic
    @Column(name = "people_status")
    public Integer getPeopleStatus() {
        return peopleStatus;
    }

    public void setPeopleStatus(Integer peopleStatus) {
        this.peopleStatus = peopleStatus;
    }

    @Basic
    @Column(name = "is_supplement")
    public Integer getIsSupplement() {
        return isSupplement;
    }

    public void setIsSupplement(Integer isSupplement) {
        this.isSupplement = isSupplement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TPeople tPeople = (TPeople) o;

        if (id != tPeople.id) return false;
        if (familyId != tPeople.familyId) return false;
        if (generation != null ? !generation.equals(tPeople.generation) : tPeople.generation != null) return false;
        if (name != null ? !name.equals(tPeople.name) : tPeople.name != null) return false;
        if (usedName != null ? !usedName.equals(tPeople.usedName) : tPeople.usedName != null) return false;
        if (sex != null ? !sex.equals(tPeople.sex) : tPeople.sex != null) return false;
        if (familyGeneration != null ? !familyGeneration.equals(tPeople.familyGeneration) : tPeople.familyGeneration != null)
            return false;
        if (familyRank != null ? !familyRank.equals(tPeople.familyRank) : tPeople.familyRank != null) return false;
        if (generationActor != null ? !generationActor.equals(tPeople.generationActor) : tPeople.generationActor != null)
            return false;
        if (nationality != null ? !nationality.equals(tPeople.nationality) : tPeople.nationality != null) return false;
        if (nation != null ? !nation.equals(tPeople.nation) : tPeople.nation != null) return false;
        if (education != null ? !education.equals(tPeople.education) : tPeople.education != null) return false;
        if (job != null ? !job.equals(tPeople.job) : tPeople.job != null) return false;
        if (idCard != null ? !idCard.equals(tPeople.idCard) : tPeople.idCard != null) return false;
        if (phone != null ? !phone.equals(tPeople.phone) : tPeople.phone != null) return false;
        if (email != null ? !email.equals(tPeople.email) : tPeople.email != null) return false;
        if (fatherId != null ? !fatherId.equals(tPeople.fatherId) : tPeople.fatherId != null) return false;
        if (motherId != null ? !motherId.equals(tPeople.motherId) : tPeople.motherId != null) return false;
        if (birthTime != null ? !birthTime.equals(tPeople.birthTime) : tPeople.birthTime != null) return false;
        if (birthAddr != null ? !birthAddr.equals(tPeople.birthAddr) : tPeople.birthAddr != null) return false;
        if (dieTime != null ? !dieTime.equals(tPeople.dieTime) : tPeople.dieTime != null) return false;
        if (dieAddr != null ? !dieAddr.equals(tPeople.dieAddr) : tPeople.dieAddr != null) return false;
        if (liveAddr != null ? !liveAddr.equals(tPeople.liveAddr) : tPeople.liveAddr != null) return false;
        if (specialRemark != null ? !specialRemark.equals(tPeople.specialRemark) : tPeople.specialRemark != null)
            return false;
        if (state != null ? !state.equals(tPeople.state) : tPeople.state != null) return false;
        if (xing != null ? !xing.equals(tPeople.xing) : tPeople.xing != null) return false;
        if (artName != null ? !artName.equals(tPeople.artName) : tPeople.artName != null) return false;
        if (cName != null ? !cName.equals(tPeople.cName) : tPeople.cName != null) return false;
        if (photoUrl != null ? !photoUrl.equals(tPeople.photoUrl) : tPeople.photoUrl != null) return false;
        if (remark != null ? !remark.equals(tPeople.remark) : tPeople.remark != null) return false;
        if (createMan != null ? !createMan.equals(tPeople.createMan) : tPeople.createMan != null) return false;
        if (createTime != null ? !createTime.equals(tPeople.createTime) : tPeople.createTime != null) return false;
        if (mateType != null ? !mateType.equals(tPeople.mateType) : tPeople.mateType != null) return false;
        if (peopleType != null ? !peopleType.equals(tPeople.peopleType) : tPeople.peopleType != null) return false;
        if (peopleStatus != null ? !peopleStatus.equals(tPeople.peopleStatus) : tPeople.peopleStatus != null) return false;
        if (isSupplement != null ? !isSupplement.equals(tPeople.isSupplement) : tPeople.isSupplement != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + familyId;
        result = 31 * result + (generation != null ? generation.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (usedName != null ? usedName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (familyGeneration != null ? familyGeneration.hashCode() : 0);
        result = 31 * result + (familyRank != null ? familyRank.hashCode() : 0);
        result = 31 * result + (generationActor != null ? generationActor.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (nation != null ? nation.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);
        result = 31 * result + (job != null ? job.hashCode() : 0);
        result = 31 * result + (idCard != null ? idCard.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fatherId != null ? fatherId.hashCode() : 0);
        result = 31 * result + (motherId != null ? motherId.hashCode() : 0);
        result = 31 * result + (birthTime != null ? birthTime.hashCode() : 0);
        result = 31 * result + (birthAddr != null ? birthAddr.hashCode() : 0);
        result = 31 * result + (dieTime != null ? dieTime.hashCode() : 0);
        result = 31 * result + (dieAddr != null ? dieAddr.hashCode() : 0);
        result = 31 * result + (liveAddr != null ? liveAddr.hashCode() : 0);
        result = 31 * result + (specialRemark != null ? specialRemark.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (xing != null ? xing.hashCode() : 0);
        result = 31 * result + (artName != null ? artName.hashCode() : 0);
        result = 31 * result + (cName != null ? cName.hashCode() : 0);
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createMan != null ? createMan.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (mateType != null ? mateType.hashCode() : 0);
        result = 31 * result + (peopleType != null ? peopleType.hashCode() : 0);
        result = 31 * result + (peopleStatus != null ? peopleStatus.hashCode() : 0);
        result = 31 * result + (isSupplement != null ? isSupplement.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
