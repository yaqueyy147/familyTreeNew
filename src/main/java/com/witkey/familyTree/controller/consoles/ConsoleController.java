package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.dao.fronts.TPeopleDao;
import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.consoles.LogService;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.service.fronts.UserService;
import com.witkey.familyTree.util.BaseUtil;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import com.witkey.familyTree.util.PeopleTree;
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
import javax.servlet.http.HttpServletResponse;
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
    private CompanyService companyService;

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @Autowired
    private TPeopleDao tPeopleDao;


    /**
     * 族谱列表页面
     * @return
     */
    @RequestMapping(value = "family")
    public ModelAndView family(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
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
    public Map<String,Object> getFamilyList(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> params) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        //获取登录用户信息
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";

        List<TFamily> list = new ArrayList<TFamily>();
        //如果不是系统管理员，则根据登录人id和登录名查询相应的族谱
        if(!"admin".equals(userName) && !"系统管理员".equals(userName)){
            params.put("userName",userName);
            params.put("userId",consolesUser.get("id"));
            list = familyService.getFamilyList1(params);
        }else{//如果是系统管理员登录，则查询所有可用的族谱
        	list = familyService.getFamilyList(params);
        }
        
        List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
        //遍历族谱，设置族谱人数
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

    /**
     * 保存/修改族谱
     * @param request
     * @param response
     * @param tFamily  传入的族谱数据
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveFamily")
    @ResponseBody
    public Map<String,Object> saveFamily(HttpServletRequest request,HttpServletResponse response, TFamily tFamily) throws Exception{

        //获取登录用户信息
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("loginName") + "";
        Map<String,Object> map = new HashMap<String,Object>();
        int ii = 0;
        String msg = "创建成功";
        try {
            tFamily.setState(1);
            //如果传入的族谱有id，则为修改族谱
            if(tFamily.getId() > 0){
                //根据族谱id获取族谱原信息
                TFamily tFamilyOld = familyService.getFamilyFromId(tFamily.getId());

                LOGGER.info("修改族谱-->" + tFamily);
                //将原族谱不能修改的信息设置到新族谱中
                tFamily.setCreateTime(tFamilyOld.getCreateTime());
                tFamily.setCreateId(tFamilyOld.getCreateId());
                tFamily.setCreateMan(tFamilyOld.getCreateMan());
                tFamily.setSupplementFlag(tFamilyOld.getSupplementFlag());
                //修改族谱
                ii = familyService.updateFamily(tFamily);
                msg = "修改成功";

                //记录日志
                logService.createLog(new TLog(2,userName,tFamily.toString(),tFamilyOld.toString()));
            }else{//如果传入的族谱id为空或者为0，则为新增族谱
                //设置创建人和创建时间
                tFamily.setCreateId(consolesUser.get("id") + "");
                tFamily.setCreateTime(new Date());
                tFamily.setCreateMan(userName);
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

    /**
     * 删除族谱
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteFamily")
    @ResponseBody
    public Map<String,Object> deleteFamily(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception{
        //获取登录用户信息
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
    public ModelAndView familyTree(HttpServletRequest request,HttpServletResponse response, Model model, @RequestParam Map<String,Object> map) throws Exception{
        //获取登录用户信息
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        model.addAttribute("consolesUser",consolesUser);

        //根据familyId查询族谱信息
        String familyId = map.get("familyId") + "";
        model.addAttribute("familyId",familyId);
        TFamily tFamily = familyService.getFamilyFromId(CommonUtil.parseInt(familyId));
        model.addAttribute("tFamily",tFamily);

        //查询当前族谱总共多少代
        int maxGeneration = familyService.getFamilyMaxGeneration(CommonUtil.parseInt(familyId));

        model.addAttribute("maxGeneration",maxGeneration);

        //查询家族的收录情况
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("primaryFamilyId",familyId);
        List<TFamilyMerge> listMerge = familyService.getMergeList(map1);
        if(listMerge != null && listMerge.size() > 0){
            model.addAttribute("merge",listMerge.get(0));
        }
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
        //获取登录用户信息
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
            tPeople.setId("");
        }
        String msg = "保存成功";
        //修改成员信息
        if(!CommonUtil.isBlank(tPeople.getId()) && !"0".equals(tPeople.getId())){
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
            tPeople.setId(CommonUtil.uuid());
            //根据登录人和族谱创建人判断是否是补录
            TFamily tFamily = familyService.getFamilyFromId(tPeople.getFamilyId());
            //如果登录人不是族谱创建人，则为补录
            if(!tFamily.getCreateMan().equals(userName)){
                tPeople.setIsSupplement(1);//设置该成员为补录成员
                tPeople.setPeopleStatus(5);//设置状态为补录未审核状态
            }

            tPeople.setCreateMan(jsonUser.get("userName")+"");
            tPeople.setCreateTime(CommonUtil.ObjToDate(CommonUtil.getDateLong()));
//            int peopleId = familyService.savePeople(tPeople);
//            tPeople.setId(peopleId);
            tPeopleDao.save(tPeople);

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
                TMate tMate = new TMate(mateId,tPeople.getId(),"",tPeople.getMateType());
                familyService.saveMateInfo(tMate);
            }

            //记录日志
            logService.createLog(new TLog(1,userName,tPeople.toString()));

        }
        map.put("msg",msg);
        map.put("code",1);
        return map;
    }

    /**
     * 删除族人
     * @param peopleId
     * @param familyId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deletePeople")
    @ResponseBody
    public Map<String,Object> deletePeople(String peopleId, int familyId, HttpServletRequest request) throws Exception{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = jsonUser.get("userName") + "";
        Map<String,Object> result = new HashMap<String,Object>();

        //查询当前成员是否含有下一代人
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("fatherId",peopleId);
        params.put("familyId",familyId);
        params.put("superiorId",peopleId);
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
     * 根据家族ID获取家族成员
     * @param params
     * @return
     */
    @RequestMapping(value = "/getPeopleList")
    @ResponseBody
    public Object getPeopleList(@RequestParam Map<String,Object> params, HttpServletRequest request){

        //查询族人
        params.put("peopleType",1);
        params.put("orderBy"," order by family_rank asc");

        System.out.print("******\n开始-->" + CommonUtil.getDateLong() + "::" + System.currentTimeMillis() + "\n********" );

//        params.put("isIndex",1);
        List<TPeople> listPeople = familyService.getPeopleList(params);
        System.out.print("******\n结束1-->" + CommonUtil.getDateLong() + "::" + System.currentTimeMillis() + "\n********" );
//        List<Map<String,Object>> list = new ArrayList<>();
        List<PeopleTree> list1 = new ArrayList<>();

        //根据族人Id查询配偶
        for (TPeople tPeople : listPeople) {
            Map<String,Object> map = new HashMap<>();
            PeopleTree pp = new PeopleTree();
            pp.setNocheck(false);
            pp.setChkDisabled(false);
            pp.setId(tPeople.getId() + "");
            pp.setpId(tPeople.getSuperiorId() + "");
            pp.setGeneration(tPeople.getGeneration() + "");
            pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head2.ico");
            if(tPeople.getState() == 0){
                pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head_die.ico");
            }
            pp.setIsSupplement(tPeople.getIsSupplement() + "");
            pp.setOpen(true);
            pp.setIsdie(tPeople.getState() + "");
            String name = tPeople.getName() + "(第" + tPeople.getGeneration() + "世)";
            if("1".equals(tPeople.getIshide())){
                name += "--已屏蔽";
            }
            pp.setName(name);
            pp.setPeopleStatus(tPeople.getPeopleStatus() + "");
//            map = CommonUtil.bean2Map(tPeople);
            String peopleId = tPeople.getId();
            List<TPeople> listMate = familyService.getMateList(peopleId);
            String mate = "";
            if(listMate != null && listMate.size() > 0){
                for(TPeople tPeople1 : listMate){
                    mate += "," + tPeople1.getName() + "--" + tPeople1.getId() + "--" + tPeople1.getPeopleStatus() + "--" + tPeople1.getIsSupplement();
                }
                mate = mate.substring(1);
            }

            pp.setMateName(mate);

//            map.put("mateList",listMate);
//            list.add(map);
            list1.add(pp);
        }
        System.out.print("******\n结束2-->" + CommonUtil.getDateLong() + "::" + System.currentTimeMillis() + "\n********" );
        return list1;
    }

    /**
     * 族谱组人数据
     * @param peopleId
     * @return
     */
    @RequestMapping(value = "getPeopleInfo")
    @ResponseBody
    public Map<String,Object> getPeopleInfo(String peopleId){
        Map<String,Object> result = new HashMap<String,Object>();
        TPeople tPeople = familyService.getPeopleInfo(peopleId);
        result.put("tPeople",tPeople);
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
    public List<Map<String,Object>> meritorcatList(@RequestParam Map<String,Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        System.out.println("英才录开始************-->" + CommonUtil.getDateLong() + "::" + CommonUtil.getTimeStamp() + "**********");
        List<Map<String,Object>> list = consoleService.getMeritocratList(params);
        System.out.println("英才录结束************-->" + CommonUtil.getDateLong() + "::" + CommonUtil.getTimeStamp() + "**********");
        result.put("meritorcatList",list);
        return list;
    }

    /**
     * 保存英才信息
     * @param tMeritocrat
     * @return
     */
    @RequestMapping(value = "saveMeritorcat")
    @ResponseBody
    public Map<String,Object> saveMeritorcat(HttpServletRequest request,HttpServletResponse response, TMeritocrat tMeritocrat) throws Exception{
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
    public Map<String,Object> deleteMeritorcat(@RequestParam Map<String,Object> params, HttpServletResponse response, HttpServletRequest request) throws Exception{
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
    public Map<String,Object> savePointsRelation(HttpServletRequest request,HttpServletResponse response,TPointsDic tPointsDic) throws Exception{
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

    @RequestMapping(value = "printFamily")
    public ModelAndView printFamily(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        model.addAttribute("consolesUser",consolesUser);
        return new ModelAndView("/consoles/printFamily");
    }

    @RequestMapping(value = "getMaxGen")
    @ResponseBody
    public Map<String,Object> getMaxGen(int familyId){
        Map<String,Object> result = new HashMap<>();

        int maxGen = familyService.getFamilyMaxGeneration(familyId);

        result.put("maxGen",maxGen);
        return result;
    }

    @RequestMapping(value = "deleteMoney")
    @ResponseBody
    public Map<String,Object> deleteMoney(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();

        int ii = 0;

        if(CommonUtil.parseInt(params.get("type")) == 1){
            ii += userService.deleteMoney(params);
        }else{
            ii += companyService.deleteMoney(params);
        }

        result.put("code",ii);
        result.put("msg","删除完成！");
        return result;
    }

    /**
     * 族谱打印页面
     * @param request
     * @param model
     * @param params
     * @return
     */
    @RequestMapping(value = "/print")
    public ModelAndView print(HttpServletRequest request, Model model, @RequestParam Map<String,Object> params){
        int familyId = CommonUtil.parseInt(params.get("printFamilyId"));

        TFamily tFamily = familyService.getFamilyFromId(CommonUtil.parseInt(familyId));
        model.addAttribute("tFamily",tFamily);

        model.addAttribute("familyId",params.get("printFamilyId"));
        model.addAttribute("beginGen",params.get("beginGen"));
        model.addAttribute("endGen",params.get("endGen"));
        model.addAttribute("isAddIntro",params.get("isAddIntro"));
        return new ModelAndView("/fronts/print");
    }

    /**
     * 打印页面族谱信息
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "peopleInfo4Print")
    @ResponseBody
    public Object peopleInfo4Print(@RequestParam Map<String,Object> params,HttpServletRequest request){

        //查询族人
        List<TPeople> listPeople = familyService.getPeopleList4Print(params);

        List<Map<String,Object>> list = new ArrayList<>();
        List<PeopleTree> list1 = new ArrayList<>();

        //根据族人Id查询配偶
        for (TPeople tPeople : listPeople) {
            Map<String,Object> map = new HashMap<>();
            PeopleTree pp = new PeopleTree();
            pp.setId(tPeople.getId() + "");
            pp.setpId(tPeople.getSuperiorId() + "");
            pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head2.ico");
            if(tPeople.getState() == 0){
                pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head_die.ico");
            }
            pp.setIsSupplement(tPeople.getIsSupplement() + "");
            pp.setOpen(true);
            pp.setName(tPeople.getName() + "(第" + tPeople.getGeneration() + "世)");
            pp.setPeopleStatus(tPeople.getPeopleStatus() + "");
            pp.setDieAddr(tPeople.getDieAddr());
//            map = CommonUtil.bean2Map(tPeople);
            String peopleId = tPeople.getId();
            List<TPeople> listMate = familyService.getMateList(peopleId);

            String mate = "";
            if(listMate != null && listMate.size() > 0){
                for(TPeople tPeople1 : listMate){
                    mate += "," + tPeople1.getName() + "--" + tPeople1.getDieAddr();
                }
                mate = mate.substring(1);
            }

            pp.setMateName(mate);
//            map.put("mateList",listMate);
//            list.add(map);
            list1.add(pp);
        }

        return list1;
    }

}
