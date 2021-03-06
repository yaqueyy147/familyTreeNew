package com.witkey.familyTree.service.fronts.impl;

import com.witkey.familyTree.dao.consoles.TLogDao;
import com.witkey.familyTree.dao.consoles.TUser1Dao;
import com.witkey.familyTree.dao.fronts.*;
import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.BaseUtil;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.PeopleTree;
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
    private TUser1Dao tUser1Dao;

    public void settUser1Dao(TUser1Dao tUser1Dao) {
        this.tUser1Dao = tUser1Dao;
    }

    @Resource
    private TUserMoneyDao tUserMoneyDao;
    
    public void settUserMoneyDao(TUserMoneyDao tUserMoneyDao) {
		this.tUserMoneyDao = tUserMoneyDao;
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

    @Override
    public String savePeople(TPeople tPeople) {
        return null;
    }

//    /**
//     * 录入族人
//     * @param tPeople
//     * @return
//     */
//    @Override
//    public int savePeople(TPeople tPeople) {
//        int peopleId = CommonUtil.parseInt(tPeopleDao.create(tPeople));
//        return peopleId;
//    }

    @Override
    public int deletePeople(String peopleId, int familyId, int peopleType) {

        int i = 0;
        System.out.println("****\n开始 -->" + CommonUtil.getDateLong() + "\n****");
        String deletepeopleid = getdeletePeopleAndChildrenid(peopleId,familyId,peopleType);
        deletepeopleid = deletepeopleid.substring(1);
        String sql = "delete from t_people where id in(" + deletepeopleid + ")";
        i += jdbcTemplate.update(sql);
        System.out.println("****\n结束 -->" + CommonUtil.getDateLong() + "\n****");
        System.out.println("要删除的id -->\n****\n" + deletepeopleid + "\n****");

        return i;
    }

    @Override
    public void updatePeople(TPeople tPeople) {
        tPeopleDao.save(tPeople);

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
    public List<Map<String,Object>> getFamilyList(Map<String,Object> params) {
        int pageNumber = CommonUtil.parseInt(params.get("pageNumber"));
        int pageSize = CommonUtil.parseInt(params.get("pageSize"));
        int startindex = (pageNumber-1) * pageSize + 1;
//        String sql = "select * from t_family where state<>9 ";
        String sql = "select t1.id,t1.family_first_name as familyFirstName,t1.family_name as familyName";
        sql += ",t1.photo_url as photoUrl,t1.create_man as createMan,t1.visit_status as visitStatus";
        sql += ",t1.visit_password as visitPassword,t1.create_time as createTime,t1.state";
        sql += ",t1.remark,t1.family_desc as familyDesc,t1.family_area as familyArea,t1.province";
        sql += ",t1.city,t1.district,t1.supplement_flag as supplementFlag,t1.create_id as createId";
        sql += ",t2.peopleCount,t3.zspeopleCount from t_family t1";
        sql += " left join (select family_id,count(id) as peopleCount from t_people where people_status=1 and people_type=1 group by family_id) t2 on t2.family_id=t1.id";
        sql += " left join (select family_id,count(id) as zspeopleCount from t_people where people_status=1 and people_type=1 and state=1 group by family_id) t3 on t3.family_id=t1.id";
        sql += " where t1.state<>9";
        if(!CommonUtil.isBlank(params)){
        	
            if(!CommonUtil.isBlank(params.get("familyArea")) && !"0".equals(params.get("familyArea"))){
                sql += " and t1.family_area='" + params.get("familyArea") + "'";
            }
            if(!CommonUtil.isBlank(params.get("province"))){
                sql += " and t1.province='" + params.get("province") + "'";
            }
            if(!CommonUtil.isBlank(params.get("city"))){
                sql += " and t1.city='" + params.get("city") + "'";
            }
            if(!CommonUtil.isBlank(params.get("district"))){
                sql += " and t1.district='" + params.get("district") + "'";
            }

            if(!CommonUtil.isBlank(params.get("familyName"))){
                sql += " and t1.family_name like '%" + params.get("familyName") + "%'";
            }
            if(!CommonUtil.isBlank(params.get("state"))){
                if(!CommonUtil.isBlank(params.get("tt")) && CommonUtil.parseInt(params.get("tt")) == 1){
                    sql += " and (state='" + params.get("state") + "' or state=5)";
                }else{
                    sql += " and state='" + params.get("state") + "'";
                }

            }
        }
        sql += " limit " + startindex + "," + pageSize;
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        return list;
    }

    /**
     * 根据登录人查询创建的族谱list
     * @return
     */
    @Override
    public List<TFamily> getFamilyListJoint(Map<String,Object> params) {
        Map<String,Object> filter = new HashMap<String,Object>();
        String sql = "select * from t_family where state<>9 and id<>?";

        List<TFamily> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TFamily>(TFamily.class),params.get("familyId"));

        return list;
    }

    /**
     * 根据登录人查询创建的族谱list
     * @return
     */
    @Override
    public List<Map<String,Object>> getFamilyList1(Map<String,Object> params) {
        Map<String,Object> filter = new HashMap<String,Object>();
        int pageNumber = CommonUtil.parseInt(params.get("pageNumber"));
        int pageSize = CommonUtil.parseInt(params.get("pageSize"));
        int startindex = (pageNumber-1) * pageSize + 1;
//        String sql = "select * from t_family where state<>9 and state=1";
        String sql = "select t1.id,t1.family_first_name as familyFirstName,t1.family_name as familyName";
        sql += ",t1.photo_url as photoUrl,t1.create_man as createMan,t1.visit_status as visitStatus";
        sql += ",t1.visit_password as visitPassword,t1.create_time as createTime,t1.state";
        sql += ",t1.remark,t1.family_desc as familyDesc,t1.family_area as familyArea,t1.province";
        sql += ",t1.city,t1.district,t1.supplement_flag as supplementFlag,t1.create_id as createId";
        sql += ",t2.peopleCount,t3.zspeopleCount from t_family t1";
        sql += " left join (select family_id,count(id) as peopleCount from t_people where people_status=1 and people_type=1 group by family_id) t2 on t2.family_id=t1.id";
        sql += " left join (select family_id,count(id) as zspeopleCount from t_people where people_status=1 and people_type=1 and state=1 group by family_id) t3 on t3.family_id=t1.id";
        sql += " where t1.state<>9 and t1.state=1";
        sql += " and ((t1.create_id='" + params.get("userId") + "' or t1.id in (select family_id from t_user_family where user_id='" + params.get("userId") + "'))";
//        sql += " or (supplement_flag in (1,5))";
        sql += ")";
        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("province"))){
                sql += " and province='" + params.get("province") + "'";
            }
            if(!CommonUtil.isBlank(params.get("city"))){
                sql += " and city='" + params.get("city") + "'";
            }
            if(!CommonUtil.isBlank(params.get("district"))){
                sql += " and district='" + params.get("district") + "'";
            }
            if(!CommonUtil.isBlank(params.get("familyName"))){
                sql += " and family_name like '%" + params.get("familyName") + "%'";
            }
            if(CommonUtil.isBlank(params.get("nopage"))){
                sql += " limit " + pageNumber + "," + pageSize;
            }
        }

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        for (Map<String,Object> map : list) {
            String photoUrl = map.get("photoUrl") + "";
            if(CommonUtil.isBlank(photoUrl)){
                map.put("photoUrl",BaseUtil.DEFAULT_FAMILY_IMG);
            }
        }

        return list;
    }
    
    /**
     * 前台首页查询族谱
     * @return
     */
    @Override
    public List<Map<String,Object>> getFamilyList2(Map<String,Object> params) {
//        String sql = "select * from t_family where state<>9 and state=1";
        String sql = "select t1.id,t1.family_first_name as familyFirstName,t1.family_name as familyName";
        sql += ",t1.photo_url as photoUrl,t1.create_man as createMan,t1.visit_status as visitStatus";
        sql += ",t1.visit_password as visitPassword,t1.create_time as createTime,t1.state";
        sql += ",t1.remark,t1.family_desc as familyDesc,t1.family_area as familyArea,t1.province";
        sql += ",t1.city,t1.district,t1.supplement_flag as supplementFlag,t1.create_id as createId";
        sql += ",t2.peopleCount,t3.zspeopleCount from t_family t1";
        sql += " left join (select family_id,count(id) as peopleCount from t_people where people_status=1 and people_type=1 group by family_id) t2 on t2.family_id=t1.id";
        sql += " left join (select family_id,count(id) as zspeopleCount from t_people where people_status=1 and people_type=1 and state=1 group by family_id) t3 on t3.family_id=t1.id";
        sql += " where t1.state<>9 and t1.state=1";
        sql += " and supplement_flag in (1,5) ";
        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("onlyInclude")) && CommonUtil.parseInt(params.get("onlyInclude")) == 1){
                sql += " and t1.create_id<>'" + params.get("userId") + "'";
            }

            if(!CommonUtil.isBlank(params.get("province"))){
                sql += " and t1.province='" + params.get("province") + "'";
            }
            if(!CommonUtil.isBlank(params.get("city"))){
                sql += " and t1.city='" + params.get("city") + "'";
            }
            if(!CommonUtil.isBlank(params.get("district"))){
                sql += " and t1.district='" + params.get("district") + "'";
            }

            if(!CommonUtil.isBlank(params.get("familyName"))){
                sql += " and t1.family_name like '%" + params.get("familyName") + "%'";
            }
            if(!CommonUtil.isBlank(params.get("indexsearchfamilyid"))){
                sql += " and t1.id in (" + params.get("indexsearchfamilyid") + ")";
            }
        }

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        for (Map<String,Object> map : list) {
            String photoUrl = map.get("photoUrl") + "";
            if(CommonUtil.isBlank(photoUrl)){
                map.put("photoUrl",BaseUtil.DEFAULT_FAMILY_IMG);
            }
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

        String sql = "select * from t_people where people_status<>9 and family_id=?";

        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("isIndex")) && CommonUtil.parseInt(params.get("isIndex")) == 1){
                sql += " and people_status=1";
            }
            if(!CommonUtil.isBlank(params.get("peopleType"))){// && "1".equals(params.get("peopleType"))
                sql += " and people_type='" + params.get("peopleType") + "'";
            }
            if(!CommonUtil.isBlank(params.get("peopleName"))){
                sql += " and name='" + params.get("peopleName") + "'";
            }
            if(!CommonUtil.isBlank(params.get("generation"))){
                sql += " and generation='" + params.get("generation") + "'";
            }
            if(!CommonUtil.isBlank(params.get("superiorId"))){
                sql += " and superior_id='" + params.get("superiorId") + "'";
            }
            if(!CommonUtil.isBlank(params.get("state"))){
                sql += " and state='" + params.get("state") + "'";
            }

            if(!CommonUtil.isBlank(params.get("orderBy"))){
                sql += " " + params.get("orderBy");
            }
            if(!CommonUtil.isBlank(params.get("limit"))){
                sql += " " + params.get("limit");
            }
        }

        List<TPeople> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TPeople>(TPeople.class),params.get("familyId"));
        return list;
    }

    /**
     * 根据族谱ID获取家族成员
     * @param params
     * @return
     */
    @Override
    public List<PeopleTree> getPeopleList4view(Map<String,Object> params) {
        Map<String,Object> filter = new HashMap<String,Object>();

//        String sql = "select * from t_people where people_status<>9 and family_id=?";
        String sql = "select t1.id,t1.superior_id as pId";
        sql += ",case when t1.state=0 then '/static/jquery/ztree/icon/head_die.ico' else '/static/jquery/ztree/icon/head2.ico' end as icon";
        sql += ",t1.is_supplement as isSupplement,t1.people_status as peopleStatus,true as open";
        sql += ",CONCAT(t1.name,'(第',t1.generation,'世)') as name,t1.generation,t1.state as isdie";
        sql += ",t2.mateName";
        sql += " from t_people t1";
        sql += " left join (select t1.people_id, GROUP_CONCAT(CONCAT_WS('--',t2.name,t2.id,t2.people_status,IFNULL(t2.is_supplement,'0')) separator ',') as mateName from t_mate t1";
        sql += " left join t_people t2 on t2.id=t1.mate_id";
        sql += " where t2.people_status<>9";
        sql += " GROUP BY t1.people_id) t2 on t2.people_id=t1.id";
        sql += " where t1.people_status<>9 and t1.people_status=1 and t1.family_id=?";
        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("isIndex")) && CommonUtil.parseInt(params.get("isIndex")) == 1){
                sql += " and people_status=1";
            }
            if(!CommonUtil.isBlank(params.get("peopleType"))){// && "1".equals(params.get("peopleType"))
                sql += " and people_type='" + params.get("peopleType") + "'";
            }
            if(!CommonUtil.isBlank(params.get("peopleName"))){
                sql += " and name='" + params.get("peopleName") + "'";
            }
            if(!CommonUtil.isBlank(params.get("generation"))){
                sql += " and generation='" + params.get("generation") + "'";
            }
            if(!CommonUtil.isBlank(params.get("superiorId"))){
                sql += " and superior_id='" + params.get("superiorId") + "'";
            }
            if(!CommonUtil.isBlank(params.get("state"))){
                sql += " and state='" + params.get("state") + "'";
            }

            if(!CommonUtil.isBlank(params.get("orderBy"))){
                sql += " " + params.get("orderBy");
            }
            if(!CommonUtil.isBlank(params.get("limit"))){
                sql += " " + params.get("limit");
            }
        }

        List<PeopleTree> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<PeopleTree>(PeopleTree.class),params.get("familyId"));
        return list;
    }

    @Override
    public List<PeopleTree> getPeopleList4consoleview(Map<String,Object> params) {
        Map<String,Object> filter = new HashMap<String,Object>();

//        String sql = "select * from t_people where people_status<>9 and family_id=?";
        String sql = "select t1.id,t1.superior_id as pId";
        sql += ",case when t1.state=0 then '/static/jquery/ztree/icon/head_die.ico' else '/static/jquery/ztree/icon/head2.ico' end as icon";
        sql += ",t1.is_supplement as isSupplement,t1.people_status as peopleStatus,true as open";
//        sql += ",CONCAT(t1.name,'(第',t1.generation,'世)') as name,t1.generation,t1.isdie";
        sql += ",case when t1.ishide='1' then CONCAT(t1.name,'(第',t1.generation,'世)--已屏蔽') else CONCAT(t1.name,'(第',t1.generation,'世)') end as name";
        sql += ",t2.mateName";
        sql += " from t_people t1";
        sql += " left join (select t1.people_id, GROUP_CONCAT(CONCAT_WS('--',t2.name,t2.id,t2.people_status,IFNULL(t2.is_supplement,'0')) separator ',') as mateName from t_mate t1";
        sql += " left join t_people t2 on t2.id=t1.mate_id";
        sql += " where t2.people_status<>9";
        sql += " GROUP BY t1.people_id) t2 on t2.people_id=t1.id";
        sql += " where t1.people_status<>9 and t1.people_status=1 and t1.family_id=?";
        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("isIndex")) && CommonUtil.parseInt(params.get("isIndex")) == 1){
                sql += " and people_status=1";
            }
            if(!CommonUtil.isBlank(params.get("peopleType"))){// && "1".equals(params.get("peopleType"))
                sql += " and people_type='" + params.get("peopleType") + "'";
            }
            if(!CommonUtil.isBlank(params.get("peopleName"))){
                sql += " and name='" + params.get("peopleName") + "'";
            }
            if(!CommonUtil.isBlank(params.get("generation"))){
                sql += " and generation='" + params.get("generation") + "'";
            }
            if(!CommonUtil.isBlank(params.get("superiorId"))){
                sql += " and superior_id='" + params.get("superiorId") + "'";
            }
            if(!CommonUtil.isBlank(params.get("state"))){
                sql += " and state='" + params.get("state") + "'";
            }

            if(!CommonUtil.isBlank(params.get("orderBy"))){
                sql += " " + params.get("orderBy");
            }
            if(!CommonUtil.isBlank(params.get("limit"))){
                sql += " " + params.get("limit");
            }
        }

        List<PeopleTree> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<PeopleTree>(PeopleTree.class),params.get("familyId"));
        return list;
    }

    @Override
    public List<PeopleTree> getPeopleListIndex(Map<String,Object> params){
//        String sql = "select * from t_people where people_status<>9 and people_status=1 and family_id=?";
        String sql = "select t1.id,t1.superior_id as pId";
        sql += ",case when t1.state=0 then '/static/jquery/ztree/icon/head_die.ico' else '/static/jquery/ztree/icon/head2.ico' end as icon";
        sql += ",t1.is_supplement as isSupplement,t1.people_status as peopleStatus,true as open";
        sql += ",case when t1.ishide='1' then '***' else CONCAT(t1.name,'(第',t1.generation,'世)') end as name";
        sql += ",case when t1.ishide='1' then '' else t2.mateName end as mateName";
        sql += " from t_people t1";
        sql += " left join (select t1.people_id, GROUP_CONCAT(CONCAT_WS('--',t2.name,t2.id,t2.people_status,IFNULL(t2.is_supplement,'0')) separator ',') as mateName from t_mate t1";
        sql += " left join t_people t2 on t2.id=t1.mate_id";
        sql += " where t2.people_status<>9";
        sql += " GROUP BY t1.people_id) t2 on t2.people_id=t1.id";
        sql += " where t1.people_status<>9 and t1.people_status=1 and t1.family_id=?";
        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("peopleType"))){
                sql += " and t1.people_type='" + params.get("peopleType") + "'";
            }

            if(!CommonUtil.isBlank(params.get("orderBy"))){
                sql += " " + params.get("orderBy");
            }
            if(!CommonUtil.isBlank(params.get("limit"))){
                sql += " " + params.get("limit");
            }
        }

        List<PeopleTree> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<PeopleTree>(PeopleTree.class),params.get("familyId"));
        return list;
    }

    /**
     * 根据族谱ID获取家族成员,导出用
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> getPeopleList4Export(Map<String,Object> params) {
        Map<String,Object> filter = new HashMap<String,Object>();

        String sql = "select t1.* from t_people t1";//,t2.name as fathername
//        sql += " left join t_people t2 on t2.id=t1.superior_id";
        sql += " where t1.people_status<>9 and t1.family_id=?";

        if(!CommonUtil.isBlank(params)){

            if(!CommonUtil.isBlank(params.get("peopleType"))){// && "1".equals(params.get("peopleType"))
                sql += " and t1.people_type='" + params.get("peopleType") + "'";
//            filter.put("peopleType",params.get("peopleType"));
            }
            if(!CommonUtil.isBlank(params.get("peopleName"))){
                sql += " and t1.name='" + params.get("peopleName") + "'";
//            filter.put("name",params.get("peopleName"));
            }
            if(!CommonUtil.isBlank(params.get("generation"))){
                sql += " and t1.generation='" + params.get("generation") + "'";
//            filter.put("generation",params.get("generation"));
            }
            if(!CommonUtil.isBlank(params.get("superiorId"))){
                sql += " and t1.superior_id='" + params.get("superiorId") + "'";
//            filter.put("generation",params.get("generation"));
            }
            if(!CommonUtil.isBlank(params.get("state"))){
                sql += " and t1.state='" + params.get("state") + "'";
            }

            if(!CommonUtil.isBlank(params.get("orderBy"))){
                sql += " " + params.get("orderBy");
            }
            if(!CommonUtil.isBlank(params.get("limit"))){
                sql += " " + params.get("limit");
            }
        }

//        List<TPeople> list = tPeopleDao.find(filter);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.get("familyId"));
        return list;
    }

    /**
     * 根据族谱ID获取家族成员
     * @param params
     * @return
     */
    @Override
    public List<PeopleTree> getPeopleList4Print(Map<String,Object> params) {
        Map<String,Object> filter = new HashMap<String,Object>();

//        String sql = "select * from t_people where people_status=1 and family_id=? and people_type=1";
        String sql = "select t1.id,t1.superior_id as pId";
        sql += ",case when t1.state=0 then '/static/jquery/ztree/icon/head_die.ico' else '/static/jquery/ztree/icon/head2.ico' end as icon";
        sql += ",t1.is_supplement as isSupplement,t1.people_status as peopleStatus,true as open";
        sql += ",CONCAT(t1.name,'(第',t1.generation,'世)') as name,t1.dieAddr";
        sql += ",t2.mateName";
        sql += " from t_people t1";
        sql += " left join (select t1.people_id, GROUP_CONCAT(CONCAT_WS('--',t2.name,t2.id,t2.people_status,IFNULL(t2.is_supplement,'0')) separator ',') as mateName from t_mate t1";
        sql += " left join t_people t2 on t2.id=t1.mate_id";
        sql += " where t2.people_status<>9";
        sql += " GROUP BY t1.people_id) t2 on t2.people_id=t1.id";
        sql += " where t1.people_status<>9 and t1.people_status=1 and t1.family_id=?";
        sql += " and t1.generation>=? and t1.generation<=? order by genneration asc,family_rank asc";

        List<PeopleTree> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<PeopleTree>(PeopleTree.class),params.get("familyId"),params.get("beginGen"),params.get("endGen"));
        return list;
    }

    @Override
    public List<PeopleTree> getPeopleList4merge(Map<String,Object> params) {
        Map<String,Object> filter = new HashMap<String,Object>();

//        String sql = "select * from t_people where people_status<>9 and family_id=?";
        String sql = "select t1.id,t1.superior_id as pId";
        sql += ",case when t1.state=0 then '/static/jquery/ztree/icon/head_die.ico' else '/static/jquery/ztree/icon/head2.ico' end as icon";
        sql += ",t1.is_supplement as isSupplement,t1.people_status as peopleStatus,true as open,t1.createId";
        sql += ",CONCAT(t1.name,'(第',t1.generation,'世)') as name,t1.generation,t1.isdie";
        sql += ",case when t1.people_status<>5 and t1.people_status<>51 and t1.people_status<>52 then true else false as nocheck";
        sql += ",t2.mateName";
        sql += " from t_people t1";
        sql += " left join (select t1.people_id, GROUP_CONCAT(CONCAT_WS('--',t2.name,t2.id,t2.people_status,IFNULL(t2.is_supplement,'0')) separator ',') as mateName from t_mate t1";
        sql += " left join t_people t2 on t2.id=t1.mate_id";
        sql += " where t2.people_status<>9";
        sql += " GROUP BY t1.people_id) t2 on t2.people_id=t1.id";
        sql += " where t1.people_status<>9 and t1.people_status=1 and t1.family_id=?";
        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("isIndex")) && CommonUtil.parseInt(params.get("isIndex")) == 1){
                sql += " and people_status=1";
            }
            if(!CommonUtil.isBlank(params.get("peopleType"))){// && "1".equals(params.get("peopleType"))
                sql += " and people_type='" + params.get("peopleType") + "'";
            }
            if(!CommonUtil.isBlank(params.get("peopleName"))){
                sql += " and name='" + params.get("peopleName") + "'";
            }
            if(!CommonUtil.isBlank(params.get("generation"))){
                sql += " and generation='" + params.get("generation") + "'";
            }
            if(!CommonUtil.isBlank(params.get("superiorId"))){
                sql += " and superior_id='" + params.get("superiorId") + "'";
            }
            if(!CommonUtil.isBlank(params.get("state"))){
                sql += " and state='" + params.get("state") + "'";
            }

            if(!CommonUtil.isBlank(params.get("orderBy"))){
                sql += " " + params.get("orderBy");
            }
            if(!CommonUtil.isBlank(params.get("limit"))){
                sql += " " + params.get("limit");
            }
        }

        List<PeopleTree> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<PeopleTree>(PeopleTree.class),params.get("familyId"));
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
    public List<TPeople> getMateList(String peopleId) {

        StringBuffer sql = new StringBuffer("select id,name,people_status,is_supplement,die_addr from t_people where id in(");
        sql.append(" select mate_id from t_mate where people_id=?) and people_status<>9");

        List<TPeople> list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<TPeople>(TPeople.class),peopleId);

        return list;
    }

    @Override
    public TPeople getPeopleInfo(String peopleId) {

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
        sql += " order by points_type asc";
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
//                tUserPoints.setPoints((tUserPoints.getPoints() + list.get(0).getPoints()));
                tUserPoints.setInputCount(tUserPoints.getInputCount() + 1);
                tUserPointsDao.save(tUserPoints);
                i ++ ;
            }else{
                tUserPoints.setInputCount(1);
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
                tCompanyPoints.setTotalMoney((tCompanyPoints.getTotalMoney() + list.get(0).getTotalMoney()));
                tCompanyPointsDao.save(tCompanyPoints);
                i ++ ;
            }else{
                i = CommonUtil.parseInt(tCompanyPointsDao.create(tCompanyPoints));
            }
        }

        return i;
    }

