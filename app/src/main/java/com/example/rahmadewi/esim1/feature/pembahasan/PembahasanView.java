package com.example.rahmadewi.esim1.feature.pembahasan;

import android.graphics.Bitmap;

interface PembahasanView {
    void setDataLayout(String soal, String jawaban);
    void setImg(Bitmap bitmap);
}
