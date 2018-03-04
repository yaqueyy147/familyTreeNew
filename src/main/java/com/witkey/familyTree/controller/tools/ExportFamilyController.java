package com.witkey.familyTree.controller.tools;

import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "exportfamily")
    @ResponseBody
    public Map<String,Object> exportfamily(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<String,Object>();
        params.put("generation",1);
        params.put("peopleType",1);
        params.put("orderBy"," order by family_rank asc");
        try {
            List<String> peopleInfo = this.createPeopleInfo4Export(params);
            map.put("peopleInfo",peopleInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    private List<String> createPeopleInfo4Export(Map<String,Object> params){
        List<String> result = new ArrayList<String>();
        String fathername = params.get("fathername") + "";
        System.out.println("***->" + params.get("generation"));
        try{
            int i = 0;
            //查询族人
            List<TPeople> listPeople = familyService.getPeopleList(params);
            if(listPeople != null && listPeople.size() > 0){
                //先循环创写当前代的族人信息
                for(TPeople pp : listPeople){
                    int familyrank = 0;
                    try {
                        familyrank = pp.getFamilyRank();
                    }catch (Exception e){
                        familyrank = 0;
                    }
                    String ppinf = "【" + pp.getGeneration() + "】" + pp.getName() + "，";
                    if(pp.getGeneration() == 1){
                        ppinf += "始祖，";
                    }else{
                        ppinf += fathername + "公" + getfamilyrankdesc(familyrank);
                    }
                    if(!CommonUtil.isBlank(pp.getcName())){
                        ppinf += "字" + pp.getcName() + "，";
                    }
                    ppinf += "|" + pp.getSpecialRemark();
                    result.add(ppinf);

                }
                //然后递归循环查询后代的信息
                for(TPeople pp : listPeople){
                    params.put("fathername",pp.getName());
                    params.put("generation",pp.getGeneration() + 1);
                    List<String> children = this.createPeopleInfo4Export(params);
                    result.addAll(children);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private String getfamilyrankdesc(int familyrank){
        String desc = "";
        if(familyrank == 0){
            desc = "长子";
        }else if(familyrank == 1){
            desc = "次子";
        }else if(familyrank >= 2){
            desc = CommonUtil.toChinese((familyrank + 1) + "");
        }
        return desc;
    }
}