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
     * data : [{"date":"1993-09-10","lifeuserid":7,"name":"qi","time":"12:00:00"},{"date":"1993-09-10","lifeuserid":8,"name":"郑皓","time":"12:00:00"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public List<DataBean> getData() { return data;}

    public void setData(List<DataBean> data) { this.data = data;}

    public static class DataBean {
        /**
         * date : 1993-09-10
         * lifeuserid : 7
         * name : qi
         * time : 12:00:00
         */

        private String date;
        private int lifeuserid;
        private String name;
        private String time;

        public String getDate() { return date;}

        public void setDate(String date) { this.date = date;}

        public int getLifeuserid() { return lifeuserid;}

        public void setLifeuserid(int lifeuserid) { this.lifeuserid = lifeuserid;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public String getTime() { return time;}

        public void setTime(String time) { this.time = time;}
    }
}
