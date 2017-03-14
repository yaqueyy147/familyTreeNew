package com.witkey.familyTree.service.fronts.impl;

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
        String sql = "select * from t_family where 1=1 ";
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
        }

//        List<TFamily> list = tFamilyDao.find(filter);

        List<TFamily> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TFamily>(TFamily.class));

        for (TFamily tFamily : list) {
            String photoUrl = tFamily.getPhotoUrl();
            if(CommonUtil.isBlank(photoUrl)){
                tFamily.setPhotoUrl(BaseUtil.DEFAULT_FAMILY_IMG);
            }
            else if(!CommonUtil.isFile(photoUrl)){
                tFamily.setPhotoUrl(BaseUtil.DEFAULT_FAMILY_IMG);
            }
        }

        return list;
    }

    /**
     * 根据族谱ID获取家族成员
     * @param params
     * @return
     */
    @Override
    public List<TPeople> getPeopleList(Map<String,Object> params) {
        Map<String,Object> filter = new HashMap<String,Object>();
        if(!CommonUtil.isBlank(params.get("familyId")) && !"0".equals(params.get("familyId"))){
            filter.put("familyId",params.get("familyId"));
        }
        if(!CommonUtil.isBlank(params.get("peopleType")) && "1".equals(params.get("peopleType"))){
            filter.put("peopleType",params.get("peopleType"));
        }
        if(!CommonUtil.isBlank(params.get("peopleName"))){
            filter.put("name",params.get("peopleName"));
        }
        if(!CommonUtil.isBlank(params.get("generation"))){
            filter.put("generation",params.get("generation"));
        }

        List<TPeople> list = tPeopleDao.find(filter);
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
        sql.append(" select mate_id from t_mate where people_id=?)");

        List<TPeople> list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<TPeople>(TPeople.class),peopleId);

        return list;
    }

    @Override
    public TPeople getPeopleInfo(int peopleId) {

        TPeople tPeople = tPeopleDao.get(peopleId);

        return tPeople;
    }

    @Override
    public List<TPointsDic> getPointsRelation(int type) {

        String sql = "select * from t_points_dic where state=1 and points_type=?";

        List<TPointsDic> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TPointsDic>(TPointsDic.class),type);

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
    public List<Map<String, Object>> getPointsRanking(int type) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        //个人
        if(type == 1){
            String sql = "select t1.points,t1.user_id,t2.user_name from t_user_points t1,t_user_front t2";
            sql += " where t1.user_id=t2.id order by t1.points desc";
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

        String sql = "select t1.meritocrat_name,t1.meritocrat_desc,t1.meritocrat_attr_id,t2.meritocrat_attr ";
        sql += " from t_meritocrat t1,t_meritocrat_attr t2 where t1.meritocrat_attr_id=t2.id and t2.state=1";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        return list;
    }
}
