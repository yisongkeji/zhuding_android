package com.foreseers.chat.bean;

/**
 * File description.
 *
 * @author how
 * @date 2019/4/16
 */
public class VipTimeBean {

    /**
     * status : success
     * data : {"head":"http://192.168.1.73:8080/88/magazine-unlock-06-2.3.1256-_3CA7C40A5FD276FF4A87F500D2927F1D.jpg","viptime":"2020-05-22
     * 10:26:26","name":"郑皓"}
     */

    private String status;
    private DataBean data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * head : http://192.168.1.73:8080/88/magazine-unlock-06-2.3.1256-_3CA7C40A5FD276FF4A87F500D2927F1D.jpg
         * viptime : 2020-05-22 10:26:26
         * name : 郑皓
         */

        private String head;
        private String viptime;
        private String name;

        public String getHead() { return head;}

        public void setHead(String head) { this.head = head;}

        public String getViptime() { return viptime;}

        public void setViptime(String viptime) { this.viptime = viptime;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}
    }
}
