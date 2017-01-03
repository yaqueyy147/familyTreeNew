package com.witkey.familyTree.interceptor;

import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.util.CookieUtil;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suyx on 2016/12/25.
 */
public class TestInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        //从cookie获取用户信息
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(httpServletRequest,"userFront");
        TUserFront tUserFront = (TUserFront)JSONObject.toBean(jsonUser,TUserFront.class);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("tUserFront",tUserFront);

        modelAndView.addAllObjects(map);

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
