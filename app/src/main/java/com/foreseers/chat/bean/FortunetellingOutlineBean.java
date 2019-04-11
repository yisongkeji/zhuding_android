package com.foreseers.chat.bean;

/**
 * File description.
 *
 * @author how
 * @date 2019/4/8
 */
public class FortunetellingOutlineBean {

    /**
     * status : success
     * data : {"bazi":"\"辛巳\",\"癸巳\",\"癸未\",\"壬戌\"","bazimatch":"\"午\",\"亥\",\"卯\"","star":"7","ziwei":"天機","zodiac":"蛇","lifeuserid":13,
     * "numerologymatch":"2,3,4","starmatch":"2,5,8","zodiacmatch":"\"牛\",\"雞\",\"猴\"","horoscopematch":"\"金牛座\",\"處女座\",\"摩羯座\"",
     * "horoscope":"金牛座","numerology":"1","ziweimatch":"\"天梁\",\"武曲\",\"天同\""}
     */

    private String status;
    private DataBean data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * bazi : "辛巳","癸巳","癸未","壬戌"
         * bazimatch : "午","亥","卯"
         * star : 7
         * ziwei : 天機
         * zodiac : 蛇
         * lifeuserid : 13
         * numerologymatch : 2,3,4
         * starmatch : 2,5,8
         * zodiacmatch : "牛","雞","猴"
         * horoscopematch : "金牛座","處女座","摩羯座"
         * horoscope : 金牛座
         * numerology : 1
         * ziweimatch : "天梁","武曲","天同"
         */

        private String bazi;
        private String bazimatch;
        private String star;
        private String ziwei;
        private String zodiac;
        private int lifeuserid;
        private String numerologymatch;
        private String starmatch;
        private String zodiacmatch;
        private String horoscopematch;
        private String horoscope;
        private String numerology;
        private String ziweimatch;

        public String getBazi() { return bazi;}

        public void setBazi(String bazi) { this.bazi = bazi;}

        public String getBazimatch() { return bazimatch;}

        public void setBazimatch(String bazimatch) { this.bazimatch = bazimatch;}

        public String getStar() { return star;}

        public void setStar(String star) { this.star = star;}

        public String getZiwei() { return ziwei;}

        public void setZiwei(String ziwei) { this.ziwei = ziwei;}

        public String getZodiac() { return zodiac;}

        public void setZodiac(String zodiac) { this.zodiac = zodiac;}

        public int getLifeuserid() { return lifeuserid;}

        public void setLifeuserid(int lifeuserid) { this.lifeuserid = lifeuserid;}

        public String getNumerologymatch() { return numerologymatch;}

        public void setNumerologymatch(String numerologymatch) { this.numerologymatch = numerologymatch;}

        public String getStarmatch() { return starmatch;}

        public void setStarmatch(String starmatch) { this.starmatch = starmatch;}

        public String getZodiacmatch() { return zodiacmatch;}

        public void setZodiacmatch(String zodiacmatch) { this.zodiacmatch = zodiacmatch;}

        public String getHoroscopematch() { return horoscopematch;}

        public void setHoroscopematch(String horoscopematch) { this.horoscopematch = horoscopematch;}

        public String getHoroscope() { return horoscope;}

        public void setHoroscope(String horoscope) { this.horoscope = horoscope;}

        public String getNumerology() { return numerology;}

        public void setNumerology(String numerology) { this.numerology = numerology;}

        public String getZiweimatch() { return ziweimatch;}

        public void setZiweimatch(String ziweimatch) { this.ziweimatch = ziweimatch;}
    }
}
