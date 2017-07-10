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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suyx on 2017/1/7.
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    /**
     * 上传图片
     * @param uploadFile
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile uploadFile, String targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {

//        String path = request.getSession().getServletContext().getRealPath("");//项目根目录
//        String requestUrl = request.getRequestURL().toString();//请求地址
//        String projectName = request.getContextPath();//项目名称
//        path = path.substring(0,path.indexOf("\\webapps") + 8) + "\\test\\upload";//上传图片的地址
        String path = "/usr/java/ImgFile" + targetFile;
        String filePath = CommonUtil.uploadFile(path, uploadFile);//上传图片
        filePath = filePath.replace("\\","/");//将路径中的\替换为/
//        filePath = filePath.substring(filePath.indexOf("/static"));

//        String resultPath = requestUrl.substring(0,requestUrl.indexOf(projectName)) + filePath.substring(filePath.indexOf("/test"));//要返回的图片路径
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("msg","进来了!");
        map.put("filePath","/ImgFile" + filePath.substring(filePath.indexOf("/upload")));
        String resultStr = JSONObject.fromObject(map).toString();
        return resultStr;
    }

    /**
     * 企业简介页面富文本编辑器上传图片
     * @param upload
     * @param CKEditorFuncNum
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/uploadImg2")
    public void uploadImg2(MultipartFile upload, String CKEditorFuncNum, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = "/usr/java/ImgFile/upload/companyImg";
        String filePath = CommonUtil.uploadFile(path, upload);//上传图片
        filePath = filePath.replace("\\","/");//将路径中的\替换为/

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("msg","进来了!");
        map.put("filePath","/ImgFile" + filePath.substring(filePath.indexOf("/upload")));
        String resultStr = JSONObject.fromObject(map).toString();

        PrintWriter out = response.getWriter();
        // 返回"图像"选项卡并显示图片  request.getContextPath()为web项目名
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum
                + ",'/ImgFile" + filePath.substring(filePath.indexOf("/upload")) + "','')");
        out.println("</script>");
        out.flush();
        out.close();
//        return resultStr;
    }
}
