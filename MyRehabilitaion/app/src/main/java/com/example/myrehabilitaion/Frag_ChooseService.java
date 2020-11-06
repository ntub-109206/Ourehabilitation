package com.example.myrehabilitaion;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Frag_ChooseService extends Fragment {

    private static String ip = "140.131.114.241";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "109-rehabilitation";
    private static String username = "case210906";
    private static String password = "1@case206";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    Statement statement = null;


    RecyclerInfoAdapter adapter_Info;
    RecyclerView recyclerexample;
    Dialog mDlog01;

    public EditText edttargetname;
    public EditText edtaddtime;

    public List<String> listStr01;
    public List<String> listStr02;
    public List<String> listStr03;
    public List<String> listStr04;
    public List<String> listStr05;
    public List<Integer> listImg;

    service_sync_fromdb service_sync_fromdb;

    public String userid;
    public String sync_servicename;

    GlobalVariable gv ;

    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_frag__record_finished, container, false);

        gv = (GlobalVariable)getActivity().getApplicationContext();
        userid = gv.getUserID();

        listStr01 = new ArrayList<String>();
        listStr02 = new ArrayList<String>();
        listStr03 = new ArrayList<String>();
        listStr04 = new ArrayList<String>();
        listStr05 = new ArrayList<String>();
        listImg = new ArrayList<Integer>();

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

//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerexample = root.findViewById(R.id.recyclerview_home);
        recyclerexample.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
//        recyclerexample.setLayoutManager(layoutManager);

        adapter_Info = new RecyclerInfoAdapter(Frag_ChooseService.super.getActivity(), Frag_ChooseService.super.getActivity().getApplicationContext(), listStr01, listStr02,listStr03, listStr04, listStr05,listImg);
        //adapter_home.addItem(sercmng.Syc());

        try {
            Thread.sleep(100);
            System.out.print("record執行緒睡眠0.1秒！\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerexample.setAdapter(adapter_Info);

        return root;
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
            ArrayList<String> array_sync03 = new ArrayList<String>();
            ArrayList<String> array_sync04 = new ArrayList<String>();
            ArrayList<String> array_sync05 = new ArrayList<String>();

            if (connection!=null){

                try{
                    statement = connection.createStatement();
                    ResultSet result = statement.executeQuery("SELECT body, date, progress, target, service_id FROM dbo.service WHERE user_id ="+Integer.valueOf(userid)+" AND progress/target < 1;");

                    while (result.next()) {
                        array_sync01.add(result.getString(1).toString().trim());
                        array_sync02.add(result.getString(2).toString().trim());
                        array_sync03.add(result.getString(3).toString().trim());
                        array_sync04.add(result.getString(4).toString().trim());
                        array_sync05.add(result.getString(5).toString().trim());
                    }

                    for (int i = 0; i < array_sync01.size(); i++) {
                        listStr01.add((String) array_sync01.get(i));
                        listStr02.add(array_sync02.get(i));
                        listStr03.add(array_sync03.get(i));
                        listStr04.add(array_sync04.get(i));
                        listStr05.add(array_sync05.get(i));
//                        double r =Math.random()*3;
//                        int image[]  = {R.drawable.bg_04, R.drawable.bg_03, R.drawable.bg_07};
                        listImg.add(R.drawable.legtrain);
                    }

                }catch (Exception e){

//                    Toast toast = Toast.makeText(getContext(),"目標數據同步失敗", Toast.LENGTH_SHORT);
//                    toast.show();
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