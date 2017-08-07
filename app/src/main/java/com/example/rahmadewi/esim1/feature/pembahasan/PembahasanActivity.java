package com.example.rahmadewi.esim1.feature.pembahasan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class PembahasanActivity extends MvpActivity<PembahasanPresenter> implements PembahasanView {

    TextView txtSoalPembahasan;
    TextView txtJawabanPembahasan;
    ImageView gbrPembahasan;
    LinearLayout bgPembahasan;

    @BindView(R.id.scrPembahasan)
    LinearLayout scrPembahasan;

    ArrayList<Integer> id_soal = new ArrayList<>();
    boolean[] validasi = new boolean[30];

    @Override
    protected PembahasanPresenter cretePresenter() {
        return new PembahasanPresenter(this);
    }

    int no=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembahasan);

        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();
        id_soal = myBundle.getIntegerArrayList("id_soal");
        validasi = myBundle.getBooleanArray("validasi");

        if(id_soal != null) {
            for (int i = 0; i < id_soal.size(); i++) {
                scrPembahasan.addView(getData(id_soal.get(i), validasi[i]));
            }
        }
    }

    @Override
    public void setDataLayout(String soal, String jawaban) {
        txtSoalPembahasan.setText(no+". "+soal);
        txtJawabanPembahasan.setText("JAWABAN \n : "+jawaban);
        no++;
    }

    @Override
    public void setImg(Bitmap bitmap) {
        gbrPembahasan.setImageBitmap(bitmap);
    }

    @Override
    public void pembahsanEror() {
        bgPembahasan.setBackgroundColor(Color.rgb(255,204,204));
    }

    private View getData(int id_soal, boolean validasi){
        View v = getLayoutInflater().inflate(R.layout.content_pembahasan, null);

        txtSoalPembahasan= (TextView)v.findViewById(R.id.txtSoalPembahasan);
        txtJawabanPembahasan= (TextView)v.findViewById(R.id.txtJawabanPembahasan);
        gbrPembahasan= (ImageView)v.findViewById(R.id.gbrPembahasan);
        bgPembahasan = (LinearLayout)v.findViewById(R.id.bgPembahasan);

        presenter.getData(id_soal, validasi);

        return v;
    }
}
