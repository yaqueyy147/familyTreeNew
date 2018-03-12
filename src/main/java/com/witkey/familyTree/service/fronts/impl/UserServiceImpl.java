package com.witkey.familyTree.service.fronts.impl;

import com.witkey.familyTree.dao.consoles.TLogDao;
import com.witkey.familyTree.dao.consoles.TUser1Dao;
import com.witkey.familyTree.dao.consoles.TUserFamilyDao;
import com.witkey.familyTree.dao.consoles.TVolunteerDao;
import com.witkey.familyTree.dao.fronts.TUserFrontDao;
import com.witkey.familyTree.domain.TUser1;
import com.witkey.familyTree.domain.TUserFamily;
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
    private TUserFamilyDao tUserFamilyDao;

    public void settUserFamilyDao(TUserFamilyDao tUserFamilyDao) {
        this.tUserFamilyDao = tUserFamilyDao;
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
            tUserFrontDao.save(tUser1);
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

    @Override
    public int saveUserFamily(TUserFamily tUserFamily){

        //查看用户是否已有可操作的族谱了
        Map<String,Object> map = CommonUtil.bean2Map(tUserFamily);
        List<TUserFamily> list = this.getUserFamilyList(map);
        if(list != null && list.size() > 0){
            return list.size();
        }

        //新增一个用户可操作的族谱
        int i = CommonUtil.parseInt(tUserFamilyDao.create(tUserFamily));

        return i;
    }

    @Override
    public List<TUserFamily> getUserFamilyList(Map<String,Object> params){

        String sql = "select * from t_user_family where 1=1";

        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("userId"))){
                sql += " and user_id=" + params.get("userId");
            }
            if(!CommonUtil.isBlank(params.get("familyId"))){
                sql += " and family_id=" + params.get("familyId");
            }
        }

        List<TUserFamily> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TUserFamily>(TUserFamily.class));
        return list;
    }

    @Override
    public int setUserConsole(int userId, int state) {

        String sql = "update t_user_1 set is_console=? where id=?";

        int i = jdbcTemplate.update(sql,state,userId);

        return i;
    }

    @Override
    public int setUserFront(int userId, int state) {
        String sql = "update t_user_1 set is_front=? where id=?";

        int i = jdbcTemplate.update(sql,state,userId);

        return i;
    }

    @Override
    public int setUserVolunteer(int userId, int state) {
        String sql = "update t_user_1 set is_volunteer=? where id=?";

        int i = jdbcTemplate.update(sql,state,userId);

        return i;
    }

    @Override
    public int deleteMoney(Map<String, Object> params) {
        String ids = params.get("moneyIds") + "";
        String[] id = ids.split(",");

        //删除充值记录
        String sql = "update t_user_money set state=? where id=?";

        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,9,id[i]);
        }

        //修改积分表的充值总金额
        sql = "update t_user_points set total_money=total_money-? where user_id=?";
        ii += jdbcTemplate.update(sql,params.get("totalMoney"),params.get("userId"));

        return ii;
    }

    @Override
    public int setuserrankfamily(Map<String, Object> params) throws Exception {

        String sql = "update t_user_1 set rankfamily=?,rankfamilyname=? where id=?";
        int i = jdbcTemplate.update(sql,params.get("familyid"),params.get("familyname"),params.get("userid"));

        return i;
    }

}
