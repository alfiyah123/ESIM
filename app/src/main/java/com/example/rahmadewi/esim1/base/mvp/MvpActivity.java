package com.example.rahmadewi.esim1.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.ui.BaseActivity;
import com.example.rahmadewi.esim1.base.ui.BasePresenter;

/**
 * Created by Rahmadewi on 7/29/2017.
 */

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P presenter;
    protected abstract P cretePresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        presenter = cretePresenter();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(presenter != null){
            presenter.dettachView();
        }
    }
}
