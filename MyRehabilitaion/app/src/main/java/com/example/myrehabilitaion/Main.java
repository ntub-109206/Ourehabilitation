package com.example.myrehabilitaion;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class Main extends AppCompatActivity {
//-----------------------------------BT宣告---------------------------------------------------
    String mReadBuffer = null;
    protected Button mDiscoverBtn, mListPairedDevicesBtn;
    protected ListView mDevicesListView;
    protected ArrayAdapter<String> mBTArrayAdapter;
    protected BluetoothAdapter mBTAdapter;
    protected BluetoothSocket mBTSocket = null;
    protected Set<BluetoothDevice> mPairedDevices;

    protected Handler mHandler01;

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3;
    private final static int SYNC_COUNT = 4; // used in bluetooth handler to identify message status

    String bt_status;

    Toolbar toolbar;
    Dialog mDlog_case;

//-----------------------------------BT宣告---------------------------------------------------

    DrawerLayout mDrawerLayout;
    private AppBarConfiguration mAppBarConfiguration;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidedrawer_home);

        mBTArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio

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
                case R.id.item_instruct:

//                    mDlog_case = new Dialog(this);
//                    mDlog_case.setContentView(R.layout.page_instruction);
//                    mDlog_case.setCancelable(true);
//                    mDlog_case.show();
//
//                    mListPairedDevicesBtn = (Button) mDlog_case.findViewById(R.id.PairedBtn);
//                    mListPairedDevicesBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            listPairedDevices(v);
//                        }
//                    });
//                    mDevicesListView = (ListView)mDlog_case.findViewById(R.id.devicesListView);
//                    mDevicesListView.setAdapter(mBTArrayAdapter); // assign model to view
//                    mDevicesListView.setOnItemClickListener(mDeviceClickListener);

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

    private void listPairedDevices(View view){
        mPairedDevices = mBTAdapter.getBondedDevices();
        if(mBTAdapter.isEnabled()) {
            // put it's one to the adapter
            for (BluetoothDevice device : mPairedDevices)
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());

        }
        else
            Toast.makeText(getApplicationContext(), "Bluetooth not on",
                    Toast.LENGTH_SHORT).show();
    }


    /* Call this from the main activity to shutdown the connection */


    private AdapterView.OnItemClickListener mDeviceClickListener =
            new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> av, final View v, int arg2, long arg3) {
                    if(!mBTAdapter.isEnabled()) {
                        Toast.makeText(v.getContext(), "Bluetooth not on",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    bt_status =  "Connecting...";
                    toolbar.setSubtitle(bt_status);

                    // Get the device MAC address, which is the last 17 chars in the View
                    String info = ((TextView) v).getText().toString();

                    final String address = info.substring(info.length() - 17);
                    GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
                    globalVariable.setDeviceAddress(address);

                    final String name = info.substring(0,info.length() - 17);
                    globalVariable.setDeviceName(name);

                    bt_status = "[ Connect to : " + name + "]";
                    toolbar.setSubtitle(bt_status);

                    Toast.makeText(getApplicationContext(), "藍芽已連接，請重新啟動紀錄頁面", Toast.LENGTH_SHORT).show();

                    mDlog_case.dismiss();
                }
            };



}
