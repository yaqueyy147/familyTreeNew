package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TMate;
import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.BaseUtil;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by suyx on 2016/12/20 0020.
 */
@Controller
@RequestMapping(value = "/family")
public class FamilyController {


    private static final Logger LOGGER = Logger.getLogger(FamilyController.class);

    @Autowired
    private FamilyService familyService;

    /**
     * 个人中心
     * @param model
     * @return
     */
    @RequestMapping(value = "/personalIndex")
    public ModelAndView personalIndex(Model model){

        List<TFamily> list = familyService.getFamilyList("ceshi123",0);

        model.addAttribute("familyList",list);

        return new ModelAndView("/fronts/personalIndex");
    }

    @RequestMapping(value = "/personalInfo")
    public ModelAndView personalInfo(Model model,HttpServletRequest request) throws UnsupportedEncodingException{

        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        model.addAttribute("userInfo",jsonUser);
        return new ModelAndView("/fronts/personalInfo");
    }

    @RequestMapping(value = "/saveFamily")
    @ResponseBody
    public Map<String,Object> saveFamily(TFamily tFamily, HttpServletRequest request){

        Map<String,Object> map = new HashMap<String,Object>();
        try {
            tFamily.setCreateMan("ceshi123");
            tFamily.setCreateTime(new Date());
            String visitPassword = tFamily.getVisitPassword();
            if(!CommonUtil.isBlank(visitPassword)){
                tFamily.setVisitPassword(CommonUtil.string2MD5(visitPassword));
            }
            //保存族谱
            int familyId = familyService.createFamily(tFamily);
            //将返回的族谱ID设置到family
            tFamily.setId(familyId);
            tFamily.setPhotoUrl(BaseUtil.DEFAULT_FAMILY_IMG);

        } catch (Exception e){
            LOGGER.error("创建族谱出错-->",e);
            map.put("tFamily",tFamily);
            map.put("code",-1);
            map.put("msg","创建族谱出错！-->" + e.getMessage());
            return map;
        }
        map.put("tFamily",tFamily);
        map.put("code",1);
        map.put("msg","创建成功！");
        return map;
    }

    /**
     * 族谱树展示页面
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/viewFamily")
    public ModelAndView viewFamily(Model model, @RequestParam Map<String,Object> map){
        String familyId = map.get("familyId") + "";
        model.addAttribute("familyId",familyId);
        TFamily tFamily = familyService.getFamilyListFromId(CommonUtil.parseInt(familyId));
        model.addAttribute("tFamily",tFamily);
        return new ModelAndView("/fronts/viewFamilyTree");
    }

    /**
     * 根据家族ID获取家族成员
     * @param familyId
     * @return
     */
    @RequestMapping(value = "/getPeopleList")
    @ResponseBody
    public List<Map<String,Object>> getPeopleList(int familyId){

        //查询族人
        List<TPeople> listPeople = familyService.getPeopleList(familyId);

        List<Map<String,Object>> list = new ArrayList<>();

        //根据族人Id查询配偶
        for (TPeople tPeople : listPeople) {
            Map<String,Object> map = new HashMap<>();
            map = CommonUtil.bean2Map(tPeople);
            int peopleId = tPeople.getId();
            List<TPeople> listMate = familyService.getMateList(peopleId);
            map.put("mateList",listMate);
            list.add(map);
        }

        return list;
    }

    /**
     * 录入保存族人
     * @param tPeople
     * @param birth_time
     * @param die_time
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savePeople")
    @ResponseBody
    public Map<String,Object> savePeople(TPeople tPeople,String birth_time,String die_time,String mateId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        if(!CommonUtil.isBlank(birth_time)){
            tPeople.setBirthTime(CommonUtil.ObjToDate(birth_time));
        }
        if(!CommonUtil.isBlank(die_time)){
            tPeople.setBirthTime(CommonUtil.ObjToDate(die_time));
        }

        String msg = "保存成功";
        //修改成员信息
        if(tPeople.getId() > 0){
            familyService.updatePeople(tPeople);
            msg = "修改成功";
        }else{//新建成员
            int peopleId = familyService.savePeople(tPeople);
            tPeople.setId(peopleId);
            //如果是添加配偶
            if(tPeople.getPeopleType() == 0){
                //保存配偶信息
                TMate tMate = new TMate(CommonUtil.parseInt(mateId),tPeople.getId(),"",tPeople.getMateType());
                familyService.saveMateInfo(tMate);
            }
        }
        map.put("msg",msg);
        map.put("code",1);
        return map;
    }

    @RequestMapping(value = "/getParent")
    @ResponseBody
    public Map<String,Object> getParent(int familyId,int generation){
        Map<String,Object> result = new HashMap<String,Object>();
        result = familyService.getParentFromGen(familyId,generation);
        return result;
    }

}
