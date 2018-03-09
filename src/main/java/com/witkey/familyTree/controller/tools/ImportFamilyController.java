package com.witkey.familyTree.controller.tools;

import com.witkey.familyTree.dao.fronts.TMateDao;
import com.witkey.familyTree.dao.fronts.TPeopleDao;
import com.witkey.familyTree.domain.TMate;
import com.witkey.familyTree.domain.TPeople;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 导入族人
 * Created by syx on 2018/3/9 0009.
 */
@Controller
@RequestMapping(value = "/import")
public class ImportFamilyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportFamilyController.class);

    @Autowired
    private TPeopleDao tPeopleDao;
    @Autowired
    private TMateDao tMateDao;

    @RequestMapping(value = "/work")
    @ResponseBody
    public Object improtwork(@RequestParam("file") MultipartFile file, @RequestParam Map<String,Object> params, HttpServletRequest request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            String importparentid = params.get("importparentid") + "";
            importparentid = CommonUtil.isBlank(importparentid)?"0":importparentid;
            String familyid =params.get("importfamilyid") + "";
            int importparentgeneration =CommonUtil.parseInt(params.get("importparentgeneration"));
            List<String[]> dataList = ExcelUtil.getDataFromExcelFile(1, 0, 0, "", file, 27);
            Map<String,String> temp = new HashMap<String,String>();
            if(dataList != null && dataList.size() > 0){
                for(int k=0;k<dataList.size();k++){
                    String[] arr = dataList.get(k);
                    TPeople tPeople = new TPeople();
                    TMate tMate = null;
                    tPeople.setId(CommonUtil.uuid());
                    tPeople.setFamilyId(CommonUtil.parseInt(familyid));
                    int gg = 0;
                    String ffstr = temp.get(arr[2]);
                    String ffid = importparentid;
                    if(!CommonUtil.isBlank(ffstr)){
                        ffid = ffstr.split("::")[0];
                        gg = CommonUtil.parseInt(ffstr.split("::")[1]) + 1;
                    }else{
                        gg = importparentgeneration + 1;
                    }

                    tPeople.setGeneration(gg);
                    tPeople.setFatherId(ffid);
                    tPeople.setSuperiorId(ffid);
                    tPeople.setName(CommonUtil.isBlank(arr[3])?"":arr[3]);
                    tPeople.setIdCard(CommonUtil.isBlank(arr[4])?"":arr[4]);
                    tPeople.setSex(this.parsesex(arr[5]));
                    tPeople.setFamilyGeneration(CommonUtil.parseInt(arr[6]));
                    tPeople.setFamilyRank(CommonUtil.parseInt(arr[7]));
                    tPeople.setGenerationActor(CommonUtil.isBlank(arr[8])?"":arr[8]);
                    tPeople.setNationality(CommonUtil.isBlank(arr[9])?"":arr[9]);
                    tPeople.setNation(CommonUtil.isBlank(arr[10])?"":arr[10]);
                    tPeople.setEducation(CommonUtil.isBlank(arr[11])?"":arr[11]);
                    tPeople.setJob(CommonUtil.isBlank(arr[12])?"":arr[12]);
                    tPeople.setPhone(CommonUtil.isBlank(arr[13])?"":arr[13]);
                    tPeople.setEmail(CommonUtil.isBlank(arr[14])?"":arr[14]);

                    Date birthtime = null;
                    try {
                        birthtime = CommonUtil.ObjToDate(arr[15]);
                    }catch (Exception e){
                        birthtime = null;
                    }

                    tPeople.setBirthTime(birthtime);
                    tPeople.setBirthAddr(CommonUtil.isBlank(arr[16])?"":arr[16]);
                    Date dietime = null;
                    try {
                        dietime = CommonUtil.ObjToDate(arr[17]);
                    }catch (Exception e){
                        dietime = null;
                    }
                    tPeople.setDieTime(dietime);
                    tPeople.setDieAddr(CommonUtil.isBlank(arr[18])?"":arr[18]);
                    tPeople.setLiveAddr(CommonUtil.isBlank(arr[19])?"":arr[19]);
                    tPeople.setState(CommonUtil.parseInt(arr[20]));
                    tPeople.setXing(CommonUtil.isBlank(arr[21])?"":arr[21]);
                    tPeople.setArtName(CommonUtil.isBlank(arr[22])?"":arr[22]);
                    tPeople.setcName(CommonUtil.isBlank(arr[23])?"":arr[23]);

                    tPeople.setCreateTime(new Date());
                    tPeople.setPeopleStatus(1);

                    int ismate = CommonUtil.parseInt(arr[24]);

                    //如果是属于配偶
                    int peopletype = 1;
                    if(ismate == 1){
                        peopletype = 0;
                        int matetype = 1;
                        if(this.parsesex(arr[5]) == 1){
                            matetype = 2;
                        }
                        tPeople.setMateType(matetype);
                        //添加一条配偶信息，先从temp中获取配偶id
                        String ppid = temp.get(arr[25]);
                        tMate = new TMate(ppid,tPeople.getId(),"",matetype);
                    }
                    tPeople.setPeopleType(peopletype);
                    tPeopleDao.save(tPeople);
                    if(!CommonUtil.isBlank(tMate)){
                        tMateDao.create(tMate);
                    }

                    temp.put(arr[1],tPeople.getId() + "::" + tPeople.getGeneration());
                }
            }
            result.put("code",1);
            result.put("message","成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put("code",0);
            result.put("message","失败");
        }

        return result;
    }

    private int parsesex(String sex){
        int result = 0;
        if("男".equals(sex)){
            result = 1;
        }
        return result;
    }

}