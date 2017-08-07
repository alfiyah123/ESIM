package com.example.rahmadewi.esim1.feature.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.ui.BasePresenter;

import java.io.File;

class ImagePresenter extends BasePresenter<ImageView> {
    ImagePresenter(ImageView view){
        super.attachView(view);
    }

    void getGambar(String fileName){
        String path = Environment.getExternalStorageDirectory()+ "/SIM_IMAGES/"+fileName;

        File imgFile = new File(path);
        if(imgFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            view.loadGambar(myBitmap);
        }else{
            view.loadGambarEror(R.drawable.back);
        }
    }
}
