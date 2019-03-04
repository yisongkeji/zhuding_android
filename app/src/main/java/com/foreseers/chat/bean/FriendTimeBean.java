package com.foreseers.chat.bean;

import java.util.List;

public class FriendTimeBean {


    /**
     * status : success
     * data : [{"hour":23,"friend":"66","userid":"88"},{"hour":8303,"friend":"64","userid":"88"},
     * {"hour":8303,"friend":"63","userid":"88"},{"hour":8303,"friend":"62","userid":"88"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * hour : 23
         * friend : 66
         * userid : 88
         */

        private long hour;
        private String friend;
        private String userid;

        public void setHour(long hour) {
            this.hour = hour;
        }

        public long getHour() {

            return hour;
        }

        public String getFriend() {
            return friend;
        }

        public void setFriend(String friend) {
            this.friend = friend;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
