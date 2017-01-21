package com.witkey.familyTree.service.consoles.impl;

import com.witkey.familyTree.dao.consoles.TVolunteerDao;
import com.witkey.familyTree.domain.TVolunteer;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private CompanyService companyService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String,Object>> getVolunteerApplyList(Map<String,Object> params) {

        String sql = "select t1.id volunteerId,t1.apply_desc,t1.audit_state,t1.create_time applyTime, ";
        sql += " t2.id userId,t2.user_name,t2.phone,t2.is_volunteer";
        sql += " from t_volunteer t1,t_user_front t2 where t1.user_id=t2.id and t1.audit_state=?";

        System.out.println(sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,0);

        return list;
    }

    @Override
    public int auditVolunteer(Map<String, Object> params) {

        String sql = "update t_volunteer set audit_state=?,audit_desc=?,audit_time=now() and audit_man=?";
        sql += " where id=?";

        int i = jdbcTemplate.update(sql,params.get("auditState"),params.get("auditDesc"),params.get("auditMan"),params.get("volunteerId"));

        sql = "update t_user_front set is_volunteer=? where id=?";
        i += jdbcTemplate.update(sql,params.get("auditState"),params.get("applyManId"));

        return i;
    }

    @Override
    public List<Map<String, Object>> getCompanyList(Map<String, Object> params) {

        String sql = "select * from t_company_sponsor where state=?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params.get("state"));

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
}

