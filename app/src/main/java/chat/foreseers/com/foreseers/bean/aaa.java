package chat.foreseers.com.foreseers.bean;

import java.util.List;

public class aaa {


    /**
     * errCode : 200
     * result : [{"user_id":2,"score":81,"desc":"DESC","comment":{"good":[],"bad":[],
     * "desc":"摩羯與金牛都屬於土象星座，特徵是內向、實際、刻苦又耐勞，您們之間絕不是浪漫的傳奇，而是有著\u201c血濃於水\u201d
     * 似的感情，並且細水長流。但首先得突破您與他被動式的愛情防護罩，才能踏出成功的第一步！最可能發生戀情的情況大都始於團體或工作伙伴、朋友、同學或一起參加活動。彼此的性格、思考模式與價值觀都大同小異，只要考慮認真交往後，就會朝著共同目標默默打拚；摩羯與牛兒的戀情多屬愛情長跑式，有的是一種柴米夫妻式的情感，外人看來也許乏味透頂，但個中甜蜜只有您們才懂！一個多疑，一個幻想，本來不是一對適當對象，但妙得是，這一對卻能非常相愛，會有幸福的生活。"},"characteristic":{"good":[],"bad":[],"desc":"行事正派，做事較為腳踏實地，注重流程和細節，不會走捷徑，擅長擔任管理階層或公務員，不適合擔任前線及銷售工作，以及帶有開創性質的職業。擁有溫和、親切柔順的人格特質，有愛心，會熱心幫助別人，處世平穩妥善，對外界反應非常被動，不喜歡衝突，容易因為缺乏自信而妥協。感情豐富，浪漫多情，也很顧家。心思細膩，但多疑，情緒容易起伏不定，也會因此而感情用事。"},"mind":{"score":84,"desc":"DESC"},"body":{"score":86,"desc":"DESC"},"character":{"score":78,"desc":"DESC"}}]
     * errMsg : OK
     */

    private int errCode;
    private String errMsg;
    private List<ResultBean> result;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * user_id : 2
         * score : 81
         * desc : DESC
         * comment : {"good":[],"bad":[],
         * "desc":"摩羯與金牛都屬於土象星座，特徵是內向、實際、刻苦又耐勞，您們之間絕不是浪漫的傳奇，而是有著\u201c血濃於水\u201d
         * 似的感情，並且細水長流。但首先得突破您與他被動式的愛情防護罩，才能踏出成功的第一步！最可能發生戀情的情況大都始於團體或工作伙伴、朋友、同學或一起參加活動。彼此的性格、思考模式與價值觀都大同小異，只要考慮認真交往後，就會朝著共同目標默默打拚；摩羯與牛兒的戀情多屬愛情長跑式，有的是一種柴米夫妻式的情感，外人看來也許乏味透頂，但個中甜蜜只有您們才懂！一個多疑，一個幻想，本來不是一對適當對象，但妙得是，這一對卻能非常相愛，會有幸福的生活。"}
         * characteristic : {"good":[],"bad":[],
         * "desc":"行事正派，做事較為腳踏實地，注重流程和細節，不會走捷徑，擅長擔任管理階層或公務員，不適合擔任前線及銷售工作，以及帶有開創性質的職業。擁有溫和、親切柔順的人格特質，有愛心，會熱心幫助別人，處世平穩妥善，對外界反應非常被動，不喜歡衝突，容易因為缺乏自信而妥協。感情豐富，浪漫多情，也很顧家。心思細膩，但多疑，情緒容易起伏不定，也會因此而感情用事。"}
         * mind : {"score":84,"desc":"DESC"}
         * body : {"score":86,"desc":"DESC"}
         * character : {"score":78,"desc":"DESC"}
         */

        private int user_id;
        private int score;
        private String desc;
        private CommentBean comment;
        private CharacteristicBean characteristic;
        private MindBean mind;
        private BodyBean body;
        private CharacterBean character;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public CharacteristicBean getCharacteristic() {
            return characteristic;
        }

        public void setCharacteristic(CharacteristicBean characteristic) {
            this.characteristic = characteristic;
        }

        public MindBean getMind() {
            return mind;
        }

        public void setMind(MindBean mind) {
            this.mind = mind;
        }

        public BodyBean getBody() {
            return body;
        }

        public void setBody(BodyBean body) {
            this.body = body;
        }

        public CharacterBean getCharacter() {
            return character;
        }

        public void setCharacter(CharacterBean character) {
            this.character = character;
        }

        public static class CommentBean {
            /**
             * good : []
             * bad : []
             * desc :
             * 摩羯與金牛都屬於土象星座，特徵是內向、實際、刻苦又耐勞，您們之間絕不是浪漫的傳奇，而是有著“血濃於水”似的感情，並且細水長流。但首先得突破您與他被動式的愛情防護罩，才能踏出成功的第一步！最可能發生戀情的情況大都始於團體或工作伙伴、朋友、同學或一起參加活動。彼此的性格、思考模式與價值觀都大同小異，只要考慮認真交往後，就會朝著共同目標默默打拚；摩羯與牛兒的戀情多屬愛情長跑式，有的是一種柴米夫妻式的情感，外人看來也許乏味透頂，但個中甜蜜只有您們才懂！一個多疑，一個幻想，本來不是一對適當對象，但妙得是，這一對卻能非常相愛，會有幸福的生活。
             */

            private String desc;
            private List<?> good;
            private List<?> bad;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public List<?> getGood() {
                return good;
            }

            public void setGood(List<?> good) {
                this.good = good;
            }

            public List<?> getBad() {
                return bad;
            }

            public void setBad(List<?> bad) {
                this.bad = bad;
            }
        }

        public static class CharacteristicBean {
            /**
             * good : []
             * bad : []
             * desc :
             * 行事正派，做事較為腳踏實地，注重流程和細節，不會走捷徑，擅長擔任管理階層或公務員，不適合擔任前線及銷售工作，以及帶有開創性質的職業。擁有溫和、親切柔順的人格特質，有愛心，會熱心幫助別人，處世平穩妥善，對外界反應非常被動，不喜歡衝突，容易因為缺乏自信而妥協。感情豐富，浪漫多情，也很顧家。心思細膩，但多疑，情緒容易起伏不定，也會因此而感情用事。
             */

            private String desc;
            private List<?> good;
            private List<?> bad;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public List<?> getGood() {
                return good;
            }

            public void setGood(List<?> good) {
                this.good = good;
            }

            public List<?> getBad() {
                return bad;
            }

            public void setBad(List<?> bad) {
                this.bad = bad;
            }
        }

        public static class MindBean {
            /**
             * score : 84
             * desc : DESC
             */

            private int score;
            private String desc;

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class BodyBean {
            /**
             * score : 86
             * desc : DESC
             */

            private int score;
            private String desc;

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class CharacterBean {
            /**
             * score : 78
             * desc : DESC
             */

            private int score;
            private String desc;

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
