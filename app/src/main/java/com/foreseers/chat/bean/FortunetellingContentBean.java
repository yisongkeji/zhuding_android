package com.foreseers.chat.bean;

import java.util.List;

/**
 * File description.
 *
 * @author how
 * @date 2019/4/1
 */
public class FortunetellingContentBean {

    /**
     * status : success
     * data : [{"icon":"https://docs.foreseers.com/lifebook/a1.jpg",
     * "comment":"一臉福相，面型圓中帶方，下巴圓潤，眉型輕柔而不帶眉峰，毛髮纖細柔軟。臉型方長，額頭較高，中年後易禿頭，眉毛修長且顏色淺淡，常給人老成持重的感覺。"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public List<DataBean> getData() { return data;}

    public void setData(List<DataBean> data) { this.data = data;}

    public static class DataBean {
        /**
         * icon : https://docs.foreseers.com/lifebook/a1.jpg
         * comment : 一臉福相，面型圓中帶方，下巴圓潤，眉型輕柔而不帶眉峰，毛髮纖細柔軟。臉型方長，額頭較高，中年後易禿頭，眉毛修長且顏色淺淡，常給人老成持重的感覺。
         */

        private String icon;
        private String comment;

        public String getIcon() { return icon;}

        public void setIcon(String icon) { this.icon = icon;}

        public String getComment() { return comment;}

        public void setComment(String comment) { this.comment = comment;}
    }
}
