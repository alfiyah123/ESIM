package com.example.rahmadewi.esim1.base.ui;

import android.util.Log;

import com.example.rahmadewi.esim1.network.NetworkClient;
import com.example.rahmadewi.esim1.network.NetworkStores;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Rahmadewi on 7/29/2017.
 */

public class BasePresenter<V> {
    public V view;
    protected NetworkStores apiStrores;
    private CompositeSubscription compositeSubscription;
    private Subscriber subscriber;

    public void attachView(V view){
        this.view = view;
        apiStrores = NetworkClient.getRetrofit().create(NetworkStores.class);
    }

    public  void dettachView(){
        this.view = null;
        onUnsubscribe();
    }

    public void onUnsubscribe(){
        if(compositeSubscription != null && compositeSubscription.hasSubscriptions()){
            compositeSubscription.unsubscribe();
            Log.e("TAG", "onUnsubscribe");
        }
    }

    protected void addSubscribe(Observable observable, Subscriber subscriber){
        this.subscriber = subscriber;
        if(compositeSubscription == null){
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(observable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(subscriber));
    }

    public void stop(){
        if(subscriber != null){
            subscriber.unsubscribe();
        }
    }
}
