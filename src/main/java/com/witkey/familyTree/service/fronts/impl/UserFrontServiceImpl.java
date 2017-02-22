package com.witkey.familyTree.service.fronts.impl;

import com.witkey.familyTree.dao.fronts.TUserFrontDao;
import com.witkey.familyTree.domain.TUserBase;
import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.service.fronts.UserFrontService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
@Service("userFrontService")
public class UserFrontServiceImpl implements UserFrontService {

    @Resource
    private TUserFrontDao tUserFrontDao;

    public void settUserFrontDao(TUserFrontDao tUserFrontDao) {
        this.tUserFrontDao = tUserFrontDao;
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 用户注册
     * @param tUserFront
     * @return
     */
    @Override
    public int createUserFront(TUserFront tUserFront) {

        tUserFront.setPassword(CommonUtil.string2MD5(tUserFront.getPassword()));
        int id = 0;
        try {
            id = CommonUtil.parseInt(tUserFrontDao.create(tUserFront));
        }catch (Exception da){
            da.printStackTrace();
        }
        return id;
    }

    @Override
    public int saveUserFront(TUserFront tUserFront) {
        int i = 0;
        try {
            tUserFrontDao.save(tUserFront);
            i ++;
        }catch (Exception e){

        }

        return i;
    }

    /**
     * 用户登录
     * @param tUserFront
     * @return
     */
    @Override
    public List<Map<String, Object>> signIn(TUserFront tUserFront) {
        String sql = "select * from t_user_front where user_name=? and password=?";
        //将密码加密
        String password = CommonUtil.string2MD5(tUserFront.getPassword());
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,tUserFront.getUserName(),password);
        return list;
    }

    /**
     * 根据传入的用户信息查询用户
     * @param tUserFront
     * @return
     */
    @Override
    public List<TUserFront> getUserInfo(TUserFront tUserFront) {
        //将密码加密
        String password = CommonUtil.string2MD5(tUserFront.getPassword());
        String sql = "select * from t_user_front where user_name=? and password=?";

        //查询
        List<TUserFront> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TUserFront>(TUserFront.class),tUserFront.getUserName(),password);

        return list;
    }
}
