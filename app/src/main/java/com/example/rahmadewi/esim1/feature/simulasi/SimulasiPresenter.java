package com.example.rahmadewi.esim1.feature.simulasi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.database.CrudKesalahan;
import com.example.rahmadewi.esim1.database.CrudSoal;
import com.example.rahmadewi.esim1.feature.hasilSimulasi.HasilActivity;
import com.example.rahmadewi.esim1.feature.image.Image2Activity;
import com.example.rahmadewi.esim1.models.soal.ResultItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class SimulasiPresenter extends BasePresenter<SimulasiView> {

    SimulasiPresenter(SimulasiView view){
        super.attachView(view);
    }

    List<String> setDataTopik(){
        List<String> topik = new ArrayList<>();
        topik.add("Pengetahuan Undang-Undang Lalu Lintas Angkutan Jalan");
        topik.add("Tata Cara Mengemudi");
        topik.add("Teknik Kendaraan");
        topik.add("Hak dan Kewajiban Mengemudi");
        topik.add("Penyebab Kecelakaan");
        topik.add("Perilaku Berlalu Lintas");
        topik.add("Pengetahuan Tentang Rambu Lalu Lintas");
        return topik;
    }

    List<Integer> setJumlahData(){
        List<Integer> jumlah = new ArrayList<>();
        jumlah.add(5);
        jumlah.add(3);
        jumlah.add(3);
        jumlah.add(4);
        jumlah.add(3);
        jumlah.add(4);
        jumlah.add(8);
        return jumlah;
    }

    void getIdSoal(List<String> topik, String kategori, List<Integer> jumlah){
        ArrayList<Integer> id = new ArrayList<>();
        CrudSoal crudSoal = new CrudSoal();
        for(int i=0; i<topik.size(); i++) {
            crudSoal.getDataRandomSimulasi(topik.get(i), kategori, jumlah.get(i), id);
            System.out.println("ukuran id :"+id.size());
        }
        view.updateId(id);
    }

    void getSoal(int id){
        ResultItem resultItem;

        CrudSoal crudSoal = new CrudSoal();
        resultItem = crudSoal.getDataSoalSimulasi(id);

        String soal = resultItem.getSoal();
        String opsi_1 = resultItem.getOpsi1();
        String opsi_2 = resultItem.getOpsi2();
        String opsi_3 = resultItem.getOpsi3();
        String jawaban = resultItem.getJawaban();

        String fileName = resultItem.getGambar();

        String path = Environment.getExternalStorageDirectory()+ "/SIM_IMAGES/"+fileName;

        File imgFile = new File(path);
        if(imgFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            view.tampilGambar(myBitmap);
        }

        view.tampilData(soal,opsi_1,opsi_2,opsi_3,jawaban,fileName);
    }

    void moveGambar(Activity activity, String fileName){
        Intent intent = new Intent(activity, Image2Activity.class);
        intent.putExtra("fileName", fileName);
        view.moveIntent(intent);
    }

    String getTopik(int id){
        System.out.printf("ini id 1 :"+id);
        ResultItem resultItem;

        CrudSoal crudSoal = new CrudSoal();
        resultItem = crudSoal.getDataSoalSimulasi(id);

        System.out.println("ini topik : "+resultItem.getKategori());
        return resultItem.getTopik();
    }

    void setDataKesalahan(String kategori, String topik){
        CrudKesalahan crudKesalahan = new CrudKesalahan();
        crudKesalahan.selectKesalahan(kategori,topik);
    }

    void moveNilai(Activity activity, String kategori, double score, int benar, int salah, ArrayList<Integer> id_soal, boolean[] validasi){
        Intent myIntent = new Intent(activity, HasilActivity.class);
        Bundle myData = new Bundle();
        myData.putString("kategori", kategori);
        myData.putInt("score", (int) Math.round(score));
        myData.putInt("benar", benar);
        myData.putInt("salah", salah);
        myData.putIntegerArrayList("id_soal", id_soal);
        myData.putBooleanArray("validasi" ,validasi);
        myIntent.putExtras(myData);
        view.moveIntent(myIntent);
    }
}
