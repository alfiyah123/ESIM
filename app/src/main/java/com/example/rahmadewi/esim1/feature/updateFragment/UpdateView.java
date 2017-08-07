package com.example.rahmadewi.esim1.feature.updateFragment;

import com.example.rahmadewi.esim1.models.gambar.Gambar;
import com.example.rahmadewi.esim1.models.materi.Materi;
import com.example.rahmadewi.esim1.models.rambu.Rambu;
import com.example.rahmadewi.esim1.models.soal.Soal;
import com.example.rahmadewi.esim1.models.topik.Topik;

import java.util.ArrayList;

interface UpdateView {
    void showLoading(String pesan);
    void hideLoading();
    void showLoadingCek();
    void getDataFail(String message);
    void getIdSuksesSoal(Soal soal);
    void getIdSuksesMateri(Materi materi);
    void getIdSuksesTopik(Topik topik);
    void getIdSuksesRambu(Rambu rambu);
    void setText();
    void hideLoadingCek();
    void getDataSuccessTopik(Topik topik);
    void getDataSuccessSoal(Soal soal);
    void getDataSuccessMateri(Materi materi);
    void getDataSuccessRambu(Rambu rambu);
    void getDataSuccessGambar(Gambar gambar);
    void showLoadingImage();
    void updateProgressImage(String message);
    void hideLoadingImage();
    void simpanGambar(String fileName, String url);
}
