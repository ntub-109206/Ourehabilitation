package com.example.rehabilitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personallist);

        Button mbtnlogin03=(Button)findViewById(R.id.btn_login);
        mbtnlogin03.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(PersonalList.this,HomePage.class);
                startActivity(intent);
            }
        });
    }
}
