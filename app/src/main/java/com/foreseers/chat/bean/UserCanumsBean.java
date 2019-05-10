package com.foreseers.chat.bean;

/**
 * File description.
 *
 * @author how
 * @date 2019/5/9
 */
public class UserCanumsBean {

    /**
     * status : success
     * data : {"ststus":1,"num":0}
     */

    private String status;
    private DataBean data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * ststus : 1
         * num : 0
         */

        private int ststus;
        private int num;

        public int getStstus() { return ststus;}

        public void setStstus(int ststus) { this.ststus = ststus;}

        public int getNum() { return num;}

        public void setNum(int num) { this.num = num;}
    }
}
