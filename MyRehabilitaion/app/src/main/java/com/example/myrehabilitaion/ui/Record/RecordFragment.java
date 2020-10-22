package com.example.myrehabilitaion.ui.Record;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myrehabilitaion.Class_Login;
import com.example.myrehabilitaion.Class_Registeration;
import com.example.myrehabilitaion.GlobalVariable;
import com.example.myrehabilitaion.Main;
import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.RecyclerExampleViewAdapter;
import com.example.myrehabilitaion.RecyclerInfoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class RecordFragment extends Fragment {
//    private static class StaticHandler extends Handler{
//        private final WeakReference <RecordFragment> mRecordFragment;
//        public StaticHandler(RecordFragment recordFragment){
//            mRecordFragment = new WeakReference<RecordFragment>(recordFragment);
//        }
//    }
//
//    Runnable r = new Runnable() {
//        @Override
//        public void run() {
//
//        }
//    }
    private static String ip = "140.131.114.241";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "109-rehabilitation";
    private static String username = "case210906";
    private static String password = "1@case206";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    Statement statement = null;


    private RecyclerExampleViewAdapter adapter_exampler;
    RecyclerView recyclerexample;
    Dialog mDlog01;

    public EditText edttargetname;
    public EditText edtaddtime;

    public List<String> listStr01;
    public List<String> listStr02;
    public List<String> listStr03;
    public List<Integer> listImg;

    service_sync_todb service_sync_todb;
    service_sync_fromdb service_sync_fromdb;

    public String sync_name;
    public String sync_servicename;

    GlobalVariable gv ;

    ProgressBar progressBar;




    private RecyclerInfoAdapter infoadapter;
    RecyclerView recyclerInfo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_frag__record, container, false);

        listStr01 = new ArrayList<String>();
        listStr02 = new ArrayList<String>();
        listStr03 = new ArrayList<String>();
        listImg = new ArrayList<Integer>();

        gv = (GlobalVariable)getActivity().getApplicationContext();

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
            Toast toast = Toast.makeText(getContext(),"Success", Toast.LENGTH_SHORT);
            toast.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getContext(),"FAILURE", Toast.LENGTH_SHORT);
            toast.show();

        }
        service_sync_fromdb = new service_sync_fromdb();
        service_sync_fromdb.execute();


//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("診療中的項目");


