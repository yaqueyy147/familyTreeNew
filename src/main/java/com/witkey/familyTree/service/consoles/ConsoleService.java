package com.witkey.familyTree.service.consoles;

import com.witkey.familyTree.domain.*;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/1/11.
 */
public interface ConsoleService {

    public List<Map<String,Object>> getVolunteerApplyList(Map<String,Object> params);

    public List<TUserFront> getUserFrontList(Map<String,Object> params);
    public List<Map<String, Object>> getUser1List(Map<String,Object> params);

    public int auditVolunteer(Map<String,Object> params);

    public List<Map<String,Object>> getCompanyList(Map<String,Object> params);

    public int auditCompany(Map<String,Object> params);

    public List<TUserBase> getUserBase(Map<String,Object> params);

    public int saveUserBase(TUserBase tUserBase);
    public int saveUser1(TUser1 tUser1);

    public int modifyPassword(Map<String,Object> params);

    public int deleteUser(Map<String,Object> params);

    public List<TRole> getRole(Map<String,Object> params);

    public int saveRole(TRole tRole);

    public int deleteRole(Map<String,Object> params);

    public List<TMeritocratAttr> getMeritocratAttrList(Map<String,Object> params);

    public List<Map<String,Object>> getMeritocratList(Map<String,Object> params);

    public int saveMeritocrat(TMeritocrat tMeritocrat);

    public int saveMeritocratAttr(TMeritocratAttr tMeritocratAttr);

    public int deleteMeritocrat(Map<String,Object> params);

    public int deleteMeritocratAttr(Map<String,Object> params);

    public List<Map<String, Object>> getMergeList(Map<String,Object> params);

    public List<TFamily> getTargetMergeList(Map<String,Object> params);

    public int rejectInclude(int mergeId,String rejectDesc,String auditMan);

    public int savePointsRelation(TPointsDic tPointsDic);

    public int deletePointsRelation(String ids);

    public int confirmInclude(Map<String,Object> params);
    
    public int auditIncludePeople(Map<String,Object> params);

    public int saveResource(TResource tResource);

    public int deleteResource(Map<String,Object> params);

    public List<TResource> getResourceList(Map<String,Object> params);

    public List<TUserResource> getUserResource(Map<String,Object> params);

    public int saveAuth(Map<String,Object> params);

    public List<Map<String,Object>> getUserMenu(Map<String,Object> params);
    
    public int completeIn(Map<String,Object> params) throws Exception;
}
