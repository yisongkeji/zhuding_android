package com.foreseers.chat.bean;

public class InquireFriendBean {


    /**
     * status : success
     * data : {"head":"http://192.168.1.73:8080/88/1550541329742.jpg","name":"郑皓","status":0,"userint":16}
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
         * head : http://192.168.1.73:8080/88/1550541329742.jpg
         * name : 郑皓
         * status : 0
         * userint : 16
         */

        private String head;
        private String name;
        private int status;
        private int userint;

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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
