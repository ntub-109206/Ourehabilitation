
package com.example.myrehabilitaion;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.myrehabilitaion.ui.Stastics.Frag_LineChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.xml.transform.Result;


public class BT_Test extends Fragment {

    protected static String ip = "140.131.114.241";
    protected static String port = "1433";
    protected static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    protected static String database = "109-rehabilitation";
    protected static String username = "case210906";
    protected static String password = "1@case206";
    protected static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;



    protected Connection connection = null;

    Statement statement01 = null;
    Statement statement02 = null;
    Statement statement03 = null;

    private int gt_targetimes;


    String mReadBuffer = null;
    protected Button mListPairedDevicesBtn, mStartBtn;
    protected TextView mBluetoothStatus01,mBluetoothStatus02,BluetoothCount, time;
    protected ListView mDevicesListView;
    protected ArrayAdapter<String> mBTArrayAdapter;
    protected BluetoothAdapter mBTAdapter;
    protected BluetoothSocket mBTSocket = null;
    protected Set<BluetoothDevice> mPairedDevices;
    //protected final String TAG = MainActivity.class.getSimpleName();
    protected Handler mHandler;
    protected ConnectedThread mConnectedThread;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    // #defines for identifying shared types between calling functions
    private final int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private final int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status

    private Long startTime;
    private Handler handler = new Handler();
    protected Button mStopBtn;
    protected TextView TimerTV;
    spinnerdata_sync_fromdb spinnerdataSyncFromdb;

    Dialog mDlog01;


    private int Count = 0;

    GlobalVariable gv ;
    String userid;
    Spinner spnbody;

    String servcie_id;
    String choose_service;

    Dialog mDlog;

    protected List<String> listStr04 = new ArrayList<String>();
    protected List<String> listStr05 = new ArrayList<String>();

    Integer choose_service_count = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bt_test, container, false);

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
//            Toast toast = Toast.makeText(getContext(),"Success", Toast.LENGTH_SHORT);
//            toast.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//            Toast toast = Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT);
//            toast.show();
        } catch (SQLException e) {
            e.printStackTrace();
//            Toast toast = Toast.makeText(getContext(),"FAILURE", Toast.LENGTH_SHORT);
//            toast.show();

        }


        TimerTV = (TextView) root.findViewById(R.id.timer);
        mStopBtn = (Button) root.findViewById(R.id.StopBn);
        time = (TextView) root.findViewById(R.id.timer);
        BluetoothCount = (TextView)root.findViewById(R.id.textCount);
        mBluetoothStatus01 = (TextView)root.findViewById(R.id.bluetoothStatus);
        mStartBtn = (Button)root.findViewById(R.id.StartBn);
//        mListPairedDevicesBtn = (Button)root.findViewById(R.id.PairedBtn);
        mBTArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio

        final FloatingActionButton fab01 = root.findViewById(R.id.floatingActionButton_open);
//        final FloatingActionButton fab02 = root.findViewById(R.id.floatingActionButton_close);
//        fab02.setVisibility(View.INVISIBLE);
        fab01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                fab01.setVisibility(View.INVISIBLE);
//                fab02.setVisibility(View.VISIBLE);

