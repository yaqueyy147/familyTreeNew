package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TCompanySponsor;
import com.witkey.familyTree.domain.TUser1;
import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.domain.TVolunteer;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.service.fronts.UserFrontService;
import com.witkey.familyTree.service.fronts.UserService;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
@Controller
@RequestMapping(value = "/sign")
public class SignInController {

    private static final Logger LOGGER = Logger.getLogger(SignInController.class);

    @Autowired
    private UserFrontService userFrontService;
    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    /**
     * 前台登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = {"","/","/login"})
    public ModelAndView frontLogin(Model model, @ModelAttribute("loginCode") String loginCode){

        model.addAttribute("loginCode", loginCode);

        return new ModelAndView("/fronts/login");
    }

//    /**
//     * 登录
//     * @param tUserFront
//     * @param ra
//     * @param response
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    @RequestMapping(value = "/signIn")
//    public RedirectView signIn(TUserFront tUserFront, RedirectAttributes ra, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException{
//
//        String contextPath = request.getContextPath();
//        //根据读取的用户名和密码查询用户
////        List<Map<String,Object>> listUser = userFrontService.signIn(tUserFront);
//
//        tUserFront.setPassword(CommonUtil.string2MD5(tUserFront.getPassword()));
//        //个人用户
//        List<TUserFront> listUser = userFrontService.getUserInfo(tUserFront);
//        //公司用户
//        List<Map<String,Object>> listCompanyUser = companyService.getCompanyInfo(CommonUtil.bean2Map(tUserFront));
//        Map<String,Object> mapUserInfo = new HashMap<String,Object>();
//        //如果用户存在则为个人用户，则登录，跳转首页
//        if(listUser != null && listUser.size() > 0){
//            mapUserInfo = CommonUtil.bean2Map(listUser.get(0));
//            mapUserInfo.put("userType",1);//设置用户类型为个人用户
//            //将用户信息添加到cookie
//            CookieUtil.addCookie("userInfo", JSONObject.fromObject(mapUserInfo).toString(),response);
//            return new RedirectView(contextPath + "/familyTree/index");
//        }else if(listCompanyUser != null && listCompanyUser.size() > 0){//否则检查是否公司用户
//            mapUserInfo = listCompanyUser.get(0);
//            mapUserInfo.put("userType",2);//设置用户类型为企业用户
//            //将用户信息添加到cookie
//            CookieUtil.addCookie("userInfo", JSONObject.fromObject(mapUserInfo).toString(),response);
//            return new RedirectView(contextPath + "/familyTree/index");
//        }
//        //否则跳回登录页面
//        ra.addFlashAttribute("loginCode",-1);
//        return new RedirectView(contextPath + "/sign/login");
//    }

    /**
     * 登录
     * @param tUser1
     * @param ra
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/signIn")
    public RedirectView signIn(TUser1 tUser1, RedirectAttributes ra, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException{

        String contextPath = request.getContextPath();
        //根据读取的用户名和密码查询用户
//        List<Map<String,Object>> listUser = userFrontService.signIn(tUserFront);

        tUser1.setPassword(CommonUtil.string2MD5(tUser1.getPassword()));
        //个人用户
        tUser1.setIsFront(1);
        List<TUser1> listUser = userService.getUserInfo1(tUser1);
        //公司用户
        List<Map<String,Object>> listCompanyUser = companyService.getCompanyInfo(CommonUtil.bean2Map(tUser1));
        Map<String,Object> mapUserInfo = new HashMap<String,Object>();
        //如果用户存在则为个人用户，则登录，跳转首页
        if(listUser != null && listUser.size() > 0){
            mapUserInfo = CommonUtil.bean2Map(listUser.get(0));
            mapUserInfo.put("userType",1);//设置用户类型为个人用户
            //将用户信息添加到cookie
            CookieUtil.addCookie("userInfo", JSONObject.fromObject(mapUserInfo).toString(),response);
            return new RedirectView(contextPath + "/familyTree/index");
        }else if(listCompanyUser != null && listCompanyUser.size() > 0){//否则检查是否公司用户
            mapUserInfo = listCompanyUser.get(0);
            mapUserInfo.put("userType",2);//设置用户类型为企业用户
            //将用户信息添加到cookie
            CookieUtil.addCookie("userInfo", JSONObject.fromObject(mapUserInfo).toString(),response);
            return new RedirectView(contextPath + "/familyTree/index");
        }
        //否则跳回登录页面
        ra.addFlashAttribute("loginCode",-1);
        return new RedirectView(contextPath + "/sign/login");
    }

    /**
     * 前台注册页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/regedit")
    public ModelAndView regedit(Model model, String regCode){
        model.addAttribute("regCode",regCode);
        return new ModelAndView("/fronts/regedit");
    }

    /**
     * 前台注册页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/regeditPersonal")
    public ModelAndView regeditPersonal(Model model, String regCode){
        model.addAttribute("regCode",regCode);
        return new ModelAndView("/fronts/regedit_personal");
    }

    /**
     * 前台注册页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/regeditCompany")
    public ModelAndView regeditCompany(Model model, String regCode){
        model.addAttribute("regCode",regCode);
        return new ModelAndView("/fronts/regedit_company");
    }

    /**
     * 注册
     * @param tUserFront
     * @return
     */
    @RequestMapping(value = "/regester")
    public RedirectView regester(TUserFront tUserFront, RedirectAttributes ra, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException{
        String contextPath = request.getContextPath();
        //注册，创建用户
        int id= userFrontService.createUserFront(tUserFront);
        //设置用户ID为返回的id
        tUserFront.setId(id);
        //注册成功，自动登录，添加cookie
        CookieUtil.addCookie("userInfo", JSONObject.fromObject(tUserFront).toString(),response);
        return new RedirectView(contextPath + "/familyTree/index");
    }

    /**
     * 个人用户注册
     * @param tUser1
     * @return
     */
    @RequestMapping(value = "/regesterPersonal")
    public RedirectView regesterPersonal(TUser1 tUser1, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException{
        String contextPath = request.getContextPath();
        int id = 0;
        JSONObject jsonObject = new JSONObject();
        //个人用户
        //检查用户名是否已经存在了
        List<TUser1> list = userService.getUserInfo1(new TUser1(tUser1.getLoginName()));

        if(list != null && list.size() > 0){
            return new RedirectView(contextPath + "/sign/regeditPersonal?regCode=-2");
        }

        tUser1.setIsFront(1);
        tUser1.setState(1);
        tUser1.setIsVolunteer(3);
        tUser1.setUserFrom(1);
        tUser1.setCreateMan(tUser1.getLoginName());
        tUser1.setCreateTime(CommonUtil.getDateLong());
        id= userService.createUser1(tUser1);
        tUser1.setId(id);

        jsonObject = JSONObject.fromObject(tUser1);
        jsonObject.put("userType",1);
        //注册成功，自动登录，添加cookie
        CookieUtil.addCookie("userInfo", jsonObject.toString(),response);

        return new RedirectView(contextPath + "/familyTree/index");
    }

    /**
     * 注册
     * @param params
     * @return
     */
    @RequestMapping(value = "/regesterNew")
    public RedirectView regesterNew(@RequestParam Map<String,Object> params, RedirectAttributes ra, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException{
        String contextPath = request.getContextPath();
        int id = 0;
        JSONObject jsonObject = new JSONObject();
        //个人用户
        if("1".equals(params.get("userType"))){

            //检查用户名是否已经存在了
            List<TUserFront> list = userFrontService.getUserInfo(new TUserFront(params.get("userName")+""));

            if(list != null && list.size() > 0){
                return new RedirectView(contextPath + "/sign/regedit?regCode=-2");
            }

            TUserFront tUserFront = new TUserFront(params.get("userName")+"",params.get("password")+"");
            id= userFrontService.createUserFront(tUserFront);
            tUserFront.setId(id);

            jsonObject = JSONObject.fromObject(tUserFront);
            jsonObject.put("userType",1);

        }else {
            //检查用户名是否已经存在了
            Map<String,Object> params2 = new HashMap<String,Object>();
            params2.put("userName",params.get("userName"));
            List<Map<String,Object>> list = companyService.getCompanyInfo(params2);

            if(list != null && list.size() > 0){
                return new RedirectView(contextPath + "/sign/regedit?regCode=-2");
            }

            TCompanySponsor tCompanySponsor = new TCompanySponsor(params.get("userName")+"",CommonUtil.string2MD5(params.get("password")+""),params.get("companyName")+"");
            id= companyService.createCompanyUser(tCompanySponsor);
            tCompanySponsor.setId(id);
            jsonObject = JSONObject.fromObject(tCompanySponsor);
            jsonObject.put("userType",2);
            jsonObject.put("company_name",tCompanySponsor.getCompanyName());
            jsonObject.put("company_login_name",tCompanySponsor.getCompanyLoginName());

        }
        //注册成功，自动登录，添加cookie
        CookieUtil.addCookie("userInfo", jsonObject.toString(),response);

        return new RedirectView(contextPath + "/familyTree/index");
    }

    /**
     * 注册
     * @param tCompanySponsor
     * @return
     */
    @RequestMapping(value = "/companyRegester")
    public RedirectView companyRegester(TCompanySponsor tCompanySponsor, RedirectAttributes ra, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException{
        String contextPath = request.getContextPath();
        //注册，创建用户
        //加密密码
        tCompanySponsor.setCompanyLoginPassword(CommonUtil.string2MD5(tCompanySponsor.getCompanyLoginPassword()));
        int id= companyService.createCompanyUser(tCompanySponsor);
        //设置用户ID为返回的id
        tCompanySponsor.setId(id);
        //注册成功，自动登录，添加cookie
        CookieUtil.addCookie("userInfo", JSONObject.fromObject(tCompanySponsor).toString(),response);
        return new RedirectView(contextPath + "/familyTree/index");
    }

    /**
     * 退出登录
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public RedirectView logout(Model model, HttpServletResponse response, HttpServletRequest request){
        String contextPath = request.getContextPath();
        //销毁登录用户信息cookie
        CookieUtil.destroyCookies(response,request);
        model.addAttribute("userInfo",null);
        //返回登录页面
        return new RedirectView(contextPath + "/familyTree/index");
    }

//    /**
//     * 修改用户信息
//     * @param tUserFront
//     * @return
//     */
//    @RequestMapping(value = "/modifyPersonalInfo")
//    @ResponseBody
//    public Map<String,Object> modifyPersonalInfo(TUserFront tUserFront){
//        Map<String,Object> map = new HashMap<String,Object>();
//
//        TUserFront tUserFront1 = userFrontService.getUserInfoFromId(tUserFront.getId());
//
//        tUserFront.setIsVolunteer(tUserFront1.getIsVolunteer());
//        tUserFront.setCreateTime(tUserFront1.getCreateTime());
//
//        int i = userFrontService.saveUserFront(tUserFront);
//
//
//        map.put("code",i);
//        map.put("msg","修改成功!");
//        return map;
//    }

    /**
     * 修改用户信息
     * @param tUser1
     * @return
     */
    @RequestMapping(value = "/modifyPersonalInfo")
    @ResponseBody
    public Map<String,Object> modifyPersonalInfo(TUser1 tUser1){
        Map<String,Object> map = new HashMap<String,Object>();

        TUser1 tUser11 = userService.getUserInfoFromId(tUser1.getId());
        tUser1.setIsFront(tUser11.getIsFront());
        tUser1.setIsConsole(tUser11.getIsConsole());
        tUser1.setUserFrom(tUser11.getUserFrom());
        tUser1.setState(tUser11.getState());
        tUser1.setIsVolunteer(tUser11.getIsVolunteer());
        tUser1.setCreateTime(tUser11.getCreateTime());

        int i = userService.saveUser1(tUser1);


        map.put("code",i);
        map.put("msg","修改成功!");
        return map;
    }


    /**
     * 修改密码
     * @param request
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/modifyPassword")
    @ResponseBody
    public Map<String,Object> modifyPassword(HttpServletRequest request,@RequestParam Map<String,Object> params) throws UnsupportedEncodingException{
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

        if(!CommonUtil.string2MD5(params.get("oldPassword") + "").equals(jsonUser.get("password"))){
            map.put("code",2);
            map.put("msg","原密码输入有误!");
            return map;
        }
        params.put("userType",jsonUser.get("userType"));
        int i = userService.modifyPassword(params);

        map.put("code",i);
        map.put("msg","修改成功!");
        return map;
    }

    /**
     * 申请成为志愿者
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/applyVolunteer")
    @ResponseBody
    public Map<String,Object> applyVolunteer(HttpServletRequest request) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

//        TVolunteer tVolunteer = new TVolunteer();
//        tVolunteer.setUserId(CommonUtil.parseInt(jsonUser.get("id")));
//        tVolunteer.setCreateMan(jsonUser.get("userName") + "");
//        tVolunteer.setCreateTime(CommonUtil.ObjToDate(CommonUtil.getDateLong()));
//        int i = userFrontService.applyVolunteer(CommonUtil.parseInt(jsonUser.get("id")));
        int i = userService.applyVolunteer(CommonUtil.parseInt(jsonUser.get("id")));
        map.put("code",i);
        map.put("msg","申请成功!");
        return map;
    }

    @RequestMapping(value = "/modifyPhoto")
    @ResponseBody
    public Map<String,Object> modifyPhoto(HttpServletRequest request,String photoPath) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
//        int i = userFrontService.modifyPhoto(jsonUser.get("id") + "", photoPath, jsonUser.get("userType")+"");
        int i = userService.modifyPhoto(jsonUser.get("id") + "", photoPath, jsonUser.get("userType")+"");
        map.put("code",i);
        map.put("msg","修改成功!");
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

        tCompanySponsor.setCreateMan(tCompanySponsor1.getCreateMan());
        tCompanySponsor.setCreateTime(tCompanySponsor1.getCreateTime());

        int i = companyService.saveCompanyInfo(tCompanySponsor);


        map.put("code",i);
        map.put("msg","修改成功!");
        return map;
    }

}
