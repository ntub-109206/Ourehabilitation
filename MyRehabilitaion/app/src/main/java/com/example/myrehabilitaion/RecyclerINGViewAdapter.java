package com.example.myrehabilitaion;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RecyclerINGViewAdapter extends RecyclerView.Adapter<RecyclerINGViewAdapter.ViewHolder> {


    public String userid;
    public String sync_serviceid;

    private List<String> mListString01;
    private List<String> mListString02;
    private List<String> mListString03 ;
    private List<String> mListString04 ;
    private List<String> mListString05 ;
    private List<String> mListString06 ;
    private List<Integer> mListImage;

    Dialog mDlog_case;
    TextView targetname;
    TextView targetaddtime;
    TextView targetbuildtime;
    TextView updatetargetfinishtime;
    Context context;
    Activity activity;

    GlobalVariable gv;

//    public void addItem(String text01, String text02) {
//        // 為了示範效果，固定新增在位置3。若要新增在最前面就把3改成0
//        mListString01.add(text01);
//        mListString02.add(text02);
//        mListImage.add(R.drawable.bg_07);
//        notifyItemInserted(mListString01.size());
//    }

//    public void updateItem(int position){
//         updatetargetname = mDlog_case.findViewById(R.id.edt_updatetargetname);
//
//        mListString01.set(position, updatetargetname.getText().toString().trim());//修改值
//        notifyDataSetChanged();//刷新版列表权
//    }

    // 刪除項目
    public void removeItem(int position){

        case_delete_todb case_delete_todb = new case_delete_todb();
        case_delete_todb.execute();

        try {
            Thread.sleep(100);
            System.out.print("record執行緒睡眠0.1秒！\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service_delete_todb service_delete_todb = new service_delete_todb();
        service_delete_todb.execute();

        mListString05.remove(position);
        notifyItemRemoved(position);
    }

    // 建構式，用來接收外部程式傳入的項目資料。
    public RecyclerINGViewAdapter(Activity activity, Context context, List<String> listString01, List<String> listString02, List<String> ListString03 , List<String> ListString04 , List<String> ListString05 , List<String> ListString06, List<Integer> listImg) {

        this.mListString01 =  listString01;
        this.mListString02 =  listString02;
        this.mListString03 = ListString03;
        this.mListString04 = ListString04;
        this.mListString05 = ListString05;
        this.mListString06 = ListString06;

        this.mListImage =  listImg;
        this.context=context;
        this.activity = activity;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImgView;
        public TextView mTxt;
        public TextView mtxtonbar;
        public ProgressBar mrecord_progressbar;
        public Button mstartrecord_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            gv = (GlobalVariable) activity.getApplicationContext();

            mImgView = (ImageView) itemView.findViewById(R.id.img_target);
            mTxt = (TextView) itemView.findViewById(R.id.txt_target);
            mtxtonbar = itemView.findViewById(R.id.txt_onbar);
            mrecord_progressbar = itemView.findViewById(R.id.reacord_progressBar);
//            mstartrecord_btn = itemView.findViewById(R.id.reacord_startbutton);
//            mstartrecord_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    gv.setServiceName(mListString05.get(getAdapterPosition()));
//
//
//                    activity.getFragmentManager().beginTransaction().replace(R.id.nav_home_container,new Fragment()).commit();
//                }
//            });

            // 處理按下的事件。
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mDlog_case = new Dialog(v.getContext());
                    mDlog_case.setContentView(R.layout.dlg_case);
                    mDlog_case.setCancelable(true);
                    mDlog_case.show();

                    //------------------設定dlg目標部位名稱、建立時間、完成時間------------------------
                    targetname = mDlog_case.findViewById(R.id.edt_updatetargetname);
                    targetname.setText(mListString01.get(getAdapterPosition()));
                    targetaddtime =mDlog_case.findViewById(R.id.edt_date);
                    targetaddtime.setText(mListString02.get(getAdapterPosition()));
                    targetbuildtime = mDlog_case.findViewById(R.id.txt_targetdate);
                    targetbuildtime.setText(mListString06.get(getAdapterPosition()));

//                    updatetargetfinishtime = mDlog_case.findViewById(R.id.txt_targetdate);
//                    updatetargetfinishtime.setText(mListString03.get(getAdapterPosition()).toString().trim());
                    //------------------設定dlg目標部位名稱------------------------

//                    Button btnupdatetargt =  mDlog_case.findViewById(R.id.btn_updatetargt);
//                    btnupdatetargt.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            updateItem(getAdapterPosition());
//
//                            Toast.makeText(v.getContext(),
//                                    "您更新了目標", Toast.LENGTH_SHORT).show();
//
//                            mDlog_case.dismiss();
//                        }
//                    });
                    Button btncanceledit = mDlog_case.findViewById(R.id.btn_cancelbox);
                    btncanceledit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDlog_case.dismiss();
                        }
                    });

                    Button btndeltarget =mDlog_case.findViewById(R.id.btn_deltbox);
                    btndeltarget.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gv.setServiceID(mListString05.get(getAdapterPosition()));

                            removeItem(getAdapterPosition());
                            mDlog_case.dismiss();
                        }
                    });

                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    // RecyclerView會呼叫這個方法，我們必須建立好項目的ViewHolder物件，
    // 然後傳回給RecyclerView。
    @NonNull
    @Override
    public RecyclerINGViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 建立一個 view。
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerexampleview, viewGroup, false);

        // 建立這個 view 的 ViewHolder。
        RecyclerINGViewAdapter.ViewHolder viewHolder = new RecyclerINGViewAdapter.ViewHolder(v);
        return viewHolder;
    }

    // RecyclerView會呼叫這個方法，我們必須把項目資料填入ViewHolder物件。
    @Override
    public void onBindViewHolder(@NonNull RecyclerINGViewAdapter.ViewHolder viewHolder, int i) {
        // 把資料設定給 ViewHolder。
        viewHolder.mImgView.setImageResource(mListImage.get(i));
        viewHolder.mTxt.setText(mListString01.get(i));

        int p = 100 * Integer.valueOf(mListString03.get(i))/Integer.valueOf(mListString04.get(i));

        viewHolder.mrecord_progressbar.setProgress(p);
        viewHolder.mtxtonbar.setText(String.valueOf(mListString03.get(i)) + "   /   " + String.valueOf(mListString04.get(i)));

//        if (i==0 || i==2 || i==4 || i==6) {
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( viewHolder.itemView.getLayoutParams());
//            lp.setMargins(0,0,100,50);
//            viewHolder.itemView.setLayoutParams(lp);
//        } else {
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(viewHolder.itemView.getLayoutParams());
//            lp.setMargins(100,0,0,50);
//            viewHolder.itemView.setLayoutParams(lp);
//        }
    }


    // RecyclerView會呼叫這個方法，我們要傳回總共有幾個項目。
    @Override
    public int getItemCount() {
        return this.mListString05.size();
    }

    public class case_delete_todb  extends AsyncTask<String, String , String> {

        private  String ip = "140.131.114.241";
        private  String port = "1433";
        private  String Classes = "net.sourceforge.jtds.jdbc.Driver";
        private  String database = "109-rehabilitation";
        private  String username = "case210906";
        private  String password = "1@case206";
        private  String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

        private Connection connection = null;

        Statement statement01 = null;

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Class.forName(Classes);
                connection = DriverManager.getConnection(url, username,password);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            } catch (SQLException e) {
                e.printStackTrace();

            }

        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected String doInBackground(String... strings) {

            GlobalVariable gv = (GlobalVariable) context.getApplicationContext();

            if (connection!=null){
                try{

                    userid = gv.getUserID();
                    sync_serviceid = gv.getServiceID();

                    statement01 = connection.createStatement();
                    statement01.executeQuery("DELETE FROM dbo.case_data WHERE user_id ="+ Integer.valueOf(userid)+" AND service_id ="+Integer.valueOf(sync_serviceid)+";");
                    statement01.executeQuery("DELETE FROM dbo.service WHERE user_id ="+ Integer.valueOf(userid)+" AND service_id ="+Integer.valueOf(sync_serviceid)+";");

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

    public class service_delete_todb  extends AsyncTask<String, String , String> {

        private  String ip = "140.131.114.241";
        private  String port = "1433";
        private  String Classes = "net.sourceforge.jtds.jdbc.Driver";
        private  String database = "109-rehabilitation";
        private  String username = "case210906";
        private  String password = "1@case206";
        private  String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

        private Connection connection = null;

        Statement statement02 = null;

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Class.forName(Classes);
                connection = DriverManager.getConnection(url, username,password);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            } catch (SQLException e) {
                e.printStackTrace();

            }

        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected String doInBackground(String... strings) {

            GlobalVariable gv = (GlobalVariable) context.getApplicationContext();

            if (connection!=null){
                try{

                    userid = gv.getUserID();
                    sync_serviceid = gv.getServiceID();

                    statement02 = connection.createStatement();
                    statement02.executeQuery("DELETE FROM dbo.service WHERE user_id ="+ Integer.valueOf(userid)+" AND service_id ="+Integer.valueOf(sync_serviceid)+";");

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
