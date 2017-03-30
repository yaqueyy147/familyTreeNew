package com.witkey.familyTree.service.consoles.impl;

import com.witkey.familyTree.dao.consoles.TLogDao;
import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TLog;
import com.witkey.familyTree.service.consoles.LogService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/3/30 0030.
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    @Resource
    private TLogDao tLogDao;

    public void settLogDao(TLogDao tLogDao) {
        this.tLogDao = tLogDao;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int createLog(TLog tLog) {
        int i = CommonUtil.parseInt(tLogDao.create(tLog));
        return i;
    }

    @Override
    public List<TLog> logList(Map<String, Object> params) {

        String sql = "select * from t_log where 1=1";

        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("beginTime"))){
                sql += " and operate_time>'" + params.get("beginTime") + "'";
            }
            if(!CommonUtil.isBlank(params.get("endTime"))){
                sql += " and operate_time<='" + params.get("endTime") + "'";
            }
            if(!CommonUtil.isBlank(params.get("operateMan"))){
                sql += " and operate_man='" + params.get("operateMan") + "'";
            }
            if(!CommonUtil.isBlank(params.get("operateType"))){
                sql += " and operate_type='" + params.get("operateType") + "'";
            }
        }

        List<TLog> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TLog>(TLog.class));

        return list;
    }
}
