/*
 * @author Jerry
 * @time 2015-12-25 上午10:22:00
 */
package com.example.goog.calendar;

import java.util.Calendar;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.goog.R;

/**
 * 日历签到
 * java日历是的月份是从0-11其他没有特殊要求.
 * 
 * @author Jerry
 * @time 2015-12-25 上午10:22:00
 */
public class CalendarSign extends LinearLayout
{

	/** 日历标题就是日、一、二、三、这些. */
	private LinearLayout	mCalendarTitle;

	/** 日历内容就是日子. */
	private LinearLayout	mCalendarContent;

	/** The today badge text view. */
	private BadgeTextView	todayBadgeTextView;

	/** 不是当月的日子是否显示灰色. */
	private boolean			isShowGray			= true;

	/** 变灰的具体颜色值. */
	private int				mGrayColor			= Color.GRAY;

	/** The Current month start position. */
	private int				mCurrentMonthStartPosition;

	/** The Back ground color. */
	private int				mBackGroundColor	= Color.WHITE;

	/**
	 * Instantiates a new calendar sign.
	 * 
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public CalendarSign(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		init();
	}

	/**
	 * Instantiates a new calendar sign.
	 * 
	 * @param context the context
	 * @param attrs the attrs
	 */
	public CalendarSign(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	/**
	 * Instantiates a new calendar sign.
	 * 
	 * @param context the context
	 */
	public CalendarSign(Context context)
	{
		this(context, null);
	}

	/**
	 * 初始化.
	 * 
	 * @author Jerry
	 * @return void
	 * @time 2015-12-25 上午10:22:59
	 */
	private void init()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		View view = layoutInflater.inflate(R.layout.include_badge_calendar, this, true);
		mCalendarTitle = (LinearLayout) view.findViewById(R.id.common_calendar_title);
		mCalendarContent = (LinearLayout) view.findViewById(R.id.common_calendar_content);

		// 浅粉色 TODO
		this.setBackgroundColor(0x33e6e3e3);

		initCalendarContent();// TODO 换成公司的项目就改掉
	}

	/**
	 * Inits the calendar content.
	 */
	// TODO 换成公司的项目就改掉
	public void initCalendarContent()
	{
		if (mCalendarContent != null)
		{
			Calendar instance = Calendar.getInstance();

			// 今天的天数
			int curDay = instance.get(Calendar.DAY_OF_MONTH);

			instance.set(Calendar.DATE, 1);
			mCurrentMonthStartPosition = instance.get(Calendar.DAY_OF_WEEK);
			// 本月的天数
			int curCount = instance.getActualMaximum(Calendar.DATE);
			// 上个月从周日开始的日子
			int preStartNumber = getPreMonthStartNumber();
			// 这里是算最后一行显不显示 计算的结果就是4行*7 得到28 加上第一行的天数得到的结果与本月总天数对比
			int tempCount = 7 - mCurrentMonthStartPosition + 1 + 28;
			// 日历的行数
			int childCount = mCalendarContent.getChildCount();
			// 当前的天数小于期望值的时候说明最后一整行空出来了 就不显示了。
			if (curCount <= tempCount)
			{
				// 隐藏最后一行
				mCalendarContent.getChildAt(mCalendarContent.getChildCount() - 1).setVisibility(View.GONE);
				childCount--;
			}
			// 日历总共绘制的天数
			int count = 0;
			// 当月的起始日子
			int curMonth = 1;
			// 下月的起始日子
			int nextMonth = 1;
			for (int i = 0; i < childCount; i++)
			{
				ViewGroup childGroup = (ViewGroup) mCalendarContent.getChildAt(i);

				for (int j = 0; j < childGroup.getChildCount(); j++)
				{
					count++;
					BadgeTextView badgeTextView = (BadgeTextView) childGroup.getChildAt(j);
					if (count < mCurrentMonthStartPosition)
					{
						badgeTextView.setText("" + preStartNumber++);
						if (isShowGray)
						{
							badgeTextView.setTextColor(mGrayColor);
						}
					}
					else if (curMonth <= curCount)
					{
						badgeTextView.setText("" + curMonth++);
						if (curDay <= 7)
						{
							if (count == mCurrentMonthStartPosition + curDay - 1)
							{
								todayBadgeTextView = badgeTextView;
							}
						}
						else
						{
							if ((count - mCurrentMonthStartPosition + 1) == curDay)
							{
								todayBadgeTextView = badgeTextView;
							}
						}
					}
					else
					{
						badgeTextView.setText("" + nextMonth++);
						badgeTextView.setTextColor(mGrayColor);
					}
				}
			}
		}
	}

	/**
	 * Gets the pre month start number.
	 * 
	 * @return the pre month start number
	 */
	private int getPreMonthStartNumber()
	{
		Calendar instance = Calendar.getInstance();

		int preStartNumber = 0;
		int curMonth = instance.get(Calendar.MONTH);
		if (curMonth == 0)
		{
			int curYear = instance.get(Calendar.YEAR);
			instance.set(Calendar.YEAR, curYear - 1);
			// 1月的上个月是去年12月
			instance.set(Calendar.MONTH, 11);
			preStartNumber = instance.getActualMaximum(Calendar.DATE);
		}
		else
		{
			instance.set(Calendar.MONTH, curMonth - 1);
			preStartNumber = instance.getActualMaximum(Calendar.DATE);
		}

		return preStartNumber - mCurrentMonthStartPosition + 2;
	}

	/**
	 * Gets the day for week to start position.
	 * 
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @return the day for week to start position
	 */
	private int getDayForWeekToStartPosition(int year, int month, int day)
	{
		Calendar instance = Calendar.getInstance();
		instance.set(year, month, day);
		return instance.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Gets the today badge text view.
	 * 
	 * @return the today badge text view
	 */
	public BadgeTextView getTodayBadgeTextView()
	{
		return todayBadgeTextView;
	}

}
