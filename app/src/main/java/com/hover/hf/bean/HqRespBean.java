package com.hover.hf.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hover on 2017/6/15.
 */

public class HqRespBean<T> implements Serializable {
    private int code;
    private String msg;
    private MetaBean meta;
    private List<T> data;

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

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static class MetaBean implements Serializable {
        /**
         * pagination : {"total":16166,"count":20,"per_page":20,"current_page":1,"total_pages":809}
         */

        private PaginationBean pagination;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public static class PaginationBean implements Serializable {
            /**
             * total : 16166
             * count : 20
             * per_page : 20
             * current_page : 1
             * total_pages : 809
             */

            private int total;
            private int count;
            private int per_page;
            private int current_page;
            private int total_pages;
            private String links;

            public String getLinks() {
                return links;
            }

            public void setLinks(String links) {
                this.links = links;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getPer_page() {
                return per_page;
            }

            public void setPer_page(int per_page) {
                this.per_page = per_page;
            }

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public int getTotal_pages() {
                return total_pages;
            }

            public void setTotal_pages(int total_pages) {
                this.total_pages = total_pages;
            }
        }
    }
}
