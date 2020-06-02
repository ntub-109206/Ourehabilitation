package com.example.myrehabilitaion.FragmentHomePage;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myrehabilitaion.RecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrehabilitaion.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceMng extends Fragment {
    private RecyclerViewAdapter adapter_mng;
//    FloatingActionButton floatingbtn ;
    RecyclerView recyclerView;
    Button btnaddtarget ;
    Dialog mDlog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_servicemng, container, false);

        // 把項目清單準備好，放在一個List物件裏頭
        List<String> listStr = new ArrayList<String>();
        for (int i = 0; i < 3 ; i++) {
            listStr.add( "目標"+ String.valueOf(i+1));
        }

        // 取得介面佈局檔中的RecyclerView元件
        recyclerView = root.findViewById(R.id.recyclerview);

        // 設定RecyclerView使用的LayoutManager，
        // LayoutManager決定項目的排列方式。
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//      recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        // 建立RecyclerView的Adapter物件，傳入包含項目清單的List物件

        adapter_mng = new RecyclerViewAdapter(listStr);

        // 把Adapter物件傳給RecyclerView
        recyclerView.setAdapter(adapter_mng);

/*
        btnaddtarget = root.findViewById(R.id.btn_addcase);
        btnaddtarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDlog = new Dialog(v.getContext());
                mDlog.setContentView(R.layout.dlg_addtarget);
                mDlog.setCancelable(true);
                mDlog.show();

                Button btnaddtargt = mDlog.findViewById(R.id.btn_addtargt);
                Button btncancelbox = mDlog.findViewById(R.id.btn_cancelbox);
                final EditText edttargetname = mDlog.findViewById(R.id.edt_targetname);
                Button check;

                btnaddtargt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter_mng.addItem(edttargetname.getText().toString().trim());
                        //mSyncTarget = edttargetname.getText().toString().trim();

                        Toast.makeText(v.getContext(),
                                "您新增了新的目標", Toast.LENGTH_SHORT).show();

                        mDlog.dismiss();
                    }
                });
            }
        });

 */
/*
        floatingbtn = root.findViewById(R.id.floatingActionButton);
        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem("New Item");

                Toast.makeText(v.getContext(),
                        "您新增了新的目標", Toast.LENGTH_SHORT).show();

            }
        });
 */

                return root;
    };

}
