package com.witkey.familyTree.interceptor;

import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.exception.UnLoginException;
import com.witkey.familyTree.util.CommonUtil;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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

    public void setExcludeMappings(List<String> excludeMappings) {
        this.excludeMappings = excludeMappings;
    }

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = urlPathHelper.getLookupPathForRequest(httpServletRequest);
        //查找到，表示不需要权限控制
        if(!StringUtils.isEmpty(lookupGroup(url))){
            return Boolean.TRUE;
        }
        //从cookie中读取用户信息
        jsonUser = CookieUtil.cookieValueToJsonObject(httpServletRequest,"userFront");
        if(!CommonUtil.isBlank(jsonUser)){
            return true;
        }
        httpServletResponse.sendRedirect("/sign?loginCode=-2");
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        //从cookie获取用户信息
        TUserFront tUserFront = (TUserFront)JSONObject.toBean(jsonUser,TUserFront.class);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("tUserFront",tUserFront);

        modelAndView.addAllObjects(map);

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

}
