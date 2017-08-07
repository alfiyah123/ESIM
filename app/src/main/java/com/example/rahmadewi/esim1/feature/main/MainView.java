package com.example.rahmadewi.esim1.feature.main;

import com.example.rahmadewi.esim1.models.kategori.Kategori;
import com.example.rahmadewi.esim1.models.materi.Materi;
import com.example.rahmadewi.esim1.models.rambu.Rambu;
import com.example.rahmadewi.esim1.models.soal.Soal;
import com.example.rahmadewi.esim1.models.topik.Topik;

/**
 * Created by Rahmadewi on 7/30/2017.
 */

interface MainView{
    void moveFragmentMain();
    void moveFragmentDashboard();
    void moveFragmentUpdate();
}
