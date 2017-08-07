package com.example.rahmadewi.esim1.feature.simulasi;

import android.content.Intent;
import android.graphics.Bitmap;

import java.util.ArrayList;

interface SimulasiView {
    void updateId(ArrayList<Integer> id_soal);
    void tampilData(String soal, String opsi_1, String opsi_2, String opsi_3, String jawaban, String fileName);
    void tampilGambar(Bitmap bitmap);
    void moveIntent(Intent intent);
    void waktu();
    void hitungNilai();
}

