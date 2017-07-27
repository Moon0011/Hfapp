package com.hover.hf.bean;

/**
 * Created by hover on 2017/6/19.
 */

public class RegisterBean {

    /**
     * code : 200
     * msg : success
     * data : {"info":"注册成功!","isOk":1}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * info : 注册成功!
         * isOk : 1
         */

        private String info;
        private int isOk;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public boolean isOk() {
            if (isOk == 1) {
                return true;
            } else {
                return false;
            }
        }

        public void setOk(boolean ok) {
            if (ok == true) {
                this.isOk = 1;
            } else {
                this.isOk = 0;
            }
        }
    }
}
