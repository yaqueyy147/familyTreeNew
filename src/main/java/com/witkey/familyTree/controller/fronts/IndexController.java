package com.witkey.familyTree.controller.fronts;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by suyx on 2016/12/18.
 */
@Controller
@RequestMapping(value = "familyTree")
public class IndexController {

    @RequestMapping(value = {"","/","index"})
    public ModelAndView index(Model model){
        return new ModelAndView("/fronts/index");
    }

}
