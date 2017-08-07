package com.example.rahmadewi.esim1.feature.register;

import android.content.Intent;

import com.example.rahmadewi.esim1.models.user.User;

interface RegisterView {
    void showLoading();
    void hideLoading();
    void getDataSuccessUser(User user);
    void getDataFail(String message);
    void moveToHome(Intent intent);
}
