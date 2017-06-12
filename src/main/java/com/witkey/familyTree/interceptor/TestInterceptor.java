package com.witkey.familyTree.interceptor;

import com.witkey.familyTree.domain.TCompanySponsor;
import com.witkey.familyTree.domain.TUser1;
import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.exception.UnLoginException;
import com.witkey.familyTree.service.fronts.CompanyService;
import com.witkey.familyTree.service.fronts.UserService;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/25.
 */
public class TestInterceptor implements HandlerInterceptor {

    private PathMatcher pathMatcher = new AntPathMatcher();
    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    private List<String> excludeMappings;
    private static JSONObject jsonUser;
    private static String currentUrl;

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;

    public void setExcludeMappings(List<String> excludeMappings) {
        this.excludeMappings = excludeMappings;
    }

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        currentUrl = urlPathHelper.getLookupPathForRequest(httpServletRequest);
        //查找到，表示不需要权限控制
        if(!StringUtils.isEmpty(lookupGroup(currentUrl))){
            return Boolean.TRUE;
        }
//        System.out.println("*********\n当前路径-->" + currentUrl + "\n************");
        //从cookie中读取用户信息
        int type = 1;
        if(isConsoles(currentUrl)){//后台用户
            jsonUser = CookieUtil.cookieValueToJsonObject(httpServletRequest,"consoleUserInfo");
            type = 1;
        }else{//前台用户
            jsonUser = CookieUtil.cookieValueToJsonObject(httpServletRequest,"userInfo");
            type = 2;
        }

        if(!CommonUtil.isBlank(jsonUser) && jsonUser.size() > 0){
            return true;
        }
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/sign/out?type=" + type);
        return false;
//        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String url = urlPathHelper.getLookupPathForRequest(httpServletRequest);
        //从cookie获取用户信息
//        TUserFront tUserFront = (TUserFront)JSONObject.toBean(jsonUser,TUserFront.class);
//        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(httpServletRequest,"userInfo");
        Map<String,Object> map = new HashMap<String, Object>();
        if(!CommonUtil.isBlank(jsonUser)){

            if(isConsoles(url)){
                map.put("consoleUserInfo",jsonUser);
            }else{
                map.put("userInfo",jsonUser);
            }

            if(!CommonUtil.isBlank(modelAndView)){
                modelAndView.addAllObjects(map);
            }
        }


    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * Ant模式的最长子串匹配法.
     * @param url
     * @return
     */
    private String lookupGroup(String url) {
        String bestPathMatch = null;
        for (String s : excludeMappings) {
            if (this.pathMatcher.match(s, url)
                    && (bestPathMatch == null || bestPathMatch.length() <= s.length())) {
                bestPathMatch = s;
            }
        }
        return bestPathMatch;
    }

    /**
     * 判断链接是后台还是前台
     * @param url
     * @return
     */
    private boolean isConsoles(String url){
        if(url.indexOf("consoles") >= 0){
            return true;
        }
        return false;
    }

}