//                fab02.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        mBTArrayAdapter.clear();
//
//                        fab02.setVisibility(View.INVISIBLE);
//                        fab01.setVisibility(View.VISIBLE);
//                    }
//                });


                mDlog01 = new Dialog(v.getContext());
                mDlog01.setContentView(R.layout.dlg_devicelist);
                mDlog01.setCancelable(true);
                mDlog01.show();

                mDevicesListView = (ListView)mDlog01.findViewById(R.id.devicesListView);
                mBluetoothStatus02 = (TextView)mDlog01.findViewById(R.id.bluetoothStatus);

                listPairedDevices(v);

                mDevicesListView.setAdapter(mBTArrayAdapter); // assign model to view
                mDevicesListView.setOnItemClickListener(mDeviceClickListener);

                mHandler = new Handler(){
                    public void handleMessage(android.os.Message msg){

                        if(msg.what == MESSAGE_READ){
                            String readMessage = null;
                            try {
                                readMessage = new String((byte[]) msg.obj, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            mReadBuffer = readMessage;
                            mConnectedThread.Counter();

                        }
                        if(msg.what == CONNECTING_STATUS){
                            if(msg.arg1 == 1){
                                mBluetoothStatus01.setText("已連線至: " + (String)(msg.obj));
                                mBluetoothStatus02.setText("已連線至: " + (String)(msg.obj));

                            }
                            else{
                                mBluetoothStatus01.setText("連線失敗");
                                mBluetoothStatus02.setText("連線失敗");
                            }

                        }
                    }
                };
            }
        });


        findViews();
        gv = (GlobalVariable)getActivity().getApplicationContext();

        spinnerdataSyncFromdb = new spinnerdata_sync_fromdb();
        spinnerdataSyncFromdb.execute();
        spnbody = root.findViewById(R.id.spinner);


        try {
            Thread.sleep(100);
            System.out.print("record執行緒睡眠0.1秒！\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final List<String> bodypart_list=new ArrayList<String>();
        for(int i=0; i<listStr05.size(); i++){
            bodypart_list.add(listStr05.get(i));
        }

//        sync_serviceid = listStr04.get(0);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, bodypart_list);
        spnbody.setAdapter(adapter);
        spnbody.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "您選擇了:" + bodypart_list.get(position), Toast.LENGTH_SHORT).show();
                servcie_id = listStr04.get(position);
                choose_service =  bodypart_list.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button mbtn_scores = root.findViewById(R.id.btn_recordfinished);
        mbtn_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Frag_RecordFinished frag_recordFinished = new Frag_RecordFinished();
//
//                Bundle bundle = new Bundle();
//                Integer counts = Integer.valueOf((String) BluetoothCount.getText());
//                bundle.putInt("scores", counts);
//                frag_recordFinished.setArguments(bundle);

                mDlog = new Dialog(getContext());
                mDlog.setContentView(R.layout.dlg_finishcheck);
                mDlog.setCancelable(true);
                mDlog.show();

                TextView txtview_bodypart= mDlog.findViewById(R.id.txt_bodypart);
//                txtview_bodypart.setText(gv.getServiceName().toString());
                Button btnfinishcheckt = mDlog.findViewById(R.id.btn_checkconfirm);
                Button btncancel = mDlog.findViewById(R.id.btn_checkcancel);

                btnfinishcheckt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        case_todb case_todb = new case_todb();
                        case_todb.execute();

                        case_todb_update_service_progress case_todb_update_service_progress = new case_todb_update_service_progress();
                        case_todb_update_service_progress.execute();

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


        return root;
    }



    private void listPairedDevices(View view){
        mPairedDevices = mBTAdapter.getBondedDevices();
        mBTArrayAdapter.clear();
        if(mBTAdapter.isEnabled()) {
            // put it's one to the adapter
            for (BluetoothDevice device : mPairedDevices)
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());

            Toast.makeText(getContext().getApplicationContext(), "Show Paired Devices",
                    Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getContext().getApplicationContext(), "Bluetooth not on",
                    Toast.LENGTH_SHORT).show();
    }


    protected void findViews() {

        //*******************************更改****************************************
        if (mBTArrayAdapter == null) {
            // Device does not support Bluetooth
            mBluetoothStatus01.setText("Status: Bluetooth not found");
            mBluetoothStatus02.setText("Status: Bluetooth not found");
            Toast.makeText(getContext(),"找不到藍芽設備!",
                    Toast.LENGTH_SHORT).show();
        }
        else {

            mStartBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(mConnectedThread != null) {//First check to make sure thread created
                        Count = 0;
                        BluetoothCount.setText(String.valueOf(Count));
                        TimerTV.setText("0:0");
                        mConnectedThread.write("a");
                        Toast.makeText(getContext(),"開始!",
                                Toast.LENGTH_SHORT).show();
                        //設定定時要執行的方法
                        handler.removeCallbacks(updateTimer);
                        //取得目前時間
                        startTime = System.currentTimeMillis();
                        //設定Delay的時間
                        handler.postDelayed(updateTimer, 1000);
                        mStartBtn.setText("重新開始");
                    }
                    else{Toast.makeText(getContext(),"設備未連接!",
                            Toast.LENGTH_SHORT).show();
                    }
                }
            });


