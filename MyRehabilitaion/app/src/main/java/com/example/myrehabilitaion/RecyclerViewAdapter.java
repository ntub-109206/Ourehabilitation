package com.example.myrehabilitaion;


import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrehabilitaion.FragmentHomePage.ServiceMng;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    // 儲存要顯示的資料。
    private List<String> mListString;
    Dialog myDlog;

    public void addItem(String text) {
        // 為了示範效果，固定新增在位置3。若要新增在最前面就把3改成0
        mListString.add(mListString.size(),text);
        notifyItemInserted(mListString.size());
    }

    // 刪除項目
    public void removeItem(int position){
        mListString.remove(position);
        notifyItemRemoved(position);
    }

    // ViewHolder 是把項目中所有的 View 物件包起來。
    // 它在 onCreateViewHolder() 中使用。
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public ImageView mImgView;
        public TextView mTxt;
        public ImageButton mEditImgPen;
        public ViewHolder(View itemView) {
            super(itemView);
            mImgView = (ImageView) itemView.findViewById(R.id.imgView);
            mTxt = (TextView) itemView.findViewById(R.id.txt);
            mEditImgPen = itemView.findViewById(R.id.imgbtnpen);
            // 處理按下的事件。
            itemView.setOnClickListener(this);
            mEditImgPen.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    myDlog = new Dialog(v.getContext());
                    myDlog.setContentView(R.layout.my_dlg);
                    myDlog.setCancelable(true);
                    myDlog.show();

                }
            });
        }

        @Override
        public void onClick(View view) {
            // 按下後執行的程式碼。
            Toast.makeText(view.getContext(),
                    mListString.get(getAdapterPosition()), Toast.LENGTH_LONG)
                    .show();
        }
    }

    // 建構式，用來接收外部程式傳入的項目資料。
    public RecyclerViewAdapter(List<String> listString) {

        mListString = listString;
    }

    // RecyclerView會呼叫這個方法，我們必須建立好項目的ViewHolder物件，
    // 然後傳回給RecyclerView。
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 建立一個 view。
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recycler_view_item, viewGroup, false);

        // 建立這個 view 的 ViewHolder。
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    // RecyclerView會呼叫這個方法，我們必須把項目資料填入ViewHolder物件。
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        // 把資料設定給 ViewHolder。
        viewHolder.mImgView.setImageResource(R.drawable.target);
        viewHolder.mTxt.setText(mListString.get(i));
        viewHolder.mEditImgPen.setImageResource(R.drawable.write);
    }

    // RecyclerView會呼叫這個方法，我們要傳回總共有幾個項目。
    @Override
    public int getItemCount() {
        return mListString.size();
    }
}


