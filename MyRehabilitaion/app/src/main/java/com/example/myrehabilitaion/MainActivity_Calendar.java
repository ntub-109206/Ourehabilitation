package com.example.myrehabilitaion;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.TrunkBranchAnnals;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity_Calendar extends BaseFragment implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnCalendarLongClickListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnWeekChangeListener,
        CalendarView.OnViewChangeListener,
        CalendarView.OnCalendarInterceptListener,
        CalendarView.OnYearViewChangeListener,
        DialogInterface.OnClickListener,
        View.OnClickListener {

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;

    private AlertDialog mMoreDialog;

    private AlertDialog mFuncDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_calendar;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
//       getsetStatusBarDarkMode();
        mTextMonthDay = mRootView.findViewById(R.id.tv_month_day);
        mTextYear = mRootView.findViewById(R.id.tv_year);
        mTextLunar = mRootView.findViewById(R.id.tv_lunar);

        mRelativeTool = mRootView.findViewById(R.id.rl_tool);
        mCalendarView = mRootView.findViewById(R.id.calendarView);
        //mCalendarView.setRange(2018, 7, 1, 2019, 4, 28);
        mTextCurrentDay = mRootView.findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });


        final DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mCalendarLayout.expand();
                                break;
                            case 1:
                                boolean result = mCalendarLayout.shrink();
                                Log.e("shrink", " --  " + result);
                                break;
                            case 2:
                                mCalendarView.scrollToPre(false);
                                break;
                            case 3:
                                mCalendarView.scrollToNext(false);
                                break;
                            case 4:
                                //mCalendarView.scrollToCurrent(true);
                                mCalendarView.scrollToCalendar(2018, 12, 30);
                                break;
                            case 5:
                                mCalendarView.setRange(2018, 7, 1, 2019, 4, 28);
