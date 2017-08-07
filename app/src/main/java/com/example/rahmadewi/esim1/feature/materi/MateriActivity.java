package com.example.rahmadewi.esim1.feature.materi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.adapter.MateriAdapter;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;
import com.example.rahmadewi.esim1.feature.artikel.ArtikelActivity;
import com.example.rahmadewi.esim1.feature.rambu.RambuActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MateriActivity extends MvpActivity<MateriPresenter> implements MateriView {

    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;

    String kategori;

    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected MateriPresenter cretePresenter() {
        return new MateriPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);

        kategori = getIntent().getStringExtra("kategori");

        expandableListDetail = presenter.getData(kategori);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new MateriAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String items = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                if(expandableListTitle.get(groupPosition).equals("Rambu Lalu Lintas")){
                    presenter.pindahIntent(MateriActivity.this, RambuActivity.class, items);
                }else{
                    presenter.pindahIntent(MateriActivity.this, ArtikelActivity.class, items);
                }
                return false;
            }
        });
    }

    @Override
    public void moveIntent(Intent intent) {
        startActivity(intent);
    }
}
