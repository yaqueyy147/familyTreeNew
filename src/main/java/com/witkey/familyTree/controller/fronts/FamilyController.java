package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
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
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        return new ModelAndView("/fronts/personalIndex");
    }

    @RequestMapping(value = "/saveFamily")
    @ResponseBody
    public Map<String,Object> saveFamily(TFamily tFamily, MultipartFile imgFile, HttpServletRequest request){

        Map<String,Object> map = new HashMap<String,Object>();
        String path = request.getSession().getServletContext().getRealPath("/static/upload/familyImg");
        String finalPath = "";
        try {
            //上传图片
            finalPath = CommonUtil.uploadFile(path,imgFile);
            finalPath = finalPath.replace("\\","/");
            finalPath = finalPath.substring(finalPath.indexOf("/static"));
            //将图片路径添加到family
            tFamily.setPhotoUrl(finalPath);
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

    /**
     * 族谱树展示页面
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/viewFamily")
    public ModelAndView viewFamily(Model model, @RequestParam Map<String,Object> map){
        return new ModelAndView("/fronts/viewFamilyTree");
    }

    @RequestMapping(value = "/savePeople")
    @ResponseBody
    public Map<String,Object> savePeople(@RequestBody TPeople tPeople){
        Map<String,Object> map = new HashMap<String,Object>();
        familyService.savePeople(tPeople);
        map.put("msg","提交成功了");
        return map;
    }

}
