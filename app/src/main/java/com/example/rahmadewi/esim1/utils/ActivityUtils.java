package com.example.rahmadewi.esim1.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Rahmadewi on 7/29/2017.
 */

public class ActivityUtils {

    /**
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */

    public static void addFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId){
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
