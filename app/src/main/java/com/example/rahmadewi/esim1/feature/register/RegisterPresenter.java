package com.example.rahmadewi.esim1.feature.register;

import android.app.Activity;
import android.content.Intent;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.models.user.User;
import com.example.rahmadewi.esim1.network.NetworkCallback;

class RegisterPresenter extends BasePresenter<RegisterView> {
    RegisterPresenter(RegisterView view){
        super.attachView(view);
    }

    void register(String username, String password, String nomor){
        System.out.println("memulai register data");
        view.showLoading();
        addSubscribe(apiStrores.register(username, password, nomor), new NetworkCallback<User>() {
            @Override
            public void onSuccess(User model) {
                view.getDataSuccessUser(model);
            }

            @Override
            public void onFailure(String message) {
                view.hideLoading();
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    void moveHome(Activity activity, Class tujuan){
        Intent intent = new Intent(activity, tujuan);
        view.moveToHome(intent);
    }
}
