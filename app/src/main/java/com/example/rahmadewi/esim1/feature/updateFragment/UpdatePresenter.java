package com.example.rahmadewi.esim1.feature.updateFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

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
import com.example.rahmadewi.esim1.models.kategori.ResultItem;
import com.example.rahmadewi.esim1.models.materi.Materi;
import com.example.rahmadewi.esim1.models.rambu.Rambu;
import com.example.rahmadewi.esim1.models.soal.Soal;
import com.example.rahmadewi.esim1.models.topik.Topik;
import com.example.rahmadewi.esim1.network.NetworkCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

class UpdatePresenter extends BasePresenter<UpdateView>{
    UpdatePresenter(UpdateView view){
        super.attachView(view);
    }

    void getMaxIdSoalServer(String[] kategori) {
        addSubscribe(apiStrores.getMaxIdSoal(kategori), new NetworkCallback<Soal>() {
            @Override
            public void onSuccess(Soal model) {
                view.getIdSuksesSoal(model);
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

    void getMaxIdMateriServer(String[] kategori) {
        addSubscribe(apiStrores.getMaxIdMateri(kategori), new NetworkCallback<Materi>() {
            @Override
            public void onSuccess(Materi model) {
                view.getIdSuksesMateri(model);
            }

            @Override
            public void onFailure(String message) {
                view.hideLoadingCek();
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
            }
        });
    }

    void getMaxIdTopikServer() {
        view.showLoadingCek();
        addSubscribe(apiStrores.getMaxIdTopik(), new NetworkCallback<Topik>() {
            @Override
            public void onSuccess(Topik model) {
                view.getIdSuksesTopik(model);
            }

            @Override
            public void onFailure(String message) {
                view.hideLoadingCek();
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
            }
        });
    }

    void getMaxIdRambuServer() {
        addSubscribe(apiStrores.getMaxIdRambu(), new NetworkCallback<Rambu>() {
            @Override
            public void onSuccess(Rambu model) {
                view.getIdSuksesRambu(model);
            }

            @Override
            public void onFailure(String message) {
                view.hideLoadingCek();
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
                view.hideLoadingCek();
            }
        });
    }

    void getMaxIdSoalDb(String[] kategori, Soal soal, ArrayList<String> text, ArrayList<Integer> id_soal, ArrayList<String> kategoriSoal){
        ArrayList<Integer> id;
        CrudSoal crudSoal = new CrudSoal();
        id = crudSoal.getMaxId(kategori);

        for(int i=0; i<id.size(); i++){
            if(id.get(i) < Integer.parseInt(soal.getResult().get(i).getIdSoal())){
                id_soal.add(id.get(i));
                kategoriSoal.add(kategori[i]);
                text.add("Soal "+kategori[i]);
            }
        }
    }

    void getMaxIdMateriDb(String[] kategori, Materi materi, ArrayList<String> text, ArrayList<Integer> id_materi, ArrayList<String> kategoriMateri){
        ArrayList<Integer> id;
        CrudMateri crudMateri = new CrudMateri();
        id = crudMateri.getMaxId(kategori);

        for(int i=0; i<id.size(); i++) {
            System.out.println("db : "+id.get(i)+" | server : "+materi.getResult().get(i).getIdMateri());
            if (materi.getResult().get(i).getIdMateri() != null){
                if (id.get(i) < Integer.parseInt(materi.getResult().get(i).getIdMateri())) {
                    id_materi.add(id.get(i));
                    kategoriMateri.add(kategori[i]);
                    text.add("Materi " + kategori[i]);
                }
            }
        }
    }

    int getMaxIdTopikDb(Topik topik, ArrayList<String> text){
        int id;
        CrudTopik crudTopik = new CrudTopik();
        id = crudTopik.getMaxId();

        if (id < Integer.parseInt(topik.getResult().get(0).getIdTopik())) {
            text.add("Topik");
        }

        return id;
    }

    int getMaxIdRambuDb(Rambu rambu, ArrayList<String> text){
        int id;
        CrudRambu crudRambu = new CrudRambu();
        id = crudRambu.getMaxId();

        if (id < Integer.parseInt(rambu.getResult().get(0).getIdRambu())) {
            text.add("Rambu");
        }

        view.setText();
        return id;
    }

    void unduhDataTopik(int id) {
        view.showLoading("Mengunduh Data Topik");
        addSubscribe(apiStrores.updateTopik(id), new NetworkCallback<Topik>() {
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

    void unduhDataSoal(int[] id, String[] kategori) {
        view.showLoading("Mengunduh Data Soal");
        addSubscribe(apiStrores.updateSoal(id, kategori), new NetworkCallback<Soal>() {
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

    void unduhDataMateri(int[] id, String[] kategori) {
        view.showLoading("Mengunduh Data Materi");
        addSubscribe(apiStrores.updateMateri(id, kategori), new NetworkCallback<Materi>() {
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

    void unduhDataRambu(int id) {
        view.showLoading("Mengunduh Data Rambu");
        addSubscribe(apiStrores.updateRambu(id), new NetworkCallback<Rambu>() {
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
        System.out.println("ini url : "+url);
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

}
