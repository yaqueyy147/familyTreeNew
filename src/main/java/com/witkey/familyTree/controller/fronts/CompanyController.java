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
 * 企业相关页面controller
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private FamilyService familyService;

    /**
     * 企业用户信息页--未用
     * @param model
     * @return
     */
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

    /**
     * 企业用户信息页--在用
     * @param model
     * @return
     */
    @RequestMapping(value = "/info")
    public ModelAndView companyInfo(Model model, HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取cookie中用户信息
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        //根据cookie信息获取企业相关信息
        TCompanySponsor tCompanySponsor = companyService.getCompanyFromId(CommonUtil.parseInt(jsonUser.get("id")));

        //通过企业id获取企业总充值金额
        double totalMoney = companyService.getTotalCompanyMoney(CommonUtil.parseInt(jsonUser.get("id")));

        double totalPoints = 0.0;
        //获取积分规则，并计算企业总积分
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

    /**
     * 企业介绍详情页面
     * @param model
     * @param companyId
     * @param xxx
     * @return
     */
    @RequestMapping(value = "/detail")
    public ModelAndView companyDetail(Model model, int companyId,int xxx){
        //根据传入的企业id获取企业信息
        TCompanySponsor tCompanySponsor = companyService.getCompanyFromId(companyId);
        model.addAttribute("tCompanySponsor",tCompanySponsor);
        //根据企业id获取企业上传的图片--没用了
        List<TCompanyPhoto> list = companyService.getCompanyPhoto(companyId);
        model.addAttribute("companyDetailList",list);
        //根据企业id获取企业充值总金额
        double totalMoney = companyService.getTotalCompanyMoney(companyId);
        model.addAttribute("totalMoney",totalMoney);

        //获取积分规则并计算企业积分
        double totalPoints = 0.0;
        List<TPointsDic> listP = familyService.getPointsRelation(2,1);
        if(listP != null && listP.size() > 0){
            totalPoints = totalMoney * (Math.ceil(listP.get(0).getPointsValue()/listP.get(0).getPointsNum()));
        }
        model.addAttribute("totalPoints",totalPoints);

        //根据企业id获取企业简介
        List<TCompanyIntroduce> list1 = companyService.getIntro(companyId);
        if(list1 != null && list1.size() > 0){
            model.addAttribute("introduce",list1.get(0));
        }

        model.addAttribute("xxx",xxx);
        //如果标识符参数xxx为空或者不为2时返回游客页面
        if(CommonUtil.isBlank(xxx) || xxx != 2){
            return new ModelAndView("/fronts/companyDetail_visitor");
        }
        return new ModelAndView("/fronts/companyDetail");
    }

    /**
     * 保存公司简介图片--没用了
     * 原公司介绍页面只用于展示图片和维护图片
     * @param tCompanyPhoto
     * @return
     */
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

    /**
     * 保存公司简介，图文说明
     * 新的公司介绍为富文本编辑器输入，保存图文简介
     * @param request
     * @param response
     * @param tCompanyIntroduce
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveIntro")
    @ResponseBody
    public Map<String,Object> saveIntro(HttpServletRequest request,HttpServletResponse response,TCompanyIntroduce tCompanyIntroduce) throws Exception{
        //从cookie获取登录企业用户信息
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        Map<String,Object> result = new HashMap<String,Object>();

        //传入的tCompanyIntroduce中企业介绍id大于0，标识为修改简介，设置修改人和修改时间
        if(tCompanyIntroduce.getId() > 0){
            tCompanyIntroduce.setUpdateMan(jsonUser.get("companyLoginName") + "");
            tCompanyIntroduce.setUpdateTime(CommonUtil.getDateLong());
        }else{//否则为新增简介，设置创建人和创建时间
            tCompanyIntroduce.setCreateMan(jsonUser.get("companyLoginName") + "");
            tCompanyIntroduce.setCreateTime(CommonUtil.getDateLong());
        }
        //保存企业介绍
        int i = companyService.saveIntro(tCompanyIntroduce);

        result.put("code",i);
        result.put("tCompanyIntroduce",tCompanyIntroduce);
        result.put("msg","添加成功!");
        return result;
    }

    /**
     * 获取企业介绍，根据传入的企业id获取企业介绍
     * @param request
     * @param companyId
     * @return
     */
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

    /**
     * 充值记录
     * @param params
     * @return
     */
    @RequestMapping(value = "/moneyList")
    @ResponseBody
    public Map<String,Object> moneyList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        //标识符type为2，即为企业用户，查询企业充值记录
        if(CommonUtil.parseInt(params.get("type")) == 2){
        	List<TCompanyMoney> list = companyService.getCompanyMoney(params);
            result.put("dataList",list);
        } else if(CommonUtil.parseInt(params.get("type")) == 1){//标识符type为1，即为个人用户，查询个人充值记录
        	List<TUserMoney> list = familyService.getUserMoney(params);
            result.put("dataList",list);
            
        }
        return result;
    }

    /**
     * 添加充值,同时修改修改积分--此积分暂时只做记录，没用
     * @param params
     * @return
     */
    @RequestMapping(value = "/addMoney")
    @ResponseBody
    public Map<String,Object> addMoney(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        //获取积分规则
        List<TPointsDic> listP = familyService.getPointsRelation(2,1);
        double points = 0.0;
        if(listP != null && listP.size() > 0){
            points = Math.ceil(listP.get(0).getPointsValue()/listP.get(0).getPointsNum());
        }

        int i = 0;
        //如果标识符type为1则为添加个人用户充值
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
        }else if(CommonUtil.parseInt(params.get("type")) == 2){//如果标识符type为2则为添加企业用户充值
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
