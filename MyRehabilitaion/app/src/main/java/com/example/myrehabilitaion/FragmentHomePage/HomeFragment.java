package com.example.myrehabilitaion.FragmentHomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myrehabilitaion.Main;
import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.StartPage;


public class HomeFragment extends Fragment {

    //ImageButton TargetBtn01;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton imageButton01, imageButton02, imageButton03, imageButton04, imageButton05, imageButton06;

        imageButton01 = root.findViewById(R.id.imageButton01);
        imageButton02 = root.findViewById(R.id.imageButton02);
        imageButton03 = root.findViewById(R.id.imageButton03);
        imageButton04 = root.findViewById(R.id.imageButton04);
        imageButton05 = root.findViewById(R.id.imageButton05);
        imageButton06 = root.findViewById(R.id.imageButton06);

        imageButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = null;
                switch (v.getId()){
                    case R.id.imageButton01:
                        intent = new Intent(HomeFragment.this, StartPage.class);
                        break;
                    case R.id.imageButton02;
                        intent = new Intent(HomeFragment.this, StartPage.class);
                        break;
                    case R.id.imageButton03:
                        intent = new Intent(HomeFragment.this, StartPage.class);
                        break;
                    case R.id.imageButton04:
                        intent = new Intent(HomeFragment.this, StartPage.class);
                        break;
                    case R.id.imageButton05:
                        intent = new Intent(HomeFragment.this, StartPage.class);
                        break;
                    case R.id.imageButton06:
                        intent = new Intent(HomeFragment.this, StartPage.class);
                        break;
                }
                startActivity(intent);

                 */
            }
        });


        return root;

    }
/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //建立點選事件
        TargetBtn01 =  getActivity().findViewById(R.id.imageButton);
        TargetBtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

*/
}
