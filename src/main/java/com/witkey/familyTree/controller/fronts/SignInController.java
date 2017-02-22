package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TCompanySponsor;
import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.service.fronts.UserFrontService;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 登录
     * @param tUserFront
     * @param ra
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/signIn")
    public RedirectView signIn(TUserFront tUserFront, RedirectAttributes ra, HttpServletResponse response) throws UnsupportedEncodingException{

        //根据读取的用户名和密码查询用户
//        List<Map<String,Object>> listUser = userFrontService.signIn(tUserFront);

        //个人用户
        List<TUserFront> listUser = userFrontService.getUserInfo(tUserFront);
        //公司用户
        List<Map<String,Object>> listCompanyUser = companyService.getCompanyInfo(CommonUtil.bean2Map(tUserFront));
        Map<String,Object> mapUserInfo = new HashMap<String,Object>();
        //如果用户存在则为个人用户，则登录，跳转首页
        if(listUser != null && listUser.size() > 0){
            mapUserInfo = CommonUtil.bean2Map(listUser.get(0));
            mapUserInfo.put("userType",1);//设置用户类型为个人用户
            //将用户信息添加到cookie
            CookieUtil.addCookie("userInfo", JSONObject.fromObject(mapUserInfo).toString(),response);
            return new RedirectView("/familyTree/index");
        }else if(listCompanyUser != null && listCompanyUser.size() > 0){//否则检查是否公司用户
            mapUserInfo = listCompanyUser.get(0);
            mapUserInfo.put("userType",2);//设置用户类型为企业用户
            //将用户信息添加到cookie
            CookieUtil.addCookie("userInfo", JSONObject.fromObject(mapUserInfo).toString(),response);
            return new RedirectView("/familyTree/index");
        }
        //否则跳回登录页面
        ra.addFlashAttribute("loginCode",-1);
        return new RedirectView("/sign/");
    }

    /**
     * 前台注册页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/regedit")
    public ModelAndView regedit(Model model){
        return new ModelAndView("/fronts/regedit");
    }

    /**
     * 注册
     * @param tUserFront
     * @return
     */
    @RequestMapping(value = "/regester")
    public RedirectView regester(TUserFront tUserFront, RedirectAttributes ra, HttpServletResponse response) throws UnsupportedEncodingException{
        //注册，创建用户
        int id= userFrontService.createUserFront(tUserFront);
        //设置用户ID为返回的id
        tUserFront.setId(id);
        //注册成功，自动登录，添加cookie
        CookieUtil.addCookie("userFront", JSONObject.fromObject(tUserFront).toString(),response);
        return new RedirectView("/familyTree/index");
    }

    /**
     * 注册
     * @param tCompanySponsor
     * @return
     */
    @RequestMapping(value = "/companyRegester")
    public RedirectView companyRegester(TCompanySponsor tCompanySponsor, RedirectAttributes ra, HttpServletResponse response) throws UnsupportedEncodingException{
        //注册，创建用户
        //加密密码
        tCompanySponsor.setCompanyLoginPassword(CommonUtil.string2MD5(tCompanySponsor.getCompanyLoginPassword()));
        int id= companyService.createCompanyUser(tCompanySponsor);
        //设置用户ID为返回的id
        tCompanySponsor.setId(id);
        //注册成功，自动登录，添加cookie
        CookieUtil.addCookie("userFront", JSONObject.fromObject(tCompanySponsor).toString(),response);
        return new RedirectView("/familyTree/index");
    }

    /**
     * 退出登录
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout(Model model, HttpServletResponse response, HttpServletRequest request){
        //销毁登录用户信息cookie
        CookieUtil.destroyCookies(response,request);
        //返回登录页面
        return new ModelAndView("/fronts/index");
    }

    /**
     * 修改用户信息
     * @param tUserFront
     * @return
     */
    @RequestMapping(value = "modifyPersonalInfo")
    @ResponseBody
    public Map<String,Object> modifyPersonalInfo(TUserFront tUserFront){
        Map<String,Object> map = new HashMap<String,Object>();
        int i = userFrontService.saveUserFront(tUserFront);
        map.put("code",i);
        map.put("msg","修改成功!");
        return map;
    }

}