//                                mCalendarView.setRange(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), 6,
//                                        mCalendarView.getCurYear(), mCalendarView.getCurMonth(), 23);
                                break;
                            case 6:
                                Log.e("scheme", "  " + mCalendarView.getSelectedCalendar().getScheme() + "  --  "
                                        + mCalendarView.getSelectedCalendar().isCurrentDay());
                                List<Calendar> weekCalendars = mCalendarView.getCurrentWeekCalendars();
                                for (Calendar calendar : weekCalendars) {
                                    Log.e("onWeekChange", calendar.toString() + "  --  " + calendar.getScheme());
                                }
                                new AlertDialog.Builder(getContext())
                                        .setMessage(String.format("Calendar Range: \n%s —— %s",
                                                mCalendarView.getMinRangeCalendar(),
                                                mCalendarView.getMaxRangeCalendar()))
                                        .show();
                                break;
                        }
                    }
                };



        mCalendarLayout = mRootView.findViewById(R.id.calendarLayout);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mCalendarView.setOnCalendarLongClickListener(this, true);
        mCalendarView.setOnWeekChangeListener(this);
        mCalendarView.setOnYearViewChangeListener(this);

        //设置日期拦截事件，仅适用单选模式，当前无效
        mCalendarView.setOnCalendarInterceptListener(this);

        mCalendarView.setOnViewChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }

    @SuppressWarnings("unused")
    @Override
    protected void initData() {

        final int year = mCalendarView.getCurYear();
        final int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        for (int y = 1997; y < 2082; y++) {
            for (int m = 1; m <= 12; m++) {
                map.put(getSchemeCalendar(y, m, 1, 0xFF40db25, "假").toString(),
                        getSchemeCalendar(y, m, 1, 0xFF40db25, "假"));
                map.put(getSchemeCalendar(y, m, 2, 0xFFe69138, "游").toString(),
                        getSchemeCalendar(y, m, 2, 0xFFe69138, "游"));
                map.put(getSchemeCalendar(y, m, 3, 0xFFdf1356, "事").toString(),
                        getSchemeCalendar(y, m, 3, 0xFFdf1356, "事"));
                map.put(getSchemeCalendar(y, m, 4, 0xFFaacc44, "车").toString(),
                        getSchemeCalendar(y, m, 4, 0xFFaacc44, "车"));
                map.put(getSchemeCalendar(y, m, 5, 0xFFbc13f0, "驾").toString(),
                        getSchemeCalendar(y, m, 5, 0xFFbc13f0, "驾"));
                map.put(getSchemeCalendar(y, m, 6, 0xFF542261, "记").toString(),
                        getSchemeCalendar(y, m, 6, 0xFF542261, "记"));
                map.put(getSchemeCalendar(y, m, 7, 0xFF4a4bd2, "会").toString(),
                        getSchemeCalendar(y, m, 7, 0xFF4a4bd2, "会"));
                map.put(getSchemeCalendar(y, m, 8, 0xFFe69138, "车").toString(),
                        getSchemeCalendar(y, m, 8, 0xFFe69138, "车"));
                map.put(getSchemeCalendar(y, m, 9, 0xFF542261, "考").toString(),
                        getSchemeCalendar(y, m, 9, 0xFF542261, "考"));
                map.put(getSchemeCalendar(y, m, 10, 0xFF87af5a, "记").toString(),
                        getSchemeCalendar(y, m, 10, 0xFF87af5a, "记"));
                map.put(getSchemeCalendar(y, m, 11, 0xFF40db25, "会").toString(),
                        getSchemeCalendar(y, m, 11, 0xFF40db25, "会"));
                map.put(getSchemeCalendar(y, m, 12, 0xFFcda1af, "游").toString(),
                        getSchemeCalendar(y, m, 12, 0xFFcda1af, "游"));
                map.put(getSchemeCalendar(y, m, 13, 0xFF95af1a, "事").toString(),
                        getSchemeCalendar(y, m, 13, 0xFF95af1a, "事"));
                map.put(getSchemeCalendar(y, m, 14, 0xFF33aadd, "学").toString(),
                        getSchemeCalendar(y, m, 14, 0xFF33aadd, "学"));
                map.put(getSchemeCalendar(y, m, 15, 0xFF1aff1a, "码").toString(),
                        getSchemeCalendar(y, m, 15, 0xFF1aff1a, "码"));
                map.put(getSchemeCalendar(y, m, 16, 0xFF22acaf, "驾").toString(),
                        getSchemeCalendar(y, m, 16, 0xFF22acaf, "驾"));
                map.put(getSchemeCalendar(y, m, 17, 0xFF99a6fa, "校").toString(),
                        getSchemeCalendar(y, m, 17, 0xFF99a6fa, "校"));
                map.put(getSchemeCalendar(y, m, 18, 0xFFe69138, "车").toString(),
                        getSchemeCalendar(y, m, 18, 0xFFe69138, "车"));
                map.put(getSchemeCalendar(y, m, 19, 0xFF40db25, "码").toString(),
                        getSchemeCalendar(y, m, 19, 0xFF40db25, "码"));
                map.put(getSchemeCalendar(y, m, 20, 0xFFe69138, "火").toString(),
                        getSchemeCalendar(y, m, 20, 0xFFe69138, "火"));
                map.put(getSchemeCalendar(y, m, 21, 0xFF40db25, "假").toString(),
                        getSchemeCalendar(y, m, 21, 0xFF40db25, "假"));
                map.put(getSchemeCalendar(y, m, 22, 0xFF99a6fa, "记").toString(),
                        getSchemeCalendar(y, m, 22, 0xFF99a6fa, "记"));
                map.put(getSchemeCalendar(y, m, 23, 0xFF33aadd, "假").toString(),
                        getSchemeCalendar(y, m, 23, 0xFF33aadd, "假"));
                map.put(getSchemeCalendar(y, m, 24, 0xFF40db25, "校").toString(),
                        getSchemeCalendar(y, m, 24, 0xFF40db25, "校"));
                map.put(getSchemeCalendar(y, m, 25, 0xFF1aff1a, "假").toString(),
                        getSchemeCalendar(y, m, 25, 0xFF1aff1a, "假"));
                map.put(getSchemeCalendar(y, m, 26, 0xFF40db25, "议").toString(),
                        getSchemeCalendar(y, m, 26, 0xFF40db25, "议"));
                map.put(getSchemeCalendar(y, m, 27, 0xFF95af1a, "假").toString(),
                        getSchemeCalendar(y, m, 27, 0xFF95af1a, "假"));
                map.put(getSchemeCalendar(y, m, 28, 0xFF40db25, "码").toString(),
                        getSchemeCalendar(y, m, 28, 0xFF40db25, "码"));
            }
        }

        //28560 数据量增长不会影响UI响应速度，请使用这个API替换
        mCalendarView.setSchemeDate(map);

        //可自行测试性能差距
        //mCalendarView.setSchemeDate(schemes);

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    public void onClick(View v) {
//
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }


    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
        Toast.makeText(getContext(), String.format("%s : OutOfRange", calendar), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        //Log.e("onDateSelected", "  -- " + calendar.getYear() + "  --  " + calendar.getMonth() + "  -- " + calendar.getDay());
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        if (isClick) {
            Toast.makeText(getContext(), getCalendarText(calendar), Toast.LENGTH_SHORT).show();
        }
//        Log.e("lunar "," --  " + calendar.getLunarCalendar().toString() + "\n" +
//        "  --  " + calendar.getLunarCalendar().getYear());
        Log.e("onDateSelected", "  -- " + calendar.getYear() +
                "  --  " + calendar.getMonth() +
                "  -- " + calendar.getDay() +
                "  --  " + isClick + "  --   " + calendar.getScheme());
        Log.e("onDateSelected", "  " + mCalendarView.getSelectedCalendar().getScheme() +
                "  --  " + mCalendarView.getSelectedCalendar().isCurrentDay());
        Log.e("干支年纪 ： ", " -- " + TrunkBranchAnnals.getTrunkBranchYear(calendar.getLunarCalendar().getYear()));
    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {
        Toast.makeText(getContext(), String.format("%s : LongClickOutOfRange", calendar), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {
        Toast.makeText(getContext(), "长按不选择日期\n" + getCalendarText(calendar), Toast.LENGTH_SHORT).show();
    }

    private static String getCalendarText(Calendar calendar) {
        return String.format("新历%s \n 农历%s \n 公历节日：%s \n 农历节日：%s \n 节气：%s \n 是否闰月：%s",
                calendar.getMonth() + "月" + calendar.getDay() + "日",
                calendar.getLunarCalendar().getMonth() + "月" + calendar.getLunarCalendar().getDay() + "日",
                TextUtils.isEmpty(calendar.getGregorianFestival()) ? "无" : calendar.getGregorianFestival(),
                TextUtils.isEmpty(calendar.getTraditionFestival()) ? "无" : calendar.getTraditionFestival(),
                TextUtils.isEmpty(calendar.getSolarTerm()) ? "无" : calendar.getSolarTerm(),
                calendar.getLeapMonth() == 0 ? "否" : String.format("闰%s月", calendar.getLeapMonth()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMonthChange(int year, int month) {
        Log.e("onMonthChange", "  -- " + year + "  --  " + month);
        Calendar calendar = mCalendarView.getSelectedCalendar();
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onViewChange(boolean isMonthView) {
        Log.e("onViewChange", "  ---  " + (isMonthView ? "月视图" : "周视图"));
    }


    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {
        for (Calendar calendar : weekCalendars) {
            Log.e("onWeekChange", calendar.toString());
        }
    }

    @Override
    public void onYearViewChange(boolean isClose) {
        Log.e("onYearViewChange", "年视图 -- " + (isClose ? "关闭" : "打开"));
    }

    /**
     * 屏蔽某些不可点击的日期，可根据自己的业务自行修改
     *
     * @param calendar calendar
     * @return 是否屏蔽某些不可点击的日期，MonthView和WeekView有类似的API可调用
     */
    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        Log.e("onCalendarIntercept", calendar.toString());
        int day = calendar.getDay();
        return day == 1 || day == 3 || day == 6 || day == 11 || day == 12 || day == 15 || day == 20 || day == 26;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
        Toast.makeText(getContext(), calendar.toString() + "拦截不可点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
        Log.e("onYearChange", " 年份变化 " + year);
    }

}