//        for( int i=0 ; i <3 ; i++){
//            listStr.add(new String("目標" + String.valueOf(i+1)));
//        }

        recyclerexample = root.findViewById(R.id.recyclerview_home);
        recyclerexample.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        adapter_exampler = new RecyclerExampleViewAdapter(RecordFragment.super.getActivity(),RecordFragment.super.getActivity().getApplicationContext(), listStr01, listStr02, listImg);
        //adapter_home.addItem(sercmng.Syc());

        try {
            Thread.sleep(100);
            System.out.print("    執行緒睡眠0.01秒！\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerexample.setAdapter(adapter_exampler);


//        imgbtnaddcase = root.findViewById(R.id.btn_addcase);
//        imgbtnaddcase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDlog = new Dialog(v.getContext());
//                mDlog.setContentView(R.layout.dlg_addtarget);
//                mDlog.setCancelable(true);
//                mDlog.show();
//
//                Button btnaddtargt = mDlog.findViewById(R.id.btn_addtargt);
//                Button btncancelbox = mDlog.findViewById(R.id.btn_cancelbox);
//                final EditText edttargetname = mDlog.findViewById(R.id.edt_targetname);
//
//                btnaddtargt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        adapter_exampler.addItem(edttargetname.getText().toString().trim());
//                        //mSyncTarget = edttargetname.getText().toString().trim();
//
//                        Toast.makeText(v.getContext(),
//                                "您新增了新的目標", Toast.LENGTH_SHORT).show();
//
//                        mDlog.dismiss();
//                    }
//                });
//
//            }
//        });

        //TypedArray infoImageList =
        // getResources().obtainTypedArray(R.array.info_icon_list);

/*
        //主頁資訊欄
        List<Map<String,Object>> listImg_info = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 5 ; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(String.valueOf(i), infoImageList.getResourceId(i, 0));
            listImg_info.add(map);
        }

        recyclerInfo = root.findViewById(R.id.recyc_rehbinfo);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerInfo.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        infoadapter = new RecyclerInfoAdapter(listImg_info);

        recyclerInfo.setAdapter(infoadapter);
 */

//----------NEWS欄位----------
//        ViewPager viewPager;
//
//        //三個view
//        View view1;
//        View view2;
//        View view3;
//
//        //用來存放view並傳遞給viewPager的介面卡。
//        ArrayList<View> pageview;
//
//        //用來存放圓點，沒有寫第四步的話，就不要定義一下三個變量了。
//        ImageView[] tips = new ImageView[3];
//        ImageView imageView;
//
//        //圓點組的物件
//        ViewGroup group;
//
//        //將view加進pageview中
//        viewPager = (ViewPager)root.findViewById(R.id.viewpager);
//        view1 = getLayoutInflater().inflate(R.layout.view1,null);
//        view2 = getLayoutInflater().inflate(R.layout.view2,null);
//        view3 = getLayoutInflater().inflate(R.layout.view3,null);
//        pageview = new ArrayList<View>();
//        pageview.add(view1);
//        pageview.add(view2);
//        pageview.add(view3);
//
//        //viewPager下面的圓點，ViewGroup
//        group = (ViewGroup)root.findViewById(R.id.pointgroup);
//        tips = new ImageView[pageview.size()];
//        for(int i =0;i<pageview.size();i++){
//            imageView = new ImageView(getContext());
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(40,40));
//            imageView.setPadding(20, 0, 20, 0);
//            tips[i] = imageView;
//
//            //預設第一張圖顯示為選中狀態
//            if (i == 0) {
//                tips[i].setBackgroundResource(R.drawable.shape_point_selected);
//            } else {
//                tips[i].setBackgroundResource(R.drawable.shape_point_normal);
//            }
//
//            group.addView(tips[i]);
//        }
//        //這裡的mypagerAdapter是第三步定義好的。
//        viewPager.setAdapter(new NewsImageAdapter(pageview));
//        //這裡的GuiPageChangeListener是第四步定義好的。
//        viewPager.addOnPageChangeListener(new GuidePageChangeListener(tips, pageview));

        final FloatingActionButton fab01 = root.findViewById(R.id.floatingActionButton);
//        final FloatingActionButton fab02 = root.findViewById(R.id.fab02);
//        fab02.setVisibility(View.INVISIBLE);

        fab01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                fab01.setVisibility(View.INVISIBLE);
//                fab02.setVisibility(View.VISIBLE);
//
//                fab02.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Snackbar.make(view, "開啟統計頁面", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//
//                Intent intent = new Intent(getContext(),HomePage.class);
//                startActivity(intent);
//
//                        Main.this.showStatisticsFragment();
//
//                        fab02.setVisibility(View.INVISIBLE);
//                        fab01.setVisibility(View.VISIBLE);
                mDlog01 = new Dialog(v.getContext());
                mDlog01.setContentView(R.layout.dlg_addtarget);
                mDlog01.setCancelable(true);
                mDlog01.show();

                Button btnaddtargt = mDlog01.findViewById(R.id.btn_addtargt);
                Button btncancelbox = mDlog01.findViewById(R.id.btn_cancelbox);
                edttargetname = mDlog01.findViewById(R.id.edt_targetname);
                edtaddtime = mDlog01.findViewById(R.id.edt_targetdate);

                edtaddtime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar now = Calendar.getInstance();

                        DatePickerDialog dataPickerDialog = new DatePickerDialog(getContext(),datePickerDlgOnDataSet, now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));

                        dataPickerDialog.setTitle("選擇日期");
                        dataPickerDialog.setMessage("請選擇您的生日日期");
                        dataPickerDialog.setCancelable(true);
                        dataPickerDialog.show();

                    }
                });

                btncancelbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDlog01.dismiss();
                    }
                });

                btnaddtargt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        adapter_exampler.addItem(edttargetname.getText().toString().trim());

                        service_sync_todb = new service_sync_todb();
                        service_sync_todb.execute();

                        mDlog01.dismiss();
                    }
                });
            }
        });

        return root;
    }

    private DatePickerDialog.OnDateSetListener datePickerDlgOnDataSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            edtaddtime.setText(
                    String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(dayOfMonth)
            );

        }
    };


    public class service_sync_todb extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(),"您新增了新的目標並傳輸成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {

            //Toast.makeText(getContext(),"測試來了", Toast.LENGTH_SHORT).show();

            if (connection!=null){

                try{
                    sync_name = gv.getUserEmail();
                    statement = connection.createStatement();
                    statement.executeQuery("INSERT INTO dbo.service (email,body,date) VALUES ('"+sync_name.toString().trim()+"','"+edttargetname.getText().toString().trim()+"','"+edtaddtime.getText().toString().trim()+"');");

                }catch (Exception e){
                    isSuccess = false;
                    z = e.getMessage();
                }
            }
            else {
                Toast toast = Toast.makeText(getContext(),"目標數據傳輸失敗", Toast.LENGTH_SHORT);
                toast.show();
            }
            return z;
        }
    }

    public class service_sync_fromdb extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(),"目標數據同步成功", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected String doInBackground(String... strings) {

            ArrayList<String> array_sync01 = new ArrayList<String>();
            ArrayList<String> array_sync02 = new ArrayList<String>();
            sync_name = gv.getUserEmail();

            if (connection!=null){
                Log.d("vin123", "抓資料");
                try{
                    statement = connection.createStatement();
                    ResultSet result = statement.executeQuery("SELECT body, date FROM dbo.service WHERE email ='"+sync_name.toString().trim()+"';");
                    Log.d("vin456", String.valueOf(result));

                    while (result.next()) {
                        array_sync01.add(result.getString(1).toString().trim());
                        array_sync02.add(result.getString(2).toString().trim());
                    }
                    for (int i = 0; i < array_sync01.size(); i++) {
                        listStr01.add((String) array_sync01.get(i));
                        listStr02.add(array_sync02.get(i));
                        double r =Math.random()*3;
                        int image[]  = {R.drawable.bg_04, R.drawable.bg_03, R.drawable.bg_07};
                        listImg.add(image[Integer.valueOf((int) r)]);
                    }
                    Log.d("text02", String.valueOf(listStr01));
                }catch (Exception e){

                    Toast toast = Toast.makeText(getContext(),"目標數據同步失敗", Toast.LENGTH_SHORT);
                    toast.show();
                    isSuccess = false;
                    z = e.getMessage();
                }

            }
            else {
                Toast toast = Toast.makeText(getContext(),"目標數據同步失敗", Toast.LENGTH_SHORT);
                toast.show();
            }
            return z;
        }
    }

//    public class service_delete_todb extends AsyncTask<String, String , String> {
//
//        String z = "";
//        Boolean isSuccess = false;
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Toast.makeText(getContext(),"目標數據同步成功", Toast.LENGTH_SHORT).show();
//        }
//
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            if (connection!=null){
//                try{
//                    sync_name = gv.getUserEmail();
//                    sync_servicename = gv.getServiceName();
//                    statement = connection.createStatement();
//                    statement.executeQuery("DELETE * FROM dbo.service WHERE email ='"+sync_name.toString().trim()+"' AND '"+sync_servicename.toString().trim()+ "' ;");
//
//
//                }catch (Exception e){
//                    isSuccess = false;
//                    z = e.getMessage();
//                }
//            }
//            else {
//                Toast toast = Toast.makeText(getContext(),"目標數據同步失敗", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//            return z;
//        }
//    }
}