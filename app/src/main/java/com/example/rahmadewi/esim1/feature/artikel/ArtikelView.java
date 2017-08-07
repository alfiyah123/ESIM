package com.example.rahmadewi.esim1.feature.artikel;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by Rahmadewi on 8/1/2017.
 */

interface ArtikelView {
    void parseH(String data);
    void parseP(String data);
    void parseOl(String data);
    void parseImg(Bitmap bitmap, String nama);
    void parseUl(String data);
    void moveIntent(Intent intent);
    void setTexView(String data);
}
