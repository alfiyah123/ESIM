package com.example.rahmadewi.esim1.feature.pembahasan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.database.CrudSoal;
import com.example.rahmadewi.esim1.models.soal.ResultItem;

import java.io.File;

class PembahasanPresenter extends BasePresenter<PembahasanView> {
    PembahasanPresenter(PembahasanView view){
        super.attachView(view);
    }

    void getData(int id_soal){
        String soal, jawaban, fileName;

        CrudSoal crudSoal = new CrudSoal();
        ResultItem resultItem = crudSoal.getDataSoalSimulasi(id_soal);

        soal = resultItem.getSoal();
        jawaban = resultItem.getJawaban();
        fileName = resultItem.getGambar();

        String path = Environment.getExternalStorageDirectory()+ "/SIM_IMAGES/"+fileName;

        File imgFile = new File(path);
        if(imgFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            view.setImg(myBitmap);
        }

        view.setDataLayout(soal, jawaban);
    }
}
