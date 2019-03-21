package com.foreseers.chat.bean;

import java.util.List;

public class ShoppingIDBean {


    /**
     * status : success
     * data : [[{"id":3,"googleID":"com.foreseers.chat.item3","appleID":null,"name":"200個","type":"item"},{"id":2,"googleID":"com.foreseers.chat
     * .item2","appleID":null,"name":"90個","type":"item"},{"id":1,"googleID":"com.foreseers.chat.item1","appleID":"com.foreseers.chat.item1",
     * "name":"30個","type":"item"}],[{"id":6,"googleID":"com.foreseers.chat.vip360","appleID":null,"name":"360天","type":"vip"},{"id":5,
     * "googleID":"com.foreseers.chat.vip90","appleID":null,"name":"90天","type":"vip"},{"id":4,"googleID":"com.foreseers.chat.vip30",
     * "appleID":null,"name":"30天","type":"vip"}]]
     */

    private String status;
    private List<List<DataBean>> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * googleID : com.foreseers.chat.item3
         * appleID : null
         * name : 200個
         * type : item
         */

        private int id;
        private String googleID;
        private Object appleID;
        private String name;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGoogleID() {
            return googleID;
        }

        public void setGoogleID(String googleID) {
            this.googleID = googleID;
        }

        public Object getAppleID() {
            return appleID;
        }

        public void setAppleID(Object appleID) {
            this.appleID = appleID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
