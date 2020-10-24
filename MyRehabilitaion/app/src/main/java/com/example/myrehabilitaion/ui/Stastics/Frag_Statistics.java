package com.example.myrehabilitaion.ui.Stastics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myrehabilitaion.Frag_PieChart;
import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.Frag_LineChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class Frag_Statistics extends Fragment {


    public Frag_Statistics() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_statistics, container, false);

//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("數據統計");

        BottomNavigationView bottomNav = root.findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,new Frag_PieChart()).commit();


        return root;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId()){
                case R.id.nav_pie:
                    selectedFragment = new Frag_PieChart();
                    break;
//                case R.id.nav_bar:
//                    selectedFragment = new Frag_BarChart();
//                    break;
                case R.id.nav_line:
                    selectedFragment = new Frag_LineChart();
                    break;
            }
            getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return true;
        }
    };

}
