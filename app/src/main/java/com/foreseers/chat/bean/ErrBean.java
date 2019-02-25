package com.foreseers.chat.bean;

public class ErrBean {

    /**
     * status : fail
     * data : {"errCode":2000,"errMsg":"用户擦子数为0"}
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
         * errCode : 2000
         * errMsg : 用户擦子数为0
         */

        private int errCode;
        private String errMsg;

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
    }
}
