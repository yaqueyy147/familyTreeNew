package com.witkey.familyTree.service.fronts;

import com.witkey.familyTree.domain.TUser1;
import com.witkey.familyTree.domain.TUserFamily;

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

    //添加用户可操作的族谱
    public int saveUserFamily(TUserFamily tUserFamily);

    //查询用户可操作的族谱
    public List<TUserFamily> getUserFamilyList(Map<String,Object> params);

    //设置用户是否可登陆后台
    public int setUserConsole(int userId,int state);

    //设置用户是否可登陆前台
    public int setUserFront(int userId,int state);

    //设置用户是否可修族谱
    public int setUserVolunteer(int userId,int state);

    //删除充值记录
    public int deleteMoney(Map<String,Object> params);

    //设置积分排序家族
    public int setuserrankfamily(Map<String,Object> params) throws Exception;

}
