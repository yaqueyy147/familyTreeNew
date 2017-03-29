package com.witkey.familyTree.service.fronts;

import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.domain.TVolunteer;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
public interface UserFrontService {

    //创建一个用户
    public int createUserFront(TUserFront tUserFront);

    //修改用户信息
    public int saveUserFront(TUserFront tUserFront);

    //根据传入的用户信息查询用户主要用户名和密码，返回list<map>
    public List<Map<String,Object>> signIn(TUserFront tUserFront);

    //根据传入的用户信息查询用户主要用户名和密码，返回list<TUserFront>
    public List<TUserFront> getUserInfo(TUserFront tUserFront);

    //修改密码
    public int modifyPassword(Map<String,Object> params);

    //申请志愿者
//    public int applyVolunteer(TVolunteer tVolunteer);
    public int applyVolunteer(int userId);
    //修改头像
    public int modifyPhoto(String userId, String photoPath, String userType);

    //根据用户ID查询用户
    public TUserFront getUserInfoFromId(int userId);

}
