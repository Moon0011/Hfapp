package com.hover.hf;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hover.hf.adapter.SlideListAdapter;
import com.hover.hf.adapter.ViewSwapperAdapter;
import com.hover.hf.bean.HeadRespBean;
import com.hover.hf.bean.HeadRespBean.DataBean;
import com.hover.hf.bean.LoginRespBean.UserInfo;
import com.hover.hf.common.Constants;
import com.hover.hf.common.UiHelper;
import com.hover.hf.common.Urls;
import com.hover.hf.ui.base.BaseActivity;
import com.hover.hf.ui.login.LoginActivity;
import com.hover.hf.ui.map.LocationFilter;
import com.hover.hf.ui.saoyisao.ScannerActivity;
import com.hover.hf.util.StringUtils;
import com.hover.hf.widget.adaptablebottomnav.view.AdaptableBottomNavigationView;
import com.hover.hf.widget.adaptablebottomnav.view.ViewSwapper;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.hover.hf.common.Constants.CHANGE_HEAD_ERROR;
import static com.hover.hf.common.Constants.CHANGE_HEAD_RESP;
import static com.hover.hf.common.Constants.MY_PERMISSIONS_REQUEST_CALL_PHONE2;
import static com.hover.hf.ui.base.BaseApplication.showToast;

