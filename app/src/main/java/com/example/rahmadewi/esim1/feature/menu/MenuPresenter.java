package com.example.rahmadewi.esim1.feature.menu;

import android.app.Activity;
import android.content.Intent;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;

/**
 * Created by Rahmadewi on 7/31/2017.
 */

public class MenuPresenter extends BasePresenter<MenuView> {
    MenuPresenter(MenuView view){
        super.attachView(view);
    }

    void pindahIntent(Activity activity, Class class1, String kategori){
        Intent intent = new Intent(activity, class1);
        intent.putExtra("kategori", kategori);
        view.moveToIntent(intent);
    }
}
