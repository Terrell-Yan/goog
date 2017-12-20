package com.example.goog.base;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;


import com.example.goog.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static cn.bmob.v3.Bmob.getApplicationContext;


public class MyAppication extends Application{

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

//        imageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).cacheInMemory(true).build();
//创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

    }
    private static Map<String, Object> datas = new LinkedHashMap<>();

    /**
     * 在集合中存放数据,用来和多个界面数据进行传递
     *
     * @param key
     *            填充的数据的KEy
     * @param value
     *            填充的数据
     * @return 如果此KEy已经与其他值产生映射关系,覆盖式返回旧值,如果没有映射关系返回null
     */
    public static Object put(String key, Object value) {
        return datas.put(key, value);

    }

    /**
     * 从全局的数据容器获取数据
     *
     * @param key
     *            获取数据的key
     * @param isDelete
     *            本次获取是否将获取的数据删除
     * @return 本次获取的数据
     */
    public static Object get(String key, boolean isDelete) {
        if (isDelete) {
            return datas.remove(key);
        }

        return datas.get(key);

    }

}
