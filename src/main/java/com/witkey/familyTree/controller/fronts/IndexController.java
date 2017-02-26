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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
        List<List<TFamily>> list = new ArrayList<List<TFamily>>();
        List<TFamily> listMainland = familyService.getFamilyList("",1);

        list.add(listMainland);
        List<TFamily> listHongKong = familyService.getFamilyList("",2);
        list.add(listHongKong);
        List<TFamily> listTaiwan = familyService.getFamilyList("",3);
        list.add(listTaiwan);
        List<TFamily> listAoMen = familyService.getFamilyList("",4);
        list.add(listAoMen);
        model.addAttribute("familyList",list);
        return new ModelAndView("/fronts/index");
    }

}
