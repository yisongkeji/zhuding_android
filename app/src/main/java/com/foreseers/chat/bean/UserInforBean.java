package com.foreseers.chat.bean;

import java.util.List;

/**
 * File description.
 *
 * @author how
 * @date 2019/4/23
 */
public class UserInforBean {

    /**
     * status : success
     * data : {"age":40,"num":0,"sex":"M","obligate":null,"friend":0,"name":"齐达内","head":"http://192.168.1.73:8080/251/20.jpg",
     * "images":["http://192.168.1.73:8080/251/20190422114909127250.jpg","http://192.168.1.73:8080/251/20190422114950772658.jpg"],"ziwei":"太陽,巨門",
     * "sevenday":1,"thirthday":1,"lookhead":1,"vip":0,"userdesc":"努力維繫","userscore":56,"distance":1,"lifeuserid":137}
     */

    private String status;
    private DataBean data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * age : 40
         * num : 0
         * sex : M
         * obligate : null
         * friend : 0
         * name : 齐达内
         * head : http://192.168.1.73:8080/251/20.jpg
         * images : ["http://192.168.1.73:8080/251/20190422114909127250.jpg","http://192.168.1.73:8080/251/20190422114950772658.jpg"]
         * ziwei : 太陽,巨門
         * sevenday : 1
         * thirthday : 1
         * lookhead : 1
         * vip : 0
         * userdesc : 努力維繫
         * userscore : 56
         * distance : 1
         * lifeuserid : 137
         */

        private int age;
        private int num;
        private String sex;
        private String obligate;
        private int friend;
        private String name;
        private String head;
        private String ziwei;
        private int sevenday;
        private int thirthday;
        private int lookhead;
        private int vip;
        private String userdesc;
        private int userscore;
        private int distance;
        private int lifeuserid;
        private List<String> images;

        public int getAge() { return age;}

        public void setAge(int age) { this.age = age;}

        public int getNum() { return num;}

        public void setNum(int num) { this.num = num;}

        public String getSex() { return sex;}

        public void setSex(String sex) { this.sex = sex;}

        public void setObligate(String obligate) {
            this.obligate = obligate;
        }

        public String getObligate() {

            return obligate;
        }

        public int getFriend() { return friend;}

        public void setFriend(int friend) { this.friend = friend;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public String getHead() { return head;}

        public void setHead(String head) { this.head = head;}

        public String getZiwei() { return ziwei;}

        public void setZiwei(String ziwei) { this.ziwei = ziwei;}

        public int getSevenday() { return sevenday;}

        public void setSevenday(int sevenday) { this.sevenday = sevenday;}

        public int getThirthday() { return thirthday;}

        public void setThirthday(int thirthday) { this.thirthday = thirthday;}

        public int getLookhead() { return lookhead;}

        public void setLookhead(int lookhead) { this.lookhead = lookhead;}

        public int getVip() { return vip;}

        public void setVip(int vip) { this.vip = vip;}

        public String getUserdesc() { return userdesc;}

        public void setUserdesc(String userdesc) { this.userdesc = userdesc;}

        public int getUserscore() { return userscore;}

        public void setUserscore(int userscore) { this.userscore = userscore;}

        public int getDistance() { return distance;}

        public void setDistance(int distance) { this.distance = distance;}

        public int getLifeuserid() { return lifeuserid;}

        public void setLifeuserid(int lifeuserid) { this.lifeuserid = lifeuserid;}

        public List<String> getImages() { return images;}

        public void setImages(List<String> images) { this.images = images;}
    }
}
