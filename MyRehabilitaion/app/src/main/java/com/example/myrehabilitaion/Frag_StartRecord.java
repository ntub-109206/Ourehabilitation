package com.example.myrehabilitaion;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Frag_StartRecord extends Fragment {


    private static String ip = "140.131.114.241";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "109-rehabilitation";
    private static String username = "case210906";
    private static String password = "1@case206";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    Statement statement = null;

    Button mbtnconfirmstart, mbtncancelstart;
    private static Variables Variables = new Variables();
    TextView body_startrecord;
    Dialog mDlog;

    GlobalVariable gv;

    private NumberPicker mNumPickerAge;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_startrecord, container, false);


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
        Spinner spnUnit = root.findViewById(R.id.spin_unit);

        mNumPickerAge = root.findViewById(R.id.numPickerAge);
        mNumPickerAge.setMaxValue(200);
        mNumPickerAge.setMinValue(0);
        mNumPickerAge.setValue(18);

        gv = (GlobalVariable)getActivity().getApplicationContext();
        body_startrecord = root.findViewById(R.id.body_startrecord);
        body_startrecord.setText(gv.getServiceName().toString());


        mbtnconfirmstart = root.findViewById(R.id.btn_confirmstart);
        mbtncancelstart = root.findViewById(R.id.btn_cancelstartpage);
        mbtnconfirmstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDlog = new Dialog(getContext());
                mDlog.setContentView(R.layout.dlg_startcheck);
                mDlog.setCancelable(true);
                mDlog.show();

                Button btnstarttargt = mDlog.findViewById(R.id.btn_checkconfirm);
                Button btncancel = mDlog.findViewById(R.id.btn_checkcancel);

                btnstarttargt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecordMain recordmain = (RecordMain) getActivity();
                        recordmain.showRecordingFragment();

                        //int count = Integer.parseInt(edtargetinput.getText().toString());
                        //Variables.timsSetter(count);

//                        Intent itt = new Intent();
//                        itt.putExtra("key1",Integer.parseInt(edtargetinput.getText().toString().trim()));

                        mDlog.dismiss();
                    }
                });


                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDlog.dismiss();
                    }
                });

            }
        });

        mbtncancelstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main.class);
                startActivity(intent);
            }
        });
        return root;

    }

    public class case_sync_todb extends AsyncTask<String, String , String> {

        GlobalVariable gv = (GlobalVariable)getActivity().getApplicationContext();

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
                    String sync_name = gv.getUserEmail();
                    String sync_servicename = gv.getServiceName();
                    String sunc_casename = gv.getCaseName();

                    statement = connection.createStatement();
                    statement.executeQuery("INSERT INTO dbo.Case_data (email,body,body,times) VALUES ('"+sync_name.toString().trim()+"','"+sync_servicename.toString().trim()+"','" + sunc_casename.toString().trim()+"');");

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


}
