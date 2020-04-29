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

        Button mbtnlogin01=(Button)findViewById(R.id.btn_visiterlogin);
        mbtnlogin01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WelcomePage.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button mbtnregistr02=(Button)findViewById(R.id.registerarion_button);
        mbtnregistr02.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WelcomePage.this,RegisterationPage.class);
                startActivity(intent);
            }
        });

    }
}

