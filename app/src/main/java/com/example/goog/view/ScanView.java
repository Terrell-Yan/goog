package com.example.goog.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.goog.activity.addpter.PageAdapter;
import com.example.goog.activity.addpter.ScanViewAdapter;

/**
 * ������������http://blog.csdn.net/zhongkejingwang/article/details/38728119
 * 
 * @author chenjing
 * 
 */
public class ScanView extends RelativeLayout
{
	public static final String TAG = "ScanView";
	private boolean isInit = true;
	// ������ʱ�������ҳ�ɻ�����Ҫ�ж�����һҳ�ڻ���
	private boolean isPreMoving = true, isCurrMoving = true;
	// ��ǰ�ǵڼ�ҳ
	private int index;
	private float lastX;
	// ǰһҳ����ǰҳ����һҳ�����λ��
	private int prePageLeft = 0, currPageLeft = 0, nextPageLeft = 0;
	// ����ҳ��
	private View prePage, currPage, nextPage;
	// ҳ��״̬
	private static final int STATE_MOVE = 0;
	private static final int STATE_STOP = 1;
	// ������ҳ�棬ֻ��ǰһҳ�͵�ǰҳ�ɻ�
	private static final int PRE = 2;
	private static final int CURR = 3;
	private int state = STATE_STOP;
	// ���ڻ�����ҳ���ұ�λ�ã����ڻ�����Ӱ
	private float right;
	// ��ָ�����ľ���
	private float moveLenght;
	// ҳ����
	private int mWidth, mHeight;
	// ��ȡ�����ٶ�
	private VelocityTracker vt;
	// ��ֹ����
	private float speed_shake = 20;
	// ��ǰ�����ٶ�
	private float speed;
	private Timer timer;
	private MyTimerTask mTask;
	// �����������ƶ��ٶ�
	public static final int MOVE_SPEED = 10;
	// ҳ��������
	private PageAdapter adapter;
	/**
	 * ���˶�㴥���Ŀ��Ʊ���
	 */
	private int mEvents;

