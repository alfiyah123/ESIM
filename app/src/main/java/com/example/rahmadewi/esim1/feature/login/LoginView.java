package com.example.rahmadewi.esim1.feature.login;

import android.content.Intent;

import com.example.rahmadewi.esim1.models.user.User;

interface LoginView {
    void showLoading();
    void hideLoading();
    void getDataSuccessUser(User user);
    void getDataFail(String message);
    void moveToHome(Intent intent);
}
