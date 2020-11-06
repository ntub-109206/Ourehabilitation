package com.example.myrehabilitaion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myrehabilitaion.ui.Record.RecordFragment_Main;
import com.example.myrehabilitaion.ui.Stastics.Frag_LineChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Frag_NewHome extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstancestate) {
        View root = inflater.inflate(R.layout.fragment_newhome, container, false);
//        GlobalVariable gv = (GlobalVariable) getActivity().getApplicationContext();
//        gv.setContext_NewFrag(getTargetFragment());
//----------------------------------------ButtomNavigationView_第三版本-----------------------------------------------
//        Frag_NewHome.InnerPagerStateAdapter pagerAdapter = new Frag_NewHome.InnerPagerStateAdapter(getActivity().getSupportFragmentManager());
//
//        ViewPager viewPager = root.findViewById(R.id.viewPager);
//
//        viewPager.setAdapter(pagerAdapter);
//
//        TabLayout tableLayout = root.findViewById(R.id.tabLayout);
//        tableLayout.setupWithViewPager(viewPager);
//
//        int[] tabIcons = {R.drawable.ic_show_chart_black_24dp, R.drawable.ic_baseline_play_circle_filled_24, R.drawable.ic_person_pin_black_24dp};
//
//
//        tableLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tableLayout.getTabAt(1).setIcon(tabIcons[1]);
//        tableLayout.getTabAt(2).setIcon(tabIcons[2]);
//----------------------------------------ButtomNavigationView_第三版本-----------------------------------------------

//----------------------------------------ButtomNavigationView_第二版本-----------------------------------------------

        BottomNavigationView bottomNav = root.findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getChildFragmentManager().beginTransaction().replace(R.id.nav_home_container,new Frag_LineChart()).commit();

//----------------------------------------ButtomNavigationView_第二版本-----------------------------------------------
        return root;
    }
//----------------------------------------ButtomNavigationView_第二版本-----------------------------------------------

        private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;


            switch(item.getItemId()){
                case R.id.navigation_record_main:
                    selectedFragment = new RecordFragment_Main();
                    break;
                case R.id.navigation_instant_record:
                    selectedFragment = new BT_Test();

                    break;
                case R.id.navigation_stastics:
                    selectedFragment = new Frag_LineChart();
                    break;
            }

            getChildFragmentManager().beginTransaction().replace(R.id.nav_home_container,selectedFragment).commit();

            return true;
        }

    };

//----------------------------------------ButtomNavigationView_第二版本-----------------------------------------------
//----------------------------------------ButtomNavigationView_第三版本-----------------------------------------------
//    class InnerPagerStateAdapter extends FragmentStatePagerAdapter {
//        public InnerPagerStateAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//
//        @Override
//        public CharSequence getPageTitle(int postion) {
//            switch (postion) {
//                case 0:
//                    return "數據統計";
//                case 1:
//                    return "目標管理";
//                case 2:
//                    return "個人資料";
//                default:
//                    return null;
//            }
//        }
//
//
//        @Override
//        public Fragment getItem(int position) {
//
//            Fragment fragment = null;
//
//            switch (position) {
//                case 0:
//                    fragment = new Frag_LineChart();
//
//                    break;
//                case 1:
//                    fragment = new RecordFragment_Main();
//
//                    break;
//                case 2:
//                    fragment = new PersonalInfoFragment();
//            }
//
//            return fragment;
//
//        }
//
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//
//            FragmentManager manager = ((Fragment) object).getFragmentManager();
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.remove((Fragment) object);
//            trans.commit();
//        }
//    }
//----------------------------------------ButtomNavigationView_第三版本-----------------------------------------------


}


