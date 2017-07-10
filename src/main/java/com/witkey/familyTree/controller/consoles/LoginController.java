package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.domain.TUser1;
import com.witkey.familyTree.domain.TUserBase;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.fronts.UserService;
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
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/14 0014.
 * 后台登录和主页相关
 */

@Controller
@RequestMapping(value = "/consoles")
public class LoginController {

    @Autowired
    private ConsoleService consoleService;
    @Autowired
    private UserService userService;

    /**
     * 后台登录页面
     * @return
     */
    @RequestMapping(value = {"/","/login",""})
    public ModelAndView login(){
        return new ModelAndView("/consoles/login");
    }

    /**
     * 后台主页面
     * @param model
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/main")
    public ModelAndView mainPage(Model model, HttpServletRequest request) throws UnsupportedEncodingException{

        JSONObject jsonObject = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        if(CommonUtil.isBlank(jsonObject)){
            return new ModelAndView("/consoles/login");
        }
        model.addAttribute("consoleUserInfo",jsonObject);
        return new ModelAndView("/consoles/main");
    }

//    @RequestMapping(value = "/loginIn")
//    public ModelAndView loginIn(Model model, @RequestParam Map<String,Object> params, HttpServletResponse response) throws UnsupportedEncodingException{
//
//        //md5加密密码
//        params.put("userPassword", CommonUtil.string2MD5(params.get("userPassword")+""));
//
//        TUser1 tUser1 = new TUser1(params.get("")+"",CommonUtil.string2MD5(params.get("userPassword")+""));
//        tUser1.setIsConsole(1);
//
//        List<TUserBase> list = consoleService.getUserBase(params);
//
//        if(list != null && list.size() > 0){
//
//            JSONObject jsonObject = JSONObject.fromObject(list.get(0));
//            CookieUtil.addCookie("consoleUserInfo",jsonObject.toString(),response);
//            model.addAttribute("consoleUserInfo",jsonObject);
//            return new ModelAndView("/consoles/main");
//        }
//
//        model.addAttribute("loginCode",-1);
//        return new ModelAndView("/consoles/login");
//    }

    /**
     * 后台登录认证
     * @param model
     * @param params
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/loginIn")
    public ModelAndView loginIn(Model model, @RequestParam Map<String,Object> params, HttpServletResponse response) throws UnsupportedEncodingException{

        //md5加密密码
        String userPassword = CommonUtil.string2MD5(params.get("userPassword")+"");

        TUser1 tUser1 = new TUser1(params.get("loginName")+"",userPassword);
        tUser1.setIsConsole(1);

        //登录成功，跳转主页面
        List<TUser1> list = userService.getUserInfo1(tUser1);

        if(list != null && list.size() > 0){

            JSONObject jsonObject = JSONObject.fromObject(list.get(0));
            //登录成功，将用户信息记录到cookie
            CookieUtil.addCookie("consoleUserInfo",jsonObject.toString(),response);
            model.addAttribute("consoleUserInfo",jsonObject);
            return new ModelAndView("/consoles/main");
        }

        //登录失败，跳转登录页面
        model.addAttribute("loginCode",-1);
        return new ModelAndView("/consoles/login");
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){

        CookieUtil.destroyCookies(response,request);

        return new ModelAndView("/consoles/login");
    }

    /**
     * 用户菜单
     * @param params
     * @return
     */
    @RequestMapping(value = "menuTree")
    @ResponseBody
    public Map<String,Object> menuTree(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();

        List<Map<String,Object>> list = consoleService.getUserMenu(params);

        for(Map<String,Object> map : list){
            map.put("pId",map.get("parent_source_id"));
            map.put("name",map.get("source_name"));
            map.put("open",true);
        }

        result.put("menuList",list);
        return result;
    }

//    @RequestMapping(value = "outTrainsfer")
//    public ModelAndView

}
