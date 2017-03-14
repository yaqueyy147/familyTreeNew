package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/1/11.
 */
@Controller
@RequestMapping(value = "/consoles")
public class ConsoleController {

    private static final Logger LOGGER = Logger.getLogger(ConsoleController.class);

    @Autowired
    private ConsoleService consoleService;
    @Autowired
    private FamilyService familyService;

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
     * 审核志愿者
     * @param params
     * @return
     */
    @RequestMapping(value = "/auditVolunteer")
    @ResponseBody
    public Map<String,Object> auditVolunteer(@RequestParam Map<String,Object> params){
        params.put("auditMan","ceshi123");
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
     * 公司列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "company")
    public ModelAndView companyList(Model model){

//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("state",0);
//        List<Map<String,Object>> list = consoleService.getCompanyList(params);
//        model.addAttribute("companyList",list);
        return new ModelAndView("/consoles/companyList");
    }

    /**
     * 赞助商列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "companyList")
    @ResponseBody
    public Map<String,Object> getCompanyList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = consoleService.getCompanyList(params);
        result.put("dataList",list);
        return result;
    }

    /**
     * 族谱列表页面
     * @return
     */
    @RequestMapping(value = "family")
    public ModelAndView family(){
        return new ModelAndView("/consoles/familyList");
    }

    /**
     * 族谱列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "familyList")
    @ResponseBody
    public Map<String,Object> getFamilyList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        List<TFamily> list = familyService.getFamilyList(null);
        List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
        for(TFamily tFamily : list){
            int peopleCount = 0;
            Map<String,Object> map = new HashMap<String,Object>();
            Map<String,Object> paramss = new HashMap<>();
            paramss.put("familyId",tFamily.getId());
            paramss.put("peopleType",1);
            List<TPeople> peopleList = familyService.getPeopleList(paramss);
            if(peopleList != null && peopleList.size() > 0)
            {
                peopleCount = peopleList.size();
            }
            map = CommonUtil.bean2Map(tFamily);
            map.put("peopleCount",peopleCount);
            list1.add(map);
        }
        result.put("dataList",list1);
        return result;
    }

    /**
     * 族谱内容页面
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "familyTree")
    public ModelAndView familyTree(Model model, @RequestParam Map<String,Object> map){
        String familyId = map.get("familyId") + "";
        model.addAttribute("familyId",familyId);
        TFamily tFamily = familyService.getFamilyFromId(CommonUtil.parseInt(familyId));
        model.addAttribute("tFamily",tFamily);
        return new ModelAndView("/consoles/familyTree_console");
    }

    /**
     * 族谱组人数据
     * @param peopleId
     * @return
     */
    @RequestMapping(value = "getPeopleInfo")
    @ResponseBody
    public Map<String,Object> getPeopleInfo(int peopleId){
        Map<String,Object> result = new HashMap<String,Object>();
        TPeople tPeople = familyService.getPeopleInfo(peopleId);
        result.put("tPeople",tPeople);
        return result;
    }

    /**
     * 用户页面
     * @return
     */
    @RequestMapping(value = "user")
    public ModelAndView user(){
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
        List<TUserBase> list = consoleService.getUserBase(params);
        result.put("dataList",list);
        return result;
    }

