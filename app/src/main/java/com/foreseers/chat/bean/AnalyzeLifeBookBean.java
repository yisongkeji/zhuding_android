package com.foreseers.chat.bean;

import java.util.List;

public class AnalyzeLifeBookBean {

    /**
     * status : success
     * data : {"id":152,"zhuid":99,"congid":88,"userscore":77,"userdesc":"一見如故",
     * "commentgood":null,"commentbad":null,"characteristicgood":null,"characteristicbad":null,
     * "mindscore":78,"minddesc":"","bodyscore":80,"bodydesc":"","characterscore":77,
     * "characterdesc":"","distance":12323,
     * "spare":"TA就是人緣好，身邊超多朋友，身上又散發魅力所以好多異性追TA，想追TA首先要接受這點，千萬不要干擾，不要囉嗦，也不要吃太大的醋！因為TA
     * 就是見友忘情的人，所以不能急，要有耐性～之後要有一件東西可以令TA佩服你，像是專業，感覺你是有料子的，TA就會對你另眼相看的～","spare1":null,
     * "spare2":null,
     * "commentdesc":"你跟TA
     * 是同一類人：對自己極有自信，對愛情的期望很高，對感情的需求十分強烈。你們會完全深陷地愛上對方，同時會以愛做藉口不停進行苛索，所以往往越是投入，越是會失望，偏偏二人都愛表現強悍的一面，經常為大小事爭吵，而且互不屈服...... 其實在愛情的國度裡，是沒有高下之分的！贏了糖，輸了廠，值得嗎？","characteristicdesc":"\"魅力型 \",\"桃花旺盛 \",\"梟雄\",\"翻版名偵探柯南\",\"多才多藝，具有優異的藝術才華\",\"所有東西都設置密碼，自我保護\",\"喜歡取巧，有時會遊走法律邊緣及挑戰現有規則\",\"說得出做得到\",\"經常搬屋，轉工作\",\"做事有效率，說到做到\"","age":17,"num":3,"sex":"M","obligate":"铭记听","friend":0,"name":"郑皓","head":"http://192.168.1.73:8080/88/magazine-unlock-06-2.3.1256-_3CA7C40A5FD276FF4A87F500D2927F1D.jpg","images":["http://192.168.1.73:8080/88/20190219100421.jpg","http://192.168.1.73:8080/88/20190219100424.jpg","http://192.168.1.73:8080/88/20190219100428.jpg","http://192.168.1.73:8080/88/20190311142503200000.jpg"],"lookimages":1,"ziwei":"破軍","sevenday":1,"thirthday":0,"lookhead":1,"vip":0}
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
         * id : 152
         * zhuid : 99
         * congid : 88
         * userscore : 77
         * userdesc : 一見如故
         * commentgood : null
         * commentbad : null
         * characteristicgood : · 慢熱、被動\n· 愛出風頭，喜歡人崇拜他\n· 吃軟不吃硬\n· 不喜受约束，獨斷獨行 \n· 跟別人翻面後定必成為陌路人\n· 喜破舊立新\n· 三分鐘熱度\n· 有話說話，不喜歡說謊\n
         * characteristicbad : null
         * mindscore : 78
         * minddesc :
         * bodyscore : 80
         * bodydesc :
         * characterscore : 77
         * characterdesc :
         * distance : 12323
         * spare : TA就是人緣好，身邊超多朋友，身上又散發魅力所以好多異性追TA，想追TA首先要接受這點，千萬不要干擾，不要囉嗦，也不要吃太大的醋！因為TA
         * 就是見友忘情的人，所以不能急，要有耐性～之後要有一件東西可以令TA佩服你，像是專業，感覺你是有料子的，TA就會對你另眼相看的～
         * spare1 : TA對你會非常溫柔，肯百份百的付出，不過TA有點霸道，如果你跟異性朋友過多的接觸，TA會吃醋吃很大，心情差時就最好不要碰TA。
         * spare2 : null
         * commentdesc :
         * 你跟TA是同一類人：對自己極有自信，對愛情的期望很高，對感情的需求十分強烈。你們會完全深陷地愛上對方，同時會以愛做藉口不停進行苛索，所以往往越是投入，越是會失望，偏偏二人都愛表現強悍的一面，經常為大小事爭吵，而且互不屈服...... 其實在愛情的國度裡，是沒有高下之分的！贏了糖，輸了廠，值得嗎？
         * characteristicdesc : "魅力型 ","桃花旺盛 ","梟雄","翻版名偵探柯南","多才多藝，具有優異的藝術才華","所有東西都設置密碼，自我保護",
         * "喜歡取巧，有時會遊走法律邊緣及挑戰現有規則","說得出做得到","經常搬屋，轉工作","做事有效率，說到做到"
         * age : 17
         * num : 3
         * sex : M
         * obligate : 铭记听
         * friend : 0
         * name : 郑皓
         * head : http://192.168.1.73:8080/88/magazine-unlock-06-2.3.1256
         * -_3CA7C40A5FD276FF4A87F500D2927F1D.jpg
         * images : ["http://192.168.1.73:8080/88/20190219100421.jpg",
         * "http://192.168.1.73:8080/88/20190219100424.jpg",
         * "http://192.168.1.73:8080/88/20190219100428.jpg",
         * "http://192.168.1.73:8080/88/20190311142503200000.jpg"]
         * lookimages : 1
         * ziwei : 破軍
         * sevenday : 1
         * thirthday : 0
         * lookhead : 1
         * vip : 0
         */

