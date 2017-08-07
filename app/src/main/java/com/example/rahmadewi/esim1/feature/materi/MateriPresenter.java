package com.example.rahmadewi.esim1.feature.materi;

import android.app.Activity;
import android.content.Intent;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.database.CrudMateri;
import com.example.rahmadewi.esim1.database.CrudRambu;

import java.util.HashMap;
import java.util.List;

public class MateriPresenter extends BasePresenter<MateriView> {
    MateriPresenter(MateriView view){
        super.attachView(view);
    }

    HashMap<String, List<String>> getData(String kategori){
        HashMap<String, List<String>> data = new HashMap<>();

        CrudMateri materi = new CrudMateri();
        materi.getDataMateri(data, kategori);

        CrudRambu rambu = new CrudRambu();
        rambu.getDataRambu(data);

        return data;
    }

    void pindahIntent(Activity activity, Class class1, String items){
        Intent intent = new Intent(activity, class1);
        intent.putExtra("item", items);
        view.moveIntent(intent);
    }
}
