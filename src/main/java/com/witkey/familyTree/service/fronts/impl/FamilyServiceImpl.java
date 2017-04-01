package com.witkey.familyTree.service.fronts.impl;

import com.witkey.familyTree.dao.consoles.TLogDao;
import com.witkey.familyTree.dao.fronts.*;
import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.BaseUtil;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/22 0022.
 */
@Service("familyService")
public class FamilyServiceImpl implements FamilyService {


    @Resource
    private TFamilyDao tFamilyDao;

    public void settFamilyDao(TFamilyDao tFamilyDao) {
        this.tFamilyDao = tFamilyDao;
    }

    @Resource
    private TPeopleDao tPeopleDao;

    public void settPeopleDao(TPeopleDao tPeopleDao) {
        this.tPeopleDao = tPeopleDao;
    }

    @Resource
    private TMateDao tMateDao;

    public void settMateDao(TMateDao tMateDao) {
        this.tMateDao = tMateDao;
    }

    @Resource
    private TUserPointsDao tUserPointsDao;

    public void settUserPointsDao(TUserPointsDao tUserPointsDao) {
        this.tUserPointsDao = tUserPointsDao;
    }

    @Resource
    private TCompanyPointsDao tCompanyPointsDao;

    public void settCompanyPointsDao(TCompanyPointsDao tCompanyPointsDao) {
        this.tCompanyPointsDao = tCompanyPointsDao;
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
    private TLogDao tLogDao;

    public void settLogDao(TLogDao tLogDao) {
        this.tLogDao = tLogDao;
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 创建族谱
     * @param tFamily
     * @return
     */
    @Override
    public int createFamily(TFamily tFamily) {

        int familyId = CommonUtil.parseInt(tFamilyDao.create(tFamily));

        return familyId;
    }

    @Override
    public int updateFamily(TFamily tFamily) {
        int i = 0;
        tFamilyDao.save(tFamily);
        i ++;
        return i;
    }

    @Override
    public int deleteFamily(Map<String, Object> params) {
        String ids = params.get("ids") + "";
        String[] id = ids.split(",");
//        String sql = "delete from t_family where id=?";
        String sql = "update t_family set state=9 where id=?";

        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }

        return ii;
    }

    /**
     * 录入族人
     * @param tPeople
     * @return
     */
    @Override
    public int savePeople(TPeople tPeople) {
        int peopleId = CommonUtil.parseInt(tPeopleDao.create(tPeople));
        return peopleId;
    }

    @Override
    public int deletePeople(int peopleId) {

        int i = 0;
        tPeopleDao.removeById(peopleId);
        i ++;

        return i;
    }

    @Override
    public void updatePeople(TPeople tPeople) {
        tPeopleDao.update(tPeople);

    }

    @Override
    public int saveMateInfo(TMate tMate) {

        int id = CommonUtil.parseInt(tMateDao.create(tMate));

        return id;
    }

    /**
     * 根据登录人查询创建的族谱list
     * @return
     */
    @Override
    public List<TFamily> getFamilyList(Map<String,Object> params) {
        Map<String,Object> filter = new HashMap<String,Object>();
        String sql = "select * from t_family where state<>9 ";
        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("userName"))){
//                filter.put("createMan",params.get("userName"));
                sql += " and create_man='" + params.get("userName") + "'";
            }
            if(!CommonUtil.isBlank(params.get("familyArea")) && !"0".equals(params.get("familyArea"))){
//                filter.put("familyArea",params.get("familyArea"));
                sql += " and family_area='" + params.get("familyArea") + "'";
            }
            if(!CommonUtil.isBlank(params.get("province"))){
//                filter.put("province",params.get("province"));
                sql += " and province='" + params.get("province") + "'";
            }
            if(!CommonUtil.isBlank(params.get("city"))){
//                filter.put("city",params.get("city"));
                sql += " and city='" + params.get("city") + "'";
            }
            if(!CommonUtil.isBlank(params.get("district"))){
//                filter.put("district",params.get("district"));
                sql += " and district='" + params.get("district") + "'";
            }

