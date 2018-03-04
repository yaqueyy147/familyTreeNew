package com.witkey.familyTree.service.fronts.impl;

import com.witkey.familyTree.dao.consoles.TLogDao;
import com.witkey.familyTree.dao.fronts.*;
import com.witkey.familyTree.domain.*;
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
 * Created by suyx on 2017/1/7.
 */

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private TCompanySponsorDao tCompanySponsorDao;
    @Autowired
    private TCompanyMoneyDao tCompanyMoneyDao;
    @Autowired
    private TCompanyPhotoDao tCompanyPhotoDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void settCompanySponsorDao(TCompanySponsorDao tCompanySponsorDao) {
        this.tCompanySponsorDao = tCompanySponsorDao;
    }

    public void settCompanyMoneyDao(TCompanyMoneyDao tCompanyMoneyDao) {
        this.tCompanyMoneyDao = tCompanyMoneyDao;
    }

    public void settCompanyPhotoDao(TCompanyPhotoDao tCompanyPhotoDao) {
        this.tCompanyPhotoDao = tCompanyPhotoDao;
    }

    @Resource
    private TCompanyPointsDao tCompanyPointsDao;
    
    public void settCompanyPointsDao(TCompanyPointsDao tCompanyPointsDao) {
		this.tCompanyPointsDao = tCompanyPointsDao;
	}

	@Resource
    private TLogDao tLogDao;

    public void settLogDao(TLogDao tLogDao) {
        this.tLogDao = tLogDao;
    }

    @Resource
    private TCompanyIntroduceDao tCompanyIntroduceDao;

    public void settCompanyIntroduceDao(TCompanyIntroduceDao tCompanyIntroduceDao) {
        this.tCompanyIntroduceDao = tCompanyIntroduceDao;
    }

    @Override
    public int createCompanyUser(TCompanySponsor tCompanySponsor) {

        int companyId = CommonUtil.parseInt(tCompanySponsorDao.create(tCompanySponsor));

        return companyId;
    }

    @Override
    public int saveCompanyInfo(TCompanySponsor tCompanySponsor) {
        int i=0;
        tCompanySponsorDao.save(tCompanySponsor);
        i++;
        return i;
    }

    @Override
    public List<TCompanySponsor> getCompanyList() {
        List<TCompanySponsor> list = tCompanySponsorDao.list();

        return list;
    }

    @Override
    public List<TCompanyMoney> getCompanyMoney(Map<String, Object> params) {
        Map<String, Object> paramss = new HashMap<String, Object>();
        paramss.put("companyId",CommonUtil.parseInt(params.get("companyId")));
        paramss.put("state",1);
        List<TCompanyMoney> list = tCompanyMoneyDao.find(paramss);
        return list;
    }

    @Override
    public TCompanySponsor getCompanyFromId(int companyId) {

        TCompanySponsor tCompanySponsor = tCompanySponsorDao.get(companyId);

        return tCompanySponsor;
    }

    @Override
    public List<TCompanySponsor> getCompanyInfo(Map<String, Object> params) {
        String sql = "select * from t_company_sponsor where 1=1";

        if(!CommonUtil.isBlank(params.get("loginName"))){
            sql += " and company_login_name='" + params.get("loginName") + "'";
        }
        if(!CommonUtil.isBlank(params.get("password"))){
            sql += " and company_login_password='" + params.get("password") + "'";
        }

        List<TCompanySponsor> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<TCompanySponsor>(TCompanySponsor.class));

        return list;
    }

    @Override
    public double getTotalCompanyMoney(int companyId) {
        String sql = "select company_id, sum(pay_money) totalMoney from t_company_money where company_id=? and state=1 group by company_id";
        List<Map<String,Object>> listMoney = jdbcTemplate.queryForList(sql,companyId);
        double total = 0.0;
        if(listMoney != null && listMoney.size() > 0){
            total = CommonUtil.parseDouble(listMoney.get(0).get("totalMoney"));
        }

        return total;
    }

    @Override
    public int addMoney(TCompanyMoney tCompanyMoney) {
    	//添加充值
        int i = CommonUtil.parseInt(tCompanyMoneyDao.create(tCompanyMoney));

        //修改积分
        TCompanyPoints tCompanyPoints = new TCompanyPoints();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("companyId", tCompanyMoney.getCompanyId());
        List<TCompanyPoints> tCompanyPointsList = tCompanyPointsDao.find(params);
        if(tCompanyPointsList != null && tCompanyPointsList.size() > 0){
        	tCompanyPoints = tCompanyPointsList.get(0);
        	tCompanyPoints.setTotalMoney(CommonUtil.parseDouble(tCompanyMoney.getPayMoney()) + CommonUtil.parseDouble(tCompanyPoints.getTotalMoney()));
        	tCompanyPointsDao.save(tCompanyPoints);
        }else{
        	tCompanyPoints = new TCompanyPoints();
        	tCompanyPoints.setCompanyId(tCompanyMoney.getCompanyId());
        	tCompanyPoints.setTotalMoney(tCompanyMoney.getPayMoney());
        	tCompanyPointsDao.create(tCompanyPoints);
        }
        
        return i;
    }

    @Override
    public List<TCompanyPhoto> getCompanyPhoto(int companyId) {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("companyId",companyId);
        List<TCompanyPhoto> list = tCompanyPhotoDao.find(map);

        return list;
    }

    @Override
    public int saveCompanyPhoto(TCompanyPhoto tCompanyPhoto) {

        int photoId = CommonUtil.parseInt(tCompanyPhotoDao.create(tCompanyPhoto));

        return photoId;
    }

    @Override
    public int saveIntro(TCompanyIntroduce tCompanyIntroduce) {

        int i = 0;
        if(tCompanyIntroduce.getId() > 0){
            tCompanyIntroduceDao.save(tCompanyIntroduce);
            i ++;
        }else{
            i += CommonUtil.parseInt(tCompanyIntroduceDao.create(tCompanyIntroduce));
        }

        return i;
    }

    @Override
    public List<TCompanyIntroduce> getIntro(int companyId) {

        List<TCompanyIntroduce> list = tCompanyIntroduceDao.find("from TCompanyIntroduce where companyId=?",companyId);

        return list;
    }

    @Override
    public int deleteMoney(Map<String, Object> params) {
        String ids = params.get("moneyIds") + "";
        String[] id = ids.split(",");

        //删除充值记录
        String sql = "update t_company_money set state=? where id=?";

        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,9,id[i]);
        }

        //修改积分表的充值总金额
        sql = "update t_company_points set total_money=total_money-? where company_id=?";
        ii += jdbcTemplate.update(sql,params.get("totalMoney"),params.get("companyId"));

        return ii;
    }

    @Override
    public int applySponsor(int companyId) {

        String sql = "update t_company_sponsor set state=3 where id=?";
        int i = jdbcTemplate.update(sql,companyId);

        return i;
    }

    @Override
    public int setrankfamily(Map<String, Object> params) throws Exception {

        String sql = "update t_company_sponsor set rankfamily=?,rankfamilyname=? where id=?";
        int i = jdbcTemplate.update(sql,params.get("familyid"),params.get("familyname"),params.get("companyid"));

        return i;
    }
}
