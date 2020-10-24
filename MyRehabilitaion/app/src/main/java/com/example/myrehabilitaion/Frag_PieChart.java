package com.example.myrehabilitaion;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;

public class Frag_PieChart extends Fragment implements OnChartValueSelectedListener{

    private PieChart chart;
    private Typeface tf;
    private TextView mTxtR;
    private ListView mListViewRegion;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstancestate) {
        View root = inflater.inflate(R.layout.fragment_pie_chart, container, false);

//        chart = root.findViewById(R.id.chart1);
////-------------------------------------------------設定數據---------------------------------------------------------
//        ArrayList<PieEntry> entries = new ArrayList<>();
//
//        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//        // the chart.
//        for (int i = 0; i < 4; i++) {
//            entries.add(new PieEntry((float) (Math.random() * 4) + 5 / 5, parties[i % parties.length]));
//        }
//
//        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
//
//        // add a lot of colors
//
//        ArrayList<Integer> colors = new ArrayList<>();
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//
//        dataSet.setColors(colors);
//        //dataSet.setSelectionShift(0f);
//
//
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        dataSet.setValueLinePart1Length(0.2f);
//        dataSet.setValueLinePart2Length(0.4f);
//
//        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface(tf);
//        chart.setData(data);
//
//        // undo all highlights
//        chart.highlightValues(null);
//        chart.invalidate();
// //-------------------------------------------------設定數據---------------------------------------------------------
//
//        chart.setUsePercentValues(true);
//        chart.getDescription().setEnabled(false);
//        chart.setExtraOffsets(5, 10, 5, 5);
//
//        chart.setDragDecelerationFrictionCoef(0.95f);
//
//        chart.setCenterText(generateCenterSpannableText());
//
//        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
//
//        chart.setDrawHoleEnabled(true);
//        chart.setHoleColor(Color.WHITE);
//
//        chart.setTransparentCircleColor(Color.WHITE);
//        chart.setTransparentCircleAlpha(110);
//
//        chart.setHoleRadius(58f);
//        chart.setTransparentCircleRadius(61f);
//
//        chart.setDrawCenterText(true);
//
//        chart.setRotationAngle(0);
//        // enable rotation of the chart by touch
//        chart.setRotationEnabled(true);
//        chart.setHighlightPerTapEnabled(true);
//
//        // chart.setUnit(" €");
//        // chart.setDrawUnitsInChart(true);
//
//        // add a selection listener
//        chart.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);
//
//
//        chart.animateY(1400, Easing.EaseInOutQuad);
//        // chart.spin(2000, 0, 360);
//
//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setEnabled(false);

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
    private AdapterView.OnItemClickListener listViewRegionOnItemClick
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String s = getString(R.string.region_selected);
            mTxtR.setText(s + ((TextView) view).getText());
        }
    };

//    private void setData(int count, float range) {
//
//
//    }

//    private SpannableString generateCenterSpannableText() {
//        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
//        s.setSpan(new RelativeSizeSpan(1.5f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
//        return s;
//    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null) {
            return;
        } else {
            Log.i("VAL SELECTED",
                    "Value: " + e.getY() + ", xIndex: " + e.getX()
                            + ", DataSet index: " + h.getDataSetIndex());
        }
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}
