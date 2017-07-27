package com.hover.hf.bean;

/**
 * Created by hover on 2017/6/24.
 */

public class HeadRespBean {

    /**
     * code : 200
     * msg : success
     * data : {"info":"修改头像成功！","path":"http://192.168.0.108:8081/Tp5ApiServer/public/uploads/pic0.jpg","filename":"pic0.jpg"}
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
         * info : 修改头像成功！
         * path : http://192.168.0.108:8081/Tp5ApiServer/public/uploads/pic0.jpg
         * filename : pic0.jpg
         */

        private String info;
        private String path;
        private String filename;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }
}