//    @Override
//    public List<Map<String, Object>> getPointsRanking(Map<String,Object> params) {
//        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        //个人
//        if("1".equals(params.get("type"))){
//            String sql = "select t1.points,t1.user_id,t2.user_name from t_user_points t1,t_user_front t2";
//            sql += " where t1.user_id=t2.id ";
//            if(!CommonUtil.isBlank(params.get("userType"))){
//                sql += " and user_type='" + params.get("userType") + "'";
//            }
//            sql += " order by t1.points desc";
//            list = jdbcTemplate.queryForList(sql);
//        }else{//公司
//            String sql = "select t1.points,t1.company_id,t2.company_name from t_company_points t1,t_company_sponsor t2";
//            sql += " where t1.company_id=t2.id order by t1.points desc";
//            list = jdbcTemplate.queryForList(sql);
//        }
//
//        return list;
//    }
    @Override
    public List<Map<String, Object>> getPointsRanking(Map<String,Object> params) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      //获取录入族人积分对应关系
        List<TPointsDic> listDic1 = this.getPointsRelation(1,1);
        int pointsCurrent4In = 0;//录入人积分
        if(listDic1 != null && listDic1.size() > 0){
        	pointsCurrent4In = listDic1.get(0).getPointsValue() / listDic1.get(0).getPointsNum();
        }
        //获取录入族人积分对应关系
        List<TPointsDic> listDic2 = this.getPointsRelation(2,1);
        int pointsCurrent4Pay = 0;//充值积分
        if(listDic2 != null && listDic2.size() > 0){
        	pointsCurrent4Pay = listDic2.get(0).getPointsValue() / listDic2.get(0).getPointsNum();
        }

        String rankfamilyId = params.get("rankfamilyId") + "";
        //个人
        if(CommonUtil.parseInt(params.get("type")) == 1){
            
            String sql = "select t1.points,t1.input_count,t1.input_count*" + pointsCurrent4In + "+t1.total_money*" + pointsCurrent4Pay + " totalPoints,";
            sql += " t1.user_id,t2.user_name,t2.login_name from t_user_points t1,t_user_1 t2";
            sql += " where t1.user_id=t2.id ";
//            if(!CommonUtil.isBlank(params.get("userType"))){
//                sql += " and user_type='" + params.get("userType") + "'";
//            }
            if(!CommonUtil.isBlank(params.get("province"))){
                sql += " and t2.province='" + params.get("province") + "'";
            }
            if(!CommonUtil.isBlank(params.get("city"))){
                sql += " and t2.city='" + params.get("city") + "'";
            }
            if(!CommonUtil.isBlank(params.get("district"))){
                sql += " and t2.district='" + params.get("district") + "'";
            }
            if(!CommonUtil.isBlank(params.get("district"))){
                sql += " and t2.id<>6 and t2.id<>99 and t2.id<>7 and t2.id<>9 and t2.id<>10 and t2.id<>11 and t2.id<>15";
            }
            if(!CommonUtil.isBlank(rankfamilyId)){
                sql += " and t2.rankfamily='" + rankfamilyId + "'";
            }
            sql += " order by totalPoints desc";
            list = jdbcTemplate.queryForList(sql);
        }else{//公司

            String sql = "select t1.points,t1.total_money,t1.total_money*" + pointsCurrent4Pay + " totalPoints";
            sql += ",t1.company_id,t2.company_name,t2.company_login_name from t_company_points t1,t_company_sponsor t2";
            sql += " where t1.company_id=t2.id and t2.state=1";
            if(!CommonUtil.isBlank(rankfamilyId)){
                sql += " and t2.rankfamily='" + rankfamilyId + "'";
            }
            sql += " order by totalPoints desc";
            list = jdbcTemplate.queryForList(sql);
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> getMeritocrat(Map<String, Object> params) {

        String sql = "select t1.meritocrat_name,t1.meritocrat_desc,t1.meritocrat_attr_id,";
        sql += " t1.meritocrat_area,t1.meritocrat_addr,t1.post_code,t1.phone,t1.fax,t1.photo";
        sql += ",t2.meritocrat_attr ";
        sql += " from t_meritocrat t1,t_meritocrat_attr t2 where t1.meritocrat_attr_id=t2.id and t1.state<>9 and t2.state=1";

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

        System.out.println(sql);
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

        String sql = " select count(*) total from t_meritocrat t1,t_meritocrat_attr t2 where t1.meritocrat_attr_id=t2.id and t1.state<>9 and t2.state=1";

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

        //先删除原有的申请
        String sql = " delete from t_family_merge where primary_family_id=?";
        jdbcTemplate.update(sql,tFamilyMerge.getPrimaryFamilyId());

        //添加收录申请
        int i = CommonUtil.parseInt(tFamilyMergeDao.create(tFamilyMerge));

        //修改t_family收录状态为申请状态
        sql = "update t_family set supplement_flag=2 where id=?";
        i += jdbcTemplate.update(sql,tFamilyMerge.getPrimaryFamilyId());

        return i;
    }

    @Override
    public List<TFamilyMerge> getMergeList(Map<String, Object> params) {

        List<TFamilyMerge> list = tFamilyMergeDao.find(params);

        return list;
    }

	@Override
	public int addMoney(TUserMoney tUserMoney) {

		int i = CommonUtil.parseInt(tUserMoneyDao.create(tUserMoney));
		
		//修改积分
        TUserPoints tUserPoints = new TUserPoints();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", tUserMoney.getUserId());
        List<TUserPoints> tUserPointsList = tUserPointsDao.find(params);
        if(tUserPointsList != null && tUserPointsList.size() > 0){
        	tUserPoints = tUserPointsList.get(0);
        	tUserPoints.setTotalMoney(CommonUtil.parseDouble(tUserMoney.getPayMoney()) + CommonUtil.parseDouble(tUserPoints.getTotalMoney()));
        	tUserPointsDao.save(tUserPoints);
        }else{
        	tUserPoints = new TUserPoints();
        	tUserPoints.setUserId(tUserMoney.getUserId());
        	tUserPoints.setTotalMoney(tUserMoney.getPayMoney());
        	tUserPointsDao.create(tUserPoints);
        }
		 return i;
	}
	
	@Override
    public List<TUserMoney> getUserMoney(Map<String, Object> params) {
        Map<String, Object> paramss = new HashMap<String, Object>();
        paramss.put("userId",CommonUtil.parseInt(params.get("userId")));
        paramss.put("state",1);
        List<TUserMoney> list = tUserMoneyDao.find(paramss);
        return list;
    }

	@Override
    public double getTotalUserMoney(int userId) {
        String sql = "select user_id, sum(pay_money) totalMoney from t_user_money where user_id=? and state=1 group by user_id";
        List<Map<String,Object>> listMoney = jdbcTemplate.queryForList(sql,userId);
        double total = 0.0;
        if(listMoney != null && listMoney.size() > 0){
            total = CommonUtil.parseDouble(listMoney.get(0).get("totalMoney"));
        }

        return total;
    }

    @Override
    public int getTotalIncludeNum(int userId) {
        String sql = "select * from t_user_points where user_id=?";
        List<TUserPoints> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TUserPoints>(TUserPoints.class),userId);
        if(list != null && list.size() > 0){
            return list.get(0).getInputCount();
        }

        return 0;
    }

    @Override
    public int getFamilyMaxGeneration(int familyId) {

        String sql = "select max(generation) maxGeneration from t_people where family_id=? and people_status=1";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,familyId);
        if(list != null && list.size() > 0){
            return CommonUtil.parseInt(list.get(0).get("maxGeneration"));
        }

        return 0;
    }

    @Override
    public int getFamilyTotalPeopleNum(int familyId,int state) {
        String sql = "select count(id) peopleCount from t_people where people_status=1 and people_type=1";
        if(!CommonUtil.isBlank(familyId) && familyId > -1){
            sql += " and family_id=" + familyId;
        }
        if(!CommonUtil.isBlank(state) && state > -1){
            sql += " and state=" + state;
        }

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0){
            return CommonUtil.parseInt(list.get(0).get("peopleCount"));
        }
        return 0;
    }

    @Override
    public String getFamilyFromPeopleName(String name) {

        String sql = "select family_id from t_people";
        sql += " where people_status=1";
        sql += " and (name like '%" + name + "%' or special_remark like '%" + name + "%')";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        String result = "";

        if(list != null && list.size() > 0){
            for (Map map : list) {
                result += ",'" + map.get("family_id") + "'";
            }
            return result.substring(1);
        }

        return null;
    }

    @Override
    public int getTotalFamilyNum(Map<String, Object> params) {

        String sql = "select count(id) as num from t_family where state=1";
        if(!CommonUtil.isBlank(params)){

            if(!"1".equals(params.get("isadmin")) && CommonUtil.parseInt(params.get("isadmin")) != 1){
                sql += " and ((create_id='" + params.get("userId") + "' or id in (select family_id from t_user_family where user_id='" + params.get("userId") + "'))";
                sql += ")";
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
            if(!CommonUtil.isBlank(params.get("familyName"))){
                sql += " and family_name like '%" + params.get("familyName") + "%'";
            }
        }
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        int total = 0;
        if(list != null && list.size() > 0){
            total = CommonUtil.parseInt(list.get(0).get("num"));
        }
        return total;
    }

    /**
     * 递归删除族人以及其子孙
     * @param peopleId
     * @param familyId
     * @param peopleType
     * @return
     */
    private int deletePeopleAndChildren(String peopleId, int familyId, int peopleType){

        int i=0;
        //如果是本族人，查询当前成员是否含有下一代人
        if(peopleType == 1){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("fatherId",peopleId);
            params.put("familyId",familyId);
            params.put("superiorId",peopleId);
            //删除下一代
            List<TPeople> list = this.getPeopleList(params);
            if(list != null && list.size() > 0){
                for(TPeople tPeople : list){
                    deletePeopleAndChildren(tPeople.getId(),tPeople.getFamilyId(),tPeople.getPeopleType());
                    String sql = "delete from t_people where id in(select mate_id from t_mate where people_id=?) or id=?";
                    i += jdbcTemplate.update(sql,tPeople.getId(),tPeople.getId());
//                    tPeopleDao.remove(tPeople);
//                    tPeopleDao.clear();
                    //删除配偶
//                    sql = "delete from t_people where id=?";
//                    i += jdbcTemplate.update(sql,tPeople.getId());
                    i ++ ;
                }
            }

        }

        return i;
    }

    /**
     * 递归查询要删除的族人及其子孙的id
     * @param peopleId
     * @param familyId
     * @param peopleType
     * @return
     */
    private String getdeletePeopleAndChildrenid(String peopleId, int familyId, int peopleType){
        StringBuilder ss = new StringBuilder();
        ss.append(",'" + peopleId + "'");
        List<String> result = new ArrayList<String>();
        int i=0;
        //如果是本族人，查询当前成员是否含有下一代人
        if(peopleType == 1){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("fatherId",peopleId);
            params.put("familyId",familyId);
            params.put("superiorId",peopleId);
            //删除下一代
            List<TPeople> list = this.getPeopleList(params);
            if(list != null && list.size() > 0){
                for(TPeople tPeople : list){
//                    ss.append(",'" + tPeople.getId() + "'");
                    ss.append(getdeletePeopleAndChildrenid(tPeople.getId(),tPeople.getFamilyId(),tPeople.getPeopleType()));
                    i ++ ;
                }
            }

        }

        return ss.toString();
    }

}