	public void setAdapter(ScanViewAdapter adapter)
	{
		removeAllViews();
		this.adapter = adapter;
		prePage = adapter.getView();
		addView(prePage, 0, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		adapter.addContent(prePage, index - 1);

		currPage = adapter.getView();
		addView(currPage, 0, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		adapter.addContent(currPage, index);

		nextPage = adapter.getView();
		addView(nextPage, 0, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		adapter.addContent(nextPage, index + 1);

	}

	/**
	 * ���󻬡�ע����Ի�����ҳ��ֻ�е�ǰҳ��ǰһҳ
	 * 
	 * @param which
	 */
	private void moveLeft(int which)
	{
		switch (which)
		{
		case PRE:
			prePageLeft -= MOVE_SPEED;
			if (prePageLeft < -mWidth)
				prePageLeft = -mWidth;
			right = mWidth + prePageLeft;
			break;
		case CURR:
			currPageLeft -= MOVE_SPEED;
			if (currPageLeft < -mWidth)
				currPageLeft = -mWidth;
			right = mWidth + currPageLeft;
			break;
		}
	}

	/**
	 * ���һ���ע����Ի�����ҳ��ֻ�е�ǰҳ��ǰһҳ
	 * 
	 * @param which
	 */
	private void moveRight(int which)
	{
		switch (which)
		{
		case PRE:
			prePageLeft += MOVE_SPEED;
			if (prePageLeft > 0)
				prePageLeft = 0;
			right = mWidth + prePageLeft;
			break;
		case CURR:
			currPageLeft += MOVE_SPEED;
			if (currPageLeft > 0)
				currPageLeft = 0;
			right = mWidth + currPageLeft;
			break;
		}
	}

	/**
	 * �����ط���һҳʱ���ǰһҳ�������
	 */
	private void addPrePage()
	{
		removeView(nextPage);
		addView(nextPage, -1, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// ����������ȡǰһҳ����
		adapter.addContent(nextPage, index - 1);
		// ����˳��
		View temp = nextPage;
		nextPage = currPage;
		currPage = prePage;
		prePage = temp;
		prePageLeft = -mWidth;
	}

	/**
	 * ����ǰ����һҳʱ�����һҳ�������
	 */
	private void addNextPage()
	{
		removeView(prePage);
		addView(prePage, 0, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// ����������ȡ��һҳ����
		adapter.addContent(prePage, index + 1);
		// ����˳��
		View temp = currPage;
		currPage = nextPage;
		nextPage = prePage;
		prePage = temp;
		currPageLeft = 0;
	}

	Handler updateHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			if (state != STATE_MOVE)
				return;
			// �ƶ�ҳ��
			// ���أ����жϵ�ǰ��һҳ����δ����״̬
			if (prePageLeft > -mWidth && speed <= 0)
			{
				// ǰһҳ����δ����״̬
				moveLeft(PRE);
			} else if (currPageLeft < 0 && speed >= 0)
			{
				// ��ǰҳ����δ����״̬
				moveRight(CURR);
			} else if (speed < 0 && index < adapter.getCount())
			{
				// ���󷭣��������ǵ�ǰҳ
				moveLeft(CURR);
				if (currPageLeft == (-mWidth))
				{
					index++;
					// ����һҳ���ڵ������һҳ�������ϲ�ҳ���Ƴ�
					addNextPage();
				}
			} else if (speed > 0 && index > 1)
			{
				// ���ҷ�����������ǰһҳ
				moveRight(PRE);
				if (prePageLeft == 0)
				{
					index--;
					// ����һҳ�����һҳ�����ϲ㣬�����������
					addPrePage();
				}
			}
			if (right == 0 || right == mWidth)
			{
				releaseMoving();
				state = STATE_STOP;
				quitMove();
			}
			ScanView.this.requestLayout();
		}

	};

	public ScanView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public ScanView(Context context)
	{
		super(context);
		init();
	}

	public ScanView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	/**
	 * �˳�������ҳ
	 */
	public void quitMove()
	{
		if (mTask != null)
		{
			mTask.cancel();
			mTask = null;
		}
	}

	private void init()
	{
		index = 1;
		timer = new Timer();
		mTask = new MyTimerTask(updateHandler);
	}

	/**
	 * �ͷŶ������������ֻ�������
	 */
	private void releaseMoving()
	{
		isPreMoving = true;
		isCurrMoving = true;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		if (adapter != null)
			switch (event.getActionMasked())
			{
			case MotionEvent.ACTION_DOWN:
				lastX = event.getX();
				try
				{
					if (vt == null)
					{
						vt = VelocityTracker.obtain();
					} else
					{
						vt.clear();
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				vt.addMovement(event);
				mEvents = 0;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
			case MotionEvent.ACTION_POINTER_UP:
				mEvents = -1;
				break;
			case MotionEvent.ACTION_MOVE:
				// ȡ������
				quitMove();
				Log.d("index", "mEvents = " + mEvents + ", isPreMoving = "
						+ isPreMoving + ", isCurrMoving = " + isCurrMoving);
				vt.addMovement(event);
				vt.computeCurrentVelocity(500);
				speed = vt.getXVelocity();
				moveLenght = event.getX() - lastX;
				if ((moveLenght > 0 || !isCurrMoving) && isPreMoving
						&& mEvents == 0)
				{
					isPreMoving = true;
					isCurrMoving = false;
					if (index == 1)
					{
						// ��һҳ���������ҷ�����ת��ǰһ��activity
						state = STATE_MOVE;
						releaseMoving();
					} else
					{
						// �ǵ�һҳ
						prePageLeft += (int) moveLenght;
						// ��ֹ�����߽�
						if (prePageLeft > 0)
							prePageLeft = 0;
						else if (prePageLeft < -mWidth)
						{
							// �߽��жϣ��ͷŶ�������ֹ���ػ������»���ǰһҳʱ��ǰҳ�޷�����
							prePageLeft = -mWidth;
							releaseMoving();
						}
						right = mWidth + prePageLeft;
						state = STATE_MOVE;
					}
				} else if ((moveLenght < 0 || !isPreMoving) && isCurrMoving
						&& mEvents == 0)
				{
					isPreMoving = false;
					isCurrMoving = true;
					if (index == adapter.getCount())
					{
						// ���һҳ����������
						state = STATE_STOP;
						releaseMoving();
					} else
					{
						currPageLeft += (int) moveLenght;
						// ��ֹ�����߽�
						if (currPageLeft < -mWidth)
							currPageLeft = -mWidth;
						else if (currPageLeft > 0)
						{
							// �߽��жϣ��ͷŶ�������ֹ���ػ������»�����ǰҳ��ǰһҳ�޷�����
							currPageLeft = 0;
							releaseMoving();
						}
						right = mWidth + currPageLeft;
						state = STATE_MOVE;
					}

				} else
					mEvents = 0;
				lastX = event.getX();
				requestLayout();
				break;
			case MotionEvent.ACTION_UP:
				if (Math.abs(speed) < speed_shake)
					speed = 0;
				quitMove();
				mTask = new MyTimerTask(updateHandler);
				timer.schedule(mTask, 0, 5);
				try
				{
					vt.clear();
					vt.recycle();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		super.dispatchTouchEvent(event);
		return true;
	}

	/*
	 * ���� Javadoc�� ��������Ʒ�ҳ��ӰЧ��
	 * 
	 * @see android.view.ViewGroup#dispatchDraw(android.graphics.Canvas)
	 */
	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
		if (right == 0 || right == mWidth)
			return;
		RectF rectF = new RectF(right, 0, mWidth, mHeight);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		LinearGradient linearGradient = new LinearGradient(right, 0,
				right + 36, 0, 0xffbbbbbb, 0x00bbbbbb, TileMode.CLAMP);
		paint.setShader(linearGradient);
		paint.setStyle(Style.FILL);
		canvas.drawRect(rectF, paint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
		if (isInit)
		{
			// ��ʼ״̬��һҳ�������������������ҳ����һ��
			prePageLeft = -mWidth;
			currPageLeft = 0;
			nextPageLeft = 0;
			isInit = false;
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		if (adapter == null)
			return;
		prePage.layout(prePageLeft, 0,
				prePageLeft + prePage.getMeasuredWidth(),
				prePage.getMeasuredHeight());
		currPage.layout(currPageLeft, 0,
				currPageLeft + currPage.getMeasuredWidth(),
				currPage.getMeasuredHeight());
		nextPage.layout(nextPageLeft, 0,
				nextPageLeft + nextPage.getMeasuredWidth(),
				nextPage.getMeasuredHeight());
		invalidate();
	}

	class MyTimerTask extends TimerTask
	{
		Handler handler;

		public MyTimerTask(Handler handler)
		{
			this.handler = handler;
		}

		@Override
		public void run()
		{
			handler.sendMessage(handler.obtainMessage());
		}

	}
}
