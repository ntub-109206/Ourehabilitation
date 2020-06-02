package com.example.myrehabilitaion.FragmentHomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myrehabilitaion.CardInformation;
import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.RecordMain;

public class PsInfoFragment extends Fragment {
    private ImageButton imgbtncard;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_psinformationpage, container, false);

        imgbtncard = root.findViewById(R.id.imgbtn_card);
        imgbtncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), CardInformation.class);
                getContext().startActivity(intent);

            }
        });



        return root;
    }
}
