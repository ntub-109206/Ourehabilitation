package com.example.myrehabilitaion;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrehabilitaion.Connection.ConnectionClass;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterationPage extends AppCompatActivity {

    EditText name,email,password,PasswordConfirm,Phone,Birthday;
    Button registerbtn;
    //TextView status;
    Connection con;
    String un, pass, db, ip;
    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ip = "140.131.114.241";
        db = "109-rehabilitation";
        un = "case210906";
        pass = "1@case206";
        setContentView(R.layout.page_registeration);
        name = (EditText)findViewById(R.id.edtName);
        email = (EditText)findViewById(R.id.edtEmail);
        password = (EditText)findViewById(R.id.edtPassword);
        PasswordConfirm = (EditText)findViewById(R.id.edtPasswordConfirm);
        Phone = (EditText)findViewById(R.id.edtPhone);
        Birthday = (EditText)findViewById(R.id.edtBirthday);
        registerbtn = (Button)findViewById(R.id.mbtnRegistr);
       // status = (TextView)findViewById(R.id.TextStatus);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registeruser registr = new Registeruser();
                registr.execute("");
            }
        });
    }

    public class Registeruser extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            //Toast.makeText(RegisterationPage.this, "Sending Data to Database", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(RegisterationPage.this, "Registration Successful", Toast.LENGTH_LONG).show();
            name.setText("");
            email.setText("");
            password.setText("");
            PasswordConfirm.setText("");
            Phone.setText("");
            Birthday.setText("");
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                con = connectionClass(ConnectionClass.ip.toString(),ConnectionClass.db.toString(),ConnectionClass.un.toString(),ConnectionClass.pass.toString());
                if(con == null){
                    z = "Check Your Internet Connection";
                }
                else{
                    String sql = "INSERT INTO dbo.registered (name,email,password,PasswordConfirm,Phone,Birthday)" +
                            " VALUES ('"+name.getText()+"','"+email.getText()+"','"+password.getText()+"','"+PasswordConfirm.getText()+"'" +
                            ",'"+Phone.getText()+"','"+Birthday.getText()+"')";
                    stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                }

            }catch (Exception e){
                isSuccess = false;
                z = e.getMessage();
            }

            return z;
        }
    }
   /* public void insertData(String data) {
        try {
            String url = new String();
            String db_user=new String();
            String db_password=new String();
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "INSERT INTO `test` (`name`) VALUES ('" + data + "')";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
            Log.v("DB", "寫入資料完成：" + data);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB", "寫入資料失敗");
            Log.e("DB", e.toString());
        }
    }*/

    @SuppressLint("NewApi")
    public Connection connectionClass(String ip, String db, String un, String pass){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + ip+"/" + db + ";user=" + un + ";password=" + pass + ";";
        connection = DriverManager.getConnection(connectionURL);
    } catch (SQLException se) {
        Log.e("ERRO", se.getMessage());
    } catch (ClassNotFoundException e) {
        Log.e("ERRO", e.getMessage());
    } catch (Exception e) {
        Log.e("ERRO", e.getMessage());
    }
        return connection;
    }
}
