package com.witkey.familyTree.service.fronts;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TPeople;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/22 0022.
 */
public interface FamilyService {

    //创建族谱
    public int createFamily(TFamily tFamily);

    //保存家族成员
    public int savePeople(TPeople tPeople);

    //查询family
    public List<TFamily> getFamilyList(String userName, int familyArea);

    //查询族谱的成员
    public List<TPeople> getPeopleList(int familyId);

    //根据族人是第几代获取其父亲母亲
    public Map<String,Object> getParentFromGen(int familyId,int generation);

    //根据familyId查询family
    public TFamily getFamilyListFromId(int familyId);

    //根据peopleID查询配偶
    public List<TPeople> getMateList(int peopleId);

}
