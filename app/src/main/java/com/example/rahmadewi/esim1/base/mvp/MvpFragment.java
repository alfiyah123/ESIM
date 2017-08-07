package com.example.rahmadewi.esim1.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.rahmadewi.esim1.base.ui.BaseFragment;
import com.example.rahmadewi.esim1.base.ui.BasePresenter;

/**
 * Created by Rahmadewi on 7/29/2017.
 */

public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P presenter;
    protected abstract P createPresenter();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        presenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter != null){
            presenter.dettachView();
        }
    }
}
