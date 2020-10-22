package com.example.myrehabilitaion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myrehabilitaion.Main;
import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.RecordMain;
import com.example.myrehabilitaion.Variables;

import org.w3c.dom.Text;

public class Frag_StartRecord extends Fragment {
    Button mbtnconfirmstart, mbtncancelstart;
    private static Variables Variables = new Variables();
    TextView body_startrecord;
    Dialog mDlog;

    GlobalVariable gv;

    private NumberPicker mNumPickerAge;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_startrecord, container, false);
        Spinner spnUnit = root.findViewById(R.id.spin_unit);

        mNumPickerAge = root.findViewById(R.id.numPickerAge);
        mNumPickerAge.setMaxValue(200);
        mNumPickerAge.setMinValue(0);
        mNumPickerAge.setValue(18);

        body_startrecord = root.findViewById(R.id.body_startrecord);
        body_startrecord.setText(gv.getServiceName().toString().trim());

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
                        recordmain.showRecordingFragment();

                        //int count = Integer.parseInt(edtargetinput.getText().toString());
                        //Variables.timsSetter(count);

//                        Intent itt = new Intent();
//                        itt.putExtra("key1",Integer.parseInt(edtargetinput.getText().toString().trim()));

                        mDlog.dismiss();
                    }
                });


                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDlog.dismiss();
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
