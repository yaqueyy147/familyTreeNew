package com.witkey.familyTree.controller.consoles;

import com.witkey.familyTree.dao.fronts.TPeopleDao;
import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.service.consoles.ConsoleService;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.PeopleTree;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/4/11 0011.
 */
@Controller
@RequestMapping(value = "/consoles")
public class JointController {

    @Autowired
    private ConsoleService consoleService;
    @Autowired
    private FamilyService familyService;

    @Autowired
    private TPeopleDao tPeopleDao;

    /**
     * 合并族谱页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/familyJoint")
    public ModelAndView merge(Model model,String familyId){
        model.addAttribute("mianFamilyId",familyId);
        TFamily tFamily = familyService.getFamilyFromId(CommonUtil.parseInt(familyId));
        model.addAttribute("mianFamily",tFamily);
        int maxGen = familyService.getFamilyMaxGeneration(tFamily.getId());
        model.addAttribute("maxGen", maxGen);
        return new ModelAndView("/consoles/familyJoint");
    }

    /**
     * 根据家族ID获取家族成员
     * @param params
     * @return
     */
    @RequestMapping(value = "/getPeopleListJoint")
    @ResponseBody
    public Object getPeopleListJoint(@RequestParam Map<String,Object> params, HttpServletRequest request){

        //查询族人
        params.put("peopleType",1);
        params.put("orderBy"," order by generation asc,family_rank asc");
//        List<TPeople> listPeople = familyService.getPeopleList(params);
        List<PeopleTree> list1 = familyService.getPeopleList4view(params);

//        //根据族人Id查询配偶
//        for (TPeople tPeople : listPeople) {
//            Map<String,Object> map = new HashMap<>();
//            PeopleTree pp = new PeopleTree();
//            pp.setNocheck(false);
//            pp.setChkDisabled(false);
//            pp.setId(tPeople.getId() + "");
//            pp.setpId(tPeople.getSuperiorId() + "");
//            pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head2.ico");
//            if(tPeople.getState() == 0){
//                pp.setIcon(request.getContextPath() + "/static/jquery/ztree/icon/head_die.ico");
//            }
//            pp.setIsSupplement(tPeople.getIsSupplement() + "");
//            pp.setOpen(true);
//            pp.setIsdie(tPeople.getState() + "");
//            pp.setName(tPeople.getName() + "(第" + tPeople.getGeneration() + "世)");
//            pp.setPeopleStatus(tPeople.getPeopleStatus() + "");
//            pp.setGeneration(tPeople.getGeneration() + "");
//            String peopleId = tPeople.getId();
//            List<TPeople> listMate = familyService.getMateList(peopleId);
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
        return list1;
    }

    /**
     * 合并族谱选择要合并的家族列表
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "targetfamily")
    @ResponseBody
    public Object targetfamily(@RequestParam Map<String,Object> params, HttpServletRequest request){

        List<TFamily> list1 = familyService.getFamilyListJoint(params);
        return list1;
    }

    /**
     * 确认合并
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "affirmjoint")
    @ResponseBody
    public Object affirmjoint(@RequestParam Map<String,Object> params, HttpServletRequest request){
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("code",1);
        try {
            String targetpeopleids = params.get("targetpeopleids") + "";
            String[] targetpeopleidarr = targetpeopleids.split(",");

            int familyId = CommonUtil.parseInt(params.get("familyId"));
            String mainpeopleid = params.get("mainpeopleid") + "";
            if(CommonUtil.isBlank(mainpeopleid)){
                mainpeopleid = "0";
            }
            int mainpeoplegeneration = CommonUtil.parseInt(params.get("mainpeoplegeneration"));
            Map<String,String> ppidtemp = new HashMap<String,String>();
            for(int i=0;i<targetpeopleidarr.length;i++){
                String ppid = targetpeopleidarr[i];
                TPeople tt = tPeopleDao.get(ppid);
                TPeople temptt = new TPeople();
                BeanUtils.copyProperties(tt,temptt);
                if(temptt != null){
                    temptt.setId(CommonUtil.uuid());
                    temptt.setFamilyId(familyId);
                    if(i == 0){
                        temptt.setSuperiorId(mainpeopleid);
                        temptt.setFatherId(mainpeopleid);
                        temptt.setGeneration(mainpeoplegeneration + 1);
                    }else{
                        String temppp = ppidtemp.get(tt.getSuperiorId());

                        temptt.setFatherId(temppp.split("::")[0]);
                        temptt.setSuperiorId(temppp.split("::")[0]);
                        temptt.setGeneration(CommonUtil.parseInt(temppp.split("::")[1]) + 1);
                    }
                    tPeopleDao.save(temptt);
                    ppidtemp.put(ppid,temptt.getId() + "::" + temptt.getGeneration());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("code",0);
        }

        return result;
    }
}
