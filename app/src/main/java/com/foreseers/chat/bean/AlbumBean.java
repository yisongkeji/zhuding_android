package com.foreseers.chat.bean;


import java.util.ArrayList;
import java.util.List;

public class AlbumBean {

    /**
     * status : success
     * data : {"id":88,"username":"郑皓","sex":"M","date":"2002-01-31","time":"18:00:00","zone":28,
     * "facebook":"467979503606542","num":0,
     * "head":"http://192.168.1.73:8080/magazine-unlock-01-2.3.1239-_B53B3BCEEDBD98A3C051912FDD3552F7.jpg",
     * "picture":null,"vip":1,"viptime":"2019-03-10 12:20:20","city":"天津市","bazi":"辛巳,辛丑,己亥,癸酉","zodiac":"蛇",
     * "xingzuo":"水瓶座","ziwei":"破軍","area":"天津市","country":"中国","lat":39.176995,"lng":117.132488,"spare":"红桥区",
     * "spare1":"保康路天津市誉华专修学院","updatetime":"2019-02-12T06:13:02.000+0000","obligate":null,"numerology":9,
     * "userstar":7,"reservedint":17,"reservedvar":"20","reservedvar1":null,"cat1":"土","countnum":6,
     * "listimage":["http://192.168.1.73:8080/88/magazine-unlock-05-2.3.1239-_DE7E980B4655122FF8C8FB207117E78E.jpg",
     * "http://192.168.1.73:8080/88/magazine-unlock-04-2.3.1238-_B230C8D6C7D614C72B7E5E7800BDD079.jpg",
     * "http://192.168.1.73:8080/88/magazine-unlock-01-2.3.1237-_92D9E0817C0E257C6334293A6743E43D.jpg",
     * "http://192.168.1.73:8080/88/magazine-unlock-01-2.3.1235-_33AF07642CCAF9A738AD4904C9C953B9.jpg",
     * "http://192.168.1.73:8080/88/magazine-unlock-04-2.3.1238-_B230C8D6C7D614C72B7E5E7800BDD079.jpg",
     * "http://192.168.1.73:8080/88/20190211104329.jpg"],"age":0,"vipday":26}
     */

    private String status;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 88
         * username : 郑皓
         * sex : M
         * date : 2002-01-31
         * time : 18:00:00
         * zone : 28
         * facebook : 467979503606542
         * num : 0
         * head : http://192.168.1.73:8080/magazine-unlock-01-2.3.1239-_B53B3BCEEDBD98A3C051912FDD3552F7.jpg
         * picture : null
         * vip : 1
         * viptime : 2019-03-10 12:20:20
         * city : 天津市
         * bazi : 辛巳,辛丑,己亥,癸酉
         * zodiac : 蛇
         * xingzuo : 水瓶座
         * ziwei : 破軍
         * area : 天津市
         * country : 中国
         * lat : 39.176995
         * lng : 117.132488
         * spare : 红桥区
         * spare1 : 保康路天津市誉华专修学院
         * updatetime : 2019-02-12T06:13:02.000+0000
         * obligate : null
         * numerology : 9
         * userstar : 7
         * reservedint : 17
         * reservedvar : 20
         * reservedvar1 : null
         * cat1 : 土
         * countnum : 6
         * listimage : ["http://192.168.1.73:8080/88/magazine-unlock-05-2.3.1239-_DE7E980B4655122FF8C8FB207117E78E
         * .jpg","http://192.168.1.73:8080/88/magazine-unlock-04-2.3.1238-_B230C8D6C7D614C72B7E5E7800BDD079.jpg",
         * "http://192.168.1.73:8080/88/magazine-unlock-01-2.3.1237-_92D9E0817C0E257C6334293A6743E43D.jpg",
         * "http://192.168.1.73:8080/88/magazine-unlock-01-2.3.1235-_33AF07642CCAF9A738AD4904C9C953B9.jpg",
         * "http://192.168.1.73:8080/88/magazine-unlock-04-2.3.1238-_B230C8D6C7D614C72B7E5E7800BDD079.jpg",
         * "http://192.168.1.73:8080/88/20190211104329.jpg"]
         * age : 0
         * vipday : 26
         */

        private int id;
        private String username;
        private String sex;
        private String date;
        private String time;
        private int zone;
        private String facebook;
        private int num;
        private String head;
        private Object picture;
        private int vip;
        private String viptime;
        private String city;
        private String bazi;
        private String zodiac;
        private String xingzuo;
        private String ziwei;
        private String area;
        private String country;
        private double lat;
        private double lng;
        private String spare;
        private String spare1;
        private String updatetime;
        private String obligate;
        private int numerology;
        private int userstar;
        private int reservedint;
        private String reservedvar;
        private Object reservedvar1;
        private String cat1;
        private int countnum;
        private int age;
        private int vipday;
        private ArrayList<String> listimage;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getZone() {
            return zone;
        }

        public void setZone(int zone) {
            this.zone = zone;
        }

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public Object getPicture() {
            return picture;
        }

        public void setPicture(Object picture) {
            this.picture = picture;
        }

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public String getViptime() {
            return viptime;
        }

        public void setViptime(String viptime) {
            this.viptime = viptime;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getBazi() {
            return bazi;
        }

        public void setBazi(String bazi) {
            this.bazi = bazi;
        }

        public String getZodiac() {
            return zodiac;
        }

        public void setZodiac(String zodiac) {
            this.zodiac = zodiac;
        }

        public String getXingzuo() {
            return xingzuo;
        }

        public void setXingzuo(String xingzuo) {
            this.xingzuo = xingzuo;
        }

        public String getZiwei() {
            return ziwei;
        }

        public void setZiwei(String ziwei) {
            this.ziwei = ziwei;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getSpare() {
            return spare;
        }

        public void setSpare(String spare) {
            this.spare = spare;
        }

        public String getSpare1() {
            return spare1;
        }

        public void setSpare1(String spare1) {
            this.spare1 = spare1;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }


        public void setObligate(String obligate) {
            this.obligate = obligate;
        }

        public String getObligate() {

            return obligate;
        }

        public int getNumerology() {
            return numerology;
        }

        public void setNumerology(int numerology) {
            this.numerology = numerology;
        }

        public int getUserstar() {
            return userstar;
        }

        public void setUserstar(int userstar) {
            this.userstar = userstar;
        }

        public int getReservedint() {
            return reservedint;
        }

        public void setReservedint(int reservedint) {
            this.reservedint = reservedint;
        }

        public String getReservedvar() {
            return reservedvar;
        }

        public void setReservedvar(String reservedvar) {
            this.reservedvar = reservedvar;
        }

        public Object getReservedvar1() {
            return reservedvar1;
        }

        public void setReservedvar1(Object reservedvar1) {
            this.reservedvar1 = reservedvar1;
        }

        public String getCat1() {
            return cat1;
        }

        public void setCat1(String cat1) {
            this.cat1 = cat1;
        }

        public int getCountnum() {
            return countnum;
        }

        public void setCountnum(int countnum) {
            this.countnum = countnum;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getVipday() {
            return vipday;
        }

        public void setVipday(int vipday) {
            this.vipday = vipday;
        }

        public void setListimage(ArrayList<String> listimage) {
            this.listimage = listimage;
        }

        public ArrayList<String> getListimage() {

            return listimage;
        }
    }
}
