package com.foreseers.chat.bean;

public class GoogleCheckBean {

    /**
     * status : success
     * data : {"purchaseToken":"opaque-token-up-to-1000-characters","productId":"exampleSku","status":1}
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
         * purchaseToken : opaque-token-up-to-1000-characters
         * productId : exampleSku
         * status : 1
         */

        private String purchaseToken;
        private String productId;
        private int status;

        public String getPurchaseToken() {
            return purchaseToken;
        }

        public void setPurchaseToken(String purchaseToken) {
            this.purchaseToken = purchaseToken;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
