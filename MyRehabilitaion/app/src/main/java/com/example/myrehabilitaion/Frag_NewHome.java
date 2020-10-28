package com.example.myrehabilitaion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myrehabilitaion.ui.PersonalInfo.PersonalInfoFragment;
import com.example.myrehabilitaion.ui.Record.RecordFragment;
import com.example.myrehabilitaion.ui.Record.RecordFragment_Main;
import com.example.myrehabilitaion.ui.Stastics.Frag_LineChart;
import com.example.myrehabilitaion.ui.Stastics.Frag_Statistics;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Frag_NewHome extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstancestate) {
        View root = inflater.inflate(R.layout.fragment_newhome, container, false);

//        BottomNavigationView navView = root.findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_record, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_home_fragment);
//        NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(), navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

        BottomNavigationView bottomNav = root.findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getChildFragmentManager().beginTransaction().replace(R.id.nav_home_container,new Frag_LineChart()).commit();

        return root;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;


            switch(item.getItemId()){
                case R.id.navigation_record_main:
                    selectedFragment = new RecordFragment_Main();
                    break;
                case R.id.navigation_stastics:
                    selectedFragment = new Frag_LineChart();
                    break;
                case R.id.navigation_psinfo:
                    selectedFragment = new PersonalInfoFragment();

                    break;
            }

            getChildFragmentManager().beginTransaction().replace(R.id.nav_home_container,selectedFragment).commit();

            return true;
        }
    };

}