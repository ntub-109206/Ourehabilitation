package com.example.rehabilitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Button mbtnlogin01=(Button)findViewById(R.id.login_button);
        mbtnlogin01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WelcomePage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        Button mbtnregistr01=(Button)findViewById(R.id.registerarion_button);
        mbtnregistr01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WelcomePage.this,RegisterationPage.class);
                startActivity(intent);
            }
        });

    }
}

