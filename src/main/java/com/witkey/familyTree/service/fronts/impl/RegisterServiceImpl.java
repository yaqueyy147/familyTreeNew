package com.witkey.familyTree.service.fronts.impl;

import com.witkey.familyTree.dao.fronts.TUserFrontDao;
import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.service.fronts.RegisterService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by suyx on 2016/12/18.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private TUserFrontDao tUserFrontDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public int createUserFront(TUserFront tUserFront) {

        int id = CommonUtil.parseInt(tUserFrontDao.create(tUserFront));
//
//        String sql = "insert into t_user_front(user_name,password,id_card,nick_name,phone,wechart,qq_num,remark)";
//        sql += "values(?,?,?,?,?,?,?,?)";
        return id;
    }
}
