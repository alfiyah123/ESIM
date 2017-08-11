package com.example.rahmadewi.esim1.feature.hasilSimulasi;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.database.CrudNilai;
import com.example.rahmadewi.esim1.feature.main.MainActivity;
import com.example.rahmadewi.esim1.models.nilai.Nilai;
import com.example.rahmadewi.esim1.network.NetworkCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

class HasilPresenter extends BasePresenter<HasilView>{
    HasilPresenter(HasilView view){super.attachView(view);}

    void simpanNilai(int nilai, String kategori){
        CrudNilai crudNilai = new CrudNilai();
        crudNilai.insertNilai(nilai, kategori);
    }

    public Bitmap getScreenShot(View view1) {
        View screenView = view1.getRootView();
        screenView.setDrawingCacheEnabled(true);
        screenView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        screenView.layout(0, 0, screenView.getMeasuredWidth(), screenView.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public  void store(Bitmap bm, String fileName, Context context){
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareImage(){
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        String fileName = "screen.png";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);

        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            view.moveToIntent(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            view.shareEror("Tidak ada aplikasi yang tersedia");
        }
    }

    void shareDashboard(String username, int nilai, String kategori){
        view.showLoading();
        addSubscribe(apiStrores.shareNilai(username, nilai, kategori), new NetworkCallback<Nilai>() {
            @Override
            public void onSuccess(Nilai model) {
                view.getDataSuccess(model);
            }

            @Override
            public void onFailure(String message) {
                view.hideLoading();
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    void moveIntent(Activity activity, Class tujuan, @Nullable ArrayList<Integer> id_soal, @Nullable boolean[] validasi, @Nullable String[][] opsi, @Nullable ArrayList<String> jawabanUser){
        Intent intent = new Intent(activity, tujuan);
        if(id_soal != null){
            Bundle myData = new Bundle();
            myData.putIntegerArrayList("id_soal", id_soal);
            myData.putBooleanArray("validasi", validasi);
            myData.putStringArrayList("jawabanUser",jawabanUser);
            myData.putSerializable("opsi",opsi);
            intent.putExtras(myData);
        }
        view.moveToIntent(intent);
    }

}
