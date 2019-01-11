package chat.foreseers.com.foreseers.bean;

public class UserDataBean {


    /**
     * status : success
     * data : {"id":4,"username":"郑皓","sex":"M","date":"2019-01-08","time":"12:00:00","zone":12,
     * "facebook":"467979503606542","num":3,"head":null,"picture":null,"vip":null,"viptime":null,
     * "city":null,"bazi":"戊戌乙丑乙巳壬","zodiac":"狗","xingzuo":"摩羯座","ziwei":"武曲,貪狼"}
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
         * id : 4
         * username : 郑皓
         * sex : M
         * date : 2019-01-08
         * time : 12:00:00
         * zone : 12
         * facebook : 467979503606542
         * num : 3
         * head : null
         * picture : null
         * vip : null
         * viptime : null
         * city : null
         * bazi : 戊戌乙丑乙巳壬
         * zodiac : 狗
         * xingzuo : 摩羯座
         * ziwei : 武曲,貪狼
         */

        private int id;
        private String username;
        private String sex;
        private String date;
        private String time;
        private int zone;
        private String facebook;
        private int num;
        private Object head;
        private Object picture;
        private Object vip;
        private Object viptime;
        private Object city;
        private String bazi;
        private String zodiac;
        private String xingzuo;
        private String ziwei;

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
    }
}
