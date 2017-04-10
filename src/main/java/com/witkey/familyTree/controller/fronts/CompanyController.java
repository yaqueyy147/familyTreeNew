package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TCompanyMoney;
import com.witkey.familyTree.domain.TCompanyPhoto;
import com.witkey.familyTree.domain.TCompanySponsor;
import com.witkey.familyTree.service.fronts.CompanyService;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/1/7.
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

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
    public ModelAndView companyInfo(Model model, HttpServletRequest request) throws UnsupportedEncodingException{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

        TCompanySponsor tCompanySponsor = companyService.getCompanyFromId(CommonUtil.parseInt(jsonUser.get("id")));

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

    @RequestMapping(value = "/moneyList")
    @ResponseBody
    public Map<String,Object> moneyList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<TCompanyMoney> list = companyService.getCompanyMoney(params);

        result.put("dataList",list);
        return result;
    }
}
