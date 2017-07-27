/**
 * Copyright 2017 bejson.com
 */
package com.hover.hf.bean;

import java.util.List;

public class SpotData {

    private int code;
    private String msg;
    private List<SpotInfo> spots;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public List<SpotInfo> getSpots() {
        return spots;
    }

    public void setSpots(List<SpotInfo> spots) {
        this.spots = spots;
    }
}