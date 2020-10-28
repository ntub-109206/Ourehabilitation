package com.example.myrehabilitaion.ui.Record;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.myrehabilitaion.R;
import com.google.android.material.tabs.TabLayout;


public class RecordFragment_Main extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_record__main, container, false);

        InnerPagerAdapter pagerAdapter = new InnerPagerAdapter(getActivity().getSupportFragmentManager());

        ViewPager viewPager = root.findViewById(R.id.viewPager);

        viewPager.setAdapter(pagerAdapter);

        TabLayout tableLayout = root.findViewById(R.id.tabLayout);
        tableLayout.setupWithViewPager(viewPager);

        return root;

    }

    class InnerPagerAdapter extends FragmentPagerAdapter{
        public InnerPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position){

            Fragment fragment = null;

            switch (position){
                case 0:
                    fragment = new RecordFragment_Finished();

                    break;
                case 1:
                    fragment = new RecordFragment();
                    break;
            }

            return fragment;

        }

        @Override
        public int getCount(){
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int postion){
            switch (postion){
                case 0:
                    return "已完成的復健";
                case 1:
                    return "正在進行的復健";
                default:
                    return null;
            }
        }
    }
}