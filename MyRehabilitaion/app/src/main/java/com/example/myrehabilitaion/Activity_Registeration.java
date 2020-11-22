package com.example.myrehabilitaion;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Activity_Registeration extends AppCompatActivity  {

    EditText edt_name,edt_email,edt_password,edt_passwordConfirm,edt_phoneNumber,edt_birthday;
    Button btn_register;
    RadioGroup radioGrp_gender;
    ImageButton imgBtn_emailInspect;
    String str_gen;
    server_checkEmailID server_checkeEmailID;



    private static String ip = "140.131.114.241";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "109-rehabilitation";
    private static String username = "case210906";
    private static String password = "1@case206";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    Statement statement = null;
    byte[] bArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_registeration);

        Bitmap photo =  BitmapFactory.decodeResource(this.getResources(), R.drawable.man);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
        bArray = bos.toByteArray();

        ActivityCompat.requestPermissions(Activity_Registeration.this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
//            Toast toast = Toast.makeText(Class_Registeration.this,"Success", Toast.LENGTH_SHORT);
//            toast.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//            Toast toast = Toast.makeText(Class_Registeration.this,"ERROR", Toast.LENGTH_SHORT);
//            toast.show();
        } catch (SQLException e) {
            e.printStackTrace();
//            Toast toast = Toast.makeText(Class_Registeration.this,"FAILURE", Toast.LENGTH_SHORT);
//            toast.show();

        }

        server_checkeEmailID = new server_checkEmailID();

        edt_name = null;
        edt_email = null;
        edt_password = null;
        edt_passwordConfirm = null;
        edt_phoneNumber = null;
        edt_birthday = null;

        edt_name = (EditText)findViewById(R.id.edtName);
        edt_email = (EditText)findViewById(R.id.edtEmail);
        edt_password = (EditText)findViewById(R.id.edtPassword);
        edt_passwordConfirm = findViewById(R.id.edtPasswordConfirm);
        edt_phoneNumber =findViewById(R.id.edtPhone);
        radioGrp_gender = findViewById(R.id.chkgendergrp);
        btn_register = (Button)findViewById(R.id.mbtnRegistr);

        switch(radioGrp_gender.getCheckedRadioButtonId()){
            case R.id.chkman:
                str_gen = "男"; //顯示結果
                break;
            case R.id.chkwmn:
                str_gen = "女"; //顯示結果
                break;
        }

        imgBtn_emailInspect = findViewById(R.id.checkemail);
        imgBtn_emailInspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                server_checkeEmailID.execute();

                try {
                    Thread.sleep(100);
                    System.out.print(" 執行緒睡眠0.01秒！\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


        edt_birthday = findViewById(R.id.edtBirthday);
        edt_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard(v);
                Calendar now = Calendar.getInstance();

                DatePickerDialog dataPickerDialog = new DatePickerDialog(Activity_Registeration.this,datePickerDlgOnDataSet, now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));

                dataPickerDialog.setTitle("選擇日期");
                dataPickerDialog.setMessage("請選擇您的生日日期");
                dataPickerDialog.setCancelable(true);
                dataPickerDialog.show();

            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_name.getText().toString().trim().equals("") ||edt_email.getText().toString().trim().equals("")||edt_password.getText().toString().trim().equals("")||edt_phoneNumber.getText().toString().trim().equals("")||edt_birthday.getText().toString().trim().equals("")){
                    Toast.makeText(Activity_Registeration.this, "請完成資料填寫", Toast.LENGTH_SHORT).show();
                }else{

                    server_checkeEmailID.execute();

                    try {
                        Thread.sleep(100);
                        System.out.print("執行緒睡眠0.01秒！\n");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(edt_password.length() < 6){
                        Toast.makeText(Activity_Registeration.this, "密碼太短", Toast.LENGTH_SHORT).show();
                    }else{
                        if(edt_password.getText().toString().trim().matches(edt_passwordConfirm.getText().toString().trim())){
                            server_registerUser registerUser =new server_registerUser();
                            registerUser.execute();

                            try {
                                Thread.sleep(100);
                                System.out.print("    執行緒睡眠0.01秒！\n");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }else{
                            edt_password.setText("");
                            edt_passwordConfirm.setText("");
                            Toast.makeText(Activity_Registeration.this, "密碼輸入不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
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

            edt_birthday.setText(
                    String.valueOf(year) + "-" + String.valueOf(month +1) + "-" + String.valueOf(dayOfMonth)
            );
        }
    };

    public class server_registerUser extends AsyncTask<String, String , String>{

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
           Toast.makeText(Activity_Registeration.this,"註冊成功", Toast.LENGTH_SHORT).show();


            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            gv.setUserEmail(edt_email.getText().toString().trim());
            gv.setUserPassword(edt_password.getText().toString().trim());

            Intent intent = new Intent(Activity_Registeration.this, Activity_Login.class);
            startActivity(intent);
        }

        @Override
        protected String doInBackground(String... strings) {

            if (connection!=null){
                try{
                    statement = connection.createStatement();
                    statement.executeQuery("INSERT INTO dbo.registered (username, email, password ,phone, gender, birthday, pic) VALUES ('"+edt_name.getText().toString().trim()+"','"+edt_email.getText().toString().trim()+"','"+ Encrypt.SHA512(edt_password.getText().toString().trim())+"','"+edt_phoneNumber.getText().toString().trim()+"','"+radioGrp_gender.toString().trim()+"','"+edt_birthday.getText().toString().trim()+"', CAST('"+bArray.toString().trim()+"' AS VARBINARY(MAX)));");

                }catch (Exception e){
                    isSuccess = false;
                    z = e.getMessage();
                }
            }
            else {
                Toast toast = Toast.makeText(Activity_Registeration.this,"註冊失敗", Toast.LENGTH_SHORT);
                toast.show();
            }
            return z;
        }
    }

    public class server_checkEmailID extends AsyncTask<String, String , String>{

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

            if (connection!=null){
                try{
                    statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT * FROM dbo.registered WHERE email = '" + edt_email.getText().toString().trim() +  "';");


                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Activity_Registeration.this, "此Email已被使用", Toast.LENGTH_SHORT).show();
                            }
                        });
                        edt_email.setText("");
                    }else{

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Activity_Registeration.this,"此Email可使用", Toast.LENGTH_SHORT).show();
                            }
                        });

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
