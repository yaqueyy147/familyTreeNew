package com.witkey.familyTree.service.consoles;

import com.witkey.familyTree.domain.TLog;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/3/30 0030.
 */
public interface LogService {

    public int createLog(TLog tLog);

    public List<TLog> logList(Map<String,Object> params);
}
