package com.example.myrehabilitaion;

import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.myrehabilitaion.R;

import java.util.ArrayList;

public class GuidePageChangeListener implements ViewPager.OnPageChangeListener{
    private  ImageView[] vlist;
    private ArrayList<View> pview;
//新增建構子讓GuidePageChangeListener.java可以取得HomeFragment.java的變數值
    public GuidePageChangeListener( ImageView[] tips,ArrayList<View> pageview) {

        this.vlist = tips;
        this.pview = pageview;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    //切換view時，下方圓點的變化。
    public void onPageSelected(int position) {
        vlist[position].setBackgroundResource(R.drawable.shape_point_selected);
        //這個圖片就是選中的view的圓點
        for(int i=0;i<pview.size();i++){
            if (position != i) {
                vlist[i].setBackgroundResource(R.drawable.shape_point_normal);
                //這個圖片是未選中view的圓點
            }
        }
    }
}