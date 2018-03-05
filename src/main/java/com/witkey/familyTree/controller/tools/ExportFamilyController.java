package com.witkey.familyTree.controller.tools;

import com.witkey.familyTree.dao.fronts.TPeopleDao;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2018-03-03.
 */
@Controller
@RequestMapping(value = "output")
public class ExportFamilyController {

    @Autowired
    private FamilyService familyService;
    @Autowired
    private TPeopleDao tPeopleDao;

    @RequestMapping(value = "exportfamily")
    @ResponseBody
    public void exportfamily(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<String,Object>();
//        params.put("generation",1);
        params.put("peopleType",1);
        params.put("orderBy"," order by t1.generation asc,t1.family_rank asc");
        BufferedOutputStream bos = null;
        try {
            String familyname = params.get("familyname") + "";
            familyname = new String(familyname.getBytes("UTF-8"),"ISO-8859-1");
            response.setContentType("application/txt");
            response.setHeader("content-disposition", "attachment;filename=" + familyname + ".txt");
            bos = new BufferedOutputStream(response.getOutputStream());
//            List<String> peopleInfo = this.createPeopleInfo4Export(params);
//
//            TxtUtil.exportTxt(new File("D:/testtxt1.txt"),peopleInfo,null);

            String peopleInfo = this.createPeopleInfo4ExportStr(params);
            bos.write(peopleInfo.getBytes());

//            map.put("peopleInfo",peopleInfo);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<String> createPeopleInfo4Export(Map<String,Object> params){
        List<String> result = new ArrayList<String>();
        try{
            int i = 0;
            //查询族人
            List<Map<String,Object>> listPeople = familyService.getPeopleList4Export(params);
            if(listPeople != null && listPeople.size() > 0){
                //先循环创写当前代的族人信息
                for(Map<String,Object> pp : listPeople){
                    String generation = pp.get("generation") + "";
                    String name = pp.get("name") + "";
                    String cname = pp.get("c_name") + "";
                    String special_remark = pp.get("special_remark") + "";
                    int familyrank = CommonUtil.parseInt(pp.get("family_rank"));
//                    try {
//                        familyrank = CommonUtil.parseInt(pp.get("family_rank"));
//                    }catch (Exception e){
//                        familyrank = 0;
//                    }
                    String ppinf = "【" + generation + "】" + name + "，";
                    if(CommonUtil.parseInt(generation) == 1){
                        ppinf += "始祖，";
                    }else{
                        String fathername = pp.get("fathername") + "";
                        ppinf += fathername + "公" + getfamilyrankdesc(familyrank);
                    }
                    if(!CommonUtil.isBlank(cname)){
                        ppinf += "字" + cname + "，";
                    }
                    ppinf += "|" + special_remark;
                    result.add(ppinf);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private String createPeopleInfo4ExportStr(Map<String,Object> params){
        StringBuilder result = new StringBuilder();
        try{
            int i = 0;
            //查询族人
            List<Map<String,Object>> listPeople = familyService.getPeopleList4Export(params);
            if(listPeople != null && listPeople.size() > 0){
                //先循环创写当前代的族人信息
                for(Map<String,Object> pp : listPeople){
                    String generation = pp.get("generation") + "";
                    String name = pp.get("name") + "";
                    String cname = pp.get("c_name") + "";
                    String special_remark = pp.get("special_remark") + "";
                    int familyrank = CommonUtil.parseInt(pp.get("family_rank"));
//                    try {
//                        familyrank = CommonUtil.parseInt(pp.get("family_rank"));
//                    }catch (Exception e){
//                        familyrank = 0;
//                    }
                    String ppinf = "【" + generation + "】" + name + "，";
                    if(CommonUtil.parseInt(generation) == 1){
                        ppinf += "始祖，";
                    }else{
                        String fathername = pp.get("fathername") + "";
                        ppinf += fathername + "公" + getfamilyrankdesc(familyrank);
                    }
                    if(!CommonUtil.isBlank(cname)){
                        ppinf += "字" + cname + "，";
                    }
                    ppinf += "|" + special_remark;
                    result.append(ppinf + "\r\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    private String getfamilyrankdesc(int familyrank){
        String desc = "";
        if(familyrank == 0){
            desc = "长子";
        }else if(familyrank == 1){
            desc = "次子";
        }else if(familyrank >= 2){
            desc = CommonUtil.toChinese((familyrank + 1) + "") + "子";
        }
        return desc;
    }
}