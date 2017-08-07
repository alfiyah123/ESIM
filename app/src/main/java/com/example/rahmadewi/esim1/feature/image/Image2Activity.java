package com.example.rahmadewi.esim1.feature.image;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.rahmadewi.esim1.base.mvp.MvpActivity;

/**
 * Created by Rahmadewi on 8/1/2017.
 */

public class Image2Activity extends MvpActivity<ImagePresenter> implements ImageView {

    TouchImageView img;

    @Override
    protected ImagePresenter cretePresenter() {
        return new ImagePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        img = new TouchImageView(this);

        String fileName = getIntent().getStringExtra("fileName");
        presenter.getGambar(fileName);

        img.setMaxZoom(4f);
        setContentView(img);
    }

    @Override
    public void loadGambar(Bitmap bitmap) {
        img.setImageBitmap(bitmap);
    }

    @Override
    public void loadGambarEror(int id) {
        img.setImageResource(id);
    }
}