public class MainActivity extends BaseActivity implements OnItemClickListener {
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.id_drawerlayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.view_swapper)
    ViewSwapper viewSwapper;
    @Bind(R.id.view_bottom_navigation)
    AdaptableBottomNavigationView bottomNavigationView;
    @Bind(R.id.listview)
    ListView listView;
    @Bind(R.id.btn_logo)
    Button btnLogo;

    private ViewSwapperAdapter viewSwapperAdapter;
    private Handler mHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.main1;
    }

    @Override
    public void initView() {
        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_NOTICE);
        filter.addAction(Constants.INTENT_ACTION_LOGOUT);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void initData() {
        mHandler = new MHandler();
        viewSwapperAdapter = new ViewSwapperAdapter(getSupportFragmentManager());
        viewSwapper.setAdapter(viewSwapperAdapter);
        bottomNavigationView.setupWithViewSwapper(viewSwapper);
        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        });
        //侧边栏
        SlideListAdapter slideListAdapter = new SlideListAdapter(Arrays.asList(Constants.SLIDELIST), mContext);
        listView.setAdapter(slideListAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case Constants.FEEDBACK:
                break;
            case Constants.LOGOUT:
                AppContext.getInstance().logOut();
                break;
            case Constants.SETTING:
                break;
            case Constants.ABOUT:
                startActivity(new Intent(MainActivity.this, LocationFilter.class));
                break;
            case Constants.SAOYISAO:
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 60);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @OnClick({R.id.img_head, R.id.btn_logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_head:
                if (AppContext.getInstance().isLogin()) {
                    UiHelper.showPopueWindow(this);
                }
                break;
            case R.id.btn_logo:
                if (!AppContext.getInstance().isLogin()) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppContext.getInstance().isLogin()) {
            UserInfo userInfo = AppContext.getInstance().getLoginUser();
            btnLogo.setText(userInfo.getAccount());
            Picasso.with(mContext)
                    .load(userInfo.getHeadimg())
                    .placeholder(R.mipmap.head)
                    .error(R.mipmap.head)
                    .into(imgHead);
        } else {
            btnLogo.setText(R.string.login);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mReceiver = null;
        AppManager.getAppManager().removeActivity(this);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction()
                    .equals(Constants.INTENT_ACTION_LOGOUT)) {
                btnLogo.setText(R.string.login);
                imgHead.setImageResource(R.mipmap.m_head);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                //noinspection unchecked
                List<ImageItem> images = (List<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                upload(images.get(0));
            } else {
                showToast("没有数据");
            }
        }
//        if (resultCode == RESULT_OK) {
//            if (requestCode == Constants.RESULT_LOCAL_IMAGE && null != data) {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                Cursor cursor = getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                final String picturePath = cursor.getString(columnIndex);
//
//                upload(picturePath);
//                cursor.close();
//            } else if (requestCode == Constants.RESULT_CAMERA_IMAGE) {
//
//                SimpleTarget target = new SimpleTarget<Bitmap>() {
//
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
////                        upload(saveMyBitmap(resource).getAbsolutePath());
//                    }
//
//                    @Override
//                    public void onLoadStarted(Drawable placeholder) {
//                        super.onLoadStarted(placeholder);
//
//                    }
//
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        super.onLoadFailed(e, errorDrawable);
//
//                    }
//                };
//
////                Glide.with(RegisterUIActivity.this).load(mCurrentPhotoPath)
////                        .asBitmap()
////                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
////                        .override(1080, 1920)//图片压缩
////                        .centerCrop()
////                        .dontAnimate()
////                        .into(target);
//
//
//            }
//        }
    }

    private void upload(ImageItem imageItem) {
        final String filePath = imageItem.path;
        final File uploadFile = new File(filePath);
//        final String filePath2 = "/storage/emulated/0/Android/pic0.jpg";
//        File uploadFile2 = new File(filePath2);
        if (!uploadFile.exists()) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
//                uploadPIC(filePath);
                uploadPICByxUtils(filePath);
            }
        }).start();
    }

    private void uploadPICByxUtils(String filePath) {
        RequestParams params = new RequestParams(Urls.UPLOADFILE2);
        // 添加到请求body体的参数, 只有POST, PUT, PATCH, DELETE请求支持.
        // params.addBodyParameter("wd", "xUtils");
        params.addQueryStringParameter("s", "/index/hqclient/upload");//tp5移除了url普通模式
        // 使用multipart表单上传文件
        params.setMultipart(true);
        params.addBodyParameter(
                "file",
                new File(filePath),
                "image/png");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                HeadRespBean headRespBean = gson.fromJson(result, HeadRespBean.class);
                Message msg = mHandler.obtainMessage();
                if (null == headRespBean) {
                    msg.what = CHANGE_HEAD_ERROR;
                    mHandler.sendMessage(msg);
                    return;
                }
                msg.what = CHANGE_HEAD_RESP;
                msg.obj = headRespBean.getData();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void uploadPIC(String filePath) {
        File uploadFile = new File(filePath);
        OkHttpClient client = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), uploadFile);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", filePath.substring(filePath.lastIndexOf("/") + 1), fileBody)
                .build();
        Request request = new Request.Builder()
                .url(Urls.UPLOADFILE3)
                .post(requestBody)
                .addHeader("account", AppContext.getInstance().getLoginUser().getAccount())
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.e("hw", " upload jsonString =" + jsonString);
            Gson gson = new Gson();
            HeadRespBean headRespBean = gson.fromJson(jsonString, HeadRespBean.class);
            Message msg = mHandler.obtainMessage();
            if (null == headRespBean) {
                msg.what = CHANGE_HEAD_ERROR;
                mHandler.sendMessage(msg);
                return;
            }
            msg.what = CHANGE_HEAD_RESP;
            msg.obj = headRespBean.getData();
            mHandler.sendMessage(msg);
        } catch (IOException e) {
            Log.e("hw", "upload IOException ", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                takePhoto();
//            } else {
//                // Permission Denied
//                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Constants.RESULT_LOCAL_IMAGE);
            } else {
                // Permission Denied
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHANGE_HEAD_RESP:
                    DataBean dateBean = ((DataBean) msg.obj);
                    if (StringUtils.isEmpty(dateBean.getPath())) {
                        Toast.makeText(mContext, ((HeadRespBean.DataBean) msg.obj).getInfo(), Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    Picasso.with(mContext)
//                            .load(dateBean.getPath())
//                            .placeholder(R.mipmap.head)
//                            .error(R.mipmap.head)
//                            .into(imgHead);
                    x.image().bind(imgHead,
                            dateBean.getPath(),
                            imageOptions,
                            null);
                    Toast.makeText(mContext, ((HeadRespBean.DataBean) msg.obj).getInfo(), Toast.LENGTH_SHORT).show();
                    break;
                case CHANGE_HEAD_ERROR:
                    Toast.makeText(mContext, "图片更换出错", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}

