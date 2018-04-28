package com.witkey.familyTree.controller.tools;

import com.witkey.familyTree.dao.fronts.TPeopleDao;
import com.witkey.familyTree.exportexcel.ExcelUtils;
import com.witkey.familyTree.exportexcel.JsGridReportBase;
import com.witkey.familyTree.exportexcel.TableData;
import com.witkey.familyTree.service.fronts.FamilyService;
import com.witkey.familyTree.util.CommonUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "exportfamily")
    @ResponseBody
    public void exportfamily(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<String,Object>();
//        params.put("generation",1);
        params.put("peopleType",1);
        params.put("orderBy"," order by t1.generation asc,t1.superior_id asc,t1.family_rank asc");
        BufferedOutputStream bos = null;
        try {
            String familyname = params.get("familyname") + "";
            familyname = new String(familyname.getBytes("UTF-8"),"ISO-8859-1");
            response.setContentType("application/txt");
            response.setHeader("content-disposition", "attachment;filename=" + familyname + ".txt");
            bos = new BufferedOutputStream(response.getOutputStream());

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

    @RequestMapping(value = "exportfamilytoxls")
    @ResponseBody
    public void exportfamilytoxls(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<String,Object>();
        params.put("peopleType",1);
        params.put("orderBy"," order by t1.generation asc,t1.superior_id asc,t1.family_rank asc");
        try {
            String familyname = params.get("familyname") + "";
            String[] hearders = new String[]{"id","第几世","父亲id","姓名","性别","排行","出生时间","出生地","死亡时间","卒葬地","是否在世","特殊说明"};
            String[] fields = new String[]{"id","generation","superior_id","name","sex","family_rank","birth_time","birth_addr","die_time","die_addr","state","special_remark"};

            List<Map<String,Object>> listPeople = familyService.getPeopleList4Export(params);
            TableData td = ExcelUtils.createTableData(listPeople, ExcelUtils.createTableHeader(hearders), fields);
            JsGridReportBase report = new JsGridReportBase(request, response);
            report.exportToExcel(familyname, "", td);
        }catch (Exception e){
            e.printStackTrace();
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
            String repetename = this.getfamilyrepetename(params.get("familyId") + "");
            Map<String,String> repeteNo = new HashMap<String,String>();
            List<Map<String,Object>> listPeople = familyService.getPeopleList4Export(params);
            if(listPeople != null && listPeople.size() > 0){
                //先循环创写当前代的族人信息
                for(Map<String,Object> pp : listPeople){
                    String generation = pp.get("generation") + "";
                    String name = pp.get("name") + "";

                    if(repetename.indexOf(name) > -1){
                        String serial = repeteNo.get(name);
                        serial = (CommonUtil.parseInt(serial) + 1) + "";
                        if(serial.trim().length() < 2){
                            serial = CommonUtil.leftConcat(serial,'0',2);
                        }
                        repeteNo.put(name,serial);
                        name = name + serial;
                    }

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
//                        String fathername = pp.get("fathername") + "";
                        String fathername = repeteNo.get(pp.get("superior_id") + "");
                        ppinf += fathername + "公" + getfamilyrankdesc(familyrank);
                    }
                    if(!CommonUtil.isBlank(cname)){
                        ppinf += "字" + cname + "，";
                    }
                    ppinf += "|" + special_remark;
                    result.append(ppinf + "\r\n");
                    repeteNo.put(pp.get("id") + "",name);
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

    private String getfamilyrepetename(String familyid){
        String result = "";
        String sql = "select name from t_people";
        sql += " where family_id=? and people_type=1 and people_status<>9 GROUP BY name HAVING COUNT(id)>1";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,familyid);
        if(list != null && list.size() > 0){
            for(Map<String,Object> map : list){
                result += "," + map.get("name");
            }
            result = result.substring(1);
        }

        return result;
    }

}