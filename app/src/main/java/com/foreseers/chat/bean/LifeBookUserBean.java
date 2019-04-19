package com.foreseers.chat.bean;

import java.util.List;

/**
 * File description.
 *
 * @author how
 * @date 2019/3/29
 */
public class LifeBookUserBean {

    /**
     * status : success
     * data : [{"date":"1998-04-18","lifeuserid":53,"name":"日益壮大","self":1,"time":"11:00:00"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public List<DataBean> getData() { return data;}

    public void setData(List<DataBean> data) { this.data = data;}

    public static class DataBean {
        /**
         * date : 1998-04-18
         * lifeuserid : 53
         * name : 日益壮大
         * self : 1
         * time : 11:00:00
         */

        private String date;
        private int lifeuserid;
        private String name;
        private int self;
        private String time;

        public String getDate() { return date;}

        public void setDate(String date) { this.date = date;}

        public int getLifeuserid() { return lifeuserid;}

        public void setLifeuserid(int lifeuserid) { this.lifeuserid = lifeuserid;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public int getSelf() { return self;}

        public void setSelf(int self) { this.self = self;}

        public String getTime() { return time;}

        public void setTime(String time) { this.time = time;}
    }
}