//            mListPairedDevicesBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v){
//                    listPairedDevices(v);
//                }
//            });

            mStopBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConnectedThread != null) {
                        mConnectedThread.write("b");
                        onStop();
                    } else {
                        Toast.makeText(getContext(), "設備未連接!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }




    private BluetoothSocket createBluetoothSocket( BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass()
                    .getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BTMODULEUUID);
        } catch (Exception e) {
            Log.e(getTag(), "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }


    public class ConnectedThread extends Thread {

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if(bytes != 0) {
                        buffer = new byte[1024];
                        SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                        bytes = mmInStream.available(); // how many bytes are ready to be read?
                        bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget(); // Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }


        public void Counter() {
            if (mReadBuffer != null)
            {
                Count++;
                BluetoothCount.setText(String.valueOf(Count));
                mReadBuffer = null;
            }
        }
        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }

    }

    /* Call this from the main activity to shutdown the connection */


    private AdapterView.OnItemClickListener mDeviceClickListener =
            new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
                    if(!mBTAdapter.isEnabled()) {
                        Toast.makeText(getContext(), "Bluetooth not on",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mBluetoothStatus01.setText("Connecting...");
                    mBluetoothStatus02.setText("Connecting...");
                    // Get the device MAC address, which is the last 17 chars in the View
                    String info = ((TextView) v).getText().toString();
                    final String address = info.substring(info.length() - 17);
                    final String name = info.substring(0,info.length() - 17);
                    // Spawn a new thread to avoid blocking the GUI one
                    new Thread()
                    {
                        public void run() {
                            boolean fail = false;
                            BluetoothDevice device = mBTAdapter.getRemoteDevice(address);
                            try {
                                mBTSocket = createBluetoothSocket(device);
                            } catch (IOException e) {
                                fail = true;
                                Toast.makeText(getContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                            }
                            // Establish the Bluetooth socket connection.
                            try {
                                mBTSocket.connect();
                            } catch (IOException e) {
                                try {
                                    fail = true;
                                    mBTSocket.close();
                                    mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                            .sendToTarget();
                                } catch (IOException e2) {
                                    //insert code to deal with this
                                    Toast.makeText(getContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if(fail == false) {
                                mConnectedThread = new ConnectedThread(mBTSocket);
                                mConnectedThread.start();

                                mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                                        .sendToTarget();
                            }
                        }
                    }.start();
                }
            };


    public void onStop() {
        super.onStop();
        handler.removeCallbacks(updateTimer);
    }

    //固定要執行的方法
    private Runnable updateTimer = new Runnable() {
        public void run() {

            Long spentTime = System.currentTimeMillis() - startTime;
            //計算目前已過分鐘數
            Long minius = (spentTime / 1000) / 60;
            //計算目前已過秒數
            Long seconds = (spentTime / 1000) % 60;
            time.setText(minius + ":" + seconds);
            handler.postDelayed(this, 1000);
        }
    };


    public class spinnerdata_sync_fromdb extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
        }


        @Override
        protected String doInBackground(String... strings) {

            ArrayList<String> array_sync04 = new ArrayList<String>();
            ArrayList<String> array_sync05= new ArrayList<String>();

            userid = gv.getUserID();

            if (connection!=null){

                try{
                    statement01 = connection.createStatement();
                    ResultSet result02 = statement01.executeQuery("SELECT service_id, body FROM dbo.service WHERE user_id = " + Integer.valueOf(userid) + ";");


                    while (result02.next()) {
                        array_sync04.add(result02.getString(1).toString().trim());
                        array_sync05.add(result02.getString(2).toString().trim());
                    }

                    for (int i = 0; i < array_sync04.size(); i++) {
                        listStr04.add((String) array_sync04.get(i).toString().trim());
                        listStr05.add((String) array_sync05.get(i).toString().trim());
                    }


                }catch (Exception e){
                    isSuccess = false;
                    z = e.getMessage();
                }

            }
            else {
                for (int i = 0; i < 7; i++) {
                    listStr05.add((String) "測試部位" + "0" + i );
                }
            }
            return z;
        }
    }

    public class case_todb extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(),"目標已新增", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {

            //Toast.makeText(getContext(),"測試來了", Toast.LENGTH_SHORT).show();

            if (connection!=null){

                try{

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH,0);
                    String str = df.format(c.getTime());

                    statement01 = connection.createStatement();
                    statement02 = connection.createStatement();

                    ResultSet resultSet =  statement01.executeQuery("SELECT progress FROM dbo.service WHERE user_id ="+Integer.valueOf(userid)+" AND service_id =" + Integer.valueOf(servcie_id)+";");
                    while (resultSet.next()){
                        choose_service_count = Integer.valueOf(resultSet.getString(1).toString().trim());
                    }

                    statement02.executeQuery("INSERT INTO dbo.case_data (user_id,service_id,case_name,num_count,builddate) VALUES ("+Integer.valueOf(userid)+","+Integer.valueOf(servcie_id)+",'"+choose_service.toString().trim() + "(紀錄" + (choose_service_count+1) + ")','"+Integer.valueOf(BluetoothCount.getText().toString().trim())+"','"+str+"');");

                }catch (Exception e){
                    isSuccess = false;
                    z = e.getMessage();
                }
            }
            else {
            }
            return z;
        }
    }

    public class case_todb_update_service_progress extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(),"目標已新增", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {

            //Toast.makeText(getContext(),"測試來了", Toast.LENGTH_SHORT).show();

            if (connection!=null){

                try{
                    statement03 = connection.createStatement();
                    statement03.executeQuery("UPDATE dbo.service SET progress="+(choose_service_count+1)+" WHERE user_id="+Integer.valueOf(userid)+" AND service_id =" + Integer.valueOf(servcie_id)+";");

                }catch (Exception e){
                    isSuccess = false;
                    z = e.getMessage();
                }
            }
            else {
            }
            return z;
        }
    }
}
