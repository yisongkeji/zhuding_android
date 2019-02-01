package com.foreseers.chat.bean;

public class InquireFriendBean {

    /**
     * status : success
     * data : {"status":0,"userint":20}
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
         * status : 0
         * userint : 20
         */

        private int status;
        private int userint;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUserint() {
            return userint;
        }

        public void setUserint(int userint) {
            this.userint = userint;
        }
    }
}
