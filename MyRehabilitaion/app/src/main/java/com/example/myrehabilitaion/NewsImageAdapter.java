package com.example.myrehabilitaion;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class NewsImageAdapter extends PagerAdapter {
    private ArrayList<View> viewlist;

    public NewsImageAdapter(ArrayList<View> viewlist) {
        this.viewlist = viewlist;
    }

    @Override
    public int getCount() {
        return viewlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        Log.d("MainActivityDestroy",position+"");
        if (viewlist.get(position)!=null) {
            container.removeView(viewlist.get(position));
        }

    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewlist.get(position));
        Log.d("MainActivityInstanti",position+"");
        return viewlist.get(position);
    }
}

