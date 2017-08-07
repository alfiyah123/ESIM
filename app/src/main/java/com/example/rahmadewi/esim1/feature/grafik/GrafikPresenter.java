package com.example.rahmadewi.esim1.feature.grafik;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.database.CrudKesalahan;
import com.example.rahmadewi.esim1.database.CrudNilai;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

class GrafikPresenter extends BasePresenter<GrafikView> {
    GrafikPresenter(GrafikView view){
        super.attachView(view);
    }

    void getNilai(String kategori){
        ArrayList<Entry> yvalues = new ArrayList<>();
        ArrayList<String> xVals1 = new ArrayList<>();
        ArrayList<Integer> dataY = new ArrayList<>();

        CrudNilai crudNilai = new CrudNilai();
        crudNilai.getData(kategori, yvalues, dataY, xVals1);

        view.dataNilai(yvalues,xVals1,dataY);
    }

    void getKesalahan(String kategori){
        ArrayList<Entry> yvaluesKesalahan = new ArrayList<>();
        ArrayList<String> xValsKesalahan = new ArrayList<>();

        CrudKesalahan crudKesalahan = new CrudKesalahan();
        crudKesalahan.getDataKesalahan(kategori,yvaluesKesalahan, xValsKesalahan);

        view.dataKesalahan(yvaluesKesalahan, xValsKesalahan);
    }
}
