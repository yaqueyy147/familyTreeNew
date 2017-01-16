package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.domain.TVolunteer;
import com.witkey.familyTree.service.consoles.ConsoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/1/11.
 */
@Controller
@RequestMapping(value = "/consoles")
public class ConsoleController {

    private static final Logger LOGGER = Logger.getLogger(ConsoleController.class);

    @Autowired
    private ConsoleService consoleService;

    @RequestMapping(value = "/volunteerList")
    public ModelAndView auditVolunteer(Model model){
        Map<String,Object> params = new HashMap<String,Object>();
        List<Map<String,Object>> list = consoleService.getVolunteerApplyList(params);
        model.addAttribute("volunteerApplyList",list);
        return new ModelAndView("/consoles/volunteerList");
    }

    @RequestMapping(value = "/auditVolunteer")
    @ResponseBody
    public Map<String,Object> auditVolunteer(Map<String,Object> params){
        params.put("auditMan","ceshi123");
        int i = 0;
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            i = consoleService.auditVolunteer(params);
            map.put("msg","审核完成");
            map.put("code",1);
        }catch (Exception e){
            LOGGER.error("操作出错了-->",e);
            map.put("msg","系统错误");
            map.put("code",-1);
        }

        return map;
    }
}
