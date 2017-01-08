package com.witkey.familyTree.controller.fronts;

import com.witkey.familyTree.util.CommonUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suyx on 2017/1/7.
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    @RequestMapping(value = "uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile uploadFile, String targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = request.getSession().getServletContext().getRealPath(targetFile);
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
