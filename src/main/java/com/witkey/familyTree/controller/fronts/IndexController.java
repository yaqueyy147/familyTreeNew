package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TMeritocratAttr;
import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.service.fronts.UserFrontService;
import com.witkey.familyTree.service.fronts.UserService;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import com.witkey.familyTree.util.PageUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
@Controller
@RequestMapping(value = "/family")
public class IndexController {
    private static final int PAGE_SIZE = 20;//初始每页条数
    private static final int PAGE_NUM = 6;//初始显示页数
    @Autowired
    private FamilyService familyService;
    @Autowired
    private ConsoleService consoleService;
    @Autowired
    private UserFrontService userFrontService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;

    /**
     * 前台首页
     * @param model
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = {"","/","/index"})
    public ModelAndView index(Model model, HttpServletRequest request) throws UnsupportedEncodingException{

        //从cookie获取用户信息
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

        if(CommonUtil.isBlank(jsonUser)){
//            model.addAttribute("loginCode",-2);
            return new ModelAndView("/fronts/login");
        }

//        model.addAttribute("userInfo",jsonUser);
//        Map<String,Object> temp = new HashMap<String, Object>();
//        if(!CommonUtil.isBlank(jsonUser)){
//            TUser1 tUser1 = new TUser1();
//            TCompanySponsor tCompanySponsor = new TCompanySponsor();
//            if("1".equals(jsonUser.get("userType"))){
//                tUser1 = userService.getUserInfoFromId(CommonUtil.parseInt(jsonUser.get("id")));
//                temp = CommonUtil.bean2Map(tUser1);
//                temp.put("userType",1);
//
//            }else if("2".equals(jsonUser.get("userType"))){
//                tCompanySponsor = companyService.getCompanyFromId(CommonUtil.parseInt(jsonUser.get("id")));
//                temp = CommonUtil.bean2Map(tCompanySponsor);
//                temp.put("userType",2);
//
//            }
//            model.addAttribute("userInfo",temp);
//        }

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
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("state",5);
        //查询前台可展示的族谱
        List<TFamily> list = familyService.getFamilyList2(params);
        List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
        //遍历族谱，查询设置族谱总人数
        for(TFamily tFamily : list){
            int peopleCount = 0;
            Map<String,Object> map = new HashMap<String,Object>();
            Map<String,Object> paramss = new HashMap<>();
            paramss.put("familyId",tFamily.getId());
            paramss.put("peopleType",1);
            paramss.put("isIndex",1);
            List<TPeople> peopleList = familyService.getPeopleList(paramss);
            if(peopleList != null && peopleList.size() > 0)
            {
                peopleCount = peopleList.size();
            }
            map = CommonUtil.bean2Map(tFamily);
            map.put("peopleCount",peopleCount);
            list1.add(map);
        }
        //查询被收录的族谱
//        params.put("state","");
//        List<TFamily> list2 = familyService.getIncludeFamilyList(params);
//        list.addAll(list2);
        model.addAttribute("familyList",list1);

//        //个人积分排名
//        Map<String,Object> params2 = new HashMap<String,Object>();
//        params2.put("type",1);
//        params2.put("userType",1);
//        List<Map<String,Object>> listPersonalPoints = familyService.getPointsRanking(params2);
//        //公司积分排名
//        params2.put("type",2);
//        List<Map<String,Object>> listCompanyPoints = familyService.getPointsRanking(params2);
//        model.addAttribute("listPersonalPoints",listPersonalPoints);
//        model.addAttribute("listCompanyPoints",listCompanyPoints);

        return new ModelAndView("/fronts/index");
    }

    /**
     * 首页查询族谱
     * @param params
     * @return
     */
    @RequestMapping(value = "/queryFamily")
    @ResponseBody
    public Map<String,Object> queryFamily(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        params.put("state",5);
        List<TFamily> list = familyService.getFamilyList2(params);
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
        result.put("familyList",list1);

//        //个人积分排名
//        params.put("type",1);
//        params.put("userType",1);
//        List<Map<String,Object>> listPersonalPoints = familyService.getPointsRanking(params);
//        //公司积分排名
//        params.put("type",2);
//        List<Map<String,Object>> listCompanyPoints = familyService.getPointsRanking(params);
//        result.put("listPersonalPoints",listPersonalPoints);
//        result.put("listCompanyPoints",listCompanyPoints);

        return result;
    }

    /**
     * 何止英才录页面
     * @param model
     * @return
     */
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

    /**
     * 分页查询英才录列表
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/meritocratList")
    @ResponseBody
    public Map<String,Object> meritorcatList(@RequestParam Map<String,Object> params) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();

        //查询总条数
        int total = familyService.getTotalMeritocrat(params);

        //获取/设置每页条数
        int pageSize = CommonUtil.parseInt(params.get("pageSize"));
        pageSize = pageSize == 0 ? PAGE_SIZE : pageSize;
        //计算总页数
        int totalPage = (int)Math.ceil((double)total / (double) pageSize);
        //获取/设置当前页面
        int pageNo = CommonUtil.parseInt(params.get("pageNo"));
        pageNo = pageNo == 0 ? 1 : pageNo;

        params.put("pageSize",pageSize);
        params.put("beginRow",(pageNo - 1)*pageSize);

        //设置翻页栏信息
        String pageChanger = PageUtil.getNumberPageChanger(pageNo,totalPage,PAGE_NUM,pageSize,params.get("tableId")+"");

//        pageChanger = new String(pageChanger.getBytes("GBK"),"UTF-8");

        //查询英才
        List<Map<String,Object>> list = familyService.getMeritocrat(params);
        result.put("meritocratList",list);

        result.put("totalPage",totalPage);
        result.put("pageNo",pageNo);
        result.put("pageChanger", pageChanger);

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
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("type",1);
        params.put("userType",1);
        List<Map<String,Object>> listPersonalPoints = familyService.getPointsRanking(params);
        //公司积分排名
        params.put("type",2);
        List<Map<String,Object>> listCompanyPoints = familyService.getPointsRanking(params);
        model.addAttribute("listPersonalPoints",listPersonalPoints);
        model.addAttribute("listCompanyPoints",listCompanyPoints);
        return new ModelAndView("/fronts/rank");
    }

}
