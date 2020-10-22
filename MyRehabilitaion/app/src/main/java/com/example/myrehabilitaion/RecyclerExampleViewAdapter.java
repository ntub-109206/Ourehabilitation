package com.example.myrehabilitaion;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrehabilitaion.ui.Record.RecordFragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecyclerExampleViewAdapter extends RecyclerView.Adapter<RecyclerExampleViewAdapter.ViewHolder> {

    private List<String> mListString01;
    private List<String> mListString02;
    private List<String> mListString03 ;
    private List<Integer> mListImage;

    Dialog mDlog_case;
    TextView updatetargetname;
    TextView updatetargetaddtime;
    TextView updatetargetfinishtime;
    Context context;
    Activity activity;

    public void addItem(String text01, String text02) {
        // 為了示範效果，固定新增在位置3。若要新增在最前面就把3改成0
        mListString01.add(text01);
        mListString02.add(text02);
        mListImage.add(R.drawable.bg_07);
        notifyItemInserted(mListString01.size());
    }

//    public void updateItem(int position){
//
//         updatetargetname = mDlog_case.findViewById(R.id.edt_updatetargetname);
//
//        mListString01.set(position, updatetargetname.getText().toString().trim());//修改值
//        notifyDataSetChanged();//刷新版列表权
//    }



    // 刪除項目
    public void removeItem(int position){
        service_delete_todb service_delete_todb = new service_delete_todb();
        service_delete_todb.execute();

        mListString01.remove(position);
        notifyItemRemoved(position);
    }

    // 建構式，用來接收外部程式傳入的項目資料。
    public RecyclerExampleViewAdapter(Activity activity,Context context, List<String> listString01, List<String> listString02, List<Integer> listImg) {

        this.mListString01 =  listString01;
        this.mListString02 =  listString02;
//        mListString03 = listString03;
        this.mListImage =  listImg;
        this.context=context;
        this.activity = activity;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImgView;
        public TextView mTxt;
        public ImageButton mEditImgPen;

        public ViewHolder(View itemView) {
            super(itemView);


            mImgView = (ImageView) itemView.findViewById(R.id.img_target);
            mTxt = (TextView) itemView.findViewById(R.id.txt_target);
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
                    updatetargetname = mDlog_case.findViewById(R.id.edt_updatetargetname);
                    updatetargetname.setText(mListString01.get(getAdapterPosition()));
                    updatetargetaddtime =mDlog_case.findViewById(R.id.edt_date);
                    updatetargetaddtime.setText(mListString02.get(getAdapterPosition()));

//                    updatetargetfinishtime = mDlog_case.findViewById(R.id.txt_targetdate);
//                    updatetargetfinishtime.setText(mListString03.get(getAdapterPosition()).toString().trim());
                    //------------------設定dlg目標部位名稱------------------------

                    Button btnupdatetargt =  mDlog_case.findViewById(R.id.btn_updatetargt);

                    btnupdatetargt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            updateItem(getAdapterPosition());

                            Toast.makeText(v.getContext(),
                                    "您更新了目標", Toast.LENGTH_SHORT).show();

                            mDlog_case.dismiss();
                        }
                    });
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
            GlobalVariable gv = (GlobalVariable) v.getContext().getApplicationContext();
            gv.setServiceName(mListString01.get(getAdapterPosition()));

            Intent intent = new Intent(v.getContext(),RecordMain.class);
            v.getContext().startActivity(intent);
        }
    }


    // RecyclerView會呼叫這個方法，我們必須建立好項目的ViewHolder物件，
    // 然後傳回給RecyclerView。
    @NonNull
    @Override
    public RecyclerExampleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 建立一個 view。
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerexampleview, viewGroup, false);

        // 建立這個 view 的 ViewHolder。
        RecyclerExampleViewAdapter.ViewHolder viewHolder = new RecyclerExampleViewAdapter.ViewHolder(v);
        return viewHolder;
    }

    // RecyclerView會呼叫這個方法，我們必須把項目資料填入ViewHolder物件。
    @Override
    public void onBindViewHolder(@NonNull RecyclerExampleViewAdapter.ViewHolder viewHolder, int i) {
        // 把資料設定給 ViewHolder。
        viewHolder.mImgView.setImageResource(mListImage.get(i));
        viewHolder.mTxt.setText(mListString01.get(i));

    }

    // RecyclerView會呼叫這個方法，我們要傳回總共有幾個項目。
    @Override
    public int getItemCount() {

//        if(mListString01.size()==0){
//            getItemCount();
//        }
//        for(i =0;i<10;i++){
//            if(mListString01.size()==0){
//                getItemCount();
//            }
//            else if(mListString01.size()>0){
//                break;
//            }
//        }

        //Log.d("vincentCount02", String.valueOf(count));
        //count=0;

//        Log.d("text01", String.valueOf(mListString01));
//        Log.d("text01", String.valueOf(mListString01.size()));
        Log.d("test08", String.valueOf(this.mListImage));

        return this.mListString01.size();
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

        Statement statement = null;

        public String sync_name;
        public String sync_servicename;


        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

        }


        @Override
        protected String doInBackground(String... strings) {

            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Class.forName(Classes);
                connection = DriverManager.getConnection(url, username,password);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            } catch (
                    SQLException e) {
                e.printStackTrace();

            }
            GlobalVariable gv = (GlobalVariable) context.getApplicationContext();

            if (connection!=null){
                try{

                    sync_name = gv.getUserEmail();
                    sync_servicename = gv.getServiceName();
                    statement = connection.createStatement();
                    statement.executeQuery("DELETE FROM dbo.service WHERE email ='"+sync_name.toString().trim()+"' AND body ='"+sync_servicename.toString().trim()+ "' ;");


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
