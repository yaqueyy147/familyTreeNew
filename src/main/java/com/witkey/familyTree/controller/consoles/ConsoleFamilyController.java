package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.service.consoles.ConsoleFamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suyx on 2018/3/2 0002.
 */
@Controller
@RequestMapping(value = "/consoles")
public class ConsoleFamilyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleFamilyController.class);

    @Autowired
    private ConsoleFamilyService consoleFamilyService;

    /**
     * 设置选择的族人屏蔽或者解除屏蔽
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "setpeoplehideornot01")
    @ResponseBody
    public Map<String,Object> setpeoplehideornot01(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,Object> params) throws Exception{

        Map<String,Object> map = new HashMap<String,Object>();
        try {
            consoleFamilyService.setHideOrNotFromId(params);
            map.put("msg","屏蔽完成");
            map.put("code",1);
        }catch (Exception e){
            LOGGER.error("操作出错了-->",e);
            map.put("msg","系统错误");
            map.put("code",-1);
        }

        return map;
    }

    /**
     * 设置所有在世族人屏蔽或者解除屏蔽
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "setpeoplehideornot02")
    @ResponseBody
    public Map<String,Object> setpeoplehideornot02(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,Object> params) throws Exception{

        Map<String,Object> map = new HashMap<String,Object>();
        try {
            consoleFamilyService.setHideOrNotFromFamily(params);
            map.put("msg","屏蔽完成");
            map.put("code",1);
        }catch (Exception e){
            LOGGER.error("操作出错了-->",e);
            map.put("msg","系统错误");
            map.put("code",-1);
        }

        return map;
    }

}
