package com.witkey.familyTree.service.fronts;

import com.witkey.familyTree.domain.TUser1;
import com.witkey.familyTree.domain.TUserFront;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
public interface UserService {

    //创建一个用户
    public int createUser1(TUser1 tUser1);
    //修改用户信息
    public int saveUser1(TUser1 tUser1);

    //根据传入的用户信息查询用户主要用户名和密码，返回list<map>
    public List<Map<String,Object>> signIn(TUser1 tUser1);

    //根据传入的用户信息查询用户主要用户名和密码，返回list<TUser1>
    public List<TUser1> getUserInfo1(TUser1 TUser1);

    //修改密码
    public int modifyPassword(Map<String, Object> params);

    //申请志愿者
//    public int applyVolunteer(TVolunteer tVolunteer);
    public int applyVolunteer(int userId);
    //修改头像
    public int modifyPhoto(String userId, String photoPath, String userType);

    //根据用户ID查询用户
    public TUser1 getUserInfoFromId(int userId);

}