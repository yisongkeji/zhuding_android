package com.foreseers.chat.bean;

import java.util.List;

public class WipeDayHistoryBean {

    /**
     * status : success
     * data : [{"head":"http://chat.foreseers.cn:80/102/1551691115296.jpg","ziwei":"紫微,貪狼","sex":"M","userscore":69,"time":"2019-03-04","age":1,
     * "DESC":"步伐一致","username":"Patrick Lam"}]
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
         * head : http://chat.foreseers.cn:80/102/1551691115296.jpg
         * ziwei : 紫微,貪狼
         * sex : M
         * userscore : 69
         * time : 2019-03-04
         * age : 1
         * DESC : 步伐一致
         * username : Patrick Lam
         */

        private String head;
        private String ziwei;
        private String sex;
        private int userscore;
        private String time;
        private int age;
        private String DESC;
        private String username;

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getZiwei() {
            return ziwei;
        }

        public void setZiwei(String ziwei) {
            this.ziwei = ziwei;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getUserscore() {
            return userscore;
        }

        public void setUserscore(int userscore) {
            this.userscore = userscore;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDESC() {
            return DESC;
        }

        public void setDESC(String DESC) {
            this.DESC = DESC;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
