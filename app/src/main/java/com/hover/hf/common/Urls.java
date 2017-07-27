package com.hover.hf.common;

/**
 * Created by hover on 2017/6/17.
 */

public class Urls {
    public static final String IP = "192.168.0.106";
    public static final String PORT = "8081";
    public static final String APP_NAME = "Tp5ApiServer";
    public static final String REQHOT = String.format("http://%s:%s/%s/public/index.php/index/hqclient/hot", IP, PORT, APP_NAME);
    public static final String LOGIN = String.format("http://%s:%s/%s/public/index.php/index/hqclient/login", IP, PORT, APP_NAME);
    public static final String REGISTER = String.format("http://%s:%s/%s/public/index.php/index/hqclient/register", IP, PORT, APP_NAME);
    public static final String UPLOADFILE = String.format("http://%s:%s/%s/public/index.php/index/hqclient/upload", IP, PORT, APP_NAME);
    public static final String UPLOADFILE3 = String.format("http://%s:%s/%s/public/index.php?s=/index/hqclient/upload", IP, PORT, APP_NAME);
    public static final String UPLOADFILE2 = String.format("http://%s:%s/%s/public/index.php", IP, PORT, APP_NAME);

}
