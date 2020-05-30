package com.example.myrehabilitaion;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerInfoAdapter extends RecyclerView.Adapter<RecyclerInfoAdapter.ViewHolder> {
    private List<String> mListImage;


    // ViewHolder 是把項目中所有的 View 物件包起來。
    // 它在 onCreateViewHolder() 中使用。
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageButton mImagInfo;
        public ViewHolder(View itemView) {
            super(itemView);
            mImagInfo = itemView.findViewById(R.id.img_rehbinfo);
            // 處理按下的事件。
            itemView.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public void onClick(View view) {
            // 按下後執行的程式碼。

        }
    }

    // 建構式，用來接收外部程式傳入的項目資料。
    public RecyclerInfoAdapter(List<String> listImage) {

        mListImage = listImage;
    }

    // RecyclerView會呼叫這個方法，我們必須建立好項目的ViewHolder物件，
    // 然後傳回給RecyclerView。
    @NonNull
    @Override
    public RecyclerInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 建立一個 view。
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_rehbinfoitem, viewGroup, false);

        // 建立這個 view 的 ViewHolder。
        RecyclerInfoAdapter.ViewHolder viewHolder = new RecyclerInfoAdapter.ViewHolder(v);
        return viewHolder;
    }

    // RecyclerView會呼叫這個方法，我們必須把項目資料填入ViewHolder物件。
    @Override
    public void onBindViewHolder(@NonNull RecyclerInfoAdapter.ViewHolder viewHolder, int i) {
        // 把資料設定給 ViewHolder。
        viewHolder.mImagInfo.setImageResource(R.drawable.example);
}

    // RecyclerView會呼叫這個方法，我們要傳回總共有幾個項目。
    @Override
    public int getItemCount() {
        return mListImage.size();
    }

}
