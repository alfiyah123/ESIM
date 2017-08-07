package com.example.rahmadewi.esim1.feature.grafik;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

interface GrafikView {
    void dataNilai(ArrayList<Entry> yValues, ArrayList<String> xVals1, ArrayList<Integer> dataY);

    void dataKesalahan(ArrayList<Entry> yvaluesKesalahan, ArrayList<String> xValsKesalahan);

}