            if(!CommonUtil.isBlank(params.get("familyName"))){
//                filter.put("familyName","%" + params.get("familyName") + "%");
                sql += " and family_name like '%" + params.get("familyName") + "%'";
            }
            if(!CommonUtil.isBlank(params.get("state"))){
                sql += " and state='" + params.get("state") + "'";
            }
        }

//        List<TFamily> list = tFamilyDao.find(filter);

        List<TFamily> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TFamily>(TFamily.class));

        for (TFamily tFamily : list) {
            String photoUrl = tFamily.getPhotoUrl();
            if(CommonUtil.isBlank(photoUrl)){
                tFamily.setPhotoUrl(BaseUtil.DEFAULT_FAMILY_IMG);
            }
//            else if(!CommonUtil.isFile(photoUrl)){
//                tFamily.setPhotoUrl(BaseUtil.DEFAULT_FAMILY_IMG);
//            }
        }

        return list;
    }

    @Override
    public List<TFamily> getIncludeFamilyList(Map<String, Object> params) {
        String sql = "select id from t_family where state=2 ";

        List<TFamily> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TFamily>(TFamily.class));
        List<TFamily> list2 = new ArrayList<TFamily>();
        if(list != null && list.size() > 0){
            String primaryIds = "";
            for(int i=0;i<list.size();i++){
                primaryIds += "," + list.get(i).getId();
            }
            primaryIds = primaryIds.substring(1);
            String sql1 = "select * from t_family where id in";
            sql1 += " (select target_family_id from t_family_merge where primary_family_id in ";
            sql1 += " (" + primaryIds + ") and state=1) and state=1 ";
            if(!CommonUtil.isBlank(params)){
                if(!CommonUtil.isBlank(params.get("userName"))){
//                filter.put("createMan",params.get("userName"));
                    sql1 += " and create_man='" + params.get("userName") + "'";
                }
                if(!CommonUtil.isBlank(params.get("familyArea")) && !"0".equals(params.get("familyArea"))){
//                filter.put("familyArea",params.get("familyArea"));
                    sql1 += " and family_area='" + params.get("familyArea") + "'";
                }
                if(!CommonUtil.isBlank(params.get("province"))){
//                filter.put("province",params.get("province"));
                    sql1 += " and province='" + params.get("province") + "'";
                }
                if(!CommonUtil.isBlank(params.get("city"))){
//                filter.put("city",params.get("city"));
                    sql1 += " and city='" + params.get("city") + "'";
                }
                if(!CommonUtil.isBlank(params.get("district"))){
//                filter.put("district",params.get("district"));
                    sql1 += " and district='" + params.get("district") + "'";
                }

                if(!CommonUtil.isBlank(params.get("familyName"))){
//                filter.put("familyName","%" + params.get("familyName") + "%");
                    sql1 += " and family_name like '%" + params.get("familyName") + "%'";
                }
            }
            list2 = jdbcTemplate.query(sql1,new BeanPropertyRowMapper<TFamily>(TFamily.class));
        }

        if(list2 != null && list2.size() > 0){
            for (TFamily tFamily : list2) {
                String photoUrl = tFamily.getPhotoUrl();
                if(CommonUtil.isBlank(photoUrl)){
                    tFamily.setPhotoUrl(BaseUtil.DEFAULT_FAMILY_IMG);
                }
//                else if(!CommonUtil.isFile(photoUrl)){
//                    tFamily.setPhotoUrl(BaseUtil.DEFAULT_FAMILY_IMG);
//                }
            }
        }

        return list2;
    }

    /**
     * 根据族谱ID获取家族成员
     * @param params
     * @return
     */
    @Override
    public List<TPeople> getPeopleList(Map<String,Object> params) {
        Map<String,Object> filter = new HashMap<String,Object>();

        String sql = "select * from t_people where state<>9";

        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("familyId")) && !"0".equals(params.get("familyId"))){
                sql += " and family_id=" + params.get("familyId");
//            filter.put("familyId",params.get("familyId"));
            }
            if(!CommonUtil.isBlank(params.get("peopleType"))){// && "1".equals(params.get("peopleType"))
                sql += " and people_type='" + params.get("peopleType") + "'";
//            filter.put("peopleType",params.get("peopleType"));
            }
            if(!CommonUtil.isBlank(params.get("peopleName"))){
                sql += " and name='" + params.get("peopleName") + "'";
//            filter.put("name",params.get("peopleName"));
            }
            if(!CommonUtil.isBlank(params.get("generation"))){
                sql += " and generation='" + params.get("generation") + "'";
//            filter.put("generation",params.get("generation"));
            }
            if(!CommonUtil.isBlank(params.get("fatherId"))){
                sql += " and father_id='" + params.get("fatherId") + "'";
//            filter.put("generation",params.get("generation"));
            }

            if(!CommonUtil.isBlank(params.get("orderBy"))){
                sql += " " + params.get("orderBy");
            }
            if(!CommonUtil.isBlank(params.get("limit"))){
                sql += " " + params.get("limit");
            }
        }

