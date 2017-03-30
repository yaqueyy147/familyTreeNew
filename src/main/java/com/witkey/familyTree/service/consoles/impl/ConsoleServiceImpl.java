package com.witkey.familyTree.service.consoles.impl;

import com.witkey.familyTree.dao.consoles.*;
import com.witkey.familyTree.dao.fronts.TFamilyMergeDao;
import com.witkey.familyTree.dao.fronts.TMeritocratDao;
import com.witkey.familyTree.dao.fronts.TPointsDicDao;
import com.witkey.familyTree.dao.fronts.TUserFrontDao;
import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/1/11.
 */
@Service("consoleService")
public class ConsoleServiceImpl implements ConsoleService {

    @Autowired
    private TVolunteerDao tVolunteerDao;

    public void settVolunteerDao(TVolunteerDao tVolunteerDao){
        this.tVolunteerDao = tVolunteerDao;
    }

    @Autowired
    private TUserBaseDao tUserBaseDao;

    public void settUserBaseDao(TUserBaseDao tUserBaseDao) {
        this.tUserBaseDao = tUserBaseDao;
    }

    @Autowired
    private TRoleDao tRoleDao;

    public void settRoleDao(TRoleDao tRoleDao) {
        this.tRoleDao = tRoleDao;
    }

    @Resource
    private TMeritocratAttrDao tMeritocratAttrDao;

    public void settMeritocratAttrDao(TMeritocratAttrDao tMeritocratAttrDao) {
        this.tMeritocratAttrDao = tMeritocratAttrDao;
    }

    @Resource
    private TMeritocratDao tMeritocratDao;

    public void settMeritocratDao(TMeritocratDao tMeritocratDao) {
        this.tMeritocratDao = tMeritocratDao;
    }

    @Resource
    private TFamilyMergeDao tFamilyMergeDao;

    public void settFamilyMergeDao(TFamilyMergeDao tFamilyMergeDao) {
        this.tFamilyMergeDao = tFamilyMergeDao;
    }

    @Resource
    private TPointsDicDao tPointsDicDao;

    public void settPointsDicDao(TPointsDicDao tPointsDicDao) {
        this.tPointsDicDao = tPointsDicDao;
    }

    @Resource
    private TUserFrontDao tUserFrontDao;

    public void settUserFrontDao(TUserFrontDao tUserFrontDao) {
        this.tUserFrontDao = tUserFrontDao;
    }

    @Resource
    private TLogDao tLogDao;

    public void settLogDao(TLogDao tLogDao) {
        this.tLogDao = tLogDao;
    }

    @Autowired
    private CompanyService companyService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String,Object>> getVolunteerApplyList(Map<String,Object> params) {

        String sql = "select t1.id volunteerId,t1.apply_desc,t1.audit_state,t1.create_time applyTime, ";
        sql += " t2.id userId,t2.user_name,t2.phone,t2.is_volunteer";
        sql += " from t_volunteer t1,t_user_front t2 where t1.user_id=t2.id";// and t1.audit_state=?

//        System.out.println(sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);//,0

        return list;
    }

    @Override
    public List<TUserFront> getUserFrontList(Map<String, Object> params) {

        List<TUserFront> list = tUserFrontDao.find(params);

        return list;
    }

    @Override
    public int auditVolunteer(Map<String, Object> params) {

//        String sql = "update t_volunteer set audit_state=?,audit_desc=?,audit_time=now(),audit_man=?";
//        sql += " where id=?";
//
//        int i = jdbcTemplate.update(sql,params.get("auditState"),params.get("auditDesc"),params.get("auditMan"),params.get("volunteerId"));

        String sql = "update t_user_front set is_volunteer=? where id=?";
        int i = jdbcTemplate.update(sql,params.get("auditState"),params.get("applyManId"));

        return i;
    }

    @Override
    public List<Map<String, Object>> getCompanyList(Map<String, Object> params) {

        String sql = "select * from t_company_sponsor";// where state=?
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);//,params.get("state")

        for (Map<String,Object> map : list) {
            int companyId = CommonUtil.parseInt(map.get("id"));
            double total = companyService.getTotalCompanyMoney(companyId);

            String photo = map.get("company_photo") + "";
            String license = map.get("business_license") + "";
            if(CommonUtil.isBlank(photo)){
                map.put("company_photo","/static/images/default1.jpg");
            }
            if(CommonUtil.isBlank(license)){
                map.put("business_license","/static/images/default2.jpg");
            }

            map.put("totalMoney",total);
        }

