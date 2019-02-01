package com.foreseers.chat.bean;

public class UserDataBean {


    /**
     * status : success
     * data : {"id":83,"username":"郑皓","sex":"M","date":"2003-01-21","time":"11:00:00","zone":28,
     * "facebook":"467979503606542","num":null,"head":null,"picture":null,"vip":null,
     * "viptime":null,"city":null,"bazi":"壬午,癸丑,甲午,庚午","zodiac":"馬","xingzuo":"水瓶座","ziwei":"太陽,
     * 太陰","area":null,"country":null,"lat":null,"lng":null,"spare":null,"spare1":null,
     * "updatetime":null,"obligate":null,"numerology":9,"userstar":6,"reservedint":null,
     * "reservedvar":null,"reservedvar1":null,"cat1":"木"}
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
         * id : 83
         * username : 郑皓
         * sex : M
         * date : 2003-01-21
         * time : 11:00:00
         * zone : 28
         * facebook : 467979503606542
         * num : null
         * head : null
         * picture : null
         * vip : null
         * viptime : null
         * city : null
         * bazi : 壬午,癸丑,甲午,庚午
         * zodiac : 馬
         * xingzuo : 水瓶座
         * ziwei : 太陽,太陰
         * area : null
         * country : null
         * lat : null
         * lng : null
         * spare : null
         * spare1 : null
         * updatetime : null
         * obligate : null
         * numerology : 9
         * userstar : 6
         * reservedint : null
         * reservedvar : null
         * reservedvar1 : null
         * cat1 : 木
         */

        private int id;
        private String username;
        private String sex;
        private String date;
        private String time;
        private int zone;
        private String facebook;
        private Object num;
        private Object head;
        private Object picture;
        private Object vip;
        private Object viptime;
        private Object city;
        private String bazi;
        private String zodiac;
        private String xingzuo;
        private String ziwei;
        private Object area;
        private Object country;
        private Object lat;
        private Object lng;
        private Object spare;
        private Object spare1;
        private Object updatetime;
        private Object obligate;
        private int numerology;
        private int userstar;
        private int reservedint;
        private Object reservedvar;
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

        public Object getNum() {
            return num;
        }

        public void setNum(Object num) {
            this.num = num;
        }

        public Object getHead() {
            return head;
        }

        public void setHead(Object head) {
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

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
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

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public Object getLat() {
            return lat;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public Object getLng() {
            return lng;
        }

        public void setLng(Object lng) {
            this.lng = lng;
        }

        public Object getSpare() {
            return spare;
        }

        public void setSpare(Object spare) {
            this.spare = spare;
        }

        public Object getSpare1() {
            return spare1;
        }

        public void setSpare1(Object spare1) {
            this.spare1 = spare1;
        }

        public Object getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(Object updatetime) {
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

        public void setReservedint(int reservedint) {
            this.reservedint = reservedint;
        }

        public int getReservedint() {

            return reservedint;
        }

        public Object getReservedvar() {
            return reservedvar;
        }

        public void setReservedvar(Object reservedvar) {
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
