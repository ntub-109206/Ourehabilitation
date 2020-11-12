package com.example.myrehabilitaion.ui.Stastics;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrehabilitaion.GlobalVariable;
import com.example.myrehabilitaion.R;
import com.example.myrehabilitaion.ui.Record.RecordFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.myrehabilitaion.R.layout.simple_list_item_01;

public class Frag_LineChart extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener {
    ProgressBar progressBar;

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

    GlobalVariable gv ;
    String userid;
    String serviceid;

    protected List<String> listStr01 = new ArrayList<String>();
    protected List<String> listStr02 = new ArrayList<String>();
    protected List<String> listStr03 = new ArrayList<String>();
    protected List<String> listStr04 = new ArrayList<String>();
    protected List<String> listStr05 = new ArrayList<String>();

    String sInfo;

    protected LineChart chart;
    protected ListView mCaseListView;
    protected ArrayAdapter<String> mCaseArrayAdapter;

    chartdata_sync_fromdb chartdataSyncFromdb;
    spinnerdata_sync_fromdb spinnerdataSyncFromdb;

    ArrayList<Entry> values01;
    ArrayList<Entry> values01_end;

    ArrayList<NameMapping> case_list;
    TextView txth;
    TextView txth01;
    TextView txth02;
    TextView txth03;

    CaseAdapter adapter_case;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstancestate) {
        final View root = inflater.inflate(R.layout.fragment_line_chart, container, false);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

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

        gv = (GlobalVariable)getActivity().getApplicationContext();

        spinnerdataSyncFromdb = new spinnerdata_sync_fromdb();
        spinnerdataSyncFromdb.execute();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chartdataSyncFromdb =new chartdata_sync_fromdb();
        chartdataSyncFromdb.execute();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final List<String> bodypart_list=new ArrayList<String>();
        for(int i=0; i<listStr05.size(); i++){
            bodypart_list.add(listStr05.get(i));
        }

        chart = root.findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);
        Spinner spnbody = root.findViewById(R.id.spinner);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, bodypart_list);
        spnbody.setAdapter(adapter);
        spnbody.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "您選擇了:" + bodypart_list.get(position), Toast.LENGTH_SHORT).show();
                gv.setServiceID(listStr04.get(position));
                case_list.clear();
                listStr01.clear();
                listStr02.clear();
                listStr03.clear();

                chartdataSyncFromdb =new chartdata_sync_fromdb();
                chartdataSyncFromdb.execute();

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //------------------------------------------建立數據------------------------------------------
                values01 = new ArrayList<>();

                for(int i= 0;i<listStr01.size() ;i++){
                    values01.add(new Entry(i,Integer.valueOf(listStr01.get(i))));
                }
                // greenLine
                values01_end = new ArrayList<>();
                values01_end.add(new Entry(6, Integer.valueOf(listStr01.get(6))));

                ArrayList<Entry> values02 = new ArrayList<>();
//        for(int i=0;i < listStr02.size();i++){
//            values01.add(new Entry(Integer.valueOf(listStr02.get(i)), Integer.valueOf(listStr01.get(i))));
//        }
                //yellowLine
                ArrayList<Entry> values02_end = new ArrayList<>();
//        values02_end.add(new Entry(Integer.valueOf(str), Integer.valueOf(10)));

                //------------------------------------------建立數據------------------------------------------

                initX();
                initY();
                initDataSet(values01, values02, values01_end, values02_end);
                initChartFormat();



                for (int j = 6; j >-1 ; j--){
                    case_list.add(new NameMapping(listStr02.get(j),listStr03.get(j),listStr01.get(j)));
                }

                adapter_case = new CaseAdapter((Activity) getContext(),case_list);
                listView = root.findViewById(R.id.caseListView);
                listView.setAdapter(adapter_case);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//------------------------------------------建立數據------------------------------------------
        values01 = new ArrayList<>();

        for(int i= 0;i<listStr01.size() ;i++){
            values01.add(new Entry(i,Integer.valueOf(listStr01.get(i))));
        }
        Log.d("test", String.valueOf(values01));
        // greenLine
        ArrayList<Entry> values01_end = new ArrayList<>();
        values01_end.add(new Entry(6, Integer.valueOf(listStr01.get(6))));

        ArrayList<Entry> values02 = new ArrayList<>();
