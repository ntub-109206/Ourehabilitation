package com.example.myrehabilitaion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myrehabilitaion.FragmentHomePage.HomeFragment;
import com.example.myrehabilitaion.FragmentHomePage.Statistics;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class Main extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    private AppBarConfiguration mAppBarConfiguration;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidedrawer_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);


        final FloatingActionButton fab01 = findViewById(R.id.fab01);
        final FloatingActionButton fab02 = findViewById(R.id.fab02);
        fab02.setVisibility(View.INVISIBLE);
        fab01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fab01.setVisibility(View.INVISIBLE);
                fab02.setVisibility(View.VISIBLE);

                fab02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                Snackbar.make(view, "開啟統計頁面", Snackbar.LENGTH_LONG).setAction("Action", null).show();
/*
                Intent intent = new Intent(Main.this,HomePage.class);
                startActivity(intent);
*/
                        //Main.this.showStatisticsFragment();

                        fab02.setVisibility(View.INVISIBLE);
                        fab01.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(navViewOnItemSelected);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menuItemHome, R.id.menuItemInfo, R.id.menuItemServiceMng, R.id.menuItemAbout, R.id.menuItemStatistics)
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
/*
        mStatistics = new Statistics();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, mStatistics, "Statistics Fragment")
                .hide(mStatistics)
                .commit();
 */
    }
/*
    public void showStatisticsFragment(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.scale_in_out,R.anim.fragment_fade_exit)
                .show(mStatistics)
                .commit();
    }
 */
/*
    public void hideStatisticsFragmne(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fragment_fade_exit, R.anim.scale_in_out)
                .hide(mStatistics)
                .commit();
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_drawer, menu);
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
