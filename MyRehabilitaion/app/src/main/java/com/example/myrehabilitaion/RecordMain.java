package com.example.myrehabilitaion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RecordMain extends AppCompatActivity {

    private Frag_StartRecord mStartRecord;
    private Frag_Recording mRecording;
    private Frag_RecordFinished mRecordFinished;
    private BT_Test mBT_Test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_main);

        mStartRecord = new Frag_StartRecord();
//        mRecording = new Frag_Recorde();
        mBT_Test = new BT_Test();
        mRecordFinished = new Frag_RecordFinished();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_record, mStartRecord, "Start Record")
//                .add(R.id.fragment_record, mRecording, "Recording")
                .add(R.id.fragment_record, mBT_Test, "Recording")
                .add(R.id.fragment_record, mRecordFinished, "Record Finish")
                .hide(mBT_Test)
                .hide(mRecordFinished)
                .commit();
    }
    public void hideStartRecordFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_right)
                .hide(mStartRecord)
                .commit();
    }

    public void showRecordingFragment(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim)
                .show(mBT_Test)
                .commit();
    };
    public void hideRecordingFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_right)
                .hide(mBT_Test)
                .commit();
    };
    public void showRecordFinishedFragment(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim)
                .show(mRecordFinished)
                .commit();
    };

    public void hideRecordFinishedFragment(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_right)
                .hide(mRecordFinished)
                .commit();
    };
}
