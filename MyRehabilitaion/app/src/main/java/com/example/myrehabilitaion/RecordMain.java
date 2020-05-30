package com.example.myrehabilitaion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myrehabilitaion.FragmentHomePage.Recording;
import com.example.myrehabilitaion.FragmentHomePage.StartRecord;

public class RecordMain extends AppCompatActivity {

    private StartRecord mStartRecord;
    private Recording mRecording;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_main);

        mStartRecord = new StartRecord();
        mRecording = new Recording();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_record, mStartRecord, "Start Record")
                .add(R.id.fragment_record, mRecording, "Recording")
                .hide(mRecording)
                .commit();
    }

    public void showRecordigFragment(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim)
                .show(mRecording)
                .commit();
    }

    public void hideRecordigFragment(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_right)
                .hide(mRecording)
                .commit();
    }
}
