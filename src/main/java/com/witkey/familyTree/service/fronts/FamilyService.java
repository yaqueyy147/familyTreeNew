package com.witkey.familyTree.service.fronts;

import com.witkey.familyTree.domain.*;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/22 0022.
 */
public interface FamilyService {

    //创建族谱
    public int createFamily(TFamily tFamily);

    //修改族谱
    public int updateFamily(TFamily tFamily);

    //删除族谱
    public int deleteFamily(Map<String,Object> params);

    //保存家族成员
    public int savePeople(TPeople tPeople);

    //修改成员信息
    public void updatePeople(TPeople tPeople);

    //保存配偶信息
    public int saveMateInfo(TMate tMate);

    //查询family
    public List<TFamily> getFamilyList(Map<String,Object> params);

    //查询被收录的族谱
    public List<TFamily> getIncludeFamilyList(Map<String,Object> params);

    //查询族谱的成员
    public List<TPeople> getPeopleList(Map<String,Object> params);//int familyId,int peopleType

    //根据族人是第几代获取其父亲母亲
    public Map<String,Object> getParentFromGen(int familyId,int generation);

    //根据familyId查询family
    public TFamily getFamilyFromId(int familyId);

    //根据peopleID查询配偶
    public List<TPeople> getMateList(int peopleId);

    //根据peopleID查询people信息
    public TPeople getPeopleInfo(int peopleId);

    //查询积分对应关系
    public List<TPointsDic> getPointsRelation(int type,int state);

    //充值或者刷新积分
    public int setPoints(Object object,int type);

    //获取积分排名
    public List<Map<String,Object>> getPointsRanking(Map<String, Object> params);

    //获取英才录
    public List<Map<String,Object>> getMeritocrat(Map<String,Object> params);

    //根据条件获取英才总数
    public int getTotalMeritocrat(Map<String,Object> params);

    //获取所有英才的属地
    public List<Map<String,Object>> getMeritocratArea();

    //根据条件从t_people表中查询familyID
    public List<Map<String,Object>> getFamilyIdForMerge(Map<String,Object> params);

    //记录申请收录
    public int saveInclude(TFamilyMerge tFamilyMerge);

    //查询收录情况
    public List<TFamilyMerge> getMergeList(Map<String,Object> params);
}
