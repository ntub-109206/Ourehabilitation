package com.example.rehabilitation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);



        //取得DrawerLayout元件，後面會呼叫他的closeDrawers()選單
        mDrawerLayout = findViewById(R.id.drawerLayout);

        //取得Navigation元件，然後設定Listener處理按下選單項目的操作
        NavigationView navView = findViewById(R.id.navigationView);
        navView.setNavigationItemSelectedListener(navViewOnItemSelected);

        //設定Action Bar,讓它顯示ActionBarDrawerToggle按鈕
        ActionBar actBar = getSupportActionBar();
        assert actBar != null;
        actBar.setDisplayHomeAsUpEnabled(true);
        actBar.setHomeButtonEnabled(true);

        //建立ActionBarDrawerToggle並且讓他和DrawerLayout元件偕同運作
        mDrawerToggle = new ActionBarDrawerToggle(
                this,mDrawerLayout,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //要先把選單得項目傳給 ActionBarDrawerToggle 處理
        //如果它回傳true,表示處理完成,不需要在繼續往下處理
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private NavigationView.OnNavigationItemSelectedListener navViewOnItemSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.menuItemChangeInfo:
                    Toast.makeText(HomePage.this,"更改個人資料",Toast.LENGTH_LONG).show();
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.menuItemServiceMng:
                    Toast.makeText(HomePage.this,"服務管理",Toast.LENGTH_LONG).show();
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.menuItemAbout:
                    Toast.makeText(HomePage.this,"關於 ",Toast.LENGTH_LONG).show();
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.menuItemExit:
                    Toast.makeText(HomePage.this,"登出 ",Toast.LENGTH_LONG).show();
                    mDrawerLayout.closeDrawers();
                    break;
            }
            return false;
        }
    };
}
