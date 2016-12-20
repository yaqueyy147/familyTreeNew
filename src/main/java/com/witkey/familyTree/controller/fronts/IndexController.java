package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by suyx on 2016/12/18.
 */
@Controller
@RequestMapping(value = "familyTree")
public class IndexController {

    @RequestMapping(value = {"","/","index"})
    public ModelAndView index(Model model, HttpServletRequest request) throws UnsupportedEncodingException{

        //从cookie获取用户信息
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userFront");
        TUserFront tUserFront1 = (TUserFront)JSONObject.toBean(jsonUser,TUserFront.class);
        model.addAttribute("tUserFront",tUserFront1);
        return new ModelAndView("/fronts/index");
    }

}