        private int id;
        private int zhuid;
        private int congid;
        private int userscore;
        private String userdesc;
        private Object commentgood;
        private Object commentbad;
        private String characteristicgood;
        private Object characteristicbad;
        private int mindscore;
        private String minddesc;
        private int bodyscore;
        private String bodydesc;
        private int characterscore;
        private String characterdesc;
        private int distance;
        private String spare;
        private String spare1;
        private Object spare2;
        private String commentdesc;
        private String characteristicdesc;
        private int age;
        private int num;
        private String sex;
        private String obligate;
        private int friend;
        private String name;
        private String head;
        private int lookimages;
        private String ziwei;
        private int sevenday;
        private int thirthday;
        private int lookhead;
        private int vip;
        private List<String> images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getZhuid() {
            return zhuid;
        }

        public void setZhuid(int zhuid) {
            this.zhuid = zhuid;
        }

        public int getCongid() {
            return congid;
        }

        public void setCongid(int congid) {
            this.congid = congid;
        }

        public int getUserscore() {
            return userscore;
        }

        public void setUserscore(int userscore) {
            this.userscore = userscore;
        }

        public String getUserdesc() {
            return userdesc;
        }

        public void setUserdesc(String userdesc) {
            this.userdesc = userdesc;
        }

        public Object getCommentgood() {
            return commentgood;
        }

        public void setCommentgood(Object commentgood) {
            this.commentgood = commentgood;
        }

        public Object getCommentbad() {
            return commentbad;
        }

        public void setCommentbad(Object commentbad) {
            this.commentbad = commentbad;
        }

        public void setCharacteristicgood(String characteristicgood) {
            this.characteristicgood = characteristicgood;
        }

        public String getCharacteristicgood() {

            return characteristicgood;
        }

        public Object getCharacteristicbad() {
            return characteristicbad;
        }

        public void setCharacteristicbad(Object characteristicbad) {
            this.characteristicbad = characteristicbad;
        }

        public int getMindscore() {
            return mindscore;
        }

        public void setMindscore(int mindscore) {
            this.mindscore = mindscore;
        }

        public String getMinddesc() {
            return minddesc;
        }

        public void setMinddesc(String minddesc) {
            this.minddesc = minddesc;
        }

        public int getBodyscore() {
            return bodyscore;
        }

        public void setBodyscore(int bodyscore) {
            this.bodyscore = bodyscore;
        }

        public String getBodydesc() {
            return bodydesc;
        }

        public void setBodydesc(String bodydesc) {
            this.bodydesc = bodydesc;
        }

        public int getCharacterscore() {
            return characterscore;
        }

        public void setCharacterscore(int characterscore) {
            this.characterscore = characterscore;
        }

        public String getCharacterdesc() {
            return characterdesc;
        }

        public void setCharacterdesc(String characterdesc) {
            this.characterdesc = characterdesc;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getSpare() {
            return spare;
        }

        public void setSpare(String spare) {
            this.spare = spare;
        }

        public void setSpare1(String spare1) {
            this.spare1 = spare1;
        }

        public String getSpare1() {

            return spare1;
        }

        public Object getSpare2() {
            return spare2;
        }

        public void setSpare2(Object spare2) {
            this.spare2 = spare2;
        }

        public String getCommentdesc() {
            return commentdesc;
        }

        public void setCommentdesc(String commentdesc) {
            this.commentdesc = commentdesc;
        }

        public String getCharacteristicdesc() {
            return characteristicdesc;
        }

        public void setCharacteristicdesc(String characteristicdesc) {
            this.characteristicdesc = characteristicdesc;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getObligate() {
            return obligate;
        }

        public void setObligate(String obligate) {
            this.obligate = obligate;
        }

        public int getFriend() {
            return friend;
        }

        public void setFriend(int friend) {
            this.friend = friend;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getLookimages() {
            return lookimages;
        }

        public void setLookimages(int lookimages) {
            this.lookimages = lookimages;
        }

        public String getZiwei() {
            return ziwei;
        }

        public void setZiwei(String ziwei) {
            this.ziwei = ziwei;
        }

        public int getSevenday() {
            return sevenday;
        }

        public void setSevenday(int sevenday) {
            this.sevenday = sevenday;
        }

        public int getThirthday() {
            return thirthday;
        }

        public void setThirthday(int thirthday) {
            this.thirthday = thirthday;
        }

        public int getLookhead() {
            return lookhead;
        }

        public void setLookhead(int lookhead) {
            this.lookhead = lookhead;
        }

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
