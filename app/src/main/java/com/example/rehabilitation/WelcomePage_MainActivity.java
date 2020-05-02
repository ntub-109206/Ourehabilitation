package com.example.rehabilitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomePage_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_welcome_activity_main);

        Button mbtnvisit=(Button)findViewById(R.id.btn_visiterlogin);
        mbtnvisit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WelcomePage_MainActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button mbtnregistr=(Button)findViewById(R.id.registerarion_button);
        mbtnregistr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WelcomePage_MainActivity.this,RegisterationPage.class);
                startActivity(intent);
            }
        });

        Button mbtnlogin=(Button)findViewById(R.id.login_button);
        mbtnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WelcomePage_MainActivity.this,LoginPage.class);
                startActivity(intent);
            }
        });

    }
}
