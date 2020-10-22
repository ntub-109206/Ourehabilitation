package com.example.myrehabilitaion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myrehabilitaion.BT_Test;
import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.RecordMain;
import com.example.myrehabilitaion.Variables;


public class Frag_RecordFinished extends Fragment {
    BT_Test bt_test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_frag__record_finished, container, false);

//       Bundle bundle = getArguments();
//       Integer test = bundle.getInt("scores");
//
//        TextView mtxt = root.findViewById(R.id.txt);
//        mtxt.setText(test.toString().trim());

        return root;
    }
}