package com.foreseers.chat.bean;

public class ShopCheckBean {
    /**
     * status : success
     * data : {}
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
         * purchaseToken :
         * productId :
         * status : 0
         */

        String purchaseToken;
        String productId;
        int status;

        public void setPurchaseToken(String purchaseToken) {
            this.purchaseToken = purchaseToken;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPurchaseToken() {

            return purchaseToken;
        }

        public String getProductId() {
            return productId;
        }

        public int getStatus() {
            return status;
        }
    }
}
