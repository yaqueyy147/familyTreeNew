package com.witkey.familyTree.service.fronts;

import com.witkey.familyTree.domain.TUserFront;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
public interface UserFrontService {

    //创建一个用户
    public int createUserFront(TUserFront tUserFront);

    //根据传入的用户信息查询用户主要用户名和密码，返回list<map>
    public List<Map<String,Object>> signIn(TUserFront tUserFront);

    //根据传入的用户信息查询用户主要用户名和密码，返回list<TUserFront>
    public List<TUserFront> getUserInfo(TUserFront tUserFront);

}
