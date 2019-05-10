package com.foreseers.chat.bean;

/**
 * File description.
 *
 * @author how
 * @date 2019/5/9
 */
public class UserStatusBean {

    /**
     * status : success
     * data : {"everyStatus":0}
     */

    private String status;
    private DataBean data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * everyStatus : 0
         */

        private int everyStatus;

        public int getEveryStatus() { return everyStatus;}

        public void setEveryStatus(int everyStatus) { this.everyStatus = everyStatus;}
    }
}