//        List<TPeople> list = tPeopleDao.find(filter);
        List<TPeople> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TPeople>(TPeople.class));
        return list;
    }

    /**
     * 根据族谱ID和辈数查询父母
     * @param familyId
     * @param generation
     * @return
     */
    @Override
    public Map<String, Object> getParentFromGen(int familyId,int generation) {
        Map<String,Object> result = new HashMap<String,Object>();

        //查询父母辈可能作为父亲的人
        Map<String,Object> filter = new HashMap<String,Object>();
        filter.put("familyId",familyId);
        filter.put("generation",generation);
        filter.put("sex",1);
        List<TPeople> listFather = tPeopleDao.find(filter);
        result.put("fatherList",listFather);

        //查询父母辈可能作为母亲的人
        filter = new HashMap<String,Object>();
        filter.put("familyId",familyId);
        filter.put("generation",generation);
        filter.put("sex",0);
        List<TPeople> listMother = tPeopleDao.find(filter);
        result.put("motherList",listMother);
        return result;
    }

    /**
     * 根据familyId查询Family
     * @param familyId
     * @return
     */
    @Override
    public TFamily getFamilyFromId(int familyId) {
//        Map<String,Object> filter = new HashMap<String,Object>();
//        filter.put("id",familyId);
//
//        List<TFamily> list = tFamilyDao.find(filter);
        TFamily tFamily = tFamilyDao.get(familyId);
        return tFamily;
    }

    @Override
    public List<TPeople> getMateList(int peopleId) {

        StringBuffer sql = new StringBuffer("select * from t_people where id in(");
        sql.append(" select mate_id from t_mate where people_id=?) and state<>9");

        List<TPeople> list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<TPeople>(TPeople.class),peopleId);

        return list;
    }

    @Override
    public TPeople getPeopleInfo(int peopleId) {

        TPeople tPeople = tPeopleDao.get(peopleId);

        return tPeople;
    }

    @Override
    public List<TPointsDic> getPointsRelation(int type,int state) {

        String sql = "select * from t_points_dic where 1=1 ";

        if(state > 0){
            sql += "and state='" + state + "'";
        }
        if(type > 0){
            sql += "and points_type='" + type + "'";
        }

        List<TPointsDic> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TPointsDic>(TPointsDic.class));

        return list;
    }

    @Override
    public int setPoints(Object object, int type) {

        int i = 0;
        //个人
        if(type == 1){
            TUserPoints tUserPoints = (TUserPoints) object;
            //查询该用户是否已经有积分了
            String sql = "select * from t_user_points where user_id=?";
            List<TUserPoints> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TUserPoints>(TUserPoints.class),tUserPoints.getUserId());
            //已经有积分了，直接更新积分
            if(list != null && list.size() > 0){

                tUserPoints.setId(list.get(0).getId());
                tUserPoints.setPoints((tUserPoints.getPoints() + list.get(0).getPoints()));
                tUserPointsDao.update(tUserPoints);
                i ++ ;
            }else{
                i = CommonUtil.parseInt(tUserPointsDao.create(tUserPoints));
            }
        }else{//公司
            TCompanyPoints tCompanyPoints = (TCompanyPoints) object;
            //查询该用户是否已经有积分了
            String sql = "select * from t_company_points where company_id=?";
            List<TCompanyPoints> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TCompanyPoints>(TCompanyPoints.class),tCompanyPoints.getCompanyId());
            //如果该公司已经有积分了
            if(list != null && list.size() > 0){
                tCompanyPoints.setId(list.get(0).getId());
                tCompanyPoints.setPoints((tCompanyPoints.getPoints() + list.get(0).getPoints()));
                tCompanyPointsDao.update(tCompanyPoints);
                i ++ ;
            }else{
                i = CommonUtil.parseInt(tCompanyPointsDao.create(tCompanyPoints));
            }
        }

        return i;
    }

    @Override
    public List<Map<String, Object>> getPointsRanking(Map<String,Object> params) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        //个人
        if("1".equals(params.get("type"))){
            String sql = "select t1.points,t1.user_id,t2.user_name from t_user_points t1,t_user_front t2";
            sql += " where t1.user_id=t2.id ";
            if(!CommonUtil.isBlank(params.get("userType"))){
                sql += " and user_type='" + params.get("userType") + "'";
            }
            sql += " order by t1.points desc";
            list = jdbcTemplate.queryForList(sql);
        }else{//公司
            String sql = "select t1.points,t1.company_id,t2.company_name from t_company_points t1,t_company_sponsor t2";
            sql += " where t1.company_id=t2.id order by t1.points desc";
            list = jdbcTemplate.queryForList(sql);
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> getMeritocrat(Map<String, Object> params) {

        String sql = "select t1.meritocrat_name,t1.meritocrat_desc,t1.meritocrat_attr_id,";
        sql += " t1.meritocrat_area,t1.meritocrat_addr,t1.post_code,t1.phone,t1.fax,t1.photo";
        sql += ",t2.meritocrat_attr ";
        sql += " from t_meritocrat t1,t_meritocrat_attr t2 where t1.meritocrat_attr_id=t2.id and t1.state<>9";

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

            if(!CommonUtil.isBlank(params.get("pageSize")) && !CommonUtil.isBlank(params.get("beginRow"))){

                sql += " limit " + params.get("beginRow") + "," + params.get("pageSize");
            }
        }

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            String photoUrl = map.get("photo") + "";
            if(CommonUtil.isBlank(photoUrl)){
                map.put("photo",BaseUtil.DEFAULT_MAN_IMG);
            }
            else if(!CommonUtil.isFile(photoUrl)){
                map.put("photo",BaseUtil.DEFAULT_MAN_IMG);
            }
        }
        return list;
    }

    @Override
    public int getTotalMeritocrat(Map<String,Object> params){

        int total = 0;

        String sql = " select count(*) total from t_meritocrat where state<>9";

        if(!CommonUtil.isBlank(params)){

            if(!CommonUtil.isBlank(params.get("meritocrat_attr_id"))){
                sql += " and meritocrat_attr_id='" + params.get("meritocrat_attr_id") + "'";
            }
            if(!CommonUtil.isBlank(params.get("meritocrat_name"))){
                sql += " and meritocrat_name='" + params.get("meritocrat_name") + "'";
            }
            if(!CommonUtil.isBlank(params.get("meritocrat_area"))){
                sql += " and meritocrat_area='" + params.get("meritocrat_area") + "'";
            }

        }

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0){
            total = CommonUtil.parseInt(list.get(0).get("total"));
        }

        return total;
    }

    @Override
    public List<Map<String, Object>> getMeritocratArea() {

        String sql = "select distinct meritocrat_area from t_meritocrat";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        return list;
    }

    @Override
    public List<Map<String, Object>> getFamilyIdForMerge(Map<String, Object> params) {
        String sql = "select distinct family_id from t_people where family_id<>" + params.get("familyId");

        if(!CommonUtil.isBlank(params.get("peopleType")) && "1".equals(params.get("peopleType"))){
            sql += " and people_type='" + params.get("peopleType") + "'";
        }
        if(!CommonUtil.isBlank(params.get("peopleName"))){
            sql += " and name='" + params.get("peopleName") + "'";
        }
        if(!CommonUtil.isBlank(params.get("generation"))){
            sql += " and generation='" + params.get("generation") + "'";
        }

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public int saveInclude(TFamilyMerge tFamilyMerge) {

        return CommonUtil.parseInt(tFamilyMergeDao.create(tFamilyMerge));
    }

    @Override
    public List<TFamilyMerge> getMergeList(Map<String, Object> params) {

        List<TFamilyMerge> list = tFamilyMergeDao.find(params);

        return list;
    }

}
