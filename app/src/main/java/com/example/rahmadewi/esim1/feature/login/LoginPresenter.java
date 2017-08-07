package com.example.rahmadewi.esim1.feature.login;

import android.app.Activity;
import android.content.Intent;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.models.user.User;
import com.example.rahmadewi.esim1.network.NetworkCallback;

class LoginPresenter extends BasePresenter<LoginView> {
    LoginPresenter(LoginView view){
        super.attachView(view);
    }

    void login(String username, String password){
        System.out.println("memulai login data");
        view.showLoading();
        addSubscribe(apiStrores.login(username, password), new NetworkCallback<User>() {
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
