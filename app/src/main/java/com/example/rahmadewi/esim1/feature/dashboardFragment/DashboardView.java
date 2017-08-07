package com.example.rahmadewi.esim1.feature.dashboardFragment;

import com.example.rahmadewi.esim1.models.nilai.Nilai;

import java.util.ArrayList;

interface DashboardView {
    void onShowLoading();
    void hideLoading();
    void getDataFail(String message);
    void layoutGrafik(ArrayList<String> user, ArrayList<Float> nilai, ArrayList<String> userUnik);
    void hapusView();
}
