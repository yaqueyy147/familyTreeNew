package com.witkey.familyTree.service.fronts.impl;

import com.witkey.familyTree.dao.fronts.TFamilyDao;
import com.witkey.familyTree.dao.fronts.TPeopleDao;
import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    /**
     * 根据登录人查询创建的族谱list
     * @return
     */
    @Override
    public List<TFamily> getFamilyList(String userName) {
        Map<String,Object> filter = new HashMap<String,Object>();
        filter.put("createMan",userName);

        List<TFamily> list = tFamilyDao.find(filter);

        return list;
    }

    /**
     * 根据族谱ID获取家族成员
     * @param familyId
     * @return
     */
    @Override
    public List<TPeople> getPeopleList(int familyId) {
        Map<String,Object> filter = new HashMap<String,Object>();
        filter.put("familyId",familyId);

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
        filter.put("generation",generation-1);
        filter.put("sex",1);
        List<TPeople> listFather = tPeopleDao.find(filter);
        result.put("fatherList",listFather);

        //查询父母辈可能作为母亲的人
        filter = new HashMap<String,Object>();
        filter.put("familyId",familyId);
        filter.put("generation",generation-1);
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
    public TFamily getFamilyListFromId(int familyId) {
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
}
