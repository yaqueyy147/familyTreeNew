package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.consoles.LogService;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.service.fronts.UserService;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by suyx on 2017/1/11.
 */
@Controller
@RequestMapping(value = "/consoles")
public class ConsoleUserController {

    private static final Logger LOGGER = Logger.getLogger(ConsoleUserController.class);

    @Autowired
    private ConsoleService consoleService;
    @Autowired
    private FamilyService familyService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @RequestMapping(value = "/volunteer")
    public ModelAndView auditVolunteer(Model model){
//        Map<String,Object> params = new HashMap<String,Object>();
//        List<Map<String,Object>> list = consoleService.getVolunteerApplyList(params);
//        model.addAttribute("volunteerApplyList",list);
        return new ModelAndView("/consoles/volunteerList");
    }

    /**
     * 志愿者列表
     * @param params
     * @return
     */
    @RequestMapping(value= "/volunteerList")
    @ResponseBody
    public Map<String,Object> getVolunteerList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = consoleService.getVolunteerApplyList(params);
        result.put("dataList",list);
        return result;
    }

    /**
     * 志愿者列表
     * @param params
     * @return
     */
    @RequestMapping(value= "/userFrontList")
    @ResponseBody
    public Map<String,Object> getUserFrontList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        params.put("userFrom",1);
        List<Map<String,Object>> list = consoleService.getUser1List(params);
        result.put("dataList",list);
        return result;
    }

    /**
     * 审核志愿者
     * @param params
     * @return
     */
    @RequestMapping(value = "/auditVolunteer")
    @ResponseBody
    public Map<String,Object> auditVolunteer(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,Object> params) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";
        params.put("auditMan",userName);
        int i = 0;
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            i = consoleService.auditVolunteer(params);
            map.put("msg","审核完成");
            map.put("code",1);
        }catch (Exception e){
            LOGGER.error("操作出错了-->",e);
            map.put("msg","系统错误");
            map.put("code",-1);
        }
        map.put("msg","审核完成");
        map.put("code",1);
        return map;
    }

    /**
     * 用户页面
     * @return
     */
    @RequestMapping(value = "user")
    public ModelAndView user(Model model){

        List<Map<String,Object>> familylist = familyService.getFamilyList2(null);
        model.addAttribute("familylist",familylist);
        return new ModelAndView("/consoles/userSetting");
    }

    /**
     * 用户列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "userList")
    @ResponseBody
    public Map<String,Object> getUserList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
//        List<TUserBase> list = consoleService.getUserBase(params);
        List<Map<String,Object>> list = consoleService.getUser1List(params);
        result.put("dataList",list);
        return result;
    }

//    /**
//     * 保存用户
//     * @param tUserBase
//     * @return
//     */
//    @RequestMapping(value = "saveUserBase")
//    @ResponseBody
//    public Map<String,Object> saveUserBase(HttpServletRequest request, TUserBase tUserBase) throws Exception{
//        Map<String,Object> result = new HashMap<String,Object>();
//        int i = 0;
//
//        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
//        String userName = consolesUser.get("userName") + "";
//
//        Map<String,Object> params = new HashMap<String,Object>();
//
//        if(tUserBase.getId() == 0){//新建用户，需要设置加密密码
//            //检查用户名是否已经存在了
//            params.put("userName",tUserBase.getUserName());
//            List<TUserBase> list = consoleService.getUserBase(params);
//            if(list != null && list.size() > 0){
//                result.put("msg","该用户已存在!");
//                result.put("tUserBase",tUserBase);
//                result.put("code",99);
//                return result;
//            }
//
//            tUserBase.setUserPassword(CommonUtil.string2MD5(tUserBase.getUserPassword()));
//            tUserBase.setCreateMan(userName);
//            tUserBase.setCreateTime(CommonUtil.ObjToDate(CommonUtil.getDateLong()));
//
//        }else{//修改用户，不修改密码
//            params = new HashMap<String,Object>();
//            params.put("id",tUserBase.getId());
//            List<TUserBase> list = consoleService.getUserBase(params);
//            tUserBase.setCreateMan(list.get(0).getCreateMan());
//            tUserBase.setCreateTime(list.get(0).getCreateTime());
////            i = consoleService.saveUserBase(tUserBase);
//        }
//        i = consoleService.saveUserBase(tUserBase);
//
//        result.put("msg","保存成功!");
//        result.put("tUserBase",tUserBase);
//        result.put("code",i);
//        return result;
//    }

    /**
     * 保存用户
     * @param tUser1
     * @return
     */
    @RequestMapping(value = "saveUserBase")
    @ResponseBody
    public Map<String,Object> saveUserBase(HttpServletRequest request,HttpServletResponse response, TUser1 tUser1) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        int i = 0;

        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";

        Map<String,Object> params = new HashMap<String,Object>();
        
        if(tUser1.getId() == 0){//新建用户，需要设置加密密码
            //检查用户名是否已经存在了
            params.put("loginName",tUser1.getLoginName());
            List<Map<String,Object>> list = consoleService.getUser1List(params);
            if(list != null && list.size() > 0){
                result.put("msg","该用户已存在!");
                result.put("tUserBase",tUser1);
                result.put("code",99);
                return result;
            }
            tUser1.setUserFrom(2);
            tUser1.setPassword(CommonUtil.string2MD5(tUser1.getPassword()));
            tUser1.setCreateMan(userName);
            tUser1.setCreateTime(CommonUtil.getDateLong());

        }else{//修改用户，不修改密码
            params = new HashMap<String,Object>();
            params.put("id",tUser1.getId());
            List<Map<String,Object>> list = consoleService.getUser1List(params);
            tUser1.setCreateMan(list.get(0).get("createMan") + "");
            tUser1.setCreateTime(list.get(0).get("createTime") + "");
            tUser1.setUserFrom(CommonUtil.parseInt(list.get(0).get("userFrom")));
            tUser1.setPassword(consolesUser.get("password") + "");
//            i = consoleService.saveUserBase(tUserBase);
        }
        i = consoleService.saveUser1(tUser1);

        result.put("msg","保存成功!");
        result.put("tUserBase",tUser1);
        result.put("code",i);
        return result;
    }

