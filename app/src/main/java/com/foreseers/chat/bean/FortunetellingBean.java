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
     * data : [{"name":"自身","sign":1,"storeId":"free"},{"name":"愛情","sign":0,"storeId":"iap1"},{"name":"事業及學業","sign":0,"storeId":"iap2"},
     * {"name":"資產及財運","sign":0,"storeId":"iap3"},{"name":"家庭及人際","sign":0,"storeId":"iap4"},{"name":"2018流年運程","sign":0,"storeId":"iap6"},
     * {"name":"2019流年運程","sign":0,"storeId":"iap7"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public List<DataBean> getData() { return data;}

    public void setData(List<DataBean> data) { this.data = data;}

    public static class DataBean {
        /**
         * name : 自身
         * sign : 1
         * storeId : free
         */

        private String name;
        private int sign;
        private String storeId;
        private int colour;

        public void setColour(int colour) {
            this.colour = colour;
        }

        public int getColour() {

            return colour;
        }

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public int getSign() { return sign;}

        public void setSign(int sign) { this.sign = sign;}

        public String getStoreId() { return storeId;}

        public void setStoreId(String storeId) { this.storeId = storeId;}
    }
}