    /**
     * 保存用户
     * @param tUserBase
     * @return
     */
    @RequestMapping(value = "saveUserBase")
    @ResponseBody
    public Map<String,Object> saveUserBase(TUserBase tUserBase){
        Map<String,Object> result = new HashMap<String,Object>();
        int i = 0;

        Map<String,Object> params = new HashMap<String,Object>();

        if(tUserBase.getId() == 0){//新建用户，需要设置加密密码
            //检查用户名是否已经存在了
            params.put("userName",tUserBase.getUserName());
            List<TUserBase> list = consoleService.getUserBase(params);
            if(list != null && list.size() > 0){
                result.put("msg","该用户已存在!");
                result.put("tUserBase",tUserBase);
                result.put("code",99);
                return result;
            }

            tUserBase.setUserPassword(CommonUtil.string2MD5(tUserBase.getUserPassword()));
            i = consoleService.saveUserBase(tUserBase);
        }else{//修改用户，不修改密码
            params = new HashMap<String,Object>();
            params.put("id",tUserBase.getId());
            List<TUserBase> list = consoleService.getUserBase(params);
            tUserBase.setCreateMan(list.get(0).getCreateMan());
            tUserBase.setCreateTime(list.get(0).getCreateTime());
            i = consoleService.saveUserBase(tUserBase);
        }


        result.put("msg","保存成功!");
        result.put("tUserBase",tUserBase);
        result.put("code",i);
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

        List<TUserBase> list = consoleService.getUserBase(condition);

        String oldPassword = CommonUtil.string2MD5(params.get("oldPassword") + "");

        if(!CommonUtil.isBlank(oldPassword)){
            if(oldPassword.equals(list.get(0).getUserPassword())){
                result.put("msg","原密码输入有误!");
                result.put("code",-2);
                return result;
            }
        }

        int i = consoleService.modifyPassword(params);
        result.put("msg","修改成功!");
        result.put("code",i);
        return result;
    }

    /**
     * 角色页面
     * @param model
     * @return
     */
    @RequestMapping(value = "role")
    public ModelAndView role(Model model){
        return new ModelAndView("/consoles/roleSetting");
    }

    /**
     * 角色列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "roleList")
    @ResponseBody
    public Map<String,Object> getroleList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<TRole> list = consoleService.getRole(params);
        result.put("dataList",list);
        return result;
    }

    /**
     * 保存角色信息
     * @param TRole
     * @return
     */
    @RequestMapping(value = "saveRole")
    @ResponseBody
    public Map<String,Object> saveRole(TRole TRole){
        int i = consoleService.saveRole(TRole);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msg","保存成功!");
        result.put("tUserBase",TRole);
        result.put("code",i);
        return result;
    }

    /**
     * 删除角色
     * @param params
     * @return
     */
    @RequestMapping(value = "deleteRole")
    @ResponseBody
    public Map<String,Object> deleteRole(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        int i = consoleService.deleteRole(params);
        result.put("code",i);
        result.put("msg","操作成功!");
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
     * 个人积分排名页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/personalPoints")
    public ModelAndView personalPoints(Model model){
        return new ModelAndView("/consoles/personalRank");
    }

    /**
     * 个人积分排名数据
     * @return
     */
    @RequestMapping(value = "/personalPointsList")
    @ResponseBody
    public Map<String,Object> personalPointsList(){
        Map<String,Object> result = new HashMap<String,Object>();
        //个人积分排名
        List<Map<String,Object>> listPersonalPoints = familyService.getPointsRanking(1);
        result.put("listPersonalPoints",listPersonalPoints);
        return result;

    }

    /**
     * 赞助商积分排名页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/companyPoints")
    public ModelAndView companyPoints(Model model){
        return new ModelAndView("/consoles/companyRank");
    }

    /**
     * 公司积分排名数据
     * @return
     */
    @RequestMapping(value = "/companyPointsList")
    @ResponseBody
    public Map<String,Object> companyPointsList(){
        Map<String,Object> result = new HashMap<String,Object>();
        //公司积分排名
        List<Map<String,Object>> listCompanyPoints = familyService.getPointsRanking(2);
        result.put("listCompanyPoints",listCompanyPoints);
        return result;

    }

    /**
     * 英才录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/meritorcat")
    public ModelAndView meritorcat(Model model){
        //获取英才属性
        List<TMeritocratAttr> listAttr = consoleService.getMeritocratAttrList(null);
        model.addAttribute("meritorcatAttr",listAttr);
        return new ModelAndView("");
    }

    @RequestMapping(value = "/merge")
    public ModelAndView merge(Model model){
        return new ModelAndView("");
    }

}
