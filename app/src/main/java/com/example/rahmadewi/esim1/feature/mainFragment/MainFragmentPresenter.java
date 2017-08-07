package com.example.rahmadewi.esim1.feature.mainFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.androidquery.AQuery;
import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.database.CrudGambar;
import com.example.rahmadewi.esim1.database.CrudKategori;
import com.example.rahmadewi.esim1.database.CrudMateri;
import com.example.rahmadewi.esim1.database.CrudRambu;
import com.example.rahmadewi.esim1.database.CrudSoal;
import com.example.rahmadewi.esim1.database.CrudTopik;
import com.example.rahmadewi.esim1.database.DatabaseHelper;
import com.example.rahmadewi.esim1.feature.menu.MenuActivity;
import com.example.rahmadewi.esim1.models.gambar.Gambar;
import com.example.rahmadewi.esim1.models.kategori.Kategori;
import com.example.rahmadewi.esim1.models.kategori.ResultItem;
import com.example.rahmadewi.esim1.models.materi.Materi;
import com.example.rahmadewi.esim1.models.rambu.Rambu;
import com.example.rahmadewi.esim1.models.soal.Soal;
import com.example.rahmadewi.esim1.models.topik.Topik;
import com.example.rahmadewi.esim1.network.NetworkCallback;

import java.io.File;
import java.util.List;

class MainFragmentPresenter extends BasePresenter<MainViewFragment> {
    MainFragmentPresenter(MainViewFragment view) {
        super.attachView(view);
    }

    void unduhDataKategori() {
        System.out.println("memulai unduh data");
        view.showLoading("Mengunduh Data Kategori");
        addSubscribe(apiStrores.getDataKategori(), new NetworkCallback<Kategori>() {
            @Override
            public void onSuccess(Kategori model) {
                view.getDataSuccessKategori(model);
            }

            @Override
            public void onFailure(String message) {
                view.hideLoading();
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    void unduhDataTopik() {
        view.showLoading("Mengunduh Data Topik");
        addSubscribe(apiStrores.getDataTopik(), new NetworkCallback<Topik>() {
            @Override
            public void onSuccess(Topik model) {
                view.getDataSuccessTopik(model);
            }

            @Override
            public void onFailure(String message) {
                view.hideLoading();
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    void unduhDataSoal(String[] key) {
        view.showLoading("Mengunduh Data Soal");
        addSubscribe(apiStrores.getDataSoal(key), new NetworkCallback<Soal>() {
            @Override
            public void onSuccess(Soal model) {
                view.getDataSuccessSoal(model);
            }

            @Override
            public void onFailure(String message) {
                view.hideLoading();
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    void unduhDataMateri(String[] key) {
        view.showLoading("Mengunduh Data Materi");
        addSubscribe(apiStrores.getDataMateri(key), new NetworkCallback<Materi>() {
            @Override
            public void onSuccess(Materi model) {
                view.getDataSuccessMateri(model);
            }

            @Override
            public void onFailure(String message) {
                view.hideLoading();
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    void unduhDataRambu() {
        view.showLoading("Mengunduh Data Rambu");
        addSubscribe(apiStrores.getDataRambu(), new NetworkCallback<Rambu>() {
            @Override
            public void onSuccess(Rambu model) {
                view.getDataSuccessRambu(model);
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

    void unduhDataGambar(String[] id){
        view.showLoading("Mengunduh Data Gambar");
        addSubscribe(apiStrores.getGambar(id), new NetworkCallback<Gambar>() {
            @Override
            public void onSuccess(Gambar model) {
                view.getDataSuccessGambar(model);
            }

            @Override
            public void onFailure(String message) {
                view.hideLoading();
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
            }
        });
    }

    void unduhGambar(String url) {
        File mFolder = new File(Environment.getExternalStorageDirectory(), "/SIM_IMAGES1/");
        if (!mFolder.exists()) {
            mFolder.mkdirs();
            mFolder.setExecutable(true);
            mFolder.setReadable(true);
            mFolder.setWritable(true);
        }
        int index = url.lastIndexOf("/");
        String fileName = url.substring(index + 1);
        view.simpanGambar(fileName, url);
    }

    void createInstance(Context context, String name){
        DatabaseHelper.getInstance(context, name);
    }

    void simpanDataKategori(ResultItem kategori){
        CrudKategori crudKategori = new CrudKategori();
        crudKategori.insertData(kategori);
    }

    void simpanDataTopik(com.example.rahmadewi.esim1.models.topik.ResultItem topik){
        CrudTopik crudTopik = new CrudTopik();
        crudTopik.insertData(topik);
    }

    void simpanDataSoal(com.example.rahmadewi.esim1.models.soal.ResultItem soal){
        CrudSoal crudSoal = new CrudSoal();
        crudSoal.insertData(soal);
    }

    void simpanDataMateri(com.example.rahmadewi.esim1.models.materi.ResultItem materi){
        CrudMateri crudMateri = new CrudMateri();
        crudMateri.insertData(materi);
    }

    void simpanDataGambar(com.example.rahmadewi.esim1.models.gambar.ResultItem gambar){
        CrudGambar crudGambar = new CrudGambar();
        crudGambar.insertData(gambar);
    }

    void simpanDataRambu(com.example.rahmadewi.esim1.models.rambu.ResultItem rambu){
        CrudRambu crudRambu = new CrudRambu();
        crudRambu.insertData(rambu);
    }

    void getKategori(String kategori, Activity activity){
        Intent intent = new Intent(activity, MenuActivity.class);
        intent.putExtra("kategori", kategori);
        view.moveToMenu(intent);
    }

}
