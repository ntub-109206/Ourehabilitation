package com.example.myrehabilitaion;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Frag_InstantRecord extends Fragment {

    private Frag_ChooseService mChooseService;
    private BT_Test bt_test;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstancestate) {
        View root = inflater.inflate(R.layout.activity_record_main, container, false);


//        mRecording = new Frag_Recorde();
        bt_test = new BT_Test();
        mChooseService = new Frag_ChooseService();

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_record01, mChooseService, "Choose Service")
//                .add(R.id.fragment_record, mRecording, "Recording")
                .add(R.id.fragment_record02, bt_test, "Recording")
                .hide(bt_test)
                .commit();

        return root;
    }
    public void hideStartRecordFragment() {
       getActivity(). getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_right)

                .commit();
    }

    public void showRecordingFragment(){
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim)
                .show(bt_test)
                .commit();
    };
    public void hideRecordingFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_right)
                .hide(bt_test)
                .commit();
    };
//    public void showRecordFinishedFragment(){
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim)
//                .show(mRecordFinished)
//                .commit();
//    };

//    public void hideRecordFinishedFragment(){
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_right)
//                .hide(mRecordFinished)
//                .commit();
//    };
}
