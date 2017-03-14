package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
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

    @Autowired
    private FamilyService familyService;

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

    @RequestMapping(value = "/meritorcat")
    public ModelAndView meritorcat(Model model){

        //获取何氏英才录
        List<Map<String,Object>> list = familyService.getMeritocrat(null);

        return new ModelAndView("/fronts/meritorcat");
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
