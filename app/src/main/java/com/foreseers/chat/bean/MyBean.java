package com.foreseers.chat.bean;

import java.util.List;

public class MyBean {

    /**
     * status : success
     * data : {"head":"http://192.168.1.73:8080/88/magazine-unlock-06-2.3.1256
     * -_3CA7C40A5FD276FF4A87F500D2927F1D.jpg",
     * "images":["http://192.168.1.73:8080/88/20190219100421.jpg",
     * "http://192.168.1.73:8080/88/20190219100424.jpg",
     * "http://192.168.1.73:8080/88/20190219100428.jpg",
     * "http://192.168.1.73:8080/88/20190225172026.jpg",
     * "http://192.168.1.73:8080/88/20190225172029.jpg",
     * "http://192.168.1.73:8080/88/20190225172033.jpg"],"ziwei":"破軍","sex":"M","num":3,
     * "name":"郑皓","sign":"铭记听","vip":1,"age":17}
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
         * head : http://192.168.1.73:8080/88/magazine-unlock-06-2.3.1256
         * -_3CA7C40A5FD276FF4A87F500D2927F1D.jpg
         * images : ["http://192.168.1.73:8080/88/20190219100421.jpg",
         * "http://192.168.1.73:8080/88/20190219100424.jpg",
         * "http://192.168.1.73:8080/88/20190219100428.jpg",
         * "http://192.168.1.73:8080/88/20190225172026.jpg",
         * "http://192.168.1.73:8080/88/20190225172029.jpg",
         * "http://192.168.1.73:8080/88/20190225172033.jpg"]
         * ziwei : 破軍
         * sex : M
         * num : 3
         * name : 郑皓
         * sign : 铭记听
         * vip : 1
         * age : 17
         */

        private String head;
        private String ziwei;
        private String sex;
        private int num;
        private String name;
        private String sign;
        private int vip;
        private int age;
        private List<String> images;

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getZiwei() {
            return ziwei;
        }

        public void setZiwei(String ziwei) {
            this.ziwei = ziwei;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
