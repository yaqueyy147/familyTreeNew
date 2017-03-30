package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.domain.TLog;
import com.witkey.familyTree.service.consoles.LogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/3/30 0030.
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {

    private static final Logger LOGGER = Logger.getLogger(LogController.class);

    @Autowired
    private LogService logService;

    @RequestMapping(value = {"/","","/index"})
    public ModelAndView logIndex(){
        return new ModelAndView("/consoles/log");
    }

    @RequestMapping(value = "/logList")
    @ResponseBody
    public Map<String,Object> logList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        List<TLog> list = logService.logList(params);
        result.put("logList",list);
        return result;
    }

}
