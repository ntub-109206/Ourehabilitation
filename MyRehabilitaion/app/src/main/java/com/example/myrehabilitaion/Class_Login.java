package com.example.myrehabilitaion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Class_Login extends AppCompatActivity {


    Button login,register;
    EditText edt_email, edt_password;
    CheckBox remember;

    private static String ip = "140.131.114.241";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "109-rehabilitation";
    private static String username = "case210906";
    private static String password = "1@case206";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        ActivityCompat.requestPermissions(Class_Login.this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        login = findViewById(R.id.btn_visit);
        edt_email= findViewById(R.id.edtEmailLogin);
        edt_password = findViewById(R.id.editPasswordLogin);
        register = findViewById(R.id.registerar_button);
        remember = findViewById(R.id.checkBox);

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(Class_Login.this, "Checked", Toast.LENGTH_SHORT).show();
                }else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(Class_Login.this, "UnChecked", Toast.LENGTH_SHORT).show();

                }
            }
        });
        SharedPreferences preferences =getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");

        if(checkbox.equals(true)){

        }else if(checkbox.equals(false)){
            Toast.makeText(Class_Login.this, "Checked", Toast.LENGTH_SHORT).show();
        }

        GlobalVariable gv = (GlobalVariable)getApplicationContext();

        String testname01 = gv.getUserEmail();
        String testpasswd01 = gv.getUserPassword();

        edt_email.setText(testname01);
        edt_password.setText(testpasswd01);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
            Toast toast = Toast.makeText(Class_Login.this,"Success", Toast.LENGTH_SHORT);
            toast.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(Class_Login.this,"ERROR", Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(Class_Login.this,"FAILURE", Toast.LENGTH_SHORT);
            toast.show();

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkLogin checkLogin = new checkLogin();
                checkLogin.execute();

                Log.d("Class_Login",  "Class_Login結束");

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Class_Login.this, Class_Registeration.class);
                startActivity(intent);

            }
        });

    }

    class checkLogin extends AsyncTask<String, String, String> {

        String z = null;
        Boolean isSuccess = false;

        GlobalVariable gv = (GlobalVariable)getApplicationContext();


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String sql = "SELECT * FROM dbo.registered WHERE email = '" + edt_email.getText() + "' AND password = '" + edt_password.getText() + "';";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {

                    gv.setUserID(rs.getString(1).toString().trim());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Class_Login.this, "登入成功", Toast.LENGTH_LONG).show();
                            gv.setUserEmail(edt_email.getText().toString().trim());
                            gv.setUserPassword(edt_password.getText().toString().trim());

                        }
                    });
                    z = "Success";

                    Intent intent = new Intent(Class_Login.this, Main.class);
                    startActivity(intent);
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Class_Login.this, "帳號或密碼有誤", Toast.LENGTH_LONG).show();
                        }
                    });

                    edt_email.setText("");
                    edt_password.setText("");
                }
            } catch (Exception e) {

                isSuccess = false;
                Log.e("SQL Error : ", e.getMessage());
            }

            return z;
        }
    }
}


