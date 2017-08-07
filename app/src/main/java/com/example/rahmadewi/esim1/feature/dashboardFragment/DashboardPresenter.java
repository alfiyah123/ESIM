package com.example.rahmadewi.esim1.feature.dashboardFragment;

import android.widget.Toast;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.models.nilai.Nilai;
import com.example.rahmadewi.esim1.network.NetworkCallback;

import java.util.ArrayList;

class DashboardPresenter extends BasePresenter<DashboardView> {
    DashboardPresenter(DashboardView view) {
        super.attachView(view);
    }

    void getDataNilai(String kategori) {
        System.out.println("memulai unduh nilai");
        view.onShowLoading();
        addSubscribe(apiStrores.getNilai(kategori), new NetworkCallback<Nilai>() {
            @Override
            public void onSuccess(Nilai model) {
                getSukses(model);
            }
            @Override
            public void onFailure(String message) {
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    private void getSukses(Nilai data) {
        view.hapusView();

        ArrayList<String> user = new ArrayList<>();
        ArrayList<Float> nilai = new ArrayList<>();
        ArrayList<String> userUnik = new ArrayList<>();

        if (data.getCode() == 200) {
            for (int i = 0; i < data.getResult().size(); i++) {
                user.add(data.getResult().get(i).getUsername());
                if (!userUnik.contains(data.getResult().get(i).getUsername())) {
                    userUnik.add(data.getResult().get(i).getUsername());
                }
                nilai.add(Float.parseFloat(data.getResult().get(i).getNilai()));
            }
            view.layoutGrafik(user, nilai, userUnik);
        } else {
            view.getDataFail("No Data Found");
        }
    }
}
