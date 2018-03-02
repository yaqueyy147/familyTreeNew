package com.witkey.familyTree.service.consoles;

import java.util.Map;

/**
 * Created by suyx on 2017/1/11.
 */
public interface ConsoleFamilyService {
    //通过族人id屏蔽族人
    public int setHideOrNotFromId(Map<String,Object> params) throws Exception;
    //一键屏蔽所有在世族人
    public int setHideOrNotFromFamily(Map<String,Object> params) throws Exception;
}
