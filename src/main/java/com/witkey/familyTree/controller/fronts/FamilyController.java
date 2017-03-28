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
        params.put("state",1);
        List<TFamily> list = familyService.getFamilyList(params);

        //查询被收录的族谱
        params.put("userName",jsonUser.get("userName"));
        params.put("state","");

        List<TFamily> list2 = familyService.getIncludeFamilyList(params);
        list.addAll(list2);

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
            JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
            tFamily.setCreateMan(jsonUser.get("userName")+"");
            tFamily.setCreateTime(new Date());
            tFamily.setState(1);
            String visitPassword = tFamily.getVisitPassword();
            if(!CommonUtil.isBlank(visitPassword)){
                tFamily.setVisitPassword(CommonUtil.string2MD5(visitPassword));
            }
            LOGGER.debug("创建族谱-->" + tFamily);
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

//        if(!CommonUtil.isBlank(jsonUser) && tFamily.getCreateMan().equals(jsonUser.get("userName"))){
//            model.addAttribute("canOperate",1);
//        }

        //查询家族的收录情况
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("primaryFamilyId",familyId);
        List<TFamilyMerge> listMerge = familyService.getMergeList(map1);
        if(listMerge != null && listMerge.size() > 0){
            model.addAttribute("merge",listMerge.get(0));
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
     * @param params
     * @return
     */
    @RequestMapping(value = "/getPeopleList")
    @ResponseBody
    public List<Map<String,Object>> getPeopleList(@RequestParam Map<String,Object> params){

        //查询族人
        params.put("peopleType",1);
        List<TPeople> listPeople = familyService.getPeopleList(params);

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
            List<TPointsDic> listDic = familyService.getPointsRelation(1,1);
            TUserPoints tUserPoints = new TUserPoints(CommonUtil.parseInt(jsonUser.get("id")),listDic.get(0).getPointsValue(),1);

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

    @RequestMapping(value = "/deletePeople")
    @ResponseBody
    public Map<String,Object> deletePeople(int peopleId){
        Map<String,Object> result = new HashMap<String,Object>();
        int i = familyService.deletePeople(peopleId);
        result.put("code",i);
        return result;
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

        paramss.put("orderBy","order by generation asc");

        //查询第一代人
        paramss.put("generation",1);
        List<TPeople> listPeople1 = familyService.getPeopleList(paramss);
        //查询第二代人
        paramss.put("generation",2);
        List<TPeople> listPeople2 = familyService.getPeopleList(paramss);

        //将第一代人 姓名 作为条件查找有相同的第一代人的家族，记录familyId
        Map<Object,Object[]> temp1 = new HashMap<Object,Object[]>();
        if(listPeople1 != null && listPeople1.size() > 0){
            for (TPeople tPeople : listPeople1) {
                paramss.put("familyId",tPeople.getFamilyId());
                paramss.put("peopleType",2);
                paramss.put("generation",1);
                paramss.put("name",tPeople.getName());
                List<Map<String,Object>> familyIdTemp = familyService.getFamilyIdForMerge(paramss);
                for(Map<String,Object> map : familyIdTemp){
                    String fId = map.get("family_id") + "";
                    if(fId.equals(tPeople.getFamilyId())){
                        continue;
                    }
                    int num = 1;
                    if(!CommonUtil.isBlank(temp1.get(fId))){
                        num = CommonUtil.parseInt(temp1.get(fId)[1]);
                        if(temp1.get(fId)[0].equals(fId)){
                            num ++;
                        }
                    }
                    temp1.put(fId,new Object[]{fId,num});
                }

            }
        }else{//如果第一代人不存在，返回
            result.put("code","-1");
            return result;
        }

        //将第二代人 姓名 作为条件查找有相同的第二代人的家族，记录familyId
        Map<Object,Object[]> temp2 = new HashMap<Object,Object[]>();
        if(listPeople2 != null && listPeople2.size() > 0){
            for (TPeople tPeople : listPeople2) {
                paramss.put("familyId",tPeople.getFamilyId());
                paramss.put("peopleType",2);
                paramss.put("generation",2);
                paramss.put("name",tPeople.getName());
                List<Map<String,Object>> familyIdTemp = familyService.getFamilyIdForMerge(paramss);
                for(Map<String,Object> map : familyIdTemp){
                    String fId = map.get("family_id") + "";
                    if(fId.equals(tPeople.getFamilyId())){
                        continue;
                    }
                    int num = 1;
                    if(!CommonUtil.isBlank(temp2.get(fId))){
                        num = CommonUtil.parseInt(temp2.get(fId)[1]);
                        if(temp2.get(fId)[0].equals(fId)){
                            num ++;
                        }
                    }

                    temp2.put(fId,new Object[]{fId,num});
                }
            }
        }else{//如果第二代人不存在，返回
            result.put("code","-2");
            return result;
        }

        List<TFamily> resultFamily = new ArrayList<TFamily>();
        for(Object key1 : temp1.keySet()){
            //如果匹配的第一代记录的当前目标家族的数量大于原第一代人数量的一半，即是有大多数人都能匹配
            if(CommonUtil.parseInt(temp1.get(key1)[1]) > (listPeople1.size() / 2)){
                for(Object key2 : temp2.keySet()){
                    //如果与第二代匹配的家族有与第一代匹配的家族相同的，并且匹配的第二代记录的当前目标家族的数量大于原第二代人数量的一半，即是有大多数人都能匹配
                    if(key1.equals(key2) && CommonUtil.parseInt(temp2.get(key2)[1]) > (listPeople2.size() / 2)){
                        //则认为当前匹配的家族与原选择的家族是相匹配的，可以进行收录/合并
                        resultFamily.add(familyService.getFamilyFromId(CommonUtil.parseInt(key2)));
                    }
                }
            }
        }
        result.put("code",1);
        result.put("resultFamilyList",resultFamily);
        return result;
    }

    @RequestMapping(value = "/familyMerge")
    @ResponseBody
    public Map<String,Object> familyMerge(HttpServletRequest request, @RequestParam Map<String,Object> params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

        TFamilyMerge tFamilyMerge = new TFamilyMerge();
        tFamilyMerge.setPrimaryFamilyId(CommonUtil.parseInt(params.get("primaryFamilyId")));
        tFamilyMerge.setTargetFamilyId(CommonUtil.parseInt(params.get("targetFamilyId")));
        tFamilyMerge.setState(2);
        tFamilyMerge.setApplyMan(jsonUser.get("userName") + "");
        tFamilyMerge.setApplyTime(CommonUtil.ObjToDate(CommonUtil.getDateLong()));

        int i = familyService.saveInclude(tFamilyMerge);

        result.put("code",i);
        return result;
    }

}
