package com.example.rahmadewi.esim1.feature.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;
import com.example.rahmadewi.esim1.feature.grafik.GrafikActivity;
import com.example.rahmadewi.esim1.feature.latihan_soal.LatihanActivity;
import com.example.rahmadewi.esim1.feature.materi.MateriActivity;
import com.example.rahmadewi.esim1.feature.simulasi.SimulasiActivity;

import butterknife.BindView;

public class MenuActivity extends MvpActivity<MenuPresenter> implements MenuView, View.OnClickListener {

    @BindView(R.id.btnLatihanSoal)
    Button btnLatihanSoal;

    @BindView(R.id.btnMateri)
    Button btnMateri;

    @BindView(R.id.btnSimulasi)
    Button btnSimulasi;

    @BindView(R.id.btnChart)
    Button btnChart;

    private String kategori;

    @Override
    protected MenuPresenter cretePresenter() {
        return new MenuPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        kategori = getIntent().getStringExtra("kategori");

        setTitle("Menu "+kategori);

        btnLatihanSoal.setOnClickListener(this);
        btnMateri.setOnClickListener(this);
        btnSimulasi.setOnClickListener(this);
        btnChart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLatihanSoal:
                presenter.pindahIntent(MenuActivity.this, LatihanActivity.class, kategori);
                break;
            case R.id.btnMateri:
                presenter.pindahIntent(MenuActivity.this, MateriActivity.class, kategori);
                break;
            case R.id.btnSimulasi:
                presenter.pindahIntent(MenuActivity.this, SimulasiActivity.class, kategori);
                break;
            case R.id.btnChart:
                presenter.pindahIntent(MenuActivity.this, GrafikActivity.class, kategori);
                break;
            default:
                break;
        }
    }

    @Override
    public void moveToIntent(Intent intent) {
        startActivity(intent);
    }
}
