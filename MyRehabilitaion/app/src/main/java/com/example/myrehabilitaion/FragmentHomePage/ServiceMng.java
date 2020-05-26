package com.example.myrehabilitaion.FragmentHomePage;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myrehabilitaion.RecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ServiceMng extends Fragment {
    private RecyclerViewAdapter adapter;
    FloatingActionButton floatingbtn ;
    RecyclerView recyclerView;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_servicemng, container, false);

        // 把項目清單準備好，放在一個List物件裏頭
        List<String> listStr = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            listStr.add(new String("第" + String.valueOf(i+1) + "項"));

        // 取得介面佈局檔中的RecyclerView元件
        recyclerView = root.findViewById(R.id.recyclerview);

        // 設定RecyclerView使用的LayoutManager，
        // LayoutManager決定項目的排列方式。
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//      recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        // 建立RecyclerView的Adapter物件，傳入包含項目清單的List物件

        adapter = new RecyclerViewAdapter(listStr);

        // 把Adapter物件傳給RecyclerView
        recyclerView.setAdapter(adapter);

        floatingbtn = root.findViewById(R.id.floatingActionButton);
        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem("New Item");

                Toast.makeText(v.getContext(),
                        "您新增了新的目標", Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }
}
