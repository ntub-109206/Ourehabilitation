package com.example.rehabilitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        Button mbtnlogin=(Button)findViewById(R.id.login_button);
        mbtnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(LoginPage.this,LoginPage.class);
                startActivity(intent);
            }
        });
    }


}
