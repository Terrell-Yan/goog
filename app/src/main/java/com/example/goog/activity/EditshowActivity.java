package com.example.goog.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goog.R;
import com.example.goog.activity.bean.Grigu;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.base.BaseActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by SeanM on 2017/4/5.
 */

public class EditshowActivity extends BaseActivity implements View.OnClickListener{
    private ImageView imageView,gridview_iv2;
    private static String path = "/sdcard/DemoHead/";//sd路径
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";
    Bitmap head;
    File myCaptureFile;
    private EditText editText;
    private TextView textView;
    private String url;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        verifyStoragePermissions(act);

        setContentView(R.layout.edit);
        textView=tvByid(R.id.edit_but);
        imageView=imgByid(R.id.edit_img);
        gridview_iv2=imgByid(R.id.gridview_iv2);
        editText=(EditText) findViewById(R.id.edit_et);
        imageView.setOnClickListener(this);
        textView.setOnClickListener(this);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从相册里面取相片的返回结果
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }

                break;

            //调用系统裁剪图片后
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        saveFileRunnable.run();

                       final BmobFile bmobFile = new BmobFile(myCaptureFile);
                        bmobFile.uploadblock(new UploadFileListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                    taost("上传文件成功:" + bmobFile.getFileUrl());
                                    url=bmobFile.getFileUrl();
                                }else{
                                    taost("上传文件失败：" + e.getMessage());
                                }

                            }

                            @Override
                            public void onProgress(Integer value) {
                                // 返回的上传进度（百分比）
                            }
                        });
                        gridview_iv2.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);


    }
    private Runnable saveFileRunnable = new Runnable(){
        @Override
        public void run() {
            try {
                saveFile(head, "jdak.jpg");
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(myCaptureFile);
                intent.setData(uri);
                sendBroadcast(intent);
            } catch (IOException e) {

                e.printStackTrace();
            }

        }

    };
    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        //找到指定URI对应的资源图片
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        //进入系统裁剪图片的界面
        startActivityForResult(intent, 3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_but:
                String text=editText.getText().toString();
                MyUser user = BmobUser.getCurrentUser(MyUser.class);
// 创建帖子信息
                String username = (String) user.getObjectByKey("nickName");
                String avatar2 = (String) user.getObjectByKey("avatarimg");
                Grigu post = new Grigu();
                post.setDynamic_text(text);
//添加一对一关联
                if ("".equals(url)){
                    return;
                }
                final Calendar mCalendar= Calendar.getInstance();



               long mHour=mCalendar.get(Calendar.HOUR);

               long mMinuts=mCalendar.get(Calendar.MINUTE);
                post.setDynamic_time(mHour+" "+mMinuts);
                post.setDynamic_name(username);
                post.setDynamic_img(avatar2);
                post.setAuthor(user);
                post.setDynamic_img2(url);
                post.save(new SaveListener<String>() {

                    @Override
                    public void done(String objectId,BmobException e) {
                        if(e==null){
                            finish();
                        }else{

                        }
                    }
                });

                break;
            case R.id.edit_img:
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
//                System.out.println("MediaStore.Images.Media.EXTERNAL_CONTENT_URI  ------------>   "
//                        + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//   content://media/external/images/media
                startActivityForResult(intent1, 1);
                break;
        }
    }

    public void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * * Checks if the app has permission to write to device storage
     * *
     * * If the app does not has permission then the user will be prompted to
     * * grant permissions
     * *
     * * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
