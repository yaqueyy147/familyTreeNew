package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.service.fronts.UserFrontService;
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

    @Autowired
    private UserFrontService userFrontService;

    /**
     * 个人中心
     * @param model
     * @return
     */
    @RequestMapping(value = "/personalIndex")
    public ModelAndView personalIndex(Model model, HttpServletRequest request) throws UnsupportedEncodingException{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userName",jsonUser.get("userName"));

        List<TFamily> list = familyService.getFamilyList(params);

        model.addAttribute("familyList",list);

        return new ModelAndView("/fronts/personalIndex");
    }

    @RequestMapping(value = "/personalInfo")
    public ModelAndView personalInfo(Model model,HttpServletRequest request) throws UnsupportedEncodingException{

        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        TUserFront tUserFront = userFrontService.getUserInfoFromId(CommonUtil.parseInt(jsonUser.get("id")));
        model.addAttribute("userInfo",jsonUser);
        model.addAttribute("tUserFront",tUserFront);
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
    public ModelAndView viewFamily(Model model, @RequestParam Map<String,Object> map, HttpServletRequest request) throws UnsupportedEncodingException{
        String familyId = map.get("familyId") + "";
        model.addAttribute("familyId",familyId);
        TFamily tFamily = familyService.getFamilyFromId(CommonUtil.parseInt(familyId));
        model.addAttribute("tFamily",tFamily);
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

        if(!CommonUtil.isBlank(jsonUser) && tFamily.getCreateMan().equals(jsonUser.get("userName"))){
            model.addAttribute("canOperate",1);
        }

        return new ModelAndView("/fronts/viewFamilyTree");
    }

    /**
     * 族谱树展示页面--仅供查看
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/viewFamily_visitor")
    public ModelAndView viewFamily_visitor(Model model, @RequestParam Map<String,Object> map, HttpServletRequest request) throws UnsupportedEncodingException{
        String familyId = map.get("familyId") + "";
        model.addAttribute("familyId",familyId);
        TFamily tFamily = familyService.getFamilyFromId(CommonUtil.parseInt(familyId));
        model.addAttribute("tFamily",tFamily);
        return new ModelAndView("/fronts/viewFamilyTree_visitor");
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
        Map<String,Object> paramss = new HashMap<>();
        paramss.put("familyId",familyId);
        paramss.put("peopleType",1);
        List<TPeople> listPeople = familyService.getPeopleList(paramss);

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
    public Map<String,Object> savePeople(HttpServletRequest request, TPeople tPeople,String birth_time,String die_time,String mateId) throws Exception{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

        Map<String,Object> map = new HashMap<String,Object>();
        if(!CommonUtil.isBlank(birth_time)){
            tPeople.setBirthTime(CommonUtil.ObjToDate(birth_time));
        }
        if(!CommonUtil.isBlank(die_time)){
            tPeople.setDieTime(CommonUtil.ObjToDate(die_time));
        }

        String msg = "保存成功";
        //修改成员信息
        if(tPeople.getId() > 0){
            familyService.updatePeople(tPeople);
            msg = "修改成功";
        }else{//新建成员
            tPeople.setCreateMan(jsonUser.get("userName")+"");
            tPeople.setCreateTime(CommonUtil.ObjToDate(CommonUtil.getDateLong()));
            int peopleId = familyService.savePeople(tPeople);
            tPeople.setId(peopleId);
            //添加积分
            //获取积分对应关系
            List<TPointsDic> listDic = familyService.getPointsRelation(1);
            TUserPoints tUserPoints = new TUserPoints(CommonUtil.parseInt(jsonUser.get("id")),listDic.get(0).getPointsValue());

            familyService.setPoints(tUserPoints,1);

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

    /**
     * 匹配可以合并的族谱
     * @param familyId
     * @return
     */
    @RequestMapping(value = "/familyMatch")
    @ResponseBody
    public Map<String,Object> familyMatch(int familyId){
        Map<String,Object> result = new HashMap<String,Object>();

        Map<String,Object> paramss = new HashMap<>();
        paramss.put("familyId",familyId);
        paramss.put("peopleType",2);

        List<TPeople> listPeople = familyService.getPeopleList(paramss);
        Map<String,List<TPeople>> tempMap = new HashMap<String,List<TPeople>>();
        for (TPeople tPeople : listPeople) {
            //比对前两代人的相同度
            if(tPeople.getGeneration() <= 2){
                paramss.put("familyId",tPeople.getFamilyId());
                paramss.put("peopleName",tPeople.getName());
                paramss.put("generation",tPeople.getGeneration());
                //查询前两代
                List<TPeople> list1 = familyService.getPeopleList(paramss);

                tempMap.put("peopleList" + tPeople.getGeneration(),list1);
            }
        }

        //对比前两代匹配结果，如果都存在的，则加入结果集
        List<TPeople> resultList = new ArrayList<TPeople>();
        for (TPeople tPeople : tempMap.get("peopleList1")) {
            for (TPeople tPeople1 : tempMap.get("peopleList2")) {
                if(tPeople.getId() == tPeople1.getId()){
                    resultList.add(tPeople);
                }
            }
        }
        result.put("resultPeopleList",resultList);
        return result;
    }

}
