package com.example.myrehabilitaion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frag_ColorfulCalendar extends BaseFragment implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnCalendarLongClickListener,
        View.OnClickListener {

    private static String ip = "140.131.114.241";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "109-rehabilitation";
    private static String username = "case210906";
    private static String password = "1@case206";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;
    Statement statement = null;
    public List<String> listStr01;
    public List<String> listStr02;
    public List<String> listStr03;
    public List<String> listStr04;
    public List<String> listStr05;

    GlobalVariable gv ;
    public String userid;
    personaldata_sync_fromdb personaldata_sync_fromdb;

    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    CalendarView mCalendarView;
    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;


    public static void show(Context context) {
        context.startActivity(new Intent(context, Frag_ColorfulCalendar.class));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_colorfulcalendar;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {

        gv = (GlobalVariable)getActivity().getApplicationContext();
        userid = gv.getUserID();

        listStr01 = new ArrayList<String>();
        listStr02 = new ArrayList<String>();
        listStr03 = new ArrayList<String>();
        listStr04 = new ArrayList<String>();
        listStr05 = new ArrayList<String>();

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
//            Toast toast = Toast.makeText(getContext(),"Success", Toast.LENGTH_SHORT);
//            toast.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//            Toast toast = Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT);
//            toast.show();
        } catch (SQLException e) {
            e.printStackTrace();
//            Toast toast = Toast.makeText(getContext(),"FAILURE", Toast.LENGTH_SHORT);
//            toast.show();

        }

//        setStatusBarDarkMode();
        mTextMonthDay = mRootView.findViewById(R.id.tv_month_day);
        mTextYear = mRootView.findViewById(R.id.tv_year);
        mTextLunar = mRootView.findViewById(R.id.tv_lunar);
        mRelativeTool = mRootView.findViewById(R.id.rl_tool);
        mCalendarView = mRootView.findViewById(R.id.calendarView);
        mTextCurrentDay =  mRootView.findViewById(R.id.tv_current_day);
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
        mRootView.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout = mRootView.findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarLongClickListener(this, true);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }

    @Override
    protected void initData() {

        userid = gv.getUserID();
        datedata_sync_fromdb datedata_sync_fromdb = new datedata_sync_fromdb();
        datedata_sync_fromdb.execute();

        try {
            Thread.sleep(100);
            System.out.print("record執行緒睡眠0.1秒！\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();

        for(int i=0; i < listStr05.size();i++){
            map.put(getSchemeCalendar(Integer.valueOf(listStr05.get(i).trim().substring(0,4)), Integer.valueOf(listStr05.get(i).trim().substring(5,7)), Integer.valueOf(listStr05.get(i).trim().substring(8,10)), 0xFF40db25, "診").toString(),
                    getSchemeCalendar(Integer.valueOf(listStr05.get(i).trim().substring(0,4)), Integer.valueOf(listStr05.get(i).trim().substring(5,7)), Integer.valueOf(listStr05.get(i).trim().substring(8,10)), 0xFF40db25, "診"));
        }
//        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
//                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
//        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
//                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
//        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
//                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
//        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
//                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
//        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
//                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
//        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
//                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);

    }


    @Override
    public void onClick(View v) {
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

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {
        Toast.makeText(getContext(), getCalendarText(calendar), Toast.LENGTH_LONG).show();
    }

    private String getCalendarText(Calendar calendar) {

        gv.setClickDate( String.valueOf(calendar.getYear()+"-"+calendar.getMonth()+"-"+calendar.getDay()));
        personaldata_sync_fromdb = new personaldata_sync_fromdb();
        personaldata_sync_fromdb.execute();

        try {
            Thread.sleep(100);
            System.out.print("record執行緒睡眠0.1秒！\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return String.format("%s",
                "看診日期:"+calendar.getYear()+"-"+calendar.getMonth()+"-"+calendar.getDay()+ "\n" + "部位描述:"+listStr01.get(0)+"\n"+"注意事項:"+listStr02.get(0)+"\n"+"下次看診日期:"+listStr04.get(0));
    }


    public class personaldata_sync_fromdb extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(getContext(),"目標數據同步成功", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected String doInBackground(String... strings) {

            ArrayList<String> array_sync01 = new ArrayList<String>();
            ArrayList<String> array_sync02 = new ArrayList<String>();
            ArrayList<String> array_sync03 = new ArrayList<String>();
            ArrayList<String> array_sync04 = new ArrayList<String>();


            if (connection!=null){

                try{
                    statement = connection.createStatement();
                    ResultSet result01 = statement.executeQuery("SELECT clinic_record, clinic_suggestion, clinic_date, next_clinicdate FROM dbo.Personal_data WHERE user_id ="+Integer.valueOf(userid)+"AND clinic_date='"+ gv.getClickDate().toString().trim() +"';");
                    while (result01.next()) {
                        array_sync01.add(result01.getString(1).toString().trim());
                        array_sync02.add(result01.getString(2).toString().trim());
                        array_sync03.add(result01.getString(3).toString().trim());
                        array_sync04.add(result01.getString(4).toString().trim());
                    }

                    if(array_sync01.size() >0){
                        for (int i = 0; i < array_sync01.size(); i++) {
                            listStr01.add((String) array_sync01.get(i));
                            listStr02.add(array_sync02.get(i));
                            listStr03.add(array_sync03.get(i));
                            listStr04.add(array_sync04.get(i));
                        }
                    }else{
                        listStr01.add("無資料");
                        listStr02.add("無資料");
                        listStr03.add("無資料");
                        listStr04.add("無資料");
                    }

                }catch (Exception e){

                    isSuccess = false;
                    z = e.getMessage();
                }

            }
            else {
            }
            return z;
        }
    }

    public class datedata_sync_fromdb extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(getContext(),"目標數據同步成功", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected String doInBackground(String... strings) {

            ArrayList<String> array_sync05 = new ArrayList<String>();
            userid = gv.getUserID();

            if (connection!=null){

                try{
                    statement= connection.createStatement();
                    ResultSet result02 = statement.executeQuery("SELECT clinic_date FROM dbo.Personal_data WHERE user_id ="+Integer.valueOf(userid)+";");

                    while (result02.next()){
                        array_sync05.add(result02.getString(1).toString().trim());
                    }

                    if(array_sync05.size() >0){
                        for(int i = 0; i < array_sync05.size(); i++){
                            listStr05.add(array_sync05.get(i));
                        }
                    }else{
                        listStr05.add("1980-01-01");
                    }



                }catch (Exception e){
                    isSuccess = false;
                    z = e.getMessage();
                }

            }
            else {
            }
            return z;
        }
    }
}
