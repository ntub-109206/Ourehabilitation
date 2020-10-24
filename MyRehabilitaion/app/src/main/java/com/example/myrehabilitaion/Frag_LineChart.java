package com.example.myrehabilitaion;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Frag_LineChart extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener {

    private LineChart chart;

    private TextView mTxtR;
    private ListView mListViewRegion;


    private static String ip = "140.131.114.241";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "109-rehabilitation";
    private static String username = "case210906";
    private static String password = "1@case206";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    Statement statement = null;

    GlobalVariable gv ;

    public List<String> listStr01;
    public List<String> listStr02;
    public List<String> xValue;
    public List<String> listStr04;

    String sInfo;

    linedata_sync_fromdb linedata_sync_fromdb;
    ArrayAdapter<String> spinnerArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstancestate) {
        View root = inflater.inflate(R.layout.fragment_line_chart, container, false);

        listStr01 = new ArrayList<String>();
        listStr02 = new ArrayList<String>();
        xValue = new ArrayList<String>();
        listStr04 = new ArrayList<String>();

        gv = (GlobalVariable)getActivity().getApplicationContext();

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
            Toast toast = Toast.makeText(getContext(),"Success", Toast.LENGTH_SHORT);
            toast.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getContext(),"FAILURE", Toast.LENGTH_SHORT);
            toast.show();
        }


        linedata_sync_fromdb =new linedata_sync_fromdb();
        linedata_sync_fromdb.execute();

        try {
            Thread.sleep(100);
            System.out.print("    執行緒睡眠0.01秒！\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



//        Spinner spinner =root.findViewById(R.id.spinner);
//        spinnerArrayAdapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_item, listStr04);
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
//        spinner.setAdapter(spinnerArrayAdapter);
//        spinner.setOnItemClickListener((AdapterView.OnItemClickListener) spnOnItemSelected);

        // Application of the Array to the Spinner

        chart = root.findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);
//------------------------------------------建立數據------------------------------------------
        ArrayList<Entry> values01 = new ArrayList<>();

        for(int i=0;i < listStr02.size();i++){
//            values01.add(new Entry(Integer.valueOf(listStr02.get(i)), Integer.valueOf(listStr01.get(i))));
        }

        ArrayList<Entry> values02 = new ArrayList<>();

        for(int i=0;i < listStr02.size();i++){
//            values01.add(new Entry(Integer.valueOf(listStr02.get(i)), Integer.valueOf(listStr01.get(i))));

        }

        // greenLine
        ArrayList<Entry> values01_end = new ArrayList<>();
//        values01_end.add(new Entry(Integer.valueOf(listStr02.get(listStr02.size()))-1, Integer.valueOf(listStr01.get(listStr01.size()-1))));
//yellowLine
        ArrayList<Entry> values02_end = new ArrayList<>();
//        values02_end.add(new Entry(Integer.valueOf(listStr02.get(listStr02.size()))-1, Integer.valueOf(listStr01.get(listStr01.size()-1))));

//        initX();
//        initY();
//        initDataSet(values01, values02, values01_end, values02_end);
//        initChartFormat();

//        LineDataSet d = new LineDataSet(dataSet01, "DataSet01" );
//        d.setLineWidth(2.5f);
//        d.setCircleRadius(4f);

//        int z = Integer.valueOf((int) ((Math.random() * 3) + 3));
//        int color = colors[z % colors.length];
//        d.setColor(color);
//        d.setCircleColor(color);
//
//        // make the first DataSet dashed
//        ((LineDataSet) d).enableDashedLine(10, 10, 0);
//        ((LineDataSet) d).setColors(ColorTemplate.VORDIPLOM_COLORS);
//        ((LineDataSet) d).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);
//
//        LineData data = new LineData(d);
//        chart.setData(data);
//        chart.invalidate();
//------------------------------------------建立數據------------------------------------------



//        chart.setDrawGridBackground(false);
//        chart.getDescription().setEnabled(false);
//        chart.setDrawBorders(false);
//
//        chart.getAxisLeft().setEnabled(false);
//        chart.getAxisLeft().setDrawAxisLine(true);
//        chart.getAxisRight().setDrawAxisLine(false);
//        chart.getAxisRight().setDrawGridLines(false);
//        chart.getXAxis().setDrawAxisLine(false);
//        chart.getXAxis().setDrawGridLines(false);
//        // enable touch gestures
//        chart.setTouchEnabled(false);
//
//        // enable scaling and dragging
//        chart.setDragEnabled(false);
//        chart.setScaleEnabled(false);
//
//        // if disabled, scaling can be done on x- and y-axis separately
//        chart.setPinchZoom(false);
//
//
//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);


        mTxtR = root.findViewById(R.id.txtR);
        mListViewRegion = root.findViewById(R.id.listViewRegion);
        ArrayAdapter<CharSequence> arrAdapRegion
                = ArrayAdapter.createFromResource(getActivity().getApplication(),
                R.array.region_list,
                android.R.layout.simple_list_item_1);
        mListViewRegion.setAdapter(arrAdapRegion);
        mListViewRegion.setOnItemClickListener(listViewRegionOnItemClick);
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

    private AdapterView.OnItemClickListener listViewRegionOnItemClick
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String s = getString(R.string.region_selected);
            mTxtR.setText(s + ((TextView) view).getText());
        }
    };

