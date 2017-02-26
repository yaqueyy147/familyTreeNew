package com.witkey.familyTree.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by suyx on 2016/6/20.
 */
public class CookieUtil {

    private static final Logger LOGGER = Logger.getLogger(CookieUtil.class);

    /**
     * 添加cookie
     * @param cookieName
     * @param cookieContent
     * @param response
     */
    public static void addCookie(String cookieName, String cookieContent, HttpServletResponse response) throws UnsupportedEncodingException{
        //encode内容，解决存储中文的问题
        cookieContent = URLEncoder.encode(cookieContent,"UTF-8");
        //新建一个cookie设置cookie键值
        Cookie cookie = new Cookie(cookieName,cookieContent);
        //设置cookie有效时间  -1 为关闭浏览器则失效
        cookie.setMaxAge(-1);
        cookie.setDomain("localhost");
        cookie.setPath("/");
//        //设置secure安全性为true，但是设置为true后http网站不能使用cookie，这个问题待解决
        cookie.setSecure(false);
        response.setCharacterEncoding("UTF-8");
        //response中添加cookie
        response.addCookie(cookie);
    }

    /**
     * 根据cookie名获取cookie值
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValueFromName(HttpServletRequest request, String cookieName) throws UnsupportedEncodingException{

        //request获取cookie数组
        Cookie[] cookies = request.getCookies();
        if(CommonUtil.isBlank(cookies)){
            return "";
        }
        //遍历cookie数组
        for (Cookie cookie : cookies){

            //如果cookie名跟传入的名称一致，则返回当前cookie值
            if(cookie.getName().equals(cookieName))
            {
                String cookieValue = cookie.getValue();
                //decode cookie值，解决取值中文的问题
                if(CommonUtil.isBlank(cookieValue)){
                    continue;
                }
                cookieValue = URLDecoder.decode(cookieValue,"UTF-8");
                return cookieValue;
            }
        }

        return "";
    }

    /**
     * 获取cookie值并转换成JSONObject返回
     * @param request
     * @param cookieName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static JSONObject cookieValueToJsonObject(HttpServletRequest request, String cookieName) throws UnsupportedEncodingException{
        String cookie = getCookieValueFromName(request,cookieName);
        if(CommonUtil.isBlank(cookie))
        {
            return null;
        }
        return JSONObject.fromObject(getCookieValueFromName(request,cookieName));
    }

    /**
     * 获取cookie值并转换成JSONArray返回
     * @param request
     * @param cookieName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static JSONArray cookieValueToJsonArray(HttpServletRequest request, String cookieName) throws UnsupportedEncodingException{
        String cookie = getCookieValueFromName(request,cookieName);
        if(CommonUtil.isBlank(cookie))
        {
            return null;
        }
        return JSONArray.fromObject(cookie);
    }

    /**
     * 通过cookie名销毁cookie
     * @param response
     * @param request
     * @param cookieName
     */
    public static void destroyCookieFromName(HttpServletResponse response, HttpServletRequest request, String cookieName) throws Exception{
        //创建一个名为cookieName的cookie
        Cookie cookie = new Cookie(cookieName,getCookieValueFromName(request,cookieName));
        //设置生命周期为0  即表示销毁该名称的cookie
        cookie.setMaxAge(0);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        //response添加此cookie 即表示销毁该名称的cookie
        response.addCookie(cookie);
    }

    public static void destroyCookies(HttpServletResponse response, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        //遍历设置cookie的什么周期为0  添加到response则表示销毁
        for (Cookie cookie : cookies){
            cookie.setMaxAge(0);
            cookie.setDomain("localhost");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

    }

}
