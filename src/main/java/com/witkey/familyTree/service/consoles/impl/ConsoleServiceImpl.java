package com.witkey.familyTree.service.consoles.impl;

import com.witkey.familyTree.dao.consoles.*;
import com.witkey.familyTree.dao.fronts.*;
import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
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

    @Resource
    private TUser1Dao tUser1Dao;

    public void settUser1Dao(TUser1Dao tUser1Dao) {
        this.tUser1Dao = tUser1Dao;
    }

    @Resource
    private TResourceDao tResourceDao;

    public void settResourceDao(TResourceDao tResourceDao) {
        this.tResourceDao = tResourceDao;
    }

    @Resource
    private TUserResourceDao tUserResourceDao;

    public void settUserResourceDao(TUserResourceDao tUserResourceDao) {
        this.tUserResourceDao = tUserResourceDao;
    }

    @Resource
    private TCompanyPointsDao tCompanyPointsDao;

    public void settCompanyPointsDao(TCompanyPointsDao tCompanyPointsDao) {
        this.tCompanyPointsDao = tCompanyPointsDao;
    }

    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private FamilyService familyService;

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
    public List<Map<String, Object>> getUser1List(Map<String, Object> params) {

        String sql = "select * from t_user_1 where state<>9";

        if(!CommonUtil.isBlank(params.get("id"))){
            sql += " and id='" + params.get("id") + "'";
        }
        if(!CommonUtil.isBlank(params.get("userFrom"))){
            sql += " and user_from='" + params.get("userFrom") + "'";
        }
        if(!CommonUtil.isBlank(params.get("userName"))){
            sql += " and user_name like '%" + params.get("userName") + "%'";
        }
        if(!CommonUtil.isBlank(params.get("loginName"))){
            sql += " and login_name like '%" + params.get("loginName") + "%'";
        }
        if(!CommonUtil.isBlank(params.get("province"))){
            sql += " and province='" + params.get("province") + "'";
        }
        if(!CommonUtil.isBlank(params.get("city"))){
            sql += " and city='" + params.get("city") + "'";
        }
        if(!CommonUtil.isBlank(params.get("district"))){
            sql += " and district='" + params.get("district") + "'";
        }
//        List<TUser1> list = tUser1Dao.find(params);
        List<TUser1> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TUser1>(TUser1.class));
        
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        
        for (TUser1 tUser1 : list) {
			int userId = tUser1.getId();
			double total = familyService.getTotalUserMoney(userId);
			Map<String,Object> temp = CommonUtil.bean2Map(tUser1);
			temp.put("totalMoney", total);
			result.add(temp);
		}
        return result;
    }

    @Override
    public int auditVolunteer(Map<String, Object> params) {

//        String sql = "update t_volunteer set audit_state=?,audit_desc=?,audit_time=now(),audit_man=?";
//        sql += " where id=?";
//
//        int i = jdbcTemplate.update(sql,params.get("auditState"),params.get("auditDesc"),params.get("auditMan"),params.get("volunteerId"));

//        String sql = "update t_user_front set is_volunteer=? where id=?";
        String sql = "update t_user_1 set is_volunteer=?,state=1 where id=?";
        int i = jdbcTemplate.update(sql,params.get("auditState"),params.get("applyManId"));

        return i;
    }

    @Override
    public List<Map<String, Object>> getCompanyList(Map<String, Object> params) {

        String sql = "select * from t_company_sponsor where 1=1";// where state=?

        if(!CommonUtil.isBlank(params)){

            if(!CommonUtil.isBlank(params.get("companyName"))){
                sql += " and company_name like '%" + params.get("companyName") + "%'";
            }
            if(!CommonUtil.isBlank(params.get("province"))){
                sql += " and province='" + params.get("province") + "'";
            }
            if(!CommonUtil.isBlank(params.get("city"))){
                sql += " and city='" + params.get("city") + "'";
            }
            if(!CommonUtil.isBlank(params.get("district"))){
                sql += " and district='" + params.get("district") + "'";
            }

        }

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);//,params.get("state")

        for (Map<String,Object> map : list) {
            int companyId = CommonUtil.parseInt(map.get("id"));
            double total = companyService.getTotalCompanyMoney(companyId);

            String photo = map.get("company_photo") + "";
            String license = map.get("business_license") + "";
            if(CommonUtil.isBlank(photo)){
                map.put("company_photo","/ImgFile/images/default1.jpg");
            }
            if(CommonUtil.isBlank(license)){
                map.put("business_license","/ImgFile/images/default2.jpg");
            }

            map.put("totalMoney",total);
        }

        return list;
    }

    @Override
    public int auditCompany(Map<String, Object> params) {
        String sql = "update t_company_sponsor set state=? where id=?";
        int i = jdbcTemplate.update(sql,params.get("auditState"),params.get("companyId"));

        TCompanyPoints tCompanyPoints = new TCompanyPoints(CommonUtil.parseInt(params.get("companyId")),0);

        i += CommonUtil.parseInt(tCompanyPointsDao.create(tCompanyPoints));

//        String sqlP = "insert into t_company_points(company_id,points) values(?,?)";

//        i += jdbcTemplate.update(sqlP,params.get("companyId"),0);

        return i;
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
    public int saveUser1(TUser1 tUser1) {
        int i = 0;
        if(tUser1.getId() == 0){
            i = CommonUtil.parseInt(tUser1Dao.create(tUser1));
        }else{
            tUser1Dao.save(tUser1);
            i ++ ;
        }
        return i;
    }

    @Override
    public int modifyPassword(Map<String, Object> params) {
        String newPassword = CommonUtil.string2MD5(params.get("newPassword") + "");
//        String sql = "update t_user_base set user_password=? where id=?";
        String sql = "update t_user_1 set password=? where id=?";

        int i = jdbcTemplate.update(sql,newPassword,params.get("userId"));

        return i;
    }

    @Override
    public int deleteUser(Map<String, Object> params) {

        String ids = params.get("ids") + "";
        String[] id = ids.split(",");

//        String sql = "delete from t_user_base where id=?";
//        String sql = "update t_user_base set state=9 where id=?";
        String sql = "update t_user_1 set state=9 where id=?";
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

        String sql = "select distinct tPrimary.*,tMerge.id mergeId,tMerge.apply_man";
        sql += ",tMerge.state mergeState";
        sql += " ,(select count(id) from t_people where family_id=tPrimary.id and people_status<>9) peopleCount";
        sql += " ,(select max(generation) from t_people where family_id=tPrimary.id and people_status<>9) genNum";
        sql += " from t_family tPrimary, t_family_merge tMerge";
        sql += " where tPrimary.id=tMerge.primary_family_id";

        if(!CommonUtil.isBlank(params)){
//            if(!CommonUtil.isBlank(params.get("userName"))){
//                sql += " and (create_man='" + params.get("userName") + "' or id in (select family_id from t_user_family where user_id='" + params.get("userId") + "'))";
//            }
            if(!CommonUtil.isBlank(params.get("mergeId"))){
                sql += " and tMerge.id='" + params.get("mergeId") + "'";
            }
            if(!CommonUtil.isBlank(params.get("province"))){
                sql += " and tPrimary.province='" + params.get("province") + "'";
            }
            if(!CommonUtil.isBlank(params.get("city"))){
                sql += " and tPrimary.city='" + params.get("city") + "'";
            }
            if(!CommonUtil.isBlank(params.get("district"))){
                sql += " and tPrimary.district='" + params.get("district") + "'";
            }

        }

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
        String sql = "update t_family set supplement_flag=5 where id=?";
        i += jdbcTemplate.update(sql,params.get("familyId"));
//        String sql = "update t_family_merge set state=1 where primary_family_id=? and (state=2 or state=0)";
        sql = "update t_family_merge set state=5 where primary_family_id=? and (state=2 or state=0)";
        i += jdbcTemplate.update(sql,params.get("familyId"));
        return i;
    }

    @Override
    public int saveResource(TResource tResource) {
        int i = 0;
        //如果resource的ID大于0，则为修改
        if(tResource.getId() > 0){
            tResourceDao.save(tResource);
            i ++;
        }else{
            i = CommonUtil.parseInt(tResourceDao.create(tResource));
        }
        return i;
    }

    @Override
    public int deleteResource(Map<String, Object> params) {
        String ids = params.get("ids") + "";
        String[] id = ids.split(",");

//        String sql = "delete from t_role where id=?";
        String sql = "update t_resource set state=9 where id=?";
        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }
        return ii;
    }

    @Override
    public List<TResource> getResourceList(Map<String, Object> params) {
        String sql = "select * from t_resource where state<>9";

        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("id"))){
                sql += " and id='" + params.get("id") + "'";
            }
            if(!CommonUtil.isBlank(params.get("sourceName"))){
                sql += " and source_name='" + params.get("sourceName") + "'";
            }
        }

        List<TResource> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TResource>(TResource.class));
