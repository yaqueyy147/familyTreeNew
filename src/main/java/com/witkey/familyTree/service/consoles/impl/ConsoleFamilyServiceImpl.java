package com.witkey.familyTree.service.consoles.impl;

import com.witkey.familyTree.service.consoles.ConsoleFamilyService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by suyx on 2017/1/11.
 */
@Service("consoleFamilyService")
public class ConsoleFamilyServiceImpl implements ConsoleFamilyService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 通过族人id屏蔽族人或者解除屏蔽
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public int setHideOrNotFromId(Map<String, Object> params) throws Exception {
        String sql = "update t_people set ishide=? where id in(?) and state=1";
        int i = jdbcTemplate.update(sql,params.get("ishide"),params.get("ids"));
        return i;
    }

    /**
     * 一键屏蔽所有在世族人或者解除屏蔽
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public int setHideOrNotFromFamily(Map<String,Object> params) throws Exception{
        String sql = "update t_people set ishide=? where family_id=? and state=1";
        int i = jdbcTemplate.update(sql,params.get("ishide"),params.get("familyid"));
        return i;
    }
}

