package com.foreseers.chat.bean;

public class UserDataBean {


    /**
     * status : success
     * data : {"id":88,"username":"郑皓","sex":"M","date":"2002-01-31","time":"18:00:00","zone":28,
     * "facebook":"467979503606542","num":0,
     * "head":"http://192.168.1.73:8080/magazine-unlock-05-2.3.1237-_D9A644D5068059696F53273370739CB7.jpg",
     * "picture":null,"vip":null,"viptime":null,"city":"天津市","bazi":"辛巳,辛丑,己亥,癸酉","zodiac":"蛇","xingzuo":"水瓶座",
     * "ziwei":"破軍","area":"天津市","country":"中国","lat":39.177038,"lng":117.132506,"spare":"红桥区",
     * "spare1":"保康路天津市誉华专修学院","updatetime":"2019-02-11T02:12:32.000+0000","obligate":null,"numerology":9,
     * "userstar":7,"reservedint":17,"reservedvar":"20","reservedvar1":null,"cat1":"土"}
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
         * head : http://192.168.1.73:8080/magazine-unlock-05-2.3.1237-_D9A644D5068059696F53273370739CB7.jpg
         * picture : null
         * vip : null
         * viptime : null
         * city : 天津市
         * bazi : 辛巳,辛丑,己亥,癸酉
         * zodiac : 蛇
         * xingzuo : 水瓶座
         * ziwei : 破軍
         * area : 天津市
         * country : 中国
         * lat : 39.177038
         * lng : 117.132506
         * spare : 红桥区
         * spare1 : 保康路天津市誉华专修学院
         * updatetime : 2019-02-11T02:12:32.000+0000
         * obligate : null
         * numerology : 9
         * userstar : 7
         * reservedint : 17
         * reservedvar : 20
         * reservedvar1 : null
         * cat1 : 土
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
        private Object vip;
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

        public Object getVip() {
            return vip;
        }

        public void setVip(Object vip) {
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
    }
}
