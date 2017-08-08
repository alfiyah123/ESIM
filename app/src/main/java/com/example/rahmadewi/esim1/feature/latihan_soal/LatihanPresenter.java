package com.example.rahmadewi.esim1.feature.latihan_soal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.database.CrudSoal;
import com.example.rahmadewi.esim1.feature.image.Image2Activity;
import com.example.rahmadewi.esim1.models.soal.ResultItem;

import java.io.File;

class LatihanPresenter extends BasePresenter<LatihanView> {
    LatihanPresenter(LatihanView view){
        super.attachView(view);
    }

    void getSoal(String kategori, int id){
        ResultItem resultItem;

        CrudSoal crudSoal = new CrudSoal();
        resultItem = crudSoal.getDataRandomLatihan(kategori, id);

        String soal = resultItem.getSoal();
        String opsi_1 = resultItem.getOpsi1();
        String opsi_2 = resultItem.getOpsi2();
        String opsi_3 = resultItem.getOpsi3();
        String jawaban = resultItem.getJawaban();
        String id_soal = resultItem.getIdSoal();

        String fileName = resultItem.getGambar();

        String path = Environment.getExternalStorageDirectory()+ "/SIM_IMAGES/"+fileName;

        File imgFile = new File(path);
        if(imgFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            view.tampilGambar(myBitmap);
        }

        view.tampilData(soal,opsi_1,opsi_2,opsi_3,jawaban,fileName, id_soal);
    }

    void moveGambar(Activity activity, String fileName){
        Intent intent = new Intent(activity, Image2Activity.class);
        intent.putExtra("fileName", fileName);
        view.moveIntent(intent);
    }
}
