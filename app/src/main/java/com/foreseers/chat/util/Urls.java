package com.foreseers.chat.util;

public  class Urls {

    public static final String URL = "http://192.168.1.73:8080";


    public static final String URL_User = URL + "/user/";
    public static final String Url_Query = URL_User + "queryUser";//判断是不是新用户
    public static final String Url_UserData = URL_User + "insertUser";
    public static final String Url_UserHead = URL_User + "uploadhead";//头像
    public static final String Url_UserBlurHead = URL_User + "uploadblurhead";//模糊头像

    public static final String Url_UpdateUser = URL_User+"updateuser";//修改個人信息
    public static final String Url_My = URL_User + "userpersonal";//我的模块

    public static final String Url_UserImg = URL+"/userimage/upload";//相册
    public static final String Url_UserBlurImg = URL+"/userimage/uploadblurimage";//模糊相册
    public static final String Url_DeleteImg = URL+"/userimage/deleteimage";//模糊相册

    public static final String Url_UserLocation = URL + "/match/matching";//主页
    public static final String Url_AnalyzeLifeBook = URL + "/match/showUser";



    public static final String Url_Countage = URL_User + "countage";


    //好友
    public static final String Url_Friend = URL + "/userfriend/";
    public static final String Url_GetFriend = Url_Friend + "getname";//获得好友列表
    public static final String Url_UserFriend = Url_Friend + "isfull";//查询是否可以添加好友
    public static final String Url_ADDFriend = Url_Friend + "addfriend";//添加好友
    public static final String Url_FriendTime = Url_Friend + "friendTime";//校验添加好友时间

    public static final String Url_GetFriendName = Url_Friend + "getname";


    //擦照片
    public static final String Url_Wipe = URL + "/userhistory/updatehistory";


}
