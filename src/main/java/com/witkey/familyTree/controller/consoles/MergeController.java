package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TFamilyMerge;
import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/4/11 0011.
 */
@Controller
@RequestMapping(value = "/consoles")
public class MergeController {

    @Autowired
    private ConsoleService consoleService;
    @Autowired
    private FamilyService familyService;

    /**
     * 后台族谱收录申请列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/merge")
    public ModelAndView merge(Model model){
        return new ModelAndView("/consoles/merge");
    }

    /**
     * 后台族谱收录审核页面
     * @param model
     * @param params
     * @return
     */
    @RequestMapping(value = "/familyMerge")
    public ModelAndView familyMerge(Model model,@RequestParam Map<String,Object> params){
        TFamily tFamily = familyService.getFamilyFromId(CommonUtil.parseInt(params.get("familyId")));
        model.addAttribute("primaryFamily",tFamily);
        Map<String,Object> paramss = new HashMap<String,Object>();
        paramss.put("mergeId",params.get("mergeId"));
        List<Map<String,Object>> mm = consoleService.getMergeList(params);
        model.addAttribute("mergeId",params.get("mergeId"));
        model.addAttribute("merge",mm.get(0));
        return new ModelAndView("/consoles/familyMerge");
    }

    /**
     * 申请收录族谱族人信息
     * @param request
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mergePrimary")
    @ResponseBody
    public Map<String,Object> mergePrimary(HttpServletRequest request,@RequestParam Map<String,Object> params) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";
        Map<String,Object> result = new HashMap<String,Object>();

        if(!"admin".equals(userName) && !"系统管理员".equals(userName)){
            params.put("userName",userName);
            params.put("userId",consolesUser.get("id"));
            params.put("province",consolesUser.get("province"));
            params.put("city",consolesUser.get("city"));
            params.put("district",consolesUser.get("district"));
        }

        List<Map<String, Object>> list = consoleService.getMergeList(params);

        result.put("primaryList",list);
        return result;
    }

    @RequestMapping(value = "/mergeTarget")
    @ResponseBody
    public Map<String,Object> mergeTarget(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        List<TFamily> list = consoleService.getTargetMergeList(params);

        result.put("targetList",list);
        return result;
    }

    /**
     * 驳回收录
     * @param request
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/rejectInclude")
    @ResponseBody
    public Map<String,Object> rejectInclude(HttpServletRequest request, @RequestParam Map<String,Object> params) throws UnsupportedEncodingException {
        Map<String,Object> result = new HashMap<String,Object>();
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";

        int i = consoleService.rejectInclude(CommonUtil.parseInt(params.get("mergeId")),params.get("rejectDesc") + "",userName);
        result.put("code",i);
        result.put("msg","操作成功!");
        return result;
    }

    /**
     * 同意收录，同意开放补录
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value = "confirmInclude")
    @ResponseBody
    public Map<String,Object> confirmInclude(HttpServletRequest request,@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        int i = consoleService.confirmInclude(params);

        result.put("code",i);
        return result;
    }

    /**
     * 审核补录的族人
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value = "auditIncludePeople")
    @ResponseBody
    public Map<String,Object> auditIncludePeople(HttpServletRequest request,@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        
        int i = consoleService.auditIncludePeople(params);

        result.put("code",i);
        return result;
    }
    
    /**
     * 完成收录，关闭补录
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value = "completeIn")
    @ResponseBody
    public Map<String,Object> completeIn(HttpServletRequest request,@RequestParam Map<String,Object> params) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";
        params.put("auditMan", userName);//设置审核人
        params.put("auditTime", CommonUtil.getDateLong());
        int i = consoleService.completeIn(params);

        result.put("code",i);
        return result;
    }
    
    @RequestMapping(value = "mergeFamily")
    @ResponseBody
    public Map<String,Object> mergeFamily(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        Map<String,Object> params1 = new HashMap<String,Object>();
        params1.put("familyId",params.get("primaryFamilyId"));
        List<TPeople> primaryFamily = familyService.getPeopleList(params1);
        params1.put("familyId",params.get("targetFamilyId"));
        List<TPeople> targetFamily = familyService.getPeopleList(params1);

        result.put("code",1);
        return result;
    }

}
