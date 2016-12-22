package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Map<String,Object> saveFamily(TFamily tFamily, MultipartFile imgFile, HttpServletRequest request) throws IOException{

        String path = request.getSession().getServletContext().getRealPath("upload/familyImg");
        String fileName = imgFile.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString() + prefix;
        // System.out.println("保存路径 " + path);
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        imgFile.transferTo(targetFile);

        tFamily.setPhotoUrl(path + "/" + fileName);

        familyService.createFamily(tFamily);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("msg","进来了");
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
