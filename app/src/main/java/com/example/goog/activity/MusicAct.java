package com.example.goog.activity;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goog.R;
import com.example.goog.activity.bean.LyricContent;
import com.example.goog.activity.bean.Myimg;
import com.example.goog.base.BaseActivity;
import com.example.goog.handle.LrcRead;
import com.example.goog.services.MusicService;
import com.example.goog.view.LyricView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.key;

/**
 * Created by SeanM on 2017/4/7.
 */

public class MusicAct extends BaseActivity implements View.OnClickListener {
    private ImageView head, play, next, add;
    Intent intent2 = getIntent();
    private ArrayList<Myimg> listdat;
    private RecyclerView recyclerView;
    private MusicService musicService;
    private SeekBar seekBar;
    private TextView musicStatus, musicTime;
    private boolean flag = false;
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    private LyricView mLyricView;
    private LrcRead mLrcRead;
    private int CurrentTime = 0;
    private int index;
    private int CountTime = 0;
    private List<LyricContent> LyricList = new ArrayList<LyricContent>();
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.MyBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };

    private void bindServiceConnection() {


        Intent intent = new Intent(act, MusicService.class);
//        intent.putExtra("music2_url",intent2.getStringExtra("music_url"));
//        intent.putStringArrayListExtra("music_list2",intent.getStringArrayListExtra("music_list"));
        startService(intent);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }

    public android.os.Handler handler = new android.os.Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (musicService.mp.isPlaying()) {
//                musicService.playOrPause();
                flag = false;
                play.setImageResource(R.drawable.play);
            } else {
                play.setImageResource(R.drawable.pause);
                flag = true;
            }
            musicStatus.setText(time.format(musicService.mp.getCurrentPosition()));
            musicTime.setText(time.format(musicService.mp.getCurrentPosition()) + "/"
                    + time.format(musicService.mp.getDuration()));
            seekBar.setProgress(musicService.mp.getCurrentPosition());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        musicService.mp.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            handler.postDelayed(runnable, 100);
        }
    };

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.music_listi);
        Log.d("hint", "ready to new MusicService");
        musicService = new MusicService();
        Log.d("hint", "finish to new MusicService");
        bindServiceConnection();

        mLyricView = (LyricView) findViewById(R.id.music_ly);
        seekBar = (SeekBar) findViewById(R.id.music_seek_bar);
        musicStatus = tvByid(R.id.play_cum);
        musicTime = tvByid(R.id.play_time);
        play = imgByid(R.id.btn_playorpause);
        next = imgByid(R.id.btn_playornext);
        add = imgByid(R.id.btn_playornextadd);
        head = (ImageView) findViewById(R.id.music_list_head);
        Animation anim = AnimationUtils.loadAnimation(act, R.anim.loading_dialog_progressbar);
        head.setAnimation(anim);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        add.setOnClickListener(this);
        mLrcRead=new LrcRead();
        try {
            mLrcRead.Read("/sdcard/江南.lrc");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LyricList = mLrcRead.GetLyricContent();

        //设置歌词资源
        mLyricView.setSentenceEntities(LyricList);


        mHandler.post(mRunnable);

        for (int i = 0; i < mLrcRead.GetLyricContent().size(); i++) {
            System.out.println(mLrcRead.GetLyricContent().get(i).getLyricTime() + "-");
            System.out.println(mLrcRead.GetLyricContent().get(i).getLyric() + "----");
        }
//        musicService.setData(intent.getStringExtra("music_url"));
//        musicService.setDatalist(intent.getStringArrayListExtra("music_list"));
    }

    @Override
    protected void onResume() {
        if (musicService.mp.isPlaying()) {
//            musicService.playOrPause();
            play.setImageResource(R.drawable.play);
            flag = false;
        } else {
            play.setImageResource(R.drawable.pause);
            flag = true;
        }

        seekBar.setProgress(musicService.mp.getCurrentPosition());
        seekBar.setMax(musicService.mp.getDuration());
        handler.post(runnable);
        super.onResume();
        Log.d("hint", "handler post runnable");
    }

    @Override
    public void onDestroy() {
        unbindService(sc);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_playorpause:

                musicService.playOrPause();
                break;
            case R.id.btn_playornext:
                musicService.preMusic();
                break;
            case R.id.btn_playornextadd:
                musicService.nextMusic();
                break;

        }
    }

    Handler mHandler = new Handler();

    Runnable mRunnable = new Runnable() {
        public void run() {

            mLyricView.SetIndex(Index());

            mLyricView.invalidate();

            mHandler.postDelayed(mRunnable, 100);
        }
    };

    public int Index() {
        if (musicService.mp.isPlaying()) {
            CurrentTime = musicService.mp.getCurrentPosition();

            CountTime = musicService.mp.getDuration();
        }
        if (CurrentTime < CountTime) {

            for (int i = 0; i < LyricList.size(); i++) {
                if (i < LyricList.size() - 1) {
                    if (CurrentTime < LyricList.get(i).getLyricTime() && i == 0) {
                        index = i;
                    }

                    if (CurrentTime > LyricList.get(i).getLyricTime() && CurrentTime < LyricList.get(i + 1).getLyricTime()) {
                        index = i;
                    }
                }

                if (i == LyricList.size() - 1 && CurrentTime > LyricList.get(i).getLyricTime()) {
                    index = i;
                }
            }
        }

        //System.out.println(index);
        return index;
    }

}
