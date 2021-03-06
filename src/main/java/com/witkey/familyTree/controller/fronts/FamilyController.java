package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.dao.fronts.TPeopleDao;
import com.witkey.familyTree.domain.*;
import com.witkey.familyTree.service.consoles.LogService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.service.fronts.UserFrontService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;
    @Autowired
    private TPeopleDao tPeopleDao;

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
        params.put("userId",jsonUser.get("id"));
        params.put("nopage",1);
//        params.put("state",1);
//        params.put("tt",1);
//        params.put("province",jsonUser.get("province"));
//        params.put("city",jsonUser.get("city"));
//        params.put("district",jsonUser.get("district"));
//        List<TFamily> list = familyService.getFamilyList1(params);
        List<Map<String,Object>> list1 = familyService.getFamilyList1(params);
        //查询被收录的族谱
//        params.put("userName",jsonUser.get("userName"));
//        params.put("state","");

//        List<TFamily> list2 = familyService.getIncludeFamilyList(params);
//        list.addAll(list2);

//        List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
//        for(TFamily tFamily : list){
//            int peopleCount = familyService.getFamilyTotalPeopleNum(tFamily.getId(),-1);
//            int zspeopleCount = familyService.getFamilyTotalPeopleNum(tFamily.getId(),1);
//            Map<String,Object> map = new HashMap<String,Object>();
////            Map<String,Object> paramss = new HashMap<>();
////            paramss.put("familyId",tFamily.getId());
////            paramss.put("peopleType",1);
////            List<TPeople> peopleList = familyService.getPeopleList(paramss);
////            if(peopleList != null && peopleList.size() > 0)
////            {
////                peopleCount = peopleList.size();
////            }
//            map = CommonUtil.bean2Map(tFamily);
//            map.put("peopleCount",peopleCount);
//            map.put("zspeopleCount",zspeopleCount);
//            list1.add(map);
//        }
        model.addAttribute("familyList",list1);

        return new ModelAndView("/fronts/personalIndex");
    }

    /**
     * 补录族谱
     * @param model
     * @return
     */
    @RequestMapping(value = "/includeFamily")
    public ModelAndView includeFamily(Model model, HttpServletRequest request) throws UnsupportedEncodingException{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userName",jsonUser.get("userName"));
        params.put("userId",jsonUser.get("id"));
//        params.put("state",1);
//        params.put("tt",1);
        params.put("province",jsonUser.get("province"));
        params.put("city",jsonUser.get("city"));
        params.put("district",jsonUser.get("district"));
        params.put("onlyInclude",1);
        List<Map<String,Object>> list1 = familyService.getFamilyList2(params);

//        List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
//        for(TFamily tFamily : list){
//            int peopleCount = 0;
//            Map<String,Object> map = new HashMap<String,Object>();
//            Map<String,Object> paramss = new HashMap<>();
//            paramss.put("familyId",tFamily.getId());
//            paramss.put("peopleType",1);
//            List<TPeople> peopleList = familyService.getPeopleList(paramss);
//            if(peopleList != null && peopleList.size() > 0)
//            {
//                peopleCount = peopleList.size();
//            }
//            map = CommonUtil.bean2Map(tFamily);
//            map.put("peopleCount",peopleCount);
//            list1.add(map);
//        }
        model.addAttribute("familyList",list1);

        return new ModelAndView("/fronts/includeFamily");
    }

    @RequestMapping(value = "/personalInfo")
    public ModelAndView personalInfo(Model model,HttpServletRequest request,int xxx) throws UnsupportedEncodingException{

        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
//        TUserFront tUserFront = userFrontService.getUserInfoFromId(CommonUtil.parseInt(jsonUser.get("id")));
        TUser1 tUserFront = userService.getUserInfoFromId(CommonUtil.parseInt(jsonUser.get("id")));
        
        double totalMoney = familyService.getTotalUserMoney(CommonUtil.parseInt(jsonUser.get("id")));

        int totalIncludeNum = familyService.getTotalIncludeNum(CommonUtil.parseInt(jsonUser.get("id")));

        double totalPoints = 0.0;
        //充值积分
        List<TPointsDic> listP = familyService.getPointsRelation(2,1);
        if(listP != null && listP.size() > 0){
            totalPoints += totalMoney * (Math.ceil(listP.get(0).getPointsValue()/listP.get(0).getPointsNum()));
        }
        //录入人数积分
        listP = familyService.getPointsRelation(1,1);
        if(listP != null && listP.size() > 0){
            totalPoints += totalIncludeNum * (Math.ceil(listP.get(0).getPointsValue()/listP.get(0).getPointsNum()));
        }
        model.addAttribute("totalPoints",totalPoints);

        model.addAttribute("totalMoney",totalMoney);
        model.addAttribute("userInfo",jsonUser);
        model.addAttribute("xxx",xxx);
        model.addAttribute("tUserFront",JSONObject.fromObject(tUserFront));
        return new ModelAndView("/fronts/personalInfo");
    }

    @RequestMapping(value = "/saveFamily")
    @ResponseBody
    public Map<String,Object> saveFamily(TFamily tFamily, HttpServletRequest request){

        Map<String,Object> map = new HashMap<String,Object>();
        String msg = "创建成功";
        try {
            JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
            String userName = jsonUser.get("loginName") + "";
            tFamily.setState(1);
            //修改族谱
            if(tFamily.getId() > 0){
                TFamily tFamilyOld = familyService.getFamilyFromId(tFamily.getId());
                tFamily.setCreateTime(tFamilyOld.getCreateTime());
                tFamily.setCreateId(tFamilyOld.getCreateId());
                tFamily.setCreateMan(tFamilyOld.getCreateMan());
                tFamily.setSupplementFlag(tFamilyOld.getSupplementFlag());
                familyService.updateFamily(tFamily);
                msg = "修改成功";
                map.put("code",2);

                //记录日志
                logService.createLog(new TLog(2,userName,tFamily.toString(),tFamilyOld.toString()));
            }else{//新建族谱
//                String visitPassword = tFamily.getVisitPassword();
//                if(!CommonUtil.isBlank(visitPassword)){
//                    tFamily.setVisitPassword(CommonUtil.string2MD5(visitPassword));
//                }
                tFamily.setCreateMan(jsonUser.get("loginName")+"");
                tFamily.setCreateId(jsonUser.get("id") + "");
                tFamily.setCreateTime(new Date());
                LOGGER.debug("创建族谱-->" + tFamily);
                //保存族谱
                int familyId = familyService.createFamily(tFamily);
                //将返回的族谱ID设置到family
                tFamily.setId(familyId);
                if(CommonUtil.isBlank(tFamily.getPhotoUrl())){
                    tFamily.setPhotoUrl(BaseUtil.DEFAULT_FAMILY_IMG);
                }

                //将族谱添加为用户可操作
                userService.saveUserFamily(new TUserFamily(CommonUtil.parseInt(jsonUser.get("id")),familyId));

                map.put("code",1);
                //记录日志
                logService.createLog(new TLog(1,userName,tFamily.toString()));

            }

        } catch (Exception e){
            LOGGER.error("创建族谱出错-->",e);
            map.put("tFamily",tFamily);
            map.put("code",-1);
            map.put("msg","创建族谱出错！-->" + e.getMessage());
            return map;
        }
        map.put("tFamily",tFamily);

        map.put("msg",msg);
        return map;
    }

    @RequestMapping(value = "/getFamilyFromId")
    @ResponseBody
    public Map<String,Object> getFamilyFromId(int familyId){
        Map<String,Object> map = new HashMap<String,Object>();

        TFamily tFamily = familyService.getFamilyFromId(familyId);

        map.put("tFamily",tFamily);

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

        int maxGeneration = familyService.getFamilyMaxGeneration(CommonUtil.parseInt(familyId));

        model.addAttribute("maxGeneration",maxGeneration);

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
     * 族谱收录展示页面
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/viewFamily_include")
    public ModelAndView viewFamily_include(Model model, @RequestParam Map<String,Object> map, HttpServletRequest request) throws UnsupportedEncodingException{
        String familyId = map.get("familyId") + "";
        model.addAttribute("familyId",familyId);
        TFamily tFamily = familyService.getFamilyFromId(CommonUtil.parseInt(familyId));
        model.addAttribute("tFamily",tFamily);
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

        int maxGeneration = familyService.getFamilyMaxGeneration(CommonUtil.parseInt(familyId));

        model.addAttribute("maxGeneration",maxGeneration);

        //查询家族的收录情况
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("primaryFamilyId",familyId);
        List<TFamilyMerge> listMerge = familyService.getMergeList(map1);
        if(listMerge != null && listMerge.size() > 0){
            model.addAttribute("merge",listMerge.get(0));
        }

        return new ModelAndView("/fronts/viewFamilyTree_include");
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

        //个人积分排名
        Map<String,Object> params2 = new HashMap<String,Object>();
        params2.put("province",tFamily.getProvince());
        params2.put("city",tFamily.getCity());
        params2.put("district",tFamily.getDistrict());

        params2.put("type",1);
        params2.put("userType",1);

        List<Map<String,Object>> listPersonalPoints = familyService.getPointsRanking(params2);
        //公司积分排名
        params2.put("type",2);
        params2.put("rankfamilyId",familyId);
        List<Map<String,Object>> listCompanyPoints = familyService.getPointsRanking(params2);
        model.addAttribute("listPersonalPoints",listPersonalPoints);
        model.addAttribute("listCompanyPoints",listCompanyPoints);
        return new ModelAndView("/fronts/viewFamilyTree_visitor");
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
        params.put("orderBy"," order by t1.generation asc,t1.superior_id asc,t1.family_rank asc");

        System.out.print("******\n开始-->" + CommonUtil.getDateLong() + "::" + System.currentTimeMillis() + "\n********" );

//        params.put("isIndex",1);
        List<PeopleTree> listPeople = familyService.getPeopleList4view(params);
        System.out.print("******\n结束1-->" + CommonUtil.getDateLong() + "::" + System.currentTimeMillis() + "\n********" );
//        List<Map<String,Object>> list = new ArrayList<>();
//        List<PeopleTree> list1 = new ArrayList<>();
//
//        //根据族人Id查询配偶
//        for (TPeople tPeople : listPeople) {
//            Map<String,Object> map = new HashMap<>();
//            PeopleTree pp = new PeopleTree();
//            pp.setId(tPeople.getId() + "");
//            pp.setpId(tPeople.getSuperiorId() + "");
//            pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head2.ico");
//            if(tPeople.getState() == 0){
//                pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head_die.ico");
//            }
//            pp.setIsSupplement(tPeople.getIsSupplement() + "");
//            pp.setOpen(true);
//
//            pp.setName(tPeople.getName() + "(第" + tPeople.getGeneration() + "世)");
//            pp.setPeopleStatus(tPeople.getPeopleStatus() + "");
//            String peopleId = tPeople.getId();
//            List<TPeople> listMate = familyService.getMateList(peopleId);
//
//            String mate = "";
//            if(listMate != null && listMate.size() > 0){
//                for(TPeople tPeople1 : listMate){
//                    mate += "," + tPeople1.getName() + "--" + tPeople1.getId() + "--" + tPeople1.getPeopleStatus() + "--" + tPeople1.getIsSupplement();
//                }
//                mate = mate.substring(1);
//            }
//
//            pp.setMateName(mate);
//            list1.add(pp);
//        }
        System.out.print("******\n结束2-->" + CommonUtil.getDateLong() + "::" + System.currentTimeMillis() + "\n********" );
        return listPeople;
    }

    /**
     * 根据家族ID获取家族成员
     * @param params
     * @return
     */
    @RequestMapping(value = "/getPeopleList4Index")
    @ResponseBody
    public Object getPeopleList4Index(@RequestParam Map<String,Object> params, HttpServletRequest request){

        //查询族人
        params.put("peopleType",1);
        params.put("orderBy"," order by t1.generation asc,t1.superior_id asc,t1.family_rank asc");

        System.out.print("******\n开始-->" + CommonUtil.getDateLong() + "::" + System.currentTimeMillis() + "\n********" );
        List<PeopleTree> list1 = familyService.getPeopleListIndex(params);
////        params.put("isIndex",1);
//        List<TPeople> listPeople = familyService.getPeopleList(params);
//        System.out.print("******\n结束1-->" + CommonUtil.getDateLong() + "::" + System.currentTimeMillis() + "\n********" );
////        List<Map<String,Object>> list = new ArrayList<>();
//        List<PeopleTree> list1 = new ArrayList<>();
//
//        //根据族人Id查询配偶
//        for (TPeople tPeople : listPeople) {
//            Map<String,Object> map = new HashMap<>();
//            PeopleTree pp = new PeopleTree();
//            pp.setId(tPeople.getId() + "");
//            pp.setpId(tPeople.getSuperiorId() + "");
//            pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head2.ico");
//            if(tPeople.getState() == 0){
//                pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head_die.ico");
//            }
//            pp.setIsSupplement(tPeople.getIsSupplement() + "");
//            pp.setOpen(true);
//            pp.setPeopleStatus(tPeople.getPeopleStatus() + "");
//            if(CommonUtil.parseInt(tPeople.getIshide()) == 1){
//                pp.setName("***");
//            }else{
//                pp.setName(tPeople.getName() + "(第" + tPeople.getGeneration() + "世)");
//                String peopleId = tPeople.getId();
//                List<TPeople> listMate = familyService.getMateList(peopleId);
//
//                String mate = "";
//                if(listMate != null && listMate.size() > 0){
//                    for(TPeople tPeople1 : listMate){
//                        mate += "," + tPeople1.getName() + "--" + tPeople1.getId() + "--" + tPeople1.getPeopleStatus() + "--" + tPeople1.getIsSupplement();
//                    }
//                    mate = mate.substring(1);
//                }
//
//                pp.setMateName(mate);
//            }
//            list1.add(pp);
//        }
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
    public Map<String,Object> savePeople(HttpServletRequest request, TPeople tPeople,String birth_time,String die_time,String mateId){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
            String userName = jsonUser.get("userName") + "";

            if(!CommonUtil.isBlank(birth_time)){
                tPeople.setBirthTime(CommonUtil.ObjToDate(birth_time));
            }
            if(!CommonUtil.isBlank(die_time)){
                tPeople.setDieTime(CommonUtil.ObjToDate(die_time));
            }

            String msg = "保存成功";
            //修改成员信息
            if(!CommonUtil.isBlank(tPeople.getId()) && !"0".equals(tPeople.getId())){
                TPeople tPeopleOld = familyService.getPeopleInfo(tPeople.getId());
                tPeople.setCreateMan(tPeopleOld.getCreateMan());
                tPeople.setCreateId(tPeopleOld.getCreateId());
                tPeople.setCreateTime(tPeopleOld.getCreateTime());
                tPeople.setIsSupplement(tPeopleOld.getIsSupplement());
                tPeople.setUpdateMan(userName);
                tPeople.setUpdateTime(CommonUtil.getDateLong());

                familyService.updatePeople(tPeople);
                msg = "修改成功";
                //记录日志
                logService.createLog(new TLog(2,userName,tPeople.toString(),tPeopleOld.toString()));

            }else{//新建成员
                tPeople.setId(CommonUtil.uuid());
                tPeople.setCreateMan(jsonUser.get("userName")+"");
                tPeople.setCreateId(CommonUtil.parseInt(jsonUser.get("id")));
                tPeople.setCreateTime(CommonUtil.ObjToDate(CommonUtil.getDateLong()));
                tPeopleDao.save(tPeople);
//            String peopleId = familyService.savePeople(tPeople);
//            tPeople.setId(peopleId);
//            //添加积分
//            //获取积分对应关系
//            List<TPointsDic> listDic = familyService.getPointsRelation(1,1);
//            TUserPoints tUserPoints = new TUserPoints(CommonUtil.parseInt(jsonUser.get("id")),listDic.get(0).getPointsValue(),1);
//
//            familyService.setPoints(tUserPoints,1);

                //如果是添加配偶
                if(tPeople.getPeopleType() == 0){
                    //保存配偶信息
                    TMate tMate = new TMate(mateId,tPeople.getId(),"",tPeople.getMateType());
                    familyService.saveMateInfo(tMate);
                }
                //记录日志
                logService.createLog(new TLog(1,userName,tPeople.toString()));
            }
            map.put("tPeople",tPeople);
            map.put("msg",msg);
            map.put("code",1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 补录保存族人
     * @param tPeople
     * @param birth_time
     * @param die_time
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savePeople_include")
    @ResponseBody
    public Map<String,Object> savePeople_include(HttpServletRequest request, TPeople tPeople,String birth_time,String die_time,String mateId) throws Exception{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        String userName = jsonUser.get("userName") + "";
        Map<String,Object> map = new HashMap<String,Object>();
        if(!CommonUtil.isBlank(birth_time)){
            tPeople.setBirthTime(CommonUtil.ObjToDate(birth_time));
        }
        if(!CommonUtil.isBlank(die_time)){
            tPeople.setDieTime(CommonUtil.ObjToDate(die_time));
        }

        String msg = "保存成功";
        //修改成员信息
        if(!CommonUtil.isBlank(tPeople.getId()) && !"0".equals(tPeople.getId())){
            TPeople tPeopleOld = familyService.getPeopleInfo(tPeople.getId());

            tPeople.setCreateMan(tPeopleOld.getCreateMan());
            tPeople.setCreateId(tPeopleOld.getCreateId());
            tPeople.setCreateTime(tPeopleOld.getCreateTime());
            tPeople.setIsSupplement(tPeopleOld.getIsSupplement());
            tPeople.setUpdateMan(userName);
            tPeople.setUpdateTime(CommonUtil.getDateLong());
            tPeople.setIsSupplement(1);
            tPeople.setPeopleStatus(51);

            familyService.updatePeople(tPeople);
            msg = "修改成功";
            //记录日志
            logService.createLog(new TLog(2,userName,"补录修改族人-->" + tPeople.toString(),tPeopleOld.toString()));

        }else{//新建成员

            tPeople.setId(CommonUtil.uuid());
            tPeople.setIsSupplement(1);//设置该成员为补录成员
            tPeople.setPeopleStatus(5);//设置状态为补录未审核状态

            tPeople.setCreateMan(jsonUser.get("userName")+"");
            tPeople.setCreateId(CommonUtil.parseInt(jsonUser.get("id")));
            tPeople.setCreateTime(CommonUtil.ObjToDate(CommonUtil.getDateLong()));
//            int peopleId = familyService.savePeople(tPeople);
//            tPeople.setId(peopleId);
            tPeopleDao.save(tPeople);
//            //添加积分
//            //获取积分对应关系
//            List<TPointsDic> listDic = familyService.getPointsRelation(1,1);
//            TUserPoints tUserPoints = new TUserPoints(CommonUtil.parseInt(jsonUser.get("id")),listDic.get(0).getPointsValue(),1);
//
//            familyService.setPoints(tUserPoints,1);

            //如果是添加配偶
            if(tPeople.getPeopleType() == 0){
                //保存配偶信息
                TMate tMate = new TMate(mateId,tPeople.getId(),"",tPeople.getMateType());
                familyService.saveMateInfo(tMate);
            }
            //记录日志
            logService.createLog(new TLog(1,userName,"补录新增族人-->" + tPeople.toString()));
        }
        map.put("tPeople",tPeople);
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
    public Map<String,Object> deletePeople(String peopleId, int familyId, int peopleType, HttpServletRequest request) throws Exception{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        String userName = jsonUser.get("userName") + "";
        Map<String,Object> result = new HashMap<String,Object>();

        //如果是本族人，查询当前成员是否含有下一代本族人
//        if(peopleType == 1){
//            Map<String,Object> params = new HashMap<String,Object>();
//            params.put("fatherId",peopleId);
//            params.put("familyId",familyId);
//            params.put("superiorId",peopleId);
//            params.put("peopleType",1);
//            //如果有下一代人，不能删除
//            List<TPeople> list = familyService.getPeopleList(params);
//            if(list != null && list.size() > 0){
//                result.put("code",-1);
//                return result;
//            }
//        }


        try {
            TPeople tPeople = familyService.getPeopleInfo(peopleId);
//        tPeople.setPeopleStatus(9);
            int i = familyService.deletePeople(peopleId, tPeople.getFamilyId(), tPeople.getPeopleType());
//        int i = 0;
//        familyService.updatePeople(tPeople);
//        i ++ ;
            //记录日志
            logService.createLog(new TLog(3,userName,"删除族人-->" + tPeople.toString()));
            result.put("code",i);
        }catch (Exception e){
            e.printStackTrace();
            result.put("code",0);
        }

        return result;
    }

    /**
     * 补录族谱删除
     * @param peopleId
     * @param familyId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deletePeople_include")
    @ResponseBody
    public Map<String,Object> deletePeople_include(String peopleId, int familyId, HttpServletRequest request) throws Exception{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        String userName = jsonUser.get("userName") + "";
        Map<String,Object> result = new HashMap<String,Object>();

        //查询当前成员是否含有下一代人
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("fatherId",peopleId);
        params.put("superiorId",peopleId);
        params.put("familyId",familyId);
        //如果有下一代人，不能删除
        List<TPeople> list = familyService.getPeopleList(params);
        if(list != null && list.size() > 0){
            result.put("code",-1);
            return result;
        }

        TPeople tPeople = familyService.getPeopleInfo(peopleId);
        tPeople.setIsSupplement(1);
        tPeople.setPeopleStatus(52);
//        int i = familyService.deletePeople(peopleId);
        int i = 0;
        familyService.updatePeople(tPeople);
        i ++ ;
        result.put("code",i);
        //记录日志
        logService.createLog(new TLog(3,userName,"补录删除族人-->" + tPeople.toString()));
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
     * 打印页面族谱信息--前台打印暂未使用
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "peopleInfo4Print")
    @ResponseBody
    public Object peopleInfo4Print(@RequestParam Map<String,Object> params,HttpServletRequest request){

        //查询族人
//        List<TPeople> listPeople = familyService.getPeopleList4Print(params);

//        List<Map<String,Object>> list = new ArrayList<>();
        List<PeopleTree> list1 = familyService.getPeopleList4Print(params);

//        //根据族人Id查询配偶
//        for (TPeople tPeople : listPeople) {
//            Map<String,Object> map = new HashMap<>();
//            PeopleTree pp = new PeopleTree();
//            pp.setId(tPeople.getId() + "");
//            pp.setpId(tPeople.getSuperiorId() + "");
//            pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head2.ico");
//            if(tPeople.getState() == 0){
//                pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head_die.ico");
//            }
//            pp.setIsSupplement(tPeople.getIsSupplement() + "");
//            pp.setOpen(true);
//            pp.setName(tPeople.getName() + "(第" + tPeople.getGeneration() + "世)");
//            pp.setPeopleStatus(tPeople.getPeopleStatus() + "");
//            pp.setDieAddr(tPeople.getDieAddr());
////            map = CommonUtil.bean2Map(tPeople);
//            String peopleId = tPeople.getId();
//            List<TPeople> listMate = familyService.getMateList(peopleId);
//
//            String mate = "";
//            if(listMate != null && listMate.size() > 0){
//                for(TPeople tPeople1 : listMate){
//                    mate += "," + tPeople1.getName() + "--" + tPeople1.getDieAddr();
//                }
//                mate = mate.substring(1);
//            }
//
//            pp.setMateName(mate);
////            map.put("mateList",listMate);
////            list.add(map);
//            list1.add(pp);
//        }

        return list1;
    }

    /**
     * 申请成为志愿者
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/applyVolunteer")
    @ResponseBody
    public Map<String,Object> applyVolunteer(HttpServletRequest request) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

//        TVolunteer tVolunteer = new TVolunteer();
//        tVolunteer.setUserId(CommonUtil.parseInt(jsonUser.get("id")));
//        tVolunteer.setCreateMan(jsonUser.get("userName") + "");
//        tVolunteer.setCreateTime(CommonUtil.ObjToDate(CommonUtil.getDateLong()));
//        int i = userFrontService.applyVolunteer(CommonUtil.parseInt(jsonUser.get("id")));
        int i = userService.applyVolunteer(CommonUtil.parseInt(jsonUser.get("id")));
        map.put("code",i);
        map.put("msg","申请成功!");
        return map;
    }

    /**
     * 修改头像
     * @param request
     * @param photoPath
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyPhoto")
    @ResponseBody
    public Map<String,Object> modifyPhoto(HttpServletRequest request,String photoPath) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
//        int i = userFrontService.modifyPhoto(jsonUser.get("id") + "", photoPath, jsonUser.get("userType")+"");
        int i = userService.modifyPhoto(jsonUser.get("id") + "", photoPath, jsonUser.get("userType")+"");
        map.put("code",i);
        map.put("msg","修改成功!");
        return map;
    }

}
