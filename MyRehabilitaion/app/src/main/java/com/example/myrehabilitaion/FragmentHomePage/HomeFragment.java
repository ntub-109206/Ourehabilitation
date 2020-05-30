package com.example.myrehabilitaion.FragmentHomePage;

import android.app.Dialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myrehabilitaion.FragmentHomePage.ServiceMng;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.RecyclerExampleViewAdapter;
import com.example.myrehabilitaion.RecyclerInfoAdapter;
import com.example.myrehabilitaion.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {
    Dialog mDlog;

    //ImageButton TargetBtn01;
    private RecyclerViewAdapter adapter_home;
    RecyclerView recyclerView_home;
    ServiceMng sercmng;

    private RecyclerInfoAdapter infoadapter;
    RecyclerView recyclerInfo;

    private RecyclerExampleViewAdapter exampleadapter;
    RecyclerView recyclerexample;
    ImageButton imgbtnaddcase;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        List<String> listStr = new ArrayList<String>();

        for( int i=0 ; i <3 ; i++){
            listStr.add(new String("目標" + String.valueOf(i+1)));
        }

        recyclerexample = root.findViewById(R.id.recyclerview_home);
        recyclerexample.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        exampleadapter = new RecyclerExampleViewAdapter(listStr);
        //adapter_home.addItem(sercmng.Syc());


        recyclerexample.setAdapter(exampleadapter);
/*
        imgbtnaddcase = root.findViewById(R.id.btn_addcase);
        imgbtnaddcase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDlog = new Dialog(v.getContext());
                mDlog.setContentView(R.layout.dlg_addtarget);
                mDlog.setCancelable(true);
                mDlog.show();

                Button btnaddtargt = mDlog.findViewById(R.id.btn_addtargt);
                Button btncancelbox = mDlog.findViewById(R.id.btn_cancelbox);
                final EditText edttargetname = mDlog.findViewById(R.id.edt_targetname);

                btnaddtargt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exampleadapter.addItem(edttargetname.getText().toString().trim());
                        //mSyncTarget = edttargetname.getText().toString().trim();

                        Toast.makeText(v.getContext(),
                                "您新增了新的目標", Toast.LENGTH_SHORT).show();

                        mDlog.dismiss();
                    }
                });

            }
        });


 */



        //TypedArray infoImageList =
               // getResources().obtainTypedArray(R.array.info_icon_list);

/*
        //主頁資訊欄
        List<Map<String,Object>> listImg_info = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 5 ; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(String.valueOf(i), infoImageList.getResourceId(i, 0));
            listImg_info.add(map);
        }

        recyclerInfo = root.findViewById(R.id.recyc_rehbinfo);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerInfo.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        infoadapter = new RecyclerInfoAdapter(listImg_info);

        recyclerInfo.setAdapter(infoadapter);
 */
        return root;
    }

}