        return list;
    }

    @Override
    public List<TUserBase> getUserBase(Map<String, Object> params) {

        String sql = "select * from t_user_base where state<>9";

        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("id"))){
                sql += " and id='" + params.get("id") + "'";
            }
            if(!CommonUtil.isBlank(params.get("userName"))){
                sql += " and user_name='" + params.get("userName") + "'";
            }
            if(!CommonUtil.isBlank(params.get("userPassword"))){
                sql += " and user_password='" + params.get("userPassword") + "'";
            }
        }

//        List<TUserBase> list = tUserBaseDao.find(params);
        List<TUserBase> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TUserBase>(TUserBase.class));

        return list;
    }

    @Override
    public int saveUserBase(TUserBase tUserBase) {
        int i = 0;
        if(tUserBase.getId() == 0){
            i = CommonUtil.parseInt(tUserBaseDao.create(tUserBase));
        }else{
            tUserBaseDao.save(tUserBase);
            i ++ ;
        }
        return i;
    }

    @Override
    public int modifyPassword(Map<String, Object> params) {

        String sql = "update t_user_base set user_password=? where id=?";
        String newPassword = CommonUtil.string2MD5(params.get("newPassword") + "");
        int i = jdbcTemplate.update(sql,newPassword,params.get("userId"));

        return i;
    }

    @Override
    public int deleteUser(Map<String, Object> params) {

        String ids = params.get("ids") + "";
        String[] id = ids.split(",");

//        String sql = "delete from t_user_base where id=?";
        String sql = "update t_user_base set state=9 where id=?";
        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }

        return ii;
    }

    @Override
    public List<TRole> getRole(Map<String, Object> params) {

        String sql = "select * from t_role where state<>9";

        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("id"))){
                sql += " and id='" + params.get("id") + "'";
            }
            if(!CommonUtil.isBlank(params.get("roleName"))){
                sql += " and role_name='" + params.get("roleName") + "'";
            }
        }

        List<TRole> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TRole>(TRole.class));
//        List<TRole> list = tRoleDao.find(params);
        return list;
    }

    @Override
    public int saveRole(TRole tRole) {
        int i = 0;
        if(tRole.getId() == 0){
            i = CommonUtil.parseInt(tRoleDao.create(tRole));
        }else{
            tRoleDao.save(tRole);
            i ++ ;
        }
        return i;
    }

    @Override
    public int deleteRole(Map<String, Object> params) {
        String ids = params.get("ids") + "";
        String[] id = ids.split(",");

//        String sql = "delete from t_role where id=?";
        String sql = "update t_role set state=9 where id=?";
        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }

        return ii;
    }

    @Override
    public List<TMeritocratAttr> getMeritocratAttrList(Map<String, Object> params) {

//        Map<String,Object> filter = new HashMap<String,Object>();
//        if(!CommonUtil.isBlank(params.get("id"))){
//            filter.put("")
//        }

        String sql = "select * from t_meritocrat_attr where state<>9";

        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("id"))){
                sql += " and id=" + params.get("id");
            }
            if(!CommonUtil.isBlank(params.get("meritocratAttr"))){
                sql += " and meritocrat_attr=" + params.get("meritocratAttr");
            }
        }

