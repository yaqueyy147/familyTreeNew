package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.consoles.LogService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.service.fronts.UserService;
import com.witkey.familyTree.util.BaseUtil;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by suyx on 2017/1/11.
 */
@Controller
@RequestMapping(value = "/consoles")
public class ConsoleController {

    private static final Logger LOGGER = Logger.getLogger(ConsoleController.class);

    @Autowired
    private ConsoleService consoleService;
    @Autowired
    private FamilyService familyService;

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    /**
     * 公司列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "company")
    public ModelAndView companyList(Model model){

//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("state",0);
//        List<Map<String,Object>> list = consoleService.getCompanyList(params);
//        model.addAttribute("companyList",list);
        return new ModelAndView("/consoles/companyList");
    }

    /**
     * 赞助商列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "companyList")
    @ResponseBody
    public Map<String,Object> getCompanyList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = consoleService.getCompanyList(params);
        result.put("dataList",list);
        return result;
    }

    /**
     * 审核赞助商
     * @param params
     * @return
     */
    @RequestMapping(value = "/auditCompany")
    @ResponseBody
    public Map<String,Object> auditCompany(HttpServletRequest request, @RequestParam Map<String,Object> params) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";
        params.put("auditMan",userName);
        int i = 0;
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            i = consoleService.auditCompany(params);
            map.put("msg","审核完成");
            map.put("code",1);
        }catch (Exception e){
            LOGGER.error("操作出错了-->",e);
            map.put("msg","系统错误");
            map.put("code",-1);
        }
        map.put("msg","审核完成");
        map.put("code",1);
        return map;
    }

    /**
     * 族谱列表页面
     * @return
     */
    @RequestMapping(value = "family")
    public ModelAndView family(HttpServletRequest request,Model model) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");

        model.addAttribute("consolesUser",consolesUser);
        return new ModelAndView("/consoles/familyList");
    }

    /**
     * 族谱列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "familyList")
    @ResponseBody
    public Map<String,Object> getFamilyList(HttpServletRequest request, @RequestParam Map<String, Object> params) throws UnsupportedEncodingException{
        Map<String,Object> result = new HashMap<String,Object>();

        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";

        List<TFamily> list = new ArrayList<TFamily>();
        if(!"admin".equals(userName) && !"系统管理员".equals(userName)){
            params.put("userName",userName);
            params.put("userId",consolesUser.get("id"));
            list = familyService.getFamilyList1(params);
        }else{
        	list = familyService.getFamilyList(params);
        }
        
        List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
        for(TFamily tFamily : list){
            int peopleCount = 0;
            Map<String,Object> map = new HashMap<String,Object>();
            Map<String,Object> paramss = new HashMap<>();
            paramss.put("familyId",tFamily.getId());
            paramss.put("peopleType",1);
            List<TPeople> peopleList = familyService.getPeopleList(paramss);
            if(peopleList != null && peopleList.size() > 0)
            {
                peopleCount = peopleList.size();
            }
            map = CommonUtil.bean2Map(tFamily);
            map.put("peopleCount",peopleCount);
            list1.add(map);
        }
        result.put("dataList",list1);
        return result;
    }

    @RequestMapping(value = "saveFamily")
    @ResponseBody
    public Map<String,Object> saveFamily(HttpServletRequest request, TFamily tFamily,String createTime4Modify) throws Exception{

        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";

        Map<String,Object> map = new HashMap<String,Object>();
        int ii = 0;
        String msg = "创建成功";
        try {
            if(tFamily.getId() > 0){
                TFamily tFamilyOld = familyService.getFamilyFromId(tFamily.getId());

                LOGGER.info("修改族谱-->" + tFamily);
                tFamily.setCreateTime(CommonUtil.ObjToDate(createTime4Modify));
                ii = familyService.updateFamily(tFamily);
                msg = "修改成功";

                //记录日志
                logService.createLog(new TLog(2,userName,tFamily.toString(),tFamilyOld.toString()));
            }else{
                tFamily.setCreateMan(userName);
                tFamily.setCreateTime(new Date());

//                String visitPassword = tFamily.getVisitPassword();
//                if(!CommonUtil.isBlank(visitPassword)){
//                    tFamily.setVisitPassword(CommonUtil.string2MD5(visitPassword));
//                }
                LOGGER.info("创建族谱-->" + tFamily);
                //保存族谱
                ii = familyService.createFamily(tFamily);
                //将返回的族谱ID设置到family
                tFamily.setId(ii);
                if(CommonUtil.isBlank(tFamily.getPhotoUrl())){
                    tFamily.setPhotoUrl(BaseUtil.DEFAULT_FAMILY_IMG);
                }

                //将族谱添加为用户可操作
                userService.saveUserFamily(new TUserFamily(CommonUtil.parseInt(consolesUser.get("id")),ii));

                //记录日志
                logService.createLog(new TLog(1,userName,tFamily.toString()));
            }

        } catch (Exception e){
            LOGGER.error("操作族谱出错-->",e);
            map.put("tFamily",tFamily);
            map.put("code",-1);
            map.put("msg","操作族谱出错！-->" + e.getMessage());
            return map;
        }
        map.put("tFamily",tFamily);
        map.put("code",ii);
        map.put("msg",msg);
        return map;
    }

    @RequestMapping(value = "/deleteFamily")
    @ResponseBody
    public Map<String,Object> deleteFamily(@RequestParam Map<String,Object> params, HttpServletRequest request) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";
        Map<String,Object> result = new HashMap<String,Object>();

        LOGGER.info("删除族谱-->" + params);
        int i = familyService.deleteFamily(params);

        //记录日志
        logService.createLog(new TLog(3,userName,"删除族谱(" + params.get("ids") + ")"));

        result.put("code",i);
        result.put("msg","操作成功!");

        return result;
    }

    /**
     * 族谱内容页面
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "familyTree")
    public ModelAndView familyTree(HttpServletRequest request, Model model, @RequestParam Map<String,Object> map) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");

        model.addAttribute("consolesUser",consolesUser);

        String familyId = map.get("familyId") + "";
        model.addAttribute("familyId",familyId);
        TFamily tFamily = familyService.getFamilyFromId(CommonUtil.parseInt(familyId));
        model.addAttribute("tFamily",tFamily);
        return new ModelAndView("/consoles/familyTree_console");
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
    public Map<String,Object> savePeople(HttpServletRequest request, TPeople tPeople,String birth_time,String die_time,String mateId,String userCC) throws Exception{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = jsonUser.get("userName") + "";
        Map<String,Object> map = new HashMap<String,Object>();
        if(!CommonUtil.isBlank(birth_time)){
            tPeople.setBirthTime(CommonUtil.ObjToDate(birth_time));
        }
        if(!CommonUtil.isBlank(die_time)){
            tPeople.setDieTime(CommonUtil.ObjToDate(die_time));
        }

        if("1".equals(userCC)){
            tPeople.setId(0);
        }
        String msg = "保存成功";
        //修改成员信息
        if(tPeople.getId() > 0){
            TPeople tPeopleOld = familyService.getPeopleInfo(tPeople.getId());
            tPeople.setCreateMan(tPeopleOld.getCreateMan());
            tPeople.setCreateId(tPeopleOld.getCreateId());
            tPeople.setCreateTime(tPeopleOld.getCreateTime());
            tPeople.setIsSupplement(tPeopleOld.getIsSupplement());
            familyService.updatePeople(tPeople);
            LOGGER.info("修改族人-->" + tPeople);
            msg = "修改成功";

            //记录日志
            logService.createLog(new TLog(2,userName,tPeople.toString(),tPeopleOld.toString()));

        }else{//新建成员

            //根据登录人和族谱创建人判断是否是补录
            TFamily tFamily = familyService.getFamilyFromId(tPeople.getFamilyId());
            //如果登录人不是族谱创建人，则为补录
            if(!tFamily.getCreateMan().equals(userName)){
                tPeople.setIsSupplement(1);//设置该成员为补录成员
                tPeople.setPeopleStatus(5);//设置状态为补录未审核状态
            }

            tPeople.setCreateMan(jsonUser.get("userName")+"");
            tPeople.setCreateTime(CommonUtil.ObjToDate(CommonUtil.getDateLong()));
            int peopleId = familyService.savePeople(tPeople);
            tPeople.setId(peopleId);

//            if(CommonUtil.isBlank(userCC) || !"1".equals(userCC)){
//                //添加积分
//                //获取积分对应关系
//                List<TPointsDic> listDic = familyService.getPointsRelation(1,1);
//                TUserPoints tUserPoints = new TUserPoints(CommonUtil.parseInt(jsonUser.get("id")),listDic.get(0).getPointsValue(),2);
//
//                familyService.setPoints(tUserPoints,1);
//            }
//
//            //如果是收录族谱，将对应的配偶的家族ID也修改为收录的族谱ID
//            if("1".equals(userCC)){
//                //查询配偶信息
//                List<TPeople> listMate = familyService.getMateList(tPeople.getId());
//                if(listMate != null && listMate.size() > 0){
//                    for(TPeople tPeople1 : listMate){
//                        tPeople1.setId(0);
//                        tPeople1.setFamilyId(tPeople.getFamilyId());
//                        tPeople1.setFatherId(tPeople.getFatherId());
//                        tPeople1.setMotherId(tPeople.getMotherId());
//                        familyService.savePeople(tPeople1);
//                    }
//                }
//            }

            //如果是添加配偶
            if(tPeople.getPeopleType() == 0){
                //保存配偶信息
                TMate tMate = new TMate(CommonUtil.parseInt(mateId),tPeople.getId(),"",tPeople.getMateType());
                familyService.saveMateInfo(tMate);
            }

            //记录日志
            logService.createLog(new TLog(1,userName,tPeople.toString()));

        }
        map.put("msg",msg);
        map.put("code",1);
        return map;
    }

    @RequestMapping(value = "/deletePeople")
    @ResponseBody
    public Map<String,Object> deletePeople(int peopleId, int familyId, HttpServletRequest request) throws Exception{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = jsonUser.get("userName") + "";
        Map<String,Object> result = new HashMap<String,Object>();

        //查询当前成员是否含有下一代人
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("fatherId",peopleId);
        params.put("familyId",familyId);
        //如果有下一代人，不能删除
        List<TPeople> list = familyService.getPeopleList(params);
        if(list != null && list.size() > 0){
            result.put("code",-1);
            return result;
        }

        TPeople tPeople = familyService.getPeopleInfo(peopleId);
        tPeople.setPeopleStatus(9);
//        int i = familyService.deletePeople(peopleId);
        int i = 0;
        familyService.updatePeople(tPeople);
        i ++ ;
        result.put("code",i);
        //记录日志
        logService.createLog(new TLog(3,userName,"删除族人-->" + tPeople.toString()));
        return result;
    }

    /**
     * 族谱组人数据
     * @param peopleId
     * @return
     */
    @RequestMapping(value = "getPeopleInfo")
    @ResponseBody
    public Map<String,Object> getPeopleInfo(int peopleId){
        Map<String,Object> result = new HashMap<String,Object>();
        TPeople tPeople = familyService.getPeopleInfo(peopleId);
        result.put("tPeople",tPeople);
        return result;
    }

    /**
     * 角色页面
     * @param model
     * @return
     */
    @RequestMapping(value = "role")
    public ModelAndView role(Model model){
        return new ModelAndView("/consoles/roleSetting");
    }

    /**
     * 角色列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "roleList")
    @ResponseBody
    public Map<String,Object> getroleList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<TRole> list = consoleService.getRole(params);
        result.put("dataList",list);
        return result;
    }

    /**
     * 保存角色信息
     * @param TRole
     * @return
     */
    @RequestMapping(value = "saveRole")
    @ResponseBody
    public Map<String,Object> saveRole(TRole TRole){
        int i = consoleService.saveRole(TRole);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msg","保存成功!");
        result.put("tRole",TRole);
        result.put("code",i);
        return result;
    }

    /**
     * 删除角色
     * @param params
     * @return
     */
    @RequestMapping(value = "deleteRole")
    @ResponseBody
    public Map<String,Object> deleteRole(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        int i = consoleService.deleteRole(params);
        result.put("code",i);
        result.put("msg","操作成功!");
        return result;
    }

    /**
     * 英才录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/meritorcat")
    public ModelAndView meritorcat(Model model){
        //获取英才属性
        List<TMeritocratAttr> listAttr = consoleService.getMeritocratAttrList(null);
        model.addAttribute("meritorcatAttr",listAttr);

        //获取英才属地
        List<Map<String,Object>> listArea = familyService.getMeritocratArea();
        model.addAttribute("meritorcatArea",listArea);
        return new ModelAndView("/consoles/meritorcat");
    }

    @RequestMapping(value = "/meritorcatList")
    @ResponseBody
    public Map<String,Object> meritorcatList(@RequestParam Map<String,Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<Map<String,Object>> list = consoleService.getMeritocratList(params);
        result.put("meritorcatList",list);
        return result;
    }

    /**
     * 保存英才信息
     * @param tMeritocrat
     * @return
     */
    @RequestMapping(value = "saveMeritorcat")
    @ResponseBody
    public Map<String,Object> saveMeritorcat(HttpServletRequest request, TMeritocrat tMeritocrat) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";
        tMeritocrat.setCreateMan(userName);
        tMeritocrat.setCreateTime(CommonUtil.getDateLong());
        int i = consoleService.saveMeritocrat(tMeritocrat);
        LOGGER.debug("创建英才-->" + tMeritocrat);
        //记录日志
        logService.createLog(new TLog(1,userName,tMeritocrat.toString()));

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msg","保存成功!");
        result.put("tMeritocrat",tMeritocrat);
        result.put("code",i);
        return result;
    }

    /**
     * 删除英才
     * @param params
     * @return
     */
    @RequestMapping(value = "deleteMeritorcat")
    @ResponseBody
    public Map<String,Object> deleteMeritorcat(@RequestParam Map<String,Object> params, HttpServletRequest request) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";
        Map<String,Object> result = new HashMap<String,Object>();
        int i = consoleService.deleteMeritocrat(params);
        //记录日志
        logService.createLog(new TLog(3,userName,"删除英才(" + params.get("ids") + ")"));

        result.put("code",i);
        result.put("msg","操作成功!");
        return result;
    }

    @RequestMapping(value = "meritorcatAttr")
    public ModelAndView meritorcatAttr(){
        return new ModelAndView("/consoles/meritorcatAttr");
    }

    @RequestMapping(value = "/meritorcatAttrList")
    @ResponseBody
    public Map<String,Object> meritorcatAttrList(@RequestParam Map<String,Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<TMeritocratAttr> list = consoleService.getMeritocratAttrList(params);
        result.put("meritorcatAttrList",list);
        return result;
    }

    @RequestMapping(value = "saveMeritorcatAttr")
    @ResponseBody
    public Map<String,Object> saveMeritorcatAttr(HttpServletRequest request, TMeritocratAttr tMeritocratAttr) throws Exception{

        int i = consoleService.saveMeritocratAttr(tMeritocratAttr);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msg","保存成功!");
        result.put("tMeritocratAttr",tMeritocratAttr);
        result.put("code",i);
        return result;
    }

    /**
     * 删除英才
     * @param params
     * @return
     */
    @RequestMapping(value = "deleteMeritorcatAttr")
    @ResponseBody
    public Map<String,Object> deleteMeritorcatAttr(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        int i = consoleService.deleteMeritocratAttr(params);
        result.put("code",i);
        result.put("msg","操作成功!");
        return result;
    }

    @RequestMapping(value = "/rank")
    public ModelAndView rank(Model model){
        return new ModelAndView("/consoles/pointsList");
    }

    /**
     * 积分排行榜
     * @param
     * @return
     */
    @RequestMapping(value = "/pointsRanking")
    @ResponseBody
    public Map<String,Object> pointsRanking(){
        Map<String,Object> result = new HashMap<String,Object>();
        //个人积分排名
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("type",1);
        params.put("userType",2);
        List<Map<String,Object>> listPersonalPoints = familyService.getPointsRanking(params);
        //公司积分排名
        params.put("type",2);
        List<Map<String,Object>> listCompanyPoints = familyService.getPointsRanking(params);
        result.put("listPersonalPoints",listPersonalPoints);
        result.put("listCompanyPoints",listCompanyPoints);
        return result;
    }

    @RequestMapping(value = "pointsRelation")
    public ModelAndView pointsRelation(){
        return new ModelAndView("/consoles/pointsRelation");
    }

    @RequestMapping(value = "pointsRelationData")
    @ResponseBody
    public Map<String,Object> pointsRelationData(HttpServletRequest request,@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();

        List<TPointsDic> list = familyService.getPointsRelation(0,0);
        result.put("pointsDicList",list);
        return result;
    }

    /**
     * 保存积分对应关系
     * @param request
     * @param tPointsDic
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "savePointsRelation")
    @ResponseBody
    public Map<String,Object> savePointsRelation(HttpServletRequest request,TPointsDic tPointsDic) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";

        tPointsDic.setCreateMan(userName);
        tPointsDic.setCreateTime(CommonUtil.ObjToDate(CommonUtil.getDateLong()));

        int i = consoleService.savePointsRelation(tPointsDic);

        result.put("code",i);

        return result;
    }

    /**
     * 删除积分对应关系
     * @param request
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "deletePointsRelation")
    @ResponseBody
    public Map<String,Object> deletePointsRelation(HttpServletRequest request,String ids) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();

        int i = consoleService.deletePointsRelation(ids);

        result.put("code",i);

        return result;
    }

    @RequestMapping(value = "resource")
    public ModelAndView resource(Model model){
        return new ModelAndView("/consoles/resource");
    }

    /**
     * 功能列表
     * @param params
     * @return
     */
    @RequestMapping(value = "resourceList")
    @ResponseBody
    public Map<String,Object> resourceList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
        List<TResource> list = consoleService.getResourceList(params);

        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

        for (TResource tt : list) {
            Map<String,Object> temp = new HashMap<String,Object>();
            temp = CommonUtil.bean2Map(tt);
            temp.put("pId",tt.getParentSourceId());
            temp.put("pid",tt.getParentSourceId());
            temp.put("name",tt.getSourceName());
            temp.put("text",tt.getSourceName());
            temp.put("_parentId",tt.getParentSourceId());
            temp.put("resourceState",tt.getState());
            temp.put("state","open");
            temp.put("open",true);
            resultList.add(temp);
        }

        result.put("resourceList",resultList);
        return result;
    }

    /**
     * 保存功能
     * @param tResource
     * @return
     */
    @RequestMapping(value = "saveResource")
    @ResponseBody
    public Map<String,Object> saveResource(TResource tResource){
        Map<String,Object> result = new HashMap<>();

        int i = consoleService.saveResource(tResource);

        result.put("code",i);
        result.put("msg","操作成功");
        return result;
    }

    /**
     * 删除功能
     * @param params
     * @return
     */
    @RequestMapping(value = "deleteResource")
    @ResponseBody
    public Map<String,Object> deleteResource(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();

        int i = consoleService.deleteResource(params);

        result.put("code",i);
        result.put("msg","删除完成");
        return result;
    }

}
