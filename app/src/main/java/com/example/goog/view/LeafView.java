package com.example.goog.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;


import com.example.goog.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by SeanM on 2017/3/22.
 */


    public class LeafView extends View {
        private String TAG = "--------LeafView";
        private Resources mResources;
        //背景图、叶子
        private Bitmap mLeafBitmap, bgBitmap, turnBitmap;
        //整个控件的宽度和高度
        private int width, height;
        //最外层边框宽度
        private int borderWidth;
        //右侧圆形直径
        private int rightCircleWidth;
        //左侧圆形直径
        private int leftCircleWidth;
        private Paint bgPaint;
        private RectF bgRect;
        private Rect bgDestRect;
        //进度条实时背景
        private Paint progressBgPaint;
        //进度条左侧半圆，进度条中间长方形部分Rect
        private RectF progressArcRectf, progressRectf;
        //当前百分比0~100
        private int currentProgress = 0;
        //存放叶子lsit
        private List<Leaf> leafList;
        //叶子的宽和高
        private int mLeafWidth, mLeafHeight;
        //叶子滑动一周的时间5秒
        private final static long cycleTime = 5000;
        //叶子数量
        private final static int leafNumber = 6;

        public LeafView(Context context, AttributeSet attrs) {
            super(context, attrs);
            mResources = getResources();
            mLeafBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.leaf3, null)).getBitmap();
            mLeafWidth = mLeafBitmap.getWidth();
            mLeafHeight = mLeafBitmap.getHeight();
            turnBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.fengshan, null)).getBitmap();
            bgBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.leaf_kuang, null)).getBitmap();
            bgPaint = new Paint();
            bgPaint.setColor(mResources.getColor(R.color.red2));
            //进度条实时背景
            progressBgPaint = new Paint();
            progressBgPaint.setColor(mResources.getColor(R.color.orager));
            //获取所有叶子的信息，放入list
            leafList = getLeafs(leafNumber);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            width = w;
            height = h;
            borderWidth = height * 10/64;
            rightCircleWidth = width * 62/303;
            leftCircleWidth = height - 2 * borderWidth;
            bgDestRect = new Rect(0, 0 , width, height);
            bgRect = new RectF(0, 0 , width, height);
            progressArcRectf = new RectF(borderWidth, borderWidth, height - borderWidth, height - borderWidth);
            progressRectf = new RectF(borderWidth+(height-2*borderWidth)/2, borderWidth,
                    width-rightCircleWidth/2, height-borderWidth);



        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //画背景颜色到画布
            canvas.drawRect(bgRect, bgPaint);
            if(currentProgress <= 100) {
                //画叶子
                int size = leafList.size();
                for (int i=0; i<size; i++) {
                    Leaf leaf = leafList.get(i);
                    //获取叶子坐标
                    getLocation(leaf);
                    //获取叶子旋转角度
                    getRotate(leaf);
                    canvas.save();
                    Matrix matrix = new Matrix();
                    //设置滑动
                    matrix.postTranslate(leaf.x, leaf.y);
                    //设置旋转
                    matrix.postRotate(leaf.rotateAngle, leaf.x + mLeafWidth / 2, leaf.y + mLeafHeight / 2);
                    //添加叶子到画布
                    canvas.drawBitmap(mLeafBitmap, matrix, new Paint());
                    canvas.restore();

                    //画滑动后的背景条
                    int currentProgressWidht = currentProgress * (width - borderWidth - rightCircleWidth/2)/100;
                    if(currentProgressWidht < leftCircleWidth/2) {
                        //angle取值范围0~90
                        int angle = 90 * currentProgressWidht / (leftCircleWidth/2);

                        // 起始的位置
                        int startAngle = 180 - angle;
                        // 扫过的角度
                        int sweepAngle = 2 * angle;
                        canvas.drawArc(progressArcRectf, startAngle, sweepAngle, false, progressBgPaint);
                    }else {
                        //画左边半圆形滑过部分
                        canvas.drawArc(progressArcRectf, 90, 180, false, progressBgPaint);
                        progressRectf.left = borderWidth + leftCircleWidth/2;
                        progressRectf.right = borderWidth + currentProgressWidht;
                        //画中间滑过部分
                        canvas.drawRect(progressRectf, progressBgPaint);
                    }
                }

                //调用onDraw()重复滑动
                if(currentProgress < 100) {
                    postInvalidate();
                }
            }

            //画背景图片到画布
            canvas.drawBitmap(bgBitmap, null, bgDestRect, null);
            //画右边选择风叶
            setTurnLeaf(canvas);
            //画百分比
            setText(canvas);
        }

        int turnLeafAngle = 0;
        private void setTurnLeaf(Canvas canvas) {
            Matrix matrix = new Matrix();
            turnLeafAngle = turnLeafAngle + 3;
            matrix.postTranslate((width - rightCircleWidth/2 - turnBitmap.getWidth()/2),
                    (height - rightCircleWidth/2 - turnBitmap.getHeight()/2));
            matrix.postRotate(turnLeafAngle,
                    width - rightCircleWidth/2 - turnBitmap.getWidth()/2 + turnBitmap.getWidth()/2,
                    height - rightCircleWidth/2 - turnBitmap.getHeight()/2 + turnBitmap.getHeight()/2);
            canvas.drawBitmap(turnBitmap, matrix, new Paint());
        }

        //显示百分比数字,大于3%开始显示，到50%停止滑动
        private void setText(Canvas canvas) {
            Paint paintText = new Paint();
            paintText.setColor(Color.WHITE);
            paintText.setTextSize(30);
            int textX = currentProgress * width / 100;
            textX = currentProgress < 50 ? (currentProgress * width / 100) : (width/2);
            if(currentProgress > 3) {
                canvas.drawText(currentProgress + "%", textX, height/2 + 10,paintText);
            }
        }

        //获取每片叶子在XY轴上的滑动值
        private void getLocation(Leaf leaf) {
            float betweenTime = leaf.startTime - System.currentTimeMillis();
            //周期结束再加一个cycleTime
            if(betweenTime < 0) {
                leaf.startTime = System.currentTimeMillis() + cycleTime + new Random().nextInt((int) (cycleTime));
                betweenTime = cycleTime;
            }

            //通过时间差计算出叶子的坐标
            float fraction = (float) betweenTime / cycleTime;
            float x = (int)(width * fraction);
            //防止叶子飘出边框
            leaf.x = x < borderWidth ? borderWidth : x;
            float w = (float) ((float) 2 * Math.PI / width);
            int y = (int) (18 * Math.sin(w * x)) + (height-mLeafHeight)/2;
            //防止叶子飘出边框
            y = y > (height - borderWidth) ? (height - borderWidth) : y;
            y = y < borderWidth ? borderWidth : y;
            leaf.y = y;
        }

        //获取每片叶子的旋转角度
        private void getRotate(Leaf leaf) {
            float scale = ((leaf.startTime - System.currentTimeMillis())%cycleTime)/ (float)cycleTime;
            int rotate = (int)(scale * 360);
            leaf.rotateAngle = rotate;
        }

        private class Leaf {
            // 叶子的坐标
            float x, y;
            // 旋转角度
            int rotateAngle;
            // 起始时间(ms)
            long startTime;
        }

        private List<Leaf> getLeafs(int leafSize) {
            List<Leaf> list = new LinkedList<Leaf>();
            for (int i=0; i<leafSize; i++) {

                list.add(getLeaf());
            }
            return list;
        }

        //使叶子初始时间有间隔
        int addTime;
        private Leaf getLeaf() {
            Random random = new Random();
            Leaf leaf = new Leaf();
            leaf.rotateAngle = random.nextInt(360);
            addTime += random.nextInt((int) (cycleTime));
            leaf.startTime = System.currentTimeMillis() + cycleTime + addTime;
            return leaf;
        }

        public void setCurrentProgress(int currentProgress) {
            this.currentProgress = currentProgress;
        }
    }


