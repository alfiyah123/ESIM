package com.example.rahmadewi.esim1.feature.hasilSimulasi;

import android.content.Intent;

import com.example.rahmadewi.esim1.models.nilai.Nilai;

interface HasilView {
    void moveToIntent(Intent intent);
    void shareEror(String pesan);
    void showLoading();
    void hideLoading();
    void getDataSuccess(Nilai nilai);
    void getDataFail(String message);
}
