package chat.foreseers.com.foreseers.util;

public class Urls {

    public static final String URL = "http://192.168.1.73:8080";


    public static final String URL_User = URL + "/user/";
    public static final String Url_Login = URL_User + "queryUser";//判断是不是新用户
    public static final String Url_UserData = URL_User + "insertUser";
    public static final String Url_UserHead = URL_User + "uploadhead";//头像

    public static final String Url_UserLocation = URL + "/match/matching";
    public static final String Url_AnalyzeLifeBook = URL + "/match/showUser";

}