//        List<TRole> list = tRoleDao.find(params);
        return list;
    }

    @Override
    public List<TUserResource> getUserResource(Map<String,Object> params){

        String hql = " from TUserResource where userId=?";
        List<TUserResource> list = tUserResourceDao.find(hql,CommonUtil.parseInt(params.get("userId")));

        return list;
    }

    @Override
    public int saveAuth(Map<String,Object> params){
        int ii = 0;

        String userId = params.get("userId") + "";
        String sourceIds = params.get("sourceIds") + "";
        String[] sourceIdArr = sourceIds.split(",");

        //先删除当前用户的权限
        String del = "delete from t_user_resource where user_id=?";

        jdbcTemplate.update(del,userId);

//        String sql = "insert into t_user_resource(user_id,resource_id,state)";
//        sql += " values(?,?,1)";
        for(int i=0;i<sourceIdArr.length;i++){
            TUserResource tUserResource = new TUserResource();
            tUserResource.setUserId(CommonUtil.parseInt(userId));
            tUserResource.setResourceId(CommonUtil.parseInt(sourceIdArr[i]));
            tUserResource.setState(1);

            ii += CommonUtil.parseInt(tUserResourceDao.create(tUserResource));
        }

        return ii;
    }

    @Override
    public List<Map<String,Object>> getUserMenu(Map<String,Object> params){

        String sql = "select t1.*,t2.user_id from t_resource t1,t_user_resource t2";
        sql += " where t1.id=t2.resource_id and t2.user_id=? and t2.state=1";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.get("userId"));
        return list;
    }

	@Override
	public int auditIncludePeople(Map<String, Object> params) {
		
		String peopleIds = params.get("peopleIds") + "";
		String[] peopleId = peopleIds.split(",");

		String auditStatuss = params.get("auditStatus") + "";
		String[] auditStatus = auditStatuss.split(",");
		
		//修改族人状态为审核状态
		String sql = "update t_people set people_status=? where id=?";
		
		int ii = 0;
		for(int i=0;i<peopleId.length;i++){
			String id = peopleId[i];
			String[] ids = id.split(":");
			ii += jdbcTemplate.update(sql,auditStatus[i],ids[0]);
			
			//如果是同意收录，并且为添加，增加创建者的积分
			if(ids.length > 1 && CommonUtil.parseInt(params.get("includeType")) == 1){//如果当前存在创建人ID
				if(CommonUtil.parseInt(params.get("auditStatus")) == 1){
					
					TUserPoints tUserPoints = new TUserPoints();
					tUserPoints.setUserId(CommonUtil.parseInt(ids[1]));
					tUserPoints.setInputCount(1);
					
					familyService.setPoints(tUserPoints, 1);
				}
			}
			
		}
		
		return ii;
	}

	@Override
	public int completeIn(Map<String, Object> params) throws Exception {
		//修改family的补录状态为完成收录
		String sql = "update t_family set supplement_flag=1 where id=?";
		int i = jdbcTemplate.update(sql,params.get("familyId"));
		//修改t_family_merge的状态为收录完成
		sql = "update t_family_merge set state=1,audit_man=?,audit_time=? where primary_family_id=?";
		i += jdbcTemplate.update(sql,params.get("auditMan"),params.get("auditTime"),params.get("familyId"));
		return i;
	}
}

