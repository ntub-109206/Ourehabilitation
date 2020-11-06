package com.example.myrehabilitaion;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerInfoAdapter extends RecyclerView.Adapter<RecyclerInfoAdapter.ViewHolder> {


    private List<String> mListString01;
    private List<String> mListString02;
    private List<String> mListString03 ;
    private List<String> mListString04 ;
    private List<String> mListString05 ;
    private List<Integer> mListImage;

    Context context;
    Activity activity;

    GlobalVariable gv;



    // ViewHolder 是把項目中所有的 View 物件包起來。
    // 它在 onCreateViewHolder() 中使用。
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageView mImgView;
        public TextView mTxt;
        public TextView mtxtonbar;
        public ProgressBar mrecord_progressbar;



        public ViewHolder(View itemView) {
            super(itemView);
            gv = (GlobalVariable) activity.getApplicationContext();

            mImgView = (ImageView) itemView.findViewById(R.id.img_target);
            mTxt = (TextView) itemView.findViewById(R.id.txt_target);
            mtxtonbar = itemView.findViewById(R.id.txt_onbar);
            mrecord_progressbar = itemView.findViewById(R.id.reacord_progressBar);
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
    public RecyclerInfoAdapter(Activity activity, Context context, List<String> listString01, List<String> listString02, List<String> ListString03 , List<String> ListString04 , List<String> ListString05 , List<Integer> listImg) {


        this.mListString01 =  listString01;
        this.mListString02 =  listString02;
        this.mListString03 = ListString03;
        this.mListString04 = ListString04;
        this.mListString05 = ListString05;

        this.mListImage =  listImg;
        this.context=context;
        this.activity = activity;

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
        viewHolder.mImgView.setImageResource(mListImage.get(i));
        viewHolder.mTxt.setText(mListString01.get(i));
}

    // RecyclerView會呼叫這個方法，我們要傳回總共有幾個項目。
    @Override
    public int getItemCount() {
        return mListImage.size();
    }

}
