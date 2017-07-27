package com.hover.hf.bean;

/**
 * Created by hover on 2017/6/18.
 */

public class LoginRespBean {

    /**
     * code : 200
     * msg : success
     * data : {"info":"登录成功!","isLogin":1,"account":"hover",""}
     */

    private int code;
    private String msg;
    private UserInfo data;

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

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public static class UserInfo {
        /**
         * info : 登录成功!
         * isLogin : 1
         * account : hover
         */
        private int id;
        private String info;
        private int isLogin;
        private String account;
        private String headimg;
        private String pwd;
        private int loginType;

        public int getLoginType() {
            return loginType;
        }

        public void setLoginType(int loginType) {
            this.loginType = loginType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public boolean isLogin() {
            if (isLogin == 1) {
                return true;
            } else {
                return false;
            }
        }

        public void setLogin(boolean login) {
            if (login == true) {
                this.isLogin = 1;
            } else {
                this.isLogin = 0;
            }
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "id=" + id +
                    ", info='" + info + '\'' +
                    ", isLogin=" + isLogin +
                    ", account='" + account + '\'' +
                    ", headimg='" + headimg + '\'' +
                    ", pwd='" + pwd + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginRespBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
