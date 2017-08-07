package com.example.rahmadewi.esim1.feature.mainFragment;

import android.content.Intent;

import com.example.rahmadewi.esim1.models.gambar.Gambar;
import com.example.rahmadewi.esim1.models.kategori.Kategori;
import com.example.rahmadewi.esim1.models.materi.Materi;
import com.example.rahmadewi.esim1.models.rambu.Rambu;
import com.example.rahmadewi.esim1.models.soal.Soal;
import com.example.rahmadewi.esim1.models.topik.Topik;

import java.util.List;

/**
 * Created by Rahmadewi on 7/30/2017.
 */

interface MainViewFragment {
    void showLoading(String pesan);
    void hideLoading();
    void getDataSuccessKategori(Kategori kategori);
    void getDataSuccessTopik(Topik topik);
    void getDataSuccessSoal(Soal soal);
    void getDataSuccessMateri(Materi materi);
    void getDataSuccessRambu(Rambu rambu);
    void getDataSuccessGambar(Gambar gambar);
    void getDataFail(String message);
    String[] listToArray(List<String> data);
    void moveToMenu(Intent intent);
    void showLoadingImage();
    void updateProgressImage(String message);
    void hideLoadingImage();
    void simpanGambar(String fileName, String url);
}
