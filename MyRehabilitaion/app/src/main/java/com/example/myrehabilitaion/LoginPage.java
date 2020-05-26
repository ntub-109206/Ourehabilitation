package com.example.myrehabilitaion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrehabilitaion.FragmentHomePage.HomeFragment;
import com.example.myrehabilitaion.Main;
import com.example.myrehabilitaion.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginPage extends AppCompatActivity {
    Button login,visitlogin;
    EditText username, password;
    ProgressBar progressBar;

    Connection con;
    String un, pass, db, ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        login = findViewById(R.id.login_button);
        visitlogin = findViewById(R.id.btn_visit);
        username = findViewById(R.id.edtEmailLogin);
        password = findViewById(R.id.editPasswordLogin);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        ip = "140.131.114.241";
        db = "109-rehabilitation";
        un = "case210906";
        pass = "1@case206";

        login.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLogin checklogin = new CheckLogin();
                checklogin.execute();
            }
        }));

        visitlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this,Main.class);
                startActivity(intent);

            }
        });
    }


    public class CheckLogin extends AsyncTask<String, String, String>{
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected  String doInBackground(String...params){
            String usernam = username.getText().toString();
            String  passwordd = password.getText().toString();

            if(usernam.trim().equals("") || passwordd.trim().equals("")){
                z = "Please enter Username and Password";
            }
            else{
                try{
                    con = connectionclass(un, pass, db, ip);
                    if(con == null){
                        z = "Check Your Internet Access!";
                    }
                    else{
                        String query = "select * from dbo.login where user_name='" + usernam.toString() + "' and pass_word ='" + passwordd.toString();
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next()){
                            z = "Login successful";
                            isSuccess = true;
                            con.close();
                        }
                        else{
                            z = "Invalid Credentials";
                            isSuccess = false;
                        }
                    }
                }
                catch (Exception ex){
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }


        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(LoginPage.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess){
                Toast.makeText(LoginPage.this, "Login Successfull", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(LoginPage.this, z, Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Drvier");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + database + ";user" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("error here 1 : ", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error here 2:", e.getMessage());
        } catch (Exception e) {
            Log.e("error here 3 :", e.getMessage());
        }
        return connection;
    }


}

