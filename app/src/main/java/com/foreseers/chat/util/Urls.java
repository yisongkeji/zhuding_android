package com.foreseers.chat.util;

public  class Urls {

//    public static final String URL = "http://192.168.1.73:8080";
    public static final String URL = "https://chat.foreseers.cn:443";


    public static final String URL_User = URL + "/user/";
    public static final String Url_Query = URL_User + "queryUser";//判断是不是新用户
    public static final String Url_UserData = URL_User + "insertUser";
    public static final String Url_UserHead = URL_User + "uploadhead";//头像
    public static final String Url_UserBlurHead = URL_User + "uploadblurhead";//模糊头像
    public static final String Url_UserShow = URL_User + "show";//个人信息

    public static final String Url_UpdateUser = URL_User+"updateuser";//修改個人信息
    public static final String Url_My = URL_User + "userpersonal";//我的模块

    public static final String Url_UserImg = URL+"/userimage/upload";//相册
    public static final String Url_UserBlurImg = URL+"/userimage/uploadblurimage";//模糊相册
    public static final String Url_DeleteImg = URL+"/userimage/deleteimage";//模糊相册

    public static final String Url_UserLocation = URL + "/match/matching";//主页
    public static final String Url_AnalyzeLifeBook = URL + "/match/showUser";

    public static final String Url_Countage = URL_User + "countage";

    public static final String Url_UserSign = URL_User + "setObligate";   //签名

    //好友
    public static final String Url_Friend = URL + "/userfriend/";
    public static final String Url_GetFriend = Url_Friend + "getname";//获得好友列表
    public static final String Url_UserFriend = Url_Friend + "isfull";//查询是否可以添加好友
    public static final String Url_ADDFriend = Url_Friend + "addfriend";//添加好友
    public static final String Url_FriendTime = Url_Friend + "friendTime";//校验添加好友时间
    public static final String Url_DelFriend = Url_Friend + "deletefriend";//删除好友


    //拉黑
    public static final String Url_Black = URL + "/userDefriend/";
    public static final String Url_AddBlack = Url_Black + "pullDefriend";//添加黑名单
    public static final String Url_BlackList = Url_Black + "getBlackList";//拉黑列表
    public static final String Url_RemoveBlack = Url_Black + "removeUser";//移除拉黑列表

    //历史记录
    public static final String Url_UserHistory = URL + "/userhistory/";
    public static final String Url_Wipe = Url_UserHistory + "updatehistory";//擦照片
    public static final String Url_History = Url_UserHistory + "showUserHistory";//谁擦过我历史记录
    public static final String Url_HistoryDay = Url_UserHistory + "showBydate";//谁擦过我历史记录全天

    //谁看过我
    public static final String Url_Look = URL + "/userLook/";//谁看过我
    public static final String Url_UserLook = Url_Look + "updateUserLook";//插入谁看过我历史表
    public static final String Url_ShowLook = Url_Look + "showUserLook";//谁看过我历史表
    public static final String Url_ShowLookDay = Url_Look + "showByDate";//谁看过我历史表全天

    //擦子
    public static final String Url_Canums = URL + "/userCanums/";
    public static final String Url_UserCanums = Url_Canums + "updatenums"; //领擦子
    public static final String Url_UserCanumsNum = Url_Canums + "getUserNums"; //擦子数





}
