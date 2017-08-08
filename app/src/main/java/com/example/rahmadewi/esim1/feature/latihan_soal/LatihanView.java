package com.example.rahmadewi.esim1.feature.latihan_soal;

import android.content.Intent;
import android.graphics.Bitmap;

interface LatihanView {
    void tampilData(String soal, String opsi_1, String opsi_2, String opsi_3, String jawaban, String fileName, String id_soal);
    void tampilGambar(Bitmap bitmap);
    void soalNext();
    void cekJawaban(int gambar, String pesan);
    void moveIntent(Intent intent);
}
