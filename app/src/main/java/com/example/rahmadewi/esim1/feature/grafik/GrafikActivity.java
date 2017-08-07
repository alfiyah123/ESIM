package com.example.rahmadewi.esim1.feature.grafik;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;

public class GrafikActivity extends MvpActivity<GrafikPresenter> implements GrafikView {

    @BindView(R.id.grafikNilai)
    LineChart grafikNilai;
    @BindView(R.id.kekurangan)
    PieChart grafik2;

    String kategori;

    ArrayList<Entry> yvalues;
    ArrayList<String> xVals1;

    ArrayList<Integer> dataY;

    ArrayList<Entry> yvaluesKesalahan;
    ArrayList<String> xValsKesalahan;


    @Override
    protected GrafikPresenter cretePresenter() {
        return new GrafikPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();
        kategori = myBundle.getString("kategori");

        grafikNilai();
        grafikKekurangan();
    }

    private void grafikNilai(){
        presenter.getNilai(kategori);

        int[] colors = new int[dataY.size()];

        LineDataSet dataNilai = new LineDataSet(yvalues, "Grafik Nilai Simulasi");
        dataNilai.setDrawCubic(true);
        for(int i=0; i<dataY.size(); i++){
            if(dataY.get(i) >= 70){
                colors[i] = Color.rgb(51,255,51);
            }else{
                colors[i] = Color.rgb(255,51,51);
            }
        }
        dataNilai.setCircleColors(colors);

        LineData data = new LineData(xVals1, dataNilai);
        grafikNilai.setData(data);
        grafikNilai.animateY(5000);
    }

    private void grafikKekurangan(){
        grafik2.setUsePercentValues(true);

        presenter.getKesalahan(kategori);

        PieDataSet dataSet = new PieDataSet(yvaluesKesalahan, "Tingkat Kesalahan");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData data = new PieData(xValsKesalahan, dataSet);

        data.setValueFormatter(new PercentFormatter());
        grafik2.setDescription("Tingkat Kesalahan");
        grafik2.setData(data);
        grafik2.animateY(5000);
    }

    @Override
    public void dataNilai(ArrayList<Entry> yValues, ArrayList<String> xVals1, ArrayList<Integer> dataY) {
        this.yvalues = yValues;
        this.xVals1 = xVals1;
        this.dataY = dataY;
    }

    @Override
    public void dataKesalahan(ArrayList<Entry> yvaluesKesalahan, ArrayList<String> xValsKesalahan) {
        this.yvaluesKesalahan = yvaluesKesalahan;
        this.xValsKesalahan = xValsKesalahan;
    }
}
