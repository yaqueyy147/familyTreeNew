package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.BaseUtil;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by suyx on 2017/1/7.
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private FamilyService familyService;

    @RequestMapping(value = "/index")
    public ModelAndView companyIndex(Model model){
        List<TCompanySponsor> list = companyService.getCompanyList();

        List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        for (TCompanySponsor tCompanySponsor : list) {
            map = CommonUtil.bean2Map(tCompanySponsor);

            int companyId = tCompanySponsor.getId();
            double total = companyService.getTotalCompanyMoney(companyId);

            map.put("totalMoney",total);
            list1.add(map);
        }

        model.addAttribute("listCompany",list1);
        return new ModelAndView("/fronts/companyIndex");
    }

    @RequestMapping(value = "/info")
    public ModelAndView companyInfo(Model model, HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        TCompanySponsor tCompanySponsor = companyService.getCompanyFromId(CommonUtil.parseInt(jsonUser.get("id")));

        double totalMoney = companyService.getTotalCompanyMoney(CommonUtil.parseInt(jsonUser.get("id")));

        double totalPoints = 0.0;
        List<TPointsDic> listP = familyService.getPointsRelation(2,1);
        if(listP != null && listP.size() > 0){
            totalPoints = totalMoney * (Math.ceil(listP.get(0).getPointsValue()/listP.get(0).getPointsNum()));
        }

        model.addAttribute("totalMoney",totalMoney);
        model.addAttribute("totalPoints",totalPoints);
        model.addAttribute("userInfo",jsonUser);
        model.addAttribute("companyInfo",tCompanySponsor);
        return new ModelAndView("/fronts/companyInfo");
    }

    @RequestMapping(value = "/detail")
    public ModelAndView companyDetail(Model model, int companyId,int xxx){
        TCompanySponsor tCompanySponsor = companyService.getCompanyFromId(companyId);
        model.addAttribute("tCompanySponsor",tCompanySponsor);
        List<TCompanyPhoto> list = companyService.getCompanyPhoto(companyId);
        model.addAttribute("companyDetailList",list);
        double totalMoney = companyService.getTotalCompanyMoney(companyId);
        model.addAttribute("totalMoney",totalMoney);

        double totalPoints = 0.0;
        List<TPointsDic> listP = familyService.getPointsRelation(2,1);
        if(listP != null && listP.size() > 0){
            totalPoints = totalMoney * (Math.ceil(listP.get(0).getPointsValue()/listP.get(0).getPointsNum()));
        }
        model.addAttribute("totalPoints",totalPoints);

        List<TCompanyIntroduce> list1 = companyService.getIntro(companyId);
        if(list1 != null && list1.size() > 0){
            model.addAttribute("introduce",list1.get(0));
        }

        model.addAttribute("xxx",xxx);
        if(CommonUtil.isBlank(xxx) || xxx != 2){
            return new ModelAndView("/fronts/companyDetail_visitor");
        }
        return new ModelAndView("/fronts/companyDetail");
    }

    @RequestMapping(value = "/savePublicity")
    @ResponseBody
    public Map<String,Object> savePublicity(TCompanyPhoto tCompanyPhoto){
        Map<String,Object> result = new HashMap<String,Object>();

        int photoId = companyService.saveCompanyPhoto(tCompanyPhoto);
        tCompanyPhoto.setId(photoId);

        result.put("tCompanyPhoto",tCompanyPhoto);
        result.put("code",1);
        result.put("msg","添加成功!");
        return result;
    }

    @RequestMapping(value = "/saveIntro")
    @ResponseBody
    public Map<String,Object> saveIntro(HttpServletRequest request,HttpServletResponse response,TCompanyIntroduce tCompanyIntroduce) throws Exception{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        Map<String,Object> result = new HashMap<String,Object>();

        if(tCompanyIntroduce.getId() > 0){
            tCompanyIntroduce.setUpdateMan(jsonUser.get("companyLoginName") + "");
            tCompanyIntroduce.setUpdateTime(CommonUtil.getDateLong());
        }else{
            tCompanyIntroduce.setCreateMan(jsonUser.get("companyLoginName") + "");
            tCompanyIntroduce.setCreateTime(CommonUtil.getDateLong());
        }
        int i = companyService.saveIntro(tCompanyIntroduce);

        result.put("code",i);
        result.put("tCompanyIntroduce",tCompanyIntroduce);
        result.put("msg","添加成功!");
        return result;
    }

    @RequestMapping(value = "/getIntro")
    @ResponseBody
    public Map<String,Object> getIntro(HttpServletRequest request,int companyId){
        Map<String,Object> result = new HashMap<String,Object>();
        List<TCompanyIntroduce> list1 = companyService.getIntro(companyId);
        if(list1 != null && list1.size() > 0){
            result.put("introduceA",list1.get(0));
        }
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

    /**
     * 申请成为志愿者
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/applySponsor")
    @ResponseBody
    public Map<String,Object> applyVolunteer(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        int i = companyService.applySponsor(CommonUtil.parseInt(jsonUser.get("id")));
        map.put("code",i);
        map.put("msg","申请成功!");
        return map;
    }

    /**
     * 修改公司信息
     * @param tCompanySponsor
     * @return
     */
    @RequestMapping(value = "/modifyCompanyInfo")
    @ResponseBody
    public Map<String,Object> modifyCompanyInfo(TCompanySponsor tCompanySponsor) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();

        TCompanySponsor tCompanySponsor1 = companyService.getCompanyFromId(tCompanySponsor.getId());
        tCompanySponsor.setState(tCompanySponsor1.getState());
        tCompanySponsor.setCreateMan(tCompanySponsor1.getCreateMan());
        tCompanySponsor.setCreateTime(tCompanySponsor1.getCreateTime());

        int i = companyService.saveCompanyInfo(tCompanySponsor);


        map.put("code",i);
        map.put("msg","修改成功!");
        return map;
    }

}
