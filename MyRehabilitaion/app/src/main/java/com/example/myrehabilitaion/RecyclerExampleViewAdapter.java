package com.example.myrehabilitaion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrehabilitaion.FragmentHomePage.HomeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecyclerExampleViewAdapter extends RecyclerView.Adapter<RecyclerExampleViewAdapter.ViewHolder>{


    private List<String> mListString = new ArrayList<String>();

    public void addItem(String text) {
        // 為了示範效果，固定新增在位置3。若要新增在最前面就把3改成0
        mListString.add( text);
        notifyItemInserted(mListString.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImgView;
        public TextView mTxt;
        public ImageButton mEditImgPen;
        public ViewHolder(View itemView) {
            super(itemView);
            mImgView = (ImageView) itemView.findViewById(R.id.img_target);
            mTxt = (TextView) itemView.findViewById(R.id.txt_target);
            // 處理按下的事件。
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // 按下後執行的程式碼。
            Intent intent = new Intent(view.getContext(),RecordMain.class);
            view.getContext().startActivity(intent);
        }
    }

    // 建構式，用來接收外部程式傳入的項目資料。
    public RecyclerExampleViewAdapter(List<String> listString) {

        mListString = listString;
    }

    // RecyclerView會呼叫這個方法，我們必須建立好項目的ViewHolder物件，
    // 然後傳回給RecyclerView。
    @NonNull
    @Override
    public RecyclerExampleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 建立一個 view。
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerexampleview, viewGroup, false);

        // 建立這個 view 的 ViewHolder。
        RecyclerExampleViewAdapter.ViewHolder viewHolder = new RecyclerExampleViewAdapter.ViewHolder(v);
        return viewHolder;
    }

    // RecyclerView會呼叫這個方法，我們必須把項目資料填入ViewHolder物件。


    @Override
    public void onBindViewHolder(@NonNull RecyclerExampleViewAdapter.ViewHolder viewHolder, int i) {
        // 把資料設定給 ViewHolder。
        viewHolder.mImgView.setImageResource(R.drawable.target);
        viewHolder.mTxt.setText(mListString.get(i).toString().trim());
    }

    // RecyclerView會呼叫這個方法，我們要傳回總共有幾個項目。
    @Override
    public int getItemCount() {
        return mListString.size();
    }


}
