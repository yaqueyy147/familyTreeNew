package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TMeritocratAttr;
import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import com.witkey.familyTree.util.PageUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
@Controller
@RequestMapping(value = "familyTree")
public class IndexController {
    private static final int PAGE_SIZE = 10;//初始每页条数
    private static final int PAGE_NUM = 6;//初始显示页数
    @Autowired
    private FamilyService familyService;
    @Autowired
    private ConsoleService consoleService;

    @RequestMapping(value = {"","/","index"})
    public ModelAndView index(Model model, HttpServletRequest request) throws UnsupportedEncodingException{

        //从cookie获取用户信息
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        model.addAttribute("userInfo",jsonUser);

//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("familyArea",1);
//        List<List<TFamily>> list = new ArrayList<List<TFamily>>();
//        List<TFamily> listMainland = familyService.getFamilyList(params);
//        list.add(listMainland);
//
//        params.put("familyArea",2);
//        List<TFamily> listHongKong = familyService.getFamilyList(params);
//        list.add(listHongKong);
//
//        params.put("familyArea",3);
//        List<TFamily> listTaiwan = familyService.getFamilyList(params);
//        list.add(listTaiwan);
//
//        params.put("familyArea",4);
//        List<TFamily> listAoMen = familyService.getFamilyList(params);
//        list.add(listAoMen);
//        model.addAttribute("familyList",list);

        List<TFamily> list = familyService.getFamilyList(null);
        model.addAttribute("familyList",list);
        return new ModelAndView("/fronts/index");
    }

    @RequestMapping(value = "/queryFamily")
    @ResponseBody
    public Map<String,Object> queryFamily(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        List<TFamily> list = familyService.getFamilyList(params);

        result.put("familyList",list);
        return result;
    }

    @RequestMapping(value = "/meritocrat")
    public ModelAndView meritocrat(Model model){

        //获取英才属性
        List<TMeritocratAttr> listAttr = consoleService.getMeritocratAttrList(null);
        model.addAttribute("meritorcatAttr",listAttr);

        //获取英才属地
        List<Map<String,Object>> listArea = familyService.getMeritocratArea();
        model.addAttribute("meritorcatArea",listArea);
        return new ModelAndView("/fronts/meritocrat");
    }

    @RequestMapping(value = "meritocratList")
    @ResponseBody
    public Map<String,Object> meritorcatList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        int total = familyService.getTotalMeritocrat(params);

        int pageSize = CommonUtil.parseInt(params.get("pageSize"));
        pageSize = pageSize == 0 ? PAGE_SIZE : pageSize;
        int totalPage = (int)Math.ceil((double)total / (double) pageSize);
        int pageNo = CommonUtil.parseInt(params.get("pageNo"));
        pageNo = pageNo == 0 ? 1 : pageNo;

        params.put("pageSize",pageSize);
        params.put("beginRow",(pageNo - 1)*pageSize);

        List<Map<String,Object>> list = familyService.getMeritocrat(params);
        result.put("meritocratList",list);

        result.put("totalPage",totalPage);
        result.put("pageNo",pageNo);
        result.put("pageChanger", PageUtil.getNumberPageChanger(pageNo,totalPage,PAGE_NUM,pageSize,params.get("tableId")+""));

        return result;
    }

    /**
     * 积分排名榜
     * @param model
     * @return
     */
    @RequestMapping(value = "/pointsRanking")
    public ModelAndView pointsRanking(Model model){

        //个人积分排名
        List<Map<String,Object>> listPersonalPoints = familyService.getPointsRanking(1);
        //公司积分排名
        List<Map<String,Object>> listCompanyPoints = familyService.getPointsRanking(2);
        model.addAttribute("listPersonalPoints",listPersonalPoints);
        model.addAttribute("listCompanyPoints",listCompanyPoints);
        return new ModelAndView("/fronts/rank");
    }

}
