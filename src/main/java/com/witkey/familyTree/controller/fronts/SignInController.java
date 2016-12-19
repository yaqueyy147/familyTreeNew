package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.service.fronts.RegisterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by suyx on 2016/12/18.
 */
@Controller
@RequestMapping(value = "/sign")
public class SignInController {

    private static final Logger LOGGER = Logger.getLogger(SignInController.class);

    @Autowired
    private RegisterService registerService;

    /**
     * 前台登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = {"","/","/login"})
    public ModelAndView frontLogin(Model model){
        return new ModelAndView("/fronts/login");
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
     * @param model
     * @return
     */
    @RequestMapping(value = "regester")
    public RedirectView regester(Model model, TUserFront tUserFront){
        LOGGER.info("tUserFront---->>");
        LOGGER.info(tUserFront);
        registerService.createUserFront(tUserFront);
        model.addAttribute("tUserFront",tUserFront);
        return new RedirectView("/familyTree/index");
    }
}
