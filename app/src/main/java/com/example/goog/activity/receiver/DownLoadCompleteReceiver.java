package com.example.goog.activity.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by SeanM on 2017/3/20.
 */
public class DownLoadCompleteReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Toast.makeText(context, "编号："+id+"的下载任务已经完成！", Toast.LENGTH_SHORT).show();
        }else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)){
            Toast.makeText(context, "下载中。。！！！", Toast.LENGTH_SHORT).show();
        }
    }

}