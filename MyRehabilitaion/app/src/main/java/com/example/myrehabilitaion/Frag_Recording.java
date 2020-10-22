package com.example.myrehabilitaion;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.Variables;


public class Frag_Recording extends Fragment {
    private static Variables Variables = new Variables();
    TextView txt_timesget;

    public int count;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //count = Variables.timesGetter();
        //txt_timesget.setText(count + "次數");
        Intent itt = new Intent();
        //int count = itt.getIntExtra("key1");
        //txt_timesget.

        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_recording, container, false);

        return root;
    }

}
