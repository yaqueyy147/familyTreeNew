package com.witkey.familyTree.service.fronts;

import com.witkey.familyTree.domain.TCompanyMoney;
import com.witkey.familyTree.domain.TCompanyPhoto;
import com.witkey.familyTree.domain.TCompanySponsor;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/1/7.
 */
public interface CompanyService {

    //公司注册
    public int createCompanyUser(TCompanySponsor tCompanySponsor);

    //保存修改公司信息
    public int saveCompanyInfo(TCompanySponsor tCompanySponsor);

    //获取赞助商list
    public List<TCompanySponsor> getCompanyList();

    //根据条件查询公司充值情况
    public List<TCompanyMoney> getCompanyMoney(Map<String,Object> params);

    //根据companyId查询公司
    public TCompanySponsor getCompanyFromId(int companyId);

    //根据用户名和密码获取公司信息
    public List<TCompanySponsor> getCompanyInfo(Map<String,Object> params);

    //根据companyId获取充值总数
    public double getTotalCompanyMoney(int companyId);

    //添加公司冲值记录
    public int addMoney(TCompanyMoney tCompanyMoney);

    //根据companyId获取公司宣传照片
    public List<TCompanyPhoto> getCompanyPhoto(int companyId);

    //新增公司宣传图片
    public int saveCompanyPhoto(TCompanyPhoto tCompanyPhoto);
}
