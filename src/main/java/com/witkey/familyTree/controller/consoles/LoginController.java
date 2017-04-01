package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.domain.TUserBase;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/14 0014.
 */

@Controller
@RequestMapping(value = "/consoles")
public class LoginController {

    @Autowired
    private ConsoleService consoleService;

    @RequestMapping(value = {"/","/login"})
    public ModelAndView login(){
        return new ModelAndView("/consoles/login");
    }

    @RequestMapping(value = "/main")
    public ModelAndView mainPage(Model model, HttpServletRequest request) throws UnsupportedEncodingException{

        JSONObject jsonObject = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        if(CommonUtil.isBlank(jsonObject)){
            return new ModelAndView("/consoles/login");
        }
        model.addAttribute("consoleUserInfo",jsonObject);
        return new ModelAndView("/consoles/main");
    }

    @RequestMapping(value = "/loginIn")
    public ModelAndView loginIn(Model model, @RequestParam Map<String,Object> params, HttpServletResponse response) throws UnsupportedEncodingException{

        //md5加密密码
        params.put("userPassword", CommonUtil.string2MD5(params.get("userPassword")+""));

        List<TUserBase> list = consoleService.getUserBase(params);

        if(list != null && list.size() > 0){

            JSONObject jsonObject = JSONObject.fromObject(list.get(0));
            CookieUtil.addCookie("consoleUserInfo",jsonObject.toString(),response);
            model.addAttribute("consoleUserInfo",jsonObject);
            return new ModelAndView("/consoles/main");
        }

        model.addAttribute("loginCode",-1);
        return new ModelAndView("/consoles/login");
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){

        CookieUtil.destroyCookies(response,request);

        return new ModelAndView("/consoles/login");
    }

}
