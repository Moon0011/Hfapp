package com.hover.hf;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.hover.hf.bean.LoginRespBean.UserInfo;
import com.hover.hf.cache.DataCleanManager;
import com.hover.hf.common.Constants;
import com.hover.hf.ui.base.BaseApplication;
import com.hover.hf.ui.map.LocationService;
import com.hover.hf.util.CyptoUtils;
import com.hover.hf.util.MethodsCompat;
import com.hover.hf.util.StringUtils;

import org.kymjs.kjframe.Core;
import org.kymjs.kjframe.http.HttpConfig;
import org.xutils.x;

import java.util.Properties;
import java.util.UUID;

import static com.hover.hf.AppConfig.KEY_FRITST_START;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 *
 * @author 火蚁 (http://my.oschina.net/LittleDY)
 * @version 1.0
 * @created 2014-04-22
 */
public class AppContext extends BaseApplication {

    private static AppContext instance;
    private boolean login;
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //xUtils初始化
        x.Ext.init(this); // 这一步之后, 我们就可以在任何地方使用x.app()来获取Application的实例了.
        x.Ext.setDebug(true); // 是否输出debug日志

        init();
        initLogin();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
    }

    private void init() {
        // 初始化网络请求
//        AsyncHttpClient client = new AsyncHttpClient();
//        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
//        client.setCookieStore(myCookieStore);
//        ApiHttpClient.setHttpClient(client);
//        ApiHttpClient.setCookie(ApiHttpClient.getCookie(this));

        // Log控制器
//        KJLoger.openDebutLog(BuildConfig.DEBUG);
//        TLog.DEBUG = BuildConfig.DEBUG;

        // Bitmap缓存地址
        HttpConfig.CACHEPATH = "OSChina/imagecache";
    }

    private void initLogin() {
        UserInfo userInfo = getLoginUser();
        if (null != userInfo && userInfo.getId() > 0) {
            login = true;
        } else {
            this.cleanLoginInfo();
        }
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 获取cookie时传AppConfig.CONF_COOKIE
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String res = AppConfig.getAppConfig(this).get(key);
        return res;
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    public boolean isLogin() {
        return login;
    }

    /**
     * 保存登录信息
     */
    @SuppressWarnings("serial")
    public void saveUserInfo(final UserInfo userInfo) {
        this.login = true;
        setProperties(new Properties() {
            {
                setProperty("user.uid", String.valueOf(userInfo.getId()));
                setProperty("user.account", userInfo.getAccount());
                setProperty("user.face", userInfo.getHeadimg());// 用户头像-文件名
                setProperty("user.pwd",
                        CyptoUtils.encode("hfapp_encode", userInfo.getPwd()));
                setProperty("user.isLogin",
                        String.valueOf(userInfo.isLogin()));// 是否记住我的信息
            }
        });
    }

    /**
     * 更新用户信息
     */
    @SuppressWarnings("serial")
    public void updateUserInfo(final UserInfo userInfo) {
        setProperties(new Properties() {
            {
                setProperty("user.account", userInfo.getAccount());
                setProperty("user.face", userInfo.getHeadimg());// 用户头像-文件名

            }
        });
    }

    /**
     * 获得登录用户的信息
     *
     * @return
     */
    public UserInfo getLoginUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(StringUtils.toInt(getProperty("user.uid"), 0));
        userInfo.setHeadimg(getProperty("user.face"));
        userInfo.setAccount(getProperty("user.account"));
        userInfo.setLogin(StringUtils.toBool(getProperty("user.isLogin")));
        return userInfo;
    }

    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        this.login = false;
        removeProperty("user.uid", "user.account", "user.face", "user.pwd",
                "user.isLogin");
    }

    /**
     * 用户注销
     */
    public void logOut() {
        cleanLoginInfo();
//        ApiHttpClient.cleanCookie();
//        this.cleanCookie();
        this.login = false;
        Intent intent = new Intent(Constants.INTENT_ACTION_LOGOUT);
        sendBroadcast(intent);
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
        Core.getKJBitmap().cleanCache();
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    public static boolean isFristStart() {
        return getPreferences().getBoolean(KEY_FRITST_START, true);
    }

    public static void setFristStart(boolean frist) {
        set(KEY_FRITST_START, frist);
    }

    public static Gson createGson() {
        com.google.gson.GsonBuilder gsonBuilder = new com.google.gson.GsonBuilder();
        //gsonBuilder.setExclusionStrategies(new SpecificClassExclusionStrategy(null, Model.class));
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        return gsonBuilder.create();
    }
}
