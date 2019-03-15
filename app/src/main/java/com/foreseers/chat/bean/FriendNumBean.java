package com.foreseers.chat.bean;

public class FriendNumBean {

    /**
     * status : success
     * data : {"friendNums":16,"usenums":0}
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
         * friendNums : 16
         * usenums : 0
         */

        private int friendNums;
        private int usenums;

        public int getFriendNums() {
            return friendNums;
        }

        public void setFriendNums(int friendNums) {
            this.friendNums = friendNums;
        }

        public int getUsenums() {
            return usenums;
        }

        public void setUsenums(int usenums) {
            this.usenums = usenums;
        }
    }
}