//    /**
//     * 修改密码
//     * @param params
//     * @return
//     */
//    @RequestMapping(value = "modifyPassword")
//    @ResponseBody
//    public Map<String,Object> modifyPassword(@RequestParam Map<String,Object> params){
//        Map<String,Object> result = new HashMap<String,Object>();
//        Map<String,Object> condition = new HashMap<String,Object>();
//
//        condition.put("id",params.get("userId"));
//
//        List<TUserBase> list = consoleService.getUserBase(condition);
//
//        String oldPassword = CommonUtil.string2MD5(params.get("oldPassword") + "");
//
//        if(!CommonUtil.isBlank(oldPassword)){
//            if(oldPassword.equals(list.get(0).getUserPassword())){
//                result.put("msg","原密码输入有误!");
//                result.put("code",-2);
//                return result;
//            }
//        }
//
//        int i = consoleService.modifyPassword(params);
//        result.put("msg","修改成功!");
//        result.put("code",i);
//        return result;
//    }

    @RequestMapping(value = "setUserOperate")
    @ResponseBody
    public Map<String,Object> setUserOperate(int userId,int state,int type){
        Map<String,Object> result = new HashMap<String,Object>();
        if(type == 1){//设置用户可登录前台

        }
        return result;
    }

    /**
     * 修改密码
     * @param params
     * @return
     */
    @RequestMapping(value = "modifyPassword")
    @ResponseBody
    public Map<String,Object> modifyPassword(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,Object> condition = new HashMap<String,Object>();

        condition.put("id",params.get("userId"));

        List<Map<String,Object>> list = consoleService.getUser1List(condition);

        String oldPassword = CommonUtil.string2MD5(params.get("oldPassword") + "");

        //非管理员操作时，要验证原密码
        if(CommonUtil.isBlank(params.get("isAdmin"))){
            if(!CommonUtil.isBlank(oldPassword)){
                if(!oldPassword.equals(list.get(0).get("password"))){
                    result.put("msg","原密码输入有误!");
                    result.put("code",-2);
                    return result;
                }
            }
        }

        int i = consoleService.modifyPassword(params);
        result.put("msg","修改成功!");
        result.put("code",i);
        return result;
    }

    /**
     * 删除用户
     * @param params
     * @return
     */
    @RequestMapping(value = "deleteUser")
    @ResponseBody
    public Map<String,Object> deleteUser(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        int i = consoleService.deleteUser(params);
        result.put("code",i);
        result.put("msg","操作成功!");
        return result;
    }
    
    /**
     * 用户功能权限树
     * @param params
     * @return
     */
    @RequestMapping(value = "userResourceList")
    @ResponseBody
    public Map<String,Object> userResourceList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
        List<TResource> list = consoleService.getResourceList(params);

        Map<String,Object> params1 = new HashMap<>();
        params1.put("userId",CommonUtil.parseInt(params.get("userId")));
        List<TUserResource> list1 = consoleService.getUserResource(params);

        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

        for (TResource tt : list) {
            Map<String,Object> temp = new HashMap<String,Object>();
            temp = CommonUtil.bean2Map(tt);
            temp.put("pId",tt.getParentSourceId());
            temp.put("pid",tt.getParentSourceId());
            temp.put("name",tt.getSourceName());
            temp.put("text",tt.getSourceName());
            temp.put("_parentId",tt.getParentSourceId());
            temp.put("resourceState",tt.getState());
            temp.put("state","open");
            temp.put("open",true);
            for(TUserResource tt1 : list1){
                if(tt1.getResourceId() == tt.getId()){
                    temp.put("checked",true);
                    break;
                }
            }
            resultList.add(temp);
        }

        result.put("resourceList",resultList);
        return result;
    }

    /**
     * 用户功能权限设置
     * @param params
     * @return
     */
    @RequestMapping(value = "saveAuth")
    @ResponseBody
    public Map<String,Object> saveAuth(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();

        int i = consoleService.saveAuth(params);

        result.put("code",i);
        result.put("msg","授权完成");
        return result;
    }

    @RequestMapping(value = "saveUserFamily")
    @ResponseBody
    public Map<String,Object> saveUserFamily(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();

        int i = consoleService.saveAuth(params);

        result.put("code",i);
        result.put("msg","授权完成");
        return result;
    }

    @RequestMapping(value = "/addMoney")
    @ResponseBody
    public Map<String,Object> addMoney(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        List<TPointsDic> listP = familyService.getPointsRelation(2,1);
        double points = 0.0;
        if(listP != null && listP.size() > 0){
            points = Math.ceil(listP.get(0).getPointsValue()/listP.get(0).getPointsNum());
        }

        int i = 0;
        if(CommonUtil.parseInt(params.get("type")) == 1){
            TUserMoney tUserMoney = new TUserMoney();
            tUserMoney.setUserId(CommonUtil.parseInt(params.get("userId")));
            tUserMoney.setPayDesc(params.get("payDesc") + "");
            tUserMoney.setPayMoney(CommonUtil.parseDouble(params.get("payMoney")));
            tUserMoney.setPayMan(params.get("userName") + "");
            tUserMoney.setPayTime(new Date());
            tUserMoney.setState(1);
            tUserMoney.setCurrentPoints(points);

            i += familyService.addMoney(tUserMoney);
        }else if(CommonUtil.parseInt(params.get("type")) == 2){
            TCompanyMoney tCompanyMoney = new TCompanyMoney();
            tCompanyMoney.setCompanyId(CommonUtil.parseInt(params.get("companyId")));
            tCompanyMoney.setPayDesc(params.get("payDesc") + "");
            tCompanyMoney.setPayMoney(CommonUtil.parseDouble(params.get("payMoney")));
            tCompanyMoney.setPayMan(params.get("companyName") + "");
            tCompanyMoney.setPayTime(new Date());
            tCompanyMoney.setState(1);
            tCompanyMoney.setCurrentPoints(points);

            i += companyService.addMoney(tCompanyMoney);
        }

        result.put("code",i);
        result.put("msg","添加成功!");
        return result;
    }

    @RequestMapping(value = "/moneyList")
    @ResponseBody
    public Map<String,Object> moneyList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        if(CommonUtil.parseInt(params.get("type")) == 2){
            List<TCompanyMoney> list = companyService.getCompanyMoney(params);
            result.put("dataList",list);
        } else if(CommonUtil.parseInt(params.get("type")) == 1){
            List<TUserMoney> list = familyService.getUserMoney(params);
            result.put("dataList",list);

        }
        return result;
    }

    @RequestMapping(value = "setuserrankfamily")
    @ResponseBody
    public Map<String,Object> setuserrankfamily(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,Object> params) throws Exception{

        Map<String,Object> map = new HashMap<String,Object>();
        try {
            int i = userService.setuserrankfamily(params);
            map.put("msg","设置成功");
            map.put("code",1);
        }catch (Exception e){
            LOGGER.error("操作出错了-->",e);
            map.put("msg","系统错误");
            map.put("code",-1);
        }
        return map;
    }

}