//    @Override
//    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//         chart.resetTracking();
//
//    }


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

        xAxis.setLabelCount(10);//X軸標籤個數
        xAxis.setSpaceMin(0.5f);//折線起點距離左側Y軸距離
        xAxis.setSpaceMax(0.5f);//折線終點距離右側Y軸距離

        xAxis.setDrawGridLines(false);//不顯示每個座標點對應X軸的線 (預設顯示)

        //設定所需特定標籤資料
        List<String> xList = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
//            xList.add("3/" + i);
            xList.add(listStr02.get(i));
        }

        /**
         * 格式化軸標籤二種方式：
         * 1、用圖表庫已寫好的類_如下X 軸使用
         * 2、自己實現接口_下一步驟中Y 軸使用
         * */
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

        //左下方Legend：圖例數據資料
        Legend legend = chart.getLegend();
        legend.setEnabled(true);//不顯示圖例 (預設顯示)

        chart.setBackgroundColor(Color.WHITE);//顯示整個圖表背景顏色 (預設灰底)

        //設定沒資料時顯示的內容
        chart.setNoDataText("暫時沒有數據");
        chart.setNoDataTextColor(Color.BLUE);//文字顏色
        description.setPosition(680, 80);//顯示位置座標 (預設右下方)
    }

    public class linedata_sync_fromdb extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(),"目標數據同步成功", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected String doInBackground(String... strings) {

            ArrayList<String> array_sync01 = new ArrayList<String>();
            ArrayList<String> array_sync02 = new ArrayList<String>();
            ArrayList<String> array_sync03 = new ArrayList<String>();
            ArrayList<String> array_sync04 = new ArrayList<String>();

            String  sync_name = gv.getUserEmail();
//            String sync_servicename = gv.getServiceName();
            String sync_servicename = "大腦";
//            String sync_casename = gv.getCaseName();
            String sync_casename = "測試01";


            if (connection!=null){

                try{

                    statement = connection.createStatement();
                    ResultSet result01 = statement.executeQuery("SELECT timeortimes, builddate FROM dbo.case_data WHERE user_id ='"+sync_name.toString().trim()+"' AND body = '" + sync_servicename.toString().trim() + "' AND case_name = '" + sync_casename.toString().trim() + "';");
                    ResultSet result02 = statement.executeQuery("SELECT body FROM dbo.service WHERE user_id ='" + sync_name.toString().trim() + "'; ");

                    while (result01.next()) {
                        array_sync01.add(result01.getString(1).toString().trim());
                        array_sync02.add(result01.getString(2).toString().trim());
                        array_sync03.add(result01.getString(2).toString().trim());
                    }


                    while (result02.next()) {
                        array_sync04.add(result01.getString(1).toString().trim());
                    }
                    for (int i = 0; i < array_sync01.size(); i++) {
                        listStr01.add((String) array_sync01.get(i));
                        listStr02.add(array_sync02.get(i));
                        xValue.add(String.valueOf(array_sync02.get(i)));
                        listStr04.add((String) array_sync04.get(i));
                    }

                }catch (Exception e){
                    isSuccess = false;
                    z = e.getMessage();
                }

            }
            else {
                Toast toast = Toast.makeText(getContext(),"目標數據同步失敗", Toast.LENGTH_SHORT);
                toast.show();
            }
            return z;
        }
    }

}


