package com.foreseers.chat.bean;

public class UserCanumsNumBean {

    /**
     * status : success
     * data : {"id":7,"userid":99,"everyday":1,"vipeveryday":0,"nums":3,"updated":"2019-03-19T09:04:36.000+0000","buynums":20,"countnums":23,
     * "spare":null,"spare1":null,"spareint":null}
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
         * id : 7
         * userid : 99
         * everyday : 1
         * vipeveryday : 0
         * nums : 3
         * updated : 2019-03-19T09:04:36.000+0000
         * buynums : 20
         * countnums : 23
         * spare : null
         * spare1 : null
         * spareint : null
         */

        private int id;
        private int userid;
        private int everyday;
        private int vipeveryday;
        private int nums;
        private String updated;
        private int buynums;
        private int countnums;
        private Object spare;
        private Object spare1;
        private Object spareint;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getEveryday() {
            return everyday;
        }

        public void setEveryday(int everyday) {
            this.everyday = everyday;
        }

        public int getVipeveryday() {
            return vipeveryday;
        }

        public void setVipeveryday(int vipeveryday) {
            this.vipeveryday = vipeveryday;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public int getBuynums() {
            return buynums;
        }

        public void setBuynums(int buynums) {
            this.buynums = buynums;
        }

        public int getCountnums() {
            return countnums;
        }

        public void setCountnums(int countnums) {
            this.countnums = countnums;
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

        public Object getSpareint() {
            return spareint;
        }

        public void setSpareint(Object spareint) {
            this.spareint = spareint;
        }
    }
}
