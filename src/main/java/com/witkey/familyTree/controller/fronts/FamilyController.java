package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
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
import java.util.*;

/**
 * Created by suyx on 2016/12/20 0020.
 */
@Controller
@RequestMapping(value = "/family")
public class FamilyController {

    private static final String DEFAULT_FAMILY_IMG = "/static/images/defaultFamily.jpg";

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

        List<TFamily> list = familyService.getFamilyList("ceshi123");

        for (TFamily tFamily : list) {
            String photoUrl = tFamily.getPhotoUrl();
            if(CommonUtil.isBlank(photoUrl)){
                tFamily.setPhotoUrl(DEFAULT_FAMILY_IMG);
            }
            else if(!CommonUtil.isFile(photoUrl)){
                tFamily.setPhotoUrl(DEFAULT_FAMILY_IMG);
            }
        }

        model.addAttribute("familyList",list);

        return new ModelAndView("/fronts/personalIndex");
    }

    @RequestMapping(value = "/saveFamilyWithImg")
    @ResponseBody
    public Map<String,Object> saveFamilyWithImg(TFamily tFamily, MultipartFile imgFile, HttpServletRequest request){

        Map<String,Object> map = new HashMap<String,Object>();
        String path = request.getSession().getServletContext().getRealPath("/static/upload/familyImg");
        String finalPath = "";
        try {
            //如果图片不为空，上传图片
            if(!imgFile.isEmpty()) {
                finalPath = CommonUtil.uploadFile(path, imgFile);
                finalPath = finalPath.replace("\\", "/");
                finalPath = finalPath.substring(finalPath.indexOf("/static"));
            }
            //将图片路径添加到family
            tFamily.setPhotoUrl(finalPath);
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

        } catch (IOException e){
            LOGGER.error("上传图片错误-->",e);
            map.put("tFamily",tFamily);
            map.put("code",-2);
            map.put("msg","上传图片出错！-->" + e.getMessage());
            return map;
        } catch (DataAccessException de){
            LOGGER.error("创建族谱出错-->",de);
            map.put("tFamily",tFamily);
            map.put("code",-1);
            map.put("msg","创建族谱出错！-->" + de.getMessage());
            return map;
        }
        map.put("tFamily",tFamily);
        map.put("code",1);
        map.put("msg","创建成功！");
        return map;
    }

    @RequestMapping(value = "/saveFamilyNoImg")
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
            tFamily.setPhotoUrl(DEFAULT_FAMILY_IMG);

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
    public Map<String,Object> savePeople(TPeople tPeople,String birth_time,String die_time) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        if(!CommonUtil.isBlank(birth_time)){
            tPeople.setBirthTime(CommonUtil.ObjToDate(birth_time));
        }
        if(!CommonUtil.isBlank(die_time)){
            tPeople.setBirthTime(CommonUtil.ObjToDate(die_time));
        }
        familyService.savePeople(tPeople);
        map.put("msg","保存成功!");
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

    @RequestMapping(value = "uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response) throws IOException{

        String path = request.getSession().getServletContext().getRealPath("/static/uploads");
        String filePath = CommonUtil.uploadFile(path, uploadFile);
        filePath = filePath.replace("\\","/");

        filePath = filePath.substring(filePath.indexOf("/static"));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("msg","进来了!");
        map.put("filePath",filePath);
        String resultStr = JSONObject.fromObject(map).toString();
        return resultStr;
    }


}
