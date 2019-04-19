package com.foreseers.chat.bean;

import java.util.List;

/**
 * File description.
 *
 * @author how
 * @date 2019/3/28
 */
public class FortunetellingBean {

    /**
     * status : success
     * data : [{"size":11,"name":"自身","sign":1,"storeId":"free"},{"size":9,"name":"愛情","sign":0,"storeId":"iap1"},{"size":12,"name":"事業及學業",
     * "sign":0,"storeId":"iap2"},{"size":8,"name":"資產及財運","sign":0,"storeId":"iap3"},{"size":8,"name":"家庭及人際","sign":0,"storeId":"iap4"},
     * {"size":13,"name":"2018流年運程","sign":0,"storeId":"iap6"},{"size":13,"name":"2019流年運程","sign":0,"storeId":"iap7"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public List<DataBean> getData() { return data;}

    public void setData(List<DataBean> data) { this.data = data;}

    public static class DataBean {
        /**
         * size : 11
         * name : 自身
         * sign : 1
         * storeId : free
         * lifeuserid
         * colour
         * price
         * img_title
         * img_content
         */

        private int size;
        private String name;
        private int sign;
        private int colour;
        private int imgtitle;
        private int imgcontent;
        private String storeId;
        private String lifeuserid;
        private String price;

        public void setImgtitle(int imgtitle) {
            this.imgtitle = imgtitle;
        }

        public void setImgcontent(int imgcontent) {
            this.imgcontent = imgcontent;
        }

        public int getImgcontent() {

            return imgcontent;
        }

        public int getImgtitle() {

            return imgtitle;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrice() {

            return price;
        }

        public void setLifeuserid(String lifeuserid) {
            this.lifeuserid = lifeuserid;
        }

        public String getLifeuserid() {

            return lifeuserid;
        }

        public void setColour(int colour) {
            this.colour = colour;
        }

        public int getColour() {

            return colour;
        }

        public int getSize() { return size;}

        public void setSize(int size) { this.size = size;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public int getSign() { return sign;}

        public void setSign(int sign) { this.sign = sign;}

        public String getStoreId() { return storeId;}

        public void setStoreId(String storeId) { this.storeId = storeId;}
    }
}