//        List<TMeritocratAttr> list = tMeritocratAttrDao.find(params);

        List<TMeritocratAttr> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TMeritocratAttr>(TMeritocratAttr.class));
        return list;
    }

    @Override
    public List<Map<String,Object>> getMeritocratList(Map<String, Object> params) {

        String sql = "select t1.*,t2.meritocrat_attr";
        sql += " from t_meritocrat t1,t_meritocrat_attr t2 ";
        sql += " where t1.meritocrat_attr_id=t2.id and t1.state<>9";// and t2.state=1

        if(!CommonUtil.isBlank(params)){

            if(!CommonUtil.isBlank(params.get("meritocrat_attr_id"))){
                sql += " and meritocrat_attr_id='" + params.get("meritocrat_attr_id") + "'";
            }
            if(!CommonUtil.isBlank(params.get("meritocrat_name"))){
                sql += " and meritocrat_name like'%" + params.get("meritocrat_name") + "%'";
            }
            if(!CommonUtil.isBlank(params.get("meritocrat_area"))){
                sql += " and meritocrat_area='" + params.get("meritocrat_area") + "'";
            }
        }

//        List<TMeritocrat> list = tMeritocratDao.find(params);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public int saveMeritocrat(TMeritocrat tMeritocrat) {
        int i=0;
        if(tMeritocrat.getId() == 0){
            i = CommonUtil.parseInt(tMeritocratDao.create(tMeritocrat));
        }else{
            tMeritocratDao.save(tMeritocrat);
            i ++ ;
        }
        return i;
    }

    @Override
    public int saveMeritocratAttr(TMeritocratAttr tMeritocratAttr) {
        int i=0;
        if(tMeritocratAttr.getId() == 0){
            i = CommonUtil.parseInt(tMeritocratAttrDao.create(tMeritocratAttr));
        }else{
            tMeritocratAttrDao.save(tMeritocratAttr);
            i ++ ;
        }
        return i;
    }

    @Override
    public int deleteMeritocrat(Map<String, Object> params) {

        String ids = params.get("ids") + "";
        String[] id = ids.split(",");

//        String sql = "delete from t_meritocrat where id=?";
        String sql = "update t_meritocrat set state=9 where id=?";
        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }

        return ii;
    }

    @Override
    public int deleteMeritocratAttr(Map<String, Object> params) {
        String ids = params.get("ids") + "";
        String[] id = ids.split(",");

//        String sql = "delete from t_meritocrat_attr where id=?";
        String sql = "update t_meritocrat_attr set state=9 where id=?";
        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }

        return ii;
    }

    @Override
    public List<Map<String, Object>> getMergeList(Map<String, Object> params) {

//        String sql = "select tMerge.id mergeId, tPrimary.id primaryId, tTarget.id targetId";
//        sql += " ,tPrimary.family_name primaryName, tTarget.family_name targetName ";
//        sql += " from t_family tPrimary, t_family tTarget, t_family_merge tMerge";
//        sql += " where tPrimary.id=tMerge.primary_family_id and tTarget.id=tMerge.target_family_id";

        String sql = "select distinct tPrimary.*,tMerge.id mergeId,tMerge.state,tMerge.apply_man";
        sql += ",tMerge.state mergeState";
        sql += " from t_family tPrimary, t_family_merge tMerge";
        sql += " where tPrimary.id=tMerge.primary_family_id";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());

        return list;
    }

    @Override
    public List<TFamily> getTargetMergeList(Map<String, Object> params) {
        String sql = "select * from t_family where id in ";
        sql += "(select target_family_id from t_family_merge where primary_family_id=?)";

        List<TFamily> list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<TFamily>(TFamily.class),params.get("primaryFamilyId"));

        return list;
    }

    @Override
    public int rejectInclude(int mergeId,String rejectDesc,String auditMan) {
        String sql = "update t_family_merge set state=3,reject_desc=?,audit_man=?,audit_time=now() where id=?";

        int i = jdbcTemplate.update(sql,rejectDesc,auditMan,mergeId);

        return i;
    }

    @Override
    public int savePointsRelation(TPointsDic tPointsDic) {
        int i = 0;
        if(tPointsDic.getId() <= 0 || CommonUtil.isBlank(tPointsDic.getId())){
            //现将同意类型的积分关系设置为不可用
            String sql = "update t_points_dic set state=2 where points_type=?";
            jdbcTemplate.update(sql,tPointsDic.getPointsType());
            //新创建一个积分对应关系
            i = CommonUtil.parseInt(tPointsDicDao.create(tPointsDic));

        }else{
            tPointsDicDao.save(tPointsDic);
            i ++ ;
        }
        return i;
    }

    @Override
    public int deletePointsRelation(String ids){
        String[] id = ids.split(",");

//        String sql = "delete from t_points_dic where id=?";
        String sql = "update t_points_dic where state=9 where id=?";
        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }

        return ii;
    }

    @Override
    public int confirmInclude(Map<String, Object> params) {

        int i = 0;
        String sql = "update t_family set state=2 where id=?";
        i += jdbcTemplate.update(sql,params.get("familyId"));
        sql = "update t_family_merge set state=1 where primary_family_id=? and (state=2 or state=0)";
        i += jdbcTemplate.update(sql,params.get("familyId"));
        return i;
    }
}

