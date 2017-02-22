package com.witkey.familyTree.service.consoles.impl;

import com.witkey.familyTree.dao.consoles.TRoleDao;
import com.witkey.familyTree.dao.consoles.TVolunteerDao;
import com.witkey.familyTree.dao.consoles.TUserBaseDao;
import com.witkey.familyTree.domain.TRole;
import com.witkey.familyTree.domain.TUserBase;
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
    private TUserBaseDao tUserBaseDao;

    public void settUserBaseDao(TUserBaseDao tUserBaseDao) {
        this.tUserBaseDao = tUserBaseDao;
    }

    @Autowired
    private TRoleDao tRoleDao;

    public void settRoleDao(TRoleDao tRoleDao) {
        this.tRoleDao = tRoleDao;
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

        List<TUserBase> list = tUserBaseDao.find(params);

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

        String sql = "delete from t_user_base where id=?";

        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }

        return ii;
    }

    @Override
    public List<TRole> getRole(Map<String, Object> params) {
        List<TRole> list = tRoleDao.find(params);
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

        String sql = "delete from t_role where id=?";

        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }

        return ii;
    }
}

