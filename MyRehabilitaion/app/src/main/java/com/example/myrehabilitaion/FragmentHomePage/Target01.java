package com.example.myrehabilitaion.FragmentHomePage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.myrehabilitaion.R;


public class Target01 extends Fragment {
    private ImageButton TargetBtn01;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_target01, container, false);
        TargetBtn01 = root.findViewById(R.id.imageButton);
        return root;
    }

}

