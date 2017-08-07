package com.example.rahmadewi.esim1.feature.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.models.kategori.Kategori;
import com.example.rahmadewi.esim1.models.materi.Materi;
import com.example.rahmadewi.esim1.models.rambu.Rambu;
import com.example.rahmadewi.esim1.models.soal.Soal;
import com.example.rahmadewi.esim1.models.topik.Topik;
import com.example.rahmadewi.esim1.network.NetworkCallback;

/**
 * Created by Rahmadewi on 7/30/2017.
 */

public class MainPresenter extends BasePresenter<MainView>{
    MainPresenter(MainView view){
        super.attachView(view);
    }

    public String setData(){
        return "lalala";
    }

}
