package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2018-03-03.
 */
@Controller
@RequestMapping(value = "/consoles")
public class ConsoleCompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleCompanyController.class);

    @Autowired
    private CompanyService companyService;
    @Autowired
    private FamilyService familyService;
    @Autowired
    private ConsoleService consoleService;

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
        List<TFamily> familylist = familyService.getFamilyList2(null);
        model.addAttribute("familylist",familylist);
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
     * 审核赞助商
     * @param params
     * @return
     */
    @RequestMapping(value = "/auditCompany")
    @ResponseBody
    public Map<String,Object> auditCompany(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,Object> params) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");

        String userName = consolesUser.get("userName") + "";
        params.put("auditMan",userName);
        int i = 0;
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            i = consoleService.auditCompany(params);
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

    @RequestMapping(value = "setrankfamily")
    @ResponseBody
    public Map<String,Object> setrankfamily(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,Object> params) throws Exception{

        Map<String,Object> map = new HashMap<String,Object>();
        try {
            int i = companyService.setrankfamily(params);
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
