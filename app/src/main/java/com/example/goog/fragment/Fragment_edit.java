package com.example.goog.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goog.R;
import com.example.goog.activity.Calendar_Activity;
import com.example.goog.activity.CollectionActivity;
import com.example.goog.activity.Couresfinsh;
import com.example.goog.activity.LoginAcivity;
import com.example.goog.activity.bean.Collection;
import com.example.goog.activity.bean.Girl;
import com.example.goog.activity.bean.MyUser;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.activity.bean.New_data;
import com.example.goog.activity.bean.UserExtend;
import com.example.goog.activity.bean.User_Et;
import com.example.goog.base.BaseFragment;
import com.example.goog.base.MyAppication;
import com.example.goog.powpwiond.PopWindowKG;
import com.example.goog.utils.SPUtils;
import com.example.goog.view.MyImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.okhttp.internal.Util;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;

/**
 * 个人中心
 * Created by SeanM on 2017/3/29.
 */

public class Fragment_edit extends BaseFragment implements View.OnClickListener {
    private TextView in_tv, signin, steep, fans;
    private MyImageView imageView;
    private DisplayImageOptions imageOptions;
    private ImageLoader imageLoader;
    private LinearLayout linearLayout, five_config,shengwang_layout,my_experience,body;
    private File tempFile;
    private static String path = "/sdcard/DemoHead/";//sd路径
    //    private File fileName;
    public final static File file = new File("sdcard/touxiang.png");
    private ImageView im2;
    private String fileName;
    private String url;
    private MyUser user;
    private List<User_Et> listdat;
    private int numm = 0;

    @Override
    public View initLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.five, null);
        in_tv = (TextView) view.findViewById(R.id.five_nickname);
        imageView = (MyImageView) view.findViewById(R.id.avatar);
        linearLayout = (LinearLayout) view.findViewById(R.id.logout);
        shengwang_layout = (LinearLayout) view.findViewById(R.id.shengwang_layout);
        my_experience = (LinearLayout) view.findViewById(R.id.my_experience);
        im2 = (ImageView) view.findViewById(R.id.five_edit);
        five_config = (LinearLayout) view.findViewById(R.id.course);
        signin = (TextView) view.findViewById(R.id.signin);
        steep = (TextView) view.findViewById(R.id.expnum);
        fans = (TextView) view.findViewById(R.id.shengwangnum);
        body= (LinearLayout) view.findViewById(R.id.body);
        return view;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 404:
                    Bundle bundle = msg.getData();
                    String text = bundle.getString("kg");
                    in_tv.setText(text);
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
        im2.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        my_experience.setOnClickListener(this);
        shengwang_layout.setOnClickListener(this);
        imageView.setOnClickListener(this);
        five_config.setOnClickListener(this);
        user = BmobUser.getCurrentUser(MyUser.class);
        signin.setOnClickListener(this);
        body.setOnClickListener(this);
        if (user != null) {

            String name = (String) user.getObjectByKey("nickName");
            String avatar = (String) user.getObjectByKey("avatarimg");

            Log.d("Log", name + "login---------");
            Log.d("Log", avatar + "login---------tou");
            in_tv.setText(name);
            //显示图片的配置
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_launcher)
                    .showImageOnFail(R.drawable.ic_launcher)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

            ImageLoader.getInstance().displayImage(avatar, imageView, options);

            if (!"".equals(avatar)) {

                ImageLoader.getInstance().displayImage(avatar, imageView, imageOptions);
            } else {
                Toast.makeText(getActivity(), "meiyoutupaim", Toast.LENGTH_SHORT).show();
            }
//            Therdone therdone = new Therdone();
//            therdone.start();
        } else {

            in_tv.setOnClickListener(this);
        }
        if (numm == 0) {

        }
//            addUserextend();
    }

    public void addUserextend() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        if (user != null) {


// 创建帖子信息
            String username = (String) user.getObjectByKey("nickName");
            String avatar2 = (String) user.getObjectByKey("avatarimg");
            Collection post = new Collection();
//            post.setUrl(url);
//添加一对一关联
            post.setAuthor(user);


            post.save(new SaveListener<String>() {

                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
//                    finish();
                    } else {

                    }
                }
            });
        } else {
            Toast.makeText(act, "您还未登录，请登录后操作", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.five_nickname:
                Intent intent2 = new Intent(getContext(), LoginAcivity.class);
                startActivity(intent2);
                break;
            case R.id.logout:
                BmobUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
                Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                System.exit(0);
                break;
            case R.id.avatar:
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
//                System.out.println("MediaStore.Images.Media.EXTERNAL_CONTENT_URI  ------------>   "
//                        + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//   content://media/external/images/media
                startActivityForResult(intent1, 1);
                break;
            case R.id.five_edit:
                PopWindowKG popWindowKG = new PopWindowKG(act, handler);
                popWindowKG.showPopupWindowBottom(v);
                break;
            case R.id.course:
                if (user != null) {
                    Intent intne = new Intent(act, CollectionActivity.class);
                    startActivity(intne);
                } else {
                    toastS("您还没有登录");
                }

                break;
            case R.id.signin:
                if (user != null) {

                    Intent intne = new Intent(act, Calendar_Activity.class);
                    startActivity(intne);

                } else {
                    toastS("您还没有登录");
                }
                break;
            case R.id.shengwang_layout:
                if (user != null) {

                    Intent intne = new Intent(act, Couresfinsh.class);
                    intne.putExtra("1","finsh");
                    startActivity(intne);

                } else {
                    toastS("您还没有登录");
                }
                break;
            case R.id.my_experience:
                if (user != null) {

                    Intent intne = new Intent(act, Couresfinsh.class);
                    intne.putExtra("2","finsh");
                    startActivity(intne);

                } else {
                    toastS("您还没有登录");
                }
                break;
            case R.id.body:
                if (user != null) {

                    Intent intne = new Intent(act, Couresfinsh.class);
                    intne.putExtra("2","finsh");
                    startActivity(intne);

                } else {
                    toastS("您还没有登录");
                }
                break;
        }

    }


    @Override
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
                    Bitmap head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        saveHttp();
                        imageView.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void saveHttp() {
        final BmobFile bmobFile = new BmobFile(new File(fileName));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址

                    url = bmobFile.getFileUrl();
                    MyUser newUser = new MyUser();
                    newUser.setAvatarimg(url);
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
//        imageView.setImageBitmap(head);//用ImageView显示出来

    }

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

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd卡是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建以此File对象为名（path）的文件夹
        fileName = path + "nima.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件（compress：压缩）

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //获取用户扩展的属性

    class Therdone extends Thread {
        @Override
        public void run() {
            super.run();
            MyUser user = BmobUser.getCurrentUser(MyUser.class);
            BmobQuery<Collection> query = new BmobQuery<Collection>();
            query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
            query.order("-updatedAt");
            query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
            query.findObjects(new FindListener<Collection>() {

                @Override
                public void done(List<Collection> object, BmobException e) {
                    if (e == null) {
                        listdat = new ArrayList<>();

                        for (Collection collection : object) {
                            New_data new_data = new New_data();
                            new_data.steep_time = collection.getSteep_time();
                            fans.setText(collection.getSteep_time());

                        }
                        Log.i("bmob", "成功" + object.size() + "数据" + listdat);

                    } else {
                        Log.i("bmob", "失败：" + e.getMessage());
                    }
                }

            });
        }
    }
}
