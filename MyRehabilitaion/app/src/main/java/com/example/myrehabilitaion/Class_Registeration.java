package com.example.myrehabilitaion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.xml.datatype.Duration;

import static android.widget.Toast.LENGTH_LONG;

public class Class_Registeration extends AppCompatActivity  {

    EditText mname,memail,mpassword;
    public EditText mbirthday;
    Button registerbtn;


    private static String ip = "140.131.114.241";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "109-rehabilitation";
    private static String username = "case210906";
    private static String password = "1@case206";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    Statement statement = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_registeration);

        mname = (EditText)findViewById(R.id.edtName);
        memail = (EditText)findViewById(R.id.edtEmail);
        mpassword = (EditText)findViewById(R.id.edtPassword);
        registerbtn = (Button)findViewById(R.id.mbtnRegistr);
        mbirthday = findViewById(R.id.edtBirthday);

        mbirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard(v);
                Calendar now = Calendar.getInstance();

                DatePickerDialog dataPickerDialog = new DatePickerDialog(Class_Registeration.this,datePickerDlgOnDataSet, now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));

                dataPickerDialog.setTitle("選擇日期");
                dataPickerDialog.setMessage("請選擇您的生日日期");
                dataPickerDialog.setCancelable(true);
                dataPickerDialog.show();

            }
        });

        ActivityCompat.requestPermissions(Class_Registeration.this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
            Toast toast = Toast.makeText(Class_Registeration.this,"Success", Toast.LENGTH_SHORT);
            toast.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(Class_Registeration.this,"ERROR", Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(Class_Registeration.this,"FAILURE", Toast.LENGTH_SHORT);
            toast.show();

        }
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeruser registeruser =new registeruser();
                registeruser.execute();
            }
        });
    }

    public void closeKeyBoard(View editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private DatePickerDialog.OnDateSetListener datePickerDlgOnDataSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            mbirthday.setText(
                    String.valueOf(year) + "年" + String.valueOf(month) + "月" + String.valueOf(dayOfMonth)
            );

        }
    };

    public class registeruser extends AsyncTask<String, String , String>{

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
           Toast.makeText(Class_Registeration.this,"註冊成功", Toast.LENGTH_SHORT).show();


            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            gv.setUserEmail(memail.getText().toString().trim());
            gv.setUserPassword(mpassword.getText().toString().trim());

            Intent intent = new Intent(Class_Registeration.this, Class_Login.class);
            startActivity(intent);
        }

        @Override
        protected String doInBackground(String... strings) {

            if (connection!=null){
                try{
                    statement = connection.createStatement();
                    statement.executeQuery("INSERT INTO dbo.registered (username,email,password,birthday) VALUES ('"+mname.getText().toString().trim()+"','"+memail.getText().toString().trim()+"','"+mpassword.getText().toString().trim()+"','"+mbirthday.getText().toString().trim()+"');");
                }catch (Exception e){
                    isSuccess = false;
                    z = e.getMessage();
                }
            }
            else {
                Toast toast = Toast.makeText(Class_Registeration.this,"註冊失敗", Toast.LENGTH_SHORT);
                toast.show();
            }
            return z;
        }
    }
}
