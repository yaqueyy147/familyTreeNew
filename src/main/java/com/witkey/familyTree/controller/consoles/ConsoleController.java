package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.domain.TUserBase;
import com.witkey.familyTree.domain.TVolunteer;
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

    @RequestMapping(value= "/volunteerList")
    @ResponseBody
    public Map<String,Object> getVolunteerList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = consoleService.getVolunteerApplyList(params);
        result.put("dataList",list);
        return result;
    }

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

    @RequestMapping(value = "company")
    public ModelAndView companyList(Model model){

//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("state",0);
//        List<Map<String,Object>> list = consoleService.getCompanyList(params);
//        model.addAttribute("companyList",list);
        return new ModelAndView("/consoles/companyList");
    }

    @RequestMapping(value = "companyList")
    @ResponseBody
    public Map<String,Object> getCompanyList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = consoleService.getCompanyList(params);
        result.put("dataList",list);
        return result;
    }

    @RequestMapping(value = "family")
    public ModelAndView family(){
        return new ModelAndView("/consoles/familyList");
    }

    @RequestMapping(value = "familyList")
    @ResponseBody
    public Map<String,Object> getFamilyList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<TFamily> list = familyService.getFamilyList("",0);
        List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
        for(TFamily tFamily : list){
            int peopleCount = 0;
            Map<String,Object> map = new HashMap<String,Object>();
            List<TPeople> peopleList = familyService.getPeopleList(tFamily.getId());
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

    @RequestMapping(value = "familyTree")
    public ModelAndView familyTree(Model model, @RequestParam Map<String,Object> map){
        String familyId = map.get("familyId") + "";
        model.addAttribute("familyId",familyId);
        TFamily tFamily = familyService.getFamilyListFromId(CommonUtil.parseInt(familyId));
        model.addAttribute("tFamily",tFamily);
        return new ModelAndView("/consoles/familyTree_console");
    }

    @RequestMapping(value = "getPeopleInfo")
    @ResponseBody
    public Map<String,Object> getPeopleInfo(int peopleId){
        Map<String,Object> result = new HashMap<String,Object>();
        TPeople tPeople = familyService.getPeopleInfo(peopleId);
        result.put("tPeople",tPeople);
        return result;
    }

    @RequestMapping(value = "user")
    public ModelAndView user(){
        return new ModelAndView("/consoles/userSetting");
    }

    @RequestMapping(value = "userList")
    @ResponseBody
    public Map<String,Object> getUserList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<TUserBase> list = consoleService.getUserBase(params);
        result.put("dataList",list);
        return result;
    }

    @RequestMapping(value = "saveUserBase")
    @ResponseBody
    public Map<String,Object> saveUserBase(TUserBase tUserBase){
        tUserBase.setUserPassword(CommonUtil.string2MD5(tUserBase.getUserPassword()));
        int i = consoleService.saveUserBase(tUserBase);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msg","保存成功!");
        result.put("tUserBase",tUserBase);
        result.put("code",i);
        return result;
    }

    @RequestMapping(value = "deleteUser")
    @ResponseBody
    public Map<String,Object> deleteUser(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        int i = consoleService.deleteUser(params);
        result.put("code",i);
        result.put("msg","操作成功!");
        return result;
    }

}
