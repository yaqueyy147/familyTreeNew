package com.witkey.familyTree.service.fronts.impl;

import com.witkey.familyTree.dao.fronts.TCompanyMoneyDao;
import com.witkey.familyTree.dao.fronts.TCompanyPhotoDao;
import com.witkey.familyTree.dao.fronts.TCompanySponsorDao;
import com.witkey.familyTree.domain.TCompanyMoney;
import com.witkey.familyTree.domain.TCompanyPhoto;
import com.witkey.familyTree.domain.TCompanySponsor;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

    @Override
    public int createCompanyUser(TCompanySponsor tCompanySponsor) {

        int companyId = CommonUtil.parseInt(tCompanySponsorDao.create(tCompanySponsor));

        return companyId;
    }

    @Override
    public List<TCompanySponsor> getCompanyList() {
        List<TCompanySponsor> list = tCompanySponsorDao.list();

        return list;
    }

    @Override
    public List<TCompanyMoney> getCompanyMoney(Map<String, Object> params) {

        List<TCompanyMoney> list = tCompanyMoneyDao.find(params);
        return list;
    }

    @Override
    public TCompanySponsor getCompanyFromId(int companyId) {

        TCompanySponsor tCompanySponsor = tCompanySponsorDao.get(companyId);

        return tCompanySponsor;
    }

    @Override
    public double getTotalCompanyMoney(int companyId) {
        String sql = "select company_id, sum(pay_money) totalMoney where company_id=?";
        List<Map<String,Object>> listMoney = jdbcTemplate.queryForList(sql,companyId);

        double total = CommonUtil.parseDouble(listMoney.get(0).get("totalMoney"));

        return total;
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
}