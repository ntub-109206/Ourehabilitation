package com.example.myrehabilitaion;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.UUID;

public class Main extends AppCompatActivity {
    GlobalVariable gv;

    Toolbar toolbar;
    Dialog mDlog_case;

    TextView side_name;
    TextView side_email;

    DrawerLayout mDrawerLayout;
    private AppBarConfiguration mAppBarConfiguration;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidedrawer_home);
        gv = (GlobalVariable)getApplicationContext();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setBackgroundColor(0xFF336699);
//        toolbar.setTitle("藍芽狀態");
//        bt_status = " [ Bluetooth Status ]";
//        toolbar.setSubtitle(bt_status);

//        toolbar.setLogo(R.drawable.new_logo);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        // 取得Header
        View header = navigationView.getHeaderView(0);
// 取得Header中的TextView
        side_name = (TextView)  header.findViewById(R.id.side_name);
        side_email = (TextView)  header.findViewById(R.id.side_email);
        side_name.setText(String.valueOf(gv.getUserName()));
        side_email.setText(String.valueOf(gv.getUserEmail()));

        //navigationView.setNavigationItemSelectedListener(navViewOnItemSelected);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menuItemHome, R.id.menuItemPersonalPage, R.id.menuItemAbout)
                .setDrawerLayout(mDrawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
/*
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

*/
    }



    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_instruction, menu);

        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.side_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }else{
            switch (item.getItemId()) {
                case R.id.item_logo:

                    mDlog_case = new Dialog(this);
                    mDlog_case.setContentView(R.layout.page_instruction);
                    mDlog_case.setCancelable(true);
                    mDlog_case.show();


                    return true;
                case R.id.menuItemExit:
                    Intent intent = new Intent(Main.this, Class_Login.class);
                    startActivity(intent);
                    return true;
                default:
            }

        }
        return super.onOptionsItemSelected(item);
    }

/*
    private NavigationView.OnNavigationItemSelectedListener navViewOnItemSelected = new NavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
            switch (menuItem.getItemId()){
                case R.id.menuItemChangeInfo:
                    Toast.makeText(HomePage_SideDrawer.this, "個人資料", Toast.LENGTH_LONG).show();
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.menuItemServiceMng:
                    Toast.makeText(HomePage_SideDrawer.this, "服務管理", Toast.LENGTH_LONG).show();
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.menuItemAbout:
                    Toast.makeText(HomePage_SideDrawer.this, "關於", Toast.LENGTH_LONG).show();
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.menuItemExit:
                    Toast.makeText(HomePage_SideDrawer.this, "成功登出", Toast.LENGTH_LONG).show();
                    mDrawerLayout.closeDrawers();
                    break;
            }
            return false;
        }
    };
*/

}
