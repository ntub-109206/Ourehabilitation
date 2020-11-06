package com.example.myrehabilitaion.ui.Record;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myrehabilitaion.R;
import com.google.android.material.tabs.TabLayout;


public class RecordFragment_Main extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_record__main, container, false);

        InnerPagerStateAdapter pagerAdapter = new InnerPagerStateAdapter(RecordFragment_Main.super.getActivity().getSupportFragmentManager());

        ViewPager viewPager = root.findViewById(R.id.viewPager);

        viewPager.setAdapter(pagerAdapter);

        TabLayout tableLayout = root.findViewById(R.id.tabLayout);
        tableLayout.setupWithViewPager(viewPager);

        return root;

    }

    public class InnerPagerStateAdapter extends FragmentStatePagerAdapter{
        public InnerPagerStateAdapter(FragmentManager fm){
            super(fm);
        }


        @Override
        public CharSequence getPageTitle(int postion){
            switch (postion){
                case 0:
                    return "正在進行的復健";
                case 1:
                    return "完成的復健";
                default:
                    return null;
            }
        }


        @Override
        public Fragment getItem(int position){

            Fragment fragment = null;

            switch (position){
                case 0:
                    fragment = new RecordFragment();

                    break;
                case 1:
                    fragment = new RecordFragment_Finished();


                    break;
            }

            return fragment;

        }


        @Override
        public int getCount(){
            return 2;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            FragmentManager manager = ((Fragment) object).getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();
        }
    }
}