package com.example.myrehabilitaion.FragmentHomePage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myrehabilitaion.LoginPage;
import com.example.myrehabilitaion.Main;
import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.RecordMain;

public class StartRecord extends Fragment {
    Button mbtnconfirmstart, mbtncancelstart;

    Dialog mDlog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_startrecord, container, false);


        mbtnconfirmstart = root.findViewById(R.id.btn_confirmstart);
        mbtncancelstart = root.findViewById(R.id.btn_cancelstartpage);
        mbtnconfirmstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDlog = new Dialog(getContext());
                mDlog.setContentView(R.layout.dlg_startcheck);
                mDlog.setCancelable(true);
                mDlog.show();

                Button btnstarttargt = mDlog.findViewById(R.id.btn_checkconfirm);
                Button btncancel = mDlog.findViewById(R.id.btn_checkcancel);

                btnstarttargt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecordMain recordmain = (RecordMain) getActivity();
                        recordmain.showRecordigFragment();
                        mDlog.dismiss();
                    }
                });


                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        });

        mbtncancelstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main.class);
                startActivity(intent);
            }
        });
        return root;

    }


}
