package com.witkey.familyTree.service.fronts.impl;

import com.witkey.familyTree.dao.consoles.TLogDao;
import com.witkey.familyTree.dao.consoles.TUser1Dao;
import com.witkey.familyTree.dao.consoles.TVolunteerDao;
import com.witkey.familyTree.dao.fronts.TUserFrontDao;
import com.witkey.familyTree.domain.TUser1;
import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.service.fronts.UserFrontService;
import com.witkey.familyTree.service.fronts.UserService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private TUserFrontDao tUserFrontDao;

    public void settUserFrontDao(TUserFrontDao tUserFrontDao) {
        this.tUserFrontDao = tUserFrontDao;
    }

    @Resource
    private TVolunteerDao tVolunteerDao;

    public void settVolunteerDao(TVolunteerDao tVolunteerDao) {
        this.tVolunteerDao = tVolunteerDao;
    }

    @Resource
    private TLogDao tLogDao;

    public void settLogDao(TLogDao tLogDao) {
        this.tLogDao = tLogDao;
    }

    @Resource
    private TUser1Dao tUser1Dao;

    public void settUser1Dao(TUser1Dao tUser1Dao) {
        this.tUser1Dao = tUser1Dao;
    }
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 用户注册
     * @param tUser1
     * @return
     */
    @Override
    public int createUser1(TUser1 tUser1) {
        tUser1.setPassword(CommonUtil.string2MD5(tUser1.getPassword()));
        int id = 0;
        try {
            id = CommonUtil.parseInt(tUser1Dao.create(tUser1));
        }catch (Exception da){
            da.printStackTrace();
        }
        return id;
    }

    @Override
    public int saveUser1(TUser1 tUser1) {
        int i = 0;
        try {
            tUserFrontDao.update(tUser1);
            i ++;
        }catch (Exception e){

        }

        return i;
    }

    /**
     * 用户登录
     * @param tUser1
     * @return
     */
    @Override
    public List<Map<String, Object>> signIn(TUser1 tUser1) {
        String sql = "select * from t_user_1 where login_name=? and password=?";
        //将密码加密
        String password = CommonUtil.string2MD5(tUser1.getPassword());
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,tUser1.getUserName(),password);
        return list;
    }

    /**
     * 根据传入的用户信息查询用户
     * @param tUser1
     * @return
     */
    @Override
    public List<TUser1> getUserInfo1(TUser1 tUser1) {
        String sql = "select * from t_user_1 where state=1";// and password=?

        if(!CommonUtil.isBlank(tUser1.getIsFront()) && tUser1.getIsFront() == 1){
            sql += " and is_front='1'" ;
        }
        if(!CommonUtil.isBlank(tUser1.getIsConsole()) && tUser1.getIsConsole() == 1){
            sql += " and is_console='1'" ;
        }
        if(!CommonUtil.isBlank(tUser1.getLoginName())){
            sql += " and login_name='"+ tUser1.getLoginName() + "'" ;
        }
        if(!CommonUtil.isBlank(tUser1.getUserName())){
            sql += " and user_name='"+ tUser1.getPassword() + "'" ;
        }
        if(!CommonUtil.isBlank(tUser1.getPassword())){
            sql += " and password='"+ tUser1.getPassword() + "'" ;
        }

        //查询
        List<TUser1> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TUser1>(TUser1.class));

        return list;
    }

    @Override
    public int modifyPassword(Map<String,Object> params) {
        String userId = params.get("userId") + "";
        String newPassword = CommonUtil.string2MD5(params.get("newPassword") + "");
        String sql = "update t_user_1 set password=? where id=?";

        if("2".equals(params.get("userType"))){
            sql = "update t_company_sponsor set company_login_password=? where id=?";
        }

        int i = jdbcTemplate.update(sql,newPassword,userId);

        return i;
    }

//    @Override
//    public int applyVolunteer(TVolunteer tVolunteer) {
////        int i = CommonUtil.parseInt(tVolunteerDao.create(tVolunteer));
//
//        String sql = "update t_user_front set is_volunteer=3 where id=?";
//        int i = jdbcTemplate.update(sql,tVolunteer.getUserId());
//
//        return i;
//    }

    @Override
    public int applyVolunteer(int userId) {

        String sql = "update t_user_1 set is_volunteer=3 where id=?";
        int i = jdbcTemplate.update(sql,userId);

        return i;
    }

    @Override
    public int modifyPhoto(String userId, String photoPath, String userType) {

        String sql = "update t_user_1 set user_photo=? where id=?";

        if("2".equals(userType)){
            sql = "update t_company_sponsor set company_photo=? where id=?";
        }

        int i = jdbcTemplate.update(sql,photoPath,userId);
        return i;
    }

    @Override
    public TUser1 getUserInfoFromId(int userId) {
        TUser1 tUser1 = tUser1Dao.get(userId);
        return tUser1;
    }
}
