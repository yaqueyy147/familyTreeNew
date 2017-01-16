package com.witkey.familyTree.service.consoles;

import com.witkey.familyTree.domain.TVolunteer;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/1/11.
 */
public interface ConsoleService {

    public List<Map<String,Object>> getVolunteerApplyList(Map<String,Object> params);

    public int auditVolunteer(Map<String,Object> params);
}
