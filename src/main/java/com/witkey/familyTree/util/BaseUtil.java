package com.witkey.familyTree.util;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by suyx on 2017/1/5 0005.
 */
public class BaseUtil {

    public static final String DEFAULT_FAMILY_IMG = "/ImgFile/images/defaultFamily.jpg";
    public static final String DEFAULT_MAN_IMG = "/ImgFile/images/defaultMan.png";

    public static boolean validateUserInfo(HttpServletResponse response, JSONObject jsonObject, String contextPath, int type) throws Exception{

        if(CommonUtil.isBlank(jsonObject)){

            //后台
            if(type == 1){
                response.sendRedirect(contextPath + "/consoles/logout");
                return false;
            }
            response.sendRedirect(contextPath + "/sign/");
            return false;
        }
        return true;
    }

    public static boolean validateUserInfo(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject, int type) throws Exception{

        if(CommonUtil.isBlank(jsonObject)){

            //后台
            if(type == 1){
                response.sendRedirect(request.getContextPath() + "/consoles/logout");

                return false;
            }
            response.sendRedirect(request.getContextPath() + "/sign/");
            return false;
        }
        return true;
    }
}
