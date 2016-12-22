package com.witkey.familyTree.service.fronts;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TPeople;

/**
 * Created by suyx on 2016/12/22 0022.
 */
public interface FamilyService {

    //创建族谱
    public int createFamily(TFamily tFamily);

    //保存家族成员
    public int savePeople(TPeople tPeople);

}
