package com.witkey.familyTree.controller.consoles;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by suyx on 2016/12/14 0014.
 */

@Controller
@RequestMapping(value = "/consoles")
public class LoginController {

    @RequestMapping(value = "/main")
    public ModelAndView mainPage(Model model){
        return new ModelAndView("/consoles/main");
    }

}
