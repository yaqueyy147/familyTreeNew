package com.witkey.familyTree.controller;

import com.witkey.familyTree.domain.TUserBase;
import com.witkey.familyTree.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/12 0012.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/testA")
    public ModelAndView testA(Model model){
        List<Map<String,Object>> list = testService.testA();

        List<TUserBase> list1 = testService.testAa();

        model.addAttribute("test","这是测试testA的");
        model.addAttribute("testList", list);
        model.addAttribute("testList1", list1);
        return new ModelAndView("/testA");
    }

}
