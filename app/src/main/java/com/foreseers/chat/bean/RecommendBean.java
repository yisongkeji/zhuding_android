package com.foreseers.chat.bean;

import java.util.List;

public class RecommendBean {

    /**
     * status : success
     * data : [{"id":62,"username":"zheng3","sex":"F","date":"2002-01-16","time":"16:00:00",
     * "zone":13,"facebook":"46797950360650","num":0,
     * "head":"http://192.168.1.73:8080/88/magazine-unlock-06-2.3.1256
     * -_3CA7C40A5FD276FF4A87F500D2927F1D.jpg",
     * "picture":"http://192.168.1.73:8080/88/1550541329742.jpg","vip":0,"viptime":null,
     * "city":"天津市","bazi":"辛巳,辛丑,甲申,壬申","zodiac":"蛇","xingzuo":"摩羯座","ziwei":"天相","area":"天津市",
     * "country":"中国","lat":39.177125,"lng":117.132536,"spare":"红桥区","spare1":"保康路天津市誉华专修学院",
     * "updatetime":"2019-02-19T02:21:00.000+0000","obligate":null,"numerology":3,"userstar":8,
     * "reservedint":17,"reservedvar":"20","reservedvar1":null,"cat1":"木","userscore":74,
     * "distance":1,"numuser":1,"age":17},{"id":66,"username":"王五","sex":"F","date":"2002-01-16",
     * "time":"16:00:00","zone":13,"facebook":"46797950360653","num":0,
     * "head":"http://192.168.1.73:8080/88/magazine-unlock-06-2.3.1256
     * -_3CA7C40A5FD276FF4A87F500D2927F1D.jpg",
     * "picture":"http://192.168.1.73:8080/88/1550541329742.jpg","vip":0,"viptime":null,
     * "city":"天津市","bazi":"辛巳,辛丑,甲申,壬申","zodiac":"蛇","xingzuo":"摩羯座","ziwei":"天相","area":"天津市",
     * "country":"中国","lat":39.177098,"lng":117.132513,"spare":"红桥区","spare1":"保康路天津市誉华专修学院",
     * "updatetime":"2019-02-19T02:21:02.000+0000","obligate":null,"numerology":3,"userstar":8,
     * "reservedint":21,"reservedvar":"20","reservedvar1":null,"cat1":"木","userscore":74,
     * "distance":1,"numuser":0,"age":17},{"id":86,"username":"郑皓","sex":"M","date":"2002-01-21",
     * "time":"16:00:00","zone":19,"facebook":"468979503606542","num":0,
     * "head":"http://192.168.1.73:8080/88/magazine-unlock-06-2.3.1256
     * -_3CA7C40A5FD276FF4A87F500D2927F1D.jpg",
     * "picture":"http://192.168.1.73:8080/88/1550541329742.jpg","vip":0,"viptime":null,
     * "city":"天津市","bazi":"辛巳,辛丑,己丑,壬申","zodiac":"蛇","xingzuo":"水瓶座","ziwei":"紫微,七殺",
     * "area":"天津市","country":"中国","lat":39.177114,"lng":117.132497,"spare":"红桥区",
     * "spare1":"保康路天津市誉华专修学院","updatetime":"2019-02-19T02:21:40.000+0000","obligate":null,
     * "numerology":8,"userstar":7,"reservedint":25,"reservedvar":"20","reservedvar1":null,
     * "cat1":"土","userscore":73,"distance":1,"numuser":0,"age":17},{"id":87,"username":"郑皓先生",
     * "sex":"M","date":"2008-01-21","time":"16:00:00","zone":37,"facebook":"469979503606542",
     * "num":0,"head":"http://192.168.1.73:8080/467979503606542.jpg","picture":null,"vip":0,
     * "viptime":null,"city":"天津市","bazi":"丁亥,癸丑,庚申,甲申","zodiac":"豬","xingzuo":"水瓶座",
     * "ziwei":"太陽","area":"天津市","country":"中国","lat":39.177111,"lng":117.132493,"spare":"红桥区",
     * "spare1":"保康路天津市誉华专修学院","updatetime":"2019-02-12T02:33:04.000+0000","obligate":null,
     * "numerology":5,"userstar":1,"reservedint":30,"reservedvar":"20","reservedvar1":null,
     * "cat1":"金","userscore":72,"distance":1,"numuser":0,"age":11}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 62
         * username : zheng3
         * sex : F
         * date : 2002-01-16
         * time : 16:00:00
         * zone : 13
         * facebook : 46797950360650
         * num : 0
         * head : http://192.168.1.73:8080/88/magazine-unlock-06-2.3.1256
         * -_3CA7C40A5FD276FF4A87F500D2927F1D.jpg
         * picture : http://192.168.1.73:8080/88/1550541329742.jpg
         * vip : 0
         * viptime : null
         * city : 天津市
         * bazi : 辛巳,辛丑,甲申,壬申
         * zodiac : 蛇
         * xingzuo : 摩羯座
         * ziwei : 天相
         * area : 天津市
         * country : 中国
         * lat : 39.177125
         * lng : 117.132536
         * spare : 红桥区
         * spare1 : 保康路天津市誉华专修学院
         * updatetime : 2019-02-19T02:21:00.000+0000
         * obligate : null
         * numerology : 3
         * userstar : 8
         * reservedint : 17
         * reservedvar : 20
         * reservedvar1 : null
         * cat1 : 木
         * userscore : 74
         * distance : 1
         * numuser : 1
         * age : 17
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
        private String picture;
        private int vip;
        private Object viptime;
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
        private Object obligate;
        private int numerology;
        private int userstar;
        private int reservedint;
        private String reservedvar;
        private Object reservedvar1;
        private String cat1;
        private int userscore;
        private int distance;
        private int numuser;
        private int age;

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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public Object getViptime() {
            return viptime;
        }

        public void setViptime(Object viptime) {
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

        public Object getObligate() {
            return obligate;
        }

        public void setObligate(Object obligate) {
            this.obligate = obligate;
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

        public int getUserscore() {
            return userscore;
        }

        public void setUserscore(int userscore) {
            this.userscore = userscore;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getNumuser() {
            return numuser;
        }

        public void setNumuser(int numuser) {
            this.numuser = numuser;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