//        for(int i=0;i < listStr02.size();i++){
//            values01.add(new Entry(Integer.valueOf(listStr02.get(i)), Integer.valueOf(listStr01.get(i))));
//        }
        //yellowLine
        ArrayList<Entry> values02_end = new ArrayList<>();
//        values02_end.add(new Entry(Integer.valueOf(str), Integer.valueOf(10)));

//------------------------------------------建立數據------------------------------------------

        initX();
        initY();
        initDataSet(values01, values02, values01_end, values02_end);
        initChartFormat();

        case_list = new ArrayList<NameMapping>();

        txth = root.findViewById(R.id.txtH);
        txth01 = root.findViewById(R.id.txth01);
        txth02 = root.findViewById(R.id.txth02);
        txth03 = root.findViewById(R.id.txth03);

        txth.setText("近期復健紀錄");
        txth01.setText("日期");
        txth02.setText("復健部位");
        txth03.setText("達成(次)");

        for (int j = 6; j >-1 ; j--){
            case_list.add(new NameMapping(listStr02.get(j),listStr03.get(j),listStr01.get(j)));
        }

        adapter_case = new CaseAdapter((Activity) getContext(),case_list);
        listView = root.findViewById(R.id.caseListView);
        listView.setAdapter(adapter_case);

        return root;
    }

    private  AdapterView.OnItemSelectedListener spnOnItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long content) {
            // 選項有選取時的動作
//            String sPos=String.valueOf(position);
            sInfo=parent.getItemAtPosition(position).toString();
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // 沒有選取時的動作
        }
    };

    private final int[] colors = new int[] {
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {}


    private void initDataSet(final ArrayList<Entry> values01, ArrayList<Entry> values02, ArrayList<Entry> values01_end, ArrayList<Entry> values02_end) {
        final LineDataSet set, set1 = null, set_end, set1_end = null;
        // greenLine
        set = new LineDataSet(values01, "");
        set.setMode(LineDataSet.Mode.LINEAR);//類型為折線
        set.setColor(getResources().getColor(R.color.green));//線的顏色
        set.setLineWidth(1.5f);//線寬
        set.setDrawCircles(false); //不顯示相應座標點的小圓圈(預設顯示)
        set.setDrawValues(false);//不顯示座標點對應Y軸的數字(預設顯示)

//greenLine最後的圓點
        set_end = new LineDataSet(values01_end, "");
        set_end.setCircleColor(getResources().getColor(R.color.green));//圓點顏色
        set_end.setColor(getResources().getColor(R.color.green));//線的顏色
        set_end.setCircleRadius(4);//圓點大小
        set_end.setDrawCircleHole(false);//圓點為實心(預設空心)
        set_end.setDrawValues(false);//不顯示座標點對應Y軸的數字(預設顯示)


        /**
         * yellowLine及其最後的圓點設定可比照如上greenLine設定，不再列示
         */


        set.setMode(LineDataSet.Mode.LINEAR);//折線
     /* 共有四種模式可作變化
STEPPED立方曲線 (如下greenLine)
CUBIC_BEZIER圓滑曲線 (如下yellowLine)
HORIZONTAL_BEZIER水平曲線
*/

        set.setDrawValues(true);//顯示座標點對應Y軸的數字(預設顯示)
        set.setValueTextSize(8);//座標點數字大小
        set.setValueFormatter(new DefaultValueFormatter(1));//座標點數字的小數位數1位

        set.setDrawFilled(true);//使用範圍背景填充(預設不使用)

        set.setHighlightEnabled(false);//禁用點擊交點後顯示高亮線 (預設顯示，如為false則以下設定均無效)
        set.enableDashedHighlightLine(5, 5, 0);//高亮線以虛線顯示，可設定虛線長度、間距等
        set.setHighlightLineWidth(2);//高亮線寬度
        set.setHighLightColor(Color.RED);//高亮線顏色

//理解爲多條線的集合
        LineData data = new LineData(set, set_end);
        chart.setData(data);//一定要放在最後
        chart.invalidate();//繪製圖表
    }
    private void initX() {
        XAxis xAxis = chart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X軸標籤顯示位置(預設顯示在上方，分為上方內/外側、下方內/外側及上下同時顯示)
        xAxis.setTextColor(Color.GRAY);//X軸標籤顏色
        xAxis.setTextSize(12);//X軸標籤大小

        xAxis.setLabelCount(7);//X軸標籤個數
        xAxis.setSpaceMin(0.5f);//折線起點距離左側Y軸距離
        xAxis.setSpaceMax(0.5f);//折線終點距離右側Y軸距離

        xAxis.setDrawGridLines(false);//不顯示每個座標點對應X軸的線 (預設顯示)

        xAxis.setLabelRotationAngle(-20);//X軸數字旋轉角度

        //設定所需特定標籤資料
        List<String> xList = new ArrayList<String>();

        for (int i = 0; i < listStr02.size() ; i++) {
//            xList.add(listStr02.get(i).trim().substring(5).replaceAll("-", "/"));
            xList.add(listStr02.get(i).trim());
        }


        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));
    }



    private void initY() {
        YAxis rightAxis = chart.getAxisRight();//獲取右側的軸線
        rightAxis.setEnabled(false);//不顯示右側Y軸
        YAxis leftAxis = chart.getAxisLeft();//獲取左側的軸線

        leftAxis.setLabelCount(4);//Y軸標籤個數
        leftAxis.setTextColor(Color.GRAY);//Y軸標籤顏色
        leftAxis.setTextSize(12);//Y軸標籤大小

        leftAxis.setAxisMinimum(0);//Y軸標籤最小值
        leftAxis.setAxisMaximum(20);//Y軸標籤最大值

        leftAxis.enableGridDashedLine(5f, 5f, 0f);//格線以虛線顯示，可設定虛線長度、間距等

        /**
         * 格式化軸標籤二種方式：
         * 1、用圖表庫已寫好的類_如上一步驟中X 軸使用
         * 2、自己實現接口_如下Y 軸使用
         * */
        leftAxis.setValueFormatter(new MyYAxisValueFormatter());
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }


    class MyYAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {

        private DecimalFormat mFormat;

        public MyYAxisValueFormatter() {
            mFormat = new DecimalFormat("###,###.0");//Y軸數值格式及小數點位數
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return mFormat.format(value);
        }
    }

    private void initChartFormat() {
        //右下方description label：設置圖表資訊
        Description description = chart.getDescription();
        description.setEnabled(false);//不顯示Description Label (預設顯示)

        chart.setScaleEnabled(false);// 禁用縮放及點二下觸摸響應，點擊可顯示高亮線
        chart.setTouchEnabled(false);// 禁用縮放及點二下觸摸響應，點擊也不顯示高亮線

        //左下方Legend：圖例數據資料
        Legend legend = chart.getLegend();
        legend.setEnabled(false);//不顯示圖例 (預設顯示)
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);

        chart.setBackgroundColor(Color.WHITE);//顯示整個圖表背景顏色 (預設灰底)

        //設定沒資料時顯示的內容
        chart.setNoDataText("暫時沒有數據");
        chart.setNoDataTextColor(Color.BLUE);//文字顏色
        description.setPosition(680, 80);//顯示位置座標 (預設右下方)
    }


    public class chartdata_sync_fromdb extends AsyncTask<String, String , String> {

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

            ArrayList<String> array_sync01 = new ArrayList<String>();
            ArrayList<String> array_sync02 = new ArrayList<String>();
            ArrayList<String> array_sync03 = new ArrayList<String>();

            userid = gv.getUserID();
            serviceid = gv.getServiceID();

          if (connection!=null){

                try{

                    statement02 = connection.createStatement();
                    ResultSet result01 = statement02.executeQuery("SELECT num_count, builddate, case_name FROM dbo.case_data WHERE user_id = "+Integer.valueOf(userid)+" AND service_id = "+Integer.valueOf(serviceid)+";");


                    if(result01.next()){
                        array_sync01.add(result01.getString(1).toString().trim());
                        array_sync02.add(result01.getString(2).toString().trim());
                        array_sync03.add(result01.getString(3).toString().trim());
                        while (result01.next()) {
                            array_sync01.add(result01.getString(1).toString().trim());
                            array_sync02.add(result01.getString(2).toString().trim());
                            array_sync03.add(result01.getString(3).toString().trim());
                        }
                    }else{
                        array_sync01.add(String.valueOf("0"));
                        array_sync02.add(String.valueOf("-"));
                        array_sync03.add(String.valueOf("-"));
                    }
                    if(array_sync01.size()<=7 ){
                        for(int i=0;i <7-array_sync01.size() ;i++){
                            listStr01.add("0");
                            listStr02.add("-");
                            listStr03.add("-");
                        }
                    }
                    for (int i = 0; i < array_sync01.size(); i++) {
                        listStr01.add((String) String.valueOf(array_sync01.get(i)));
                        listStr02.add((String) String.valueOf(array_sync02.get(i)));
                        listStr03.add(String.valueOf(array_sync03.get(i)));
                    }



                }catch (Exception e){

                    isSuccess = false;
                    z = e.getMessage();
                }

            }
            else {
              int r;
              ArrayList<Integer> r_list= new ArrayList<Integer>();
              for(int i=0;i<7;i++){
                 r = (int) (Math.random()*21);
                  r_list.add(r);
              }
              for (int i = 6; i >-1; i--) {
                  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                  Calendar c = Calendar.getInstance();
                  c.add(Calendar.DAY_OF_MONTH, -i);
                  String str = df.format(c.getTime());
                  listStr02.add(str);
              }

              for (int i = 0; i < 7; i++) {
                  listStr01.add(String.valueOf(r_list.get(i)));
                  listStr03.add(String.valueOf(listStr05.get(i)));
              }
            }
            return z;
        }
    }

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

                    if(result02.next()){
                        array_sync04.add(result02.getString(1).toString().trim());
                        array_sync05.add(result02.getString(2).toString().trim());
                        while (result02.next()) {
                            array_sync04.add(result02.getString(1).toString().trim());
                            array_sync05.add(result02.getString(2).toString().trim());
                        }
                    }else{
                        array_sync04.add("000000");
                        array_sync05.add("-");
                    }

                    for (int i = 0; i < array_sync04.size(); i++) {
                        listStr04.add((String) array_sync04.get(i).toString().trim());
                        listStr05.add((String) array_sync05.get(i).toString().trim());
                    }

                    gv.setServiceID(String.valueOf(listStr04.get(0)));

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


    public class CaseAdapter extends ArrayAdapter<NameMapping> {
        //建構式
        public CaseAdapter(Activity context, ArrayList<NameMapping> case_list){
            super(context, 0, case_list);
        }

        @Override
        //改寫getView()方法
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItemView = convertView;

            //listItemView可能會是空的，例如App剛啟動時，沒有預先儲存的view可使用
            if(listItemView == null){
                listItemView = LayoutInflater.from(getContext()).inflate(simple_list_item_01, parent, false);
            }

            //找到data，並在View上設定正確的data
            NameMapping currentName = getItem(position);

            //找到ListItem.xml中的兩個TextView(物種學名和中文名)
            TextView text_view01 = listItemView.findViewById(R.id.txth01_1);
            text_view01.setText(currentName.getDate());

            TextView text_view02 = listItemView.findViewById(R.id.txth02_2);
            text_view02.setText(currentName.getName());

            TextView text_view03 = listItemView.findViewById(R.id.txth03_3);
            text_view03.setText(currentName.getTimes());

            return listItemView;
        }
    }

    public class NameMapping {


        private String mDate;
        private String mName;
        private String mTimes;

        //建構式
        public NameMapping(String txt_date, String txt_name,String txt_times){
            mDate = txt_date;
            mName = txt_name;
            mTimes = txt_times;
        }

        public String getDate(){
            return mDate;
        }
        public String getName(){
            return mName;
        }
        public String getTimes(){
            return mTimes;
        }
    }

}


