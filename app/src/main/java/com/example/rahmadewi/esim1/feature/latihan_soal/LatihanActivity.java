package com.example.rahmadewi.esim1.feature.latihan_soal;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;

import butterknife.BindView;

public class LatihanActivity extends MvpActivity<LatihanPresenter> implements LatihanView, View.OnClickListener {

    String jawaban, kategori, fileName, jawaban_user;

    @BindView(R.id.txtSoalLatihan)
    TextView txtSoalLatihan;

    @BindView(R.id.gbrLatihan)
    ImageView gbrLatihan;

    @BindView(R.id.opsi_1_latihan)
    RadioButton opsi_1_latihan;

    @BindView(R.id.opsi_2_latihan)
    RadioButton opsi_2_latihan;

    @BindView(R.id.opsi_3_latihan)
    RadioButton opsi_3_latihan;

    @BindView(R.id.opsiLatihan)
    RadioGroup opsi_latihan;

    @BindView(R.id.btnCekLatihan)
    Button btnCekLatihan;

    @BindView(R.id.btnNextLatihan)
    Button btnNextLatihan;

    ImageView emoji;
    TextView txtCekLatihan;
    RadioButton jawaban_latihan;
    Button dialogButton;

    @Override
    protected LatihanPresenter cretePresenter() {
        return new LatihanPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihan);

        kategori = getIntent().getStringExtra("kategori");
        presenter.getSoal(kategori);

        btnCekLatihan.setOnClickListener(this);
        btnNextLatihan.setOnClickListener(this);
        gbrLatihan.setOnClickListener(this);

        opsi_latihan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = opsi_latihan.getCheckedRadioButtonId();
                if(id != 0){
                    btnCekLatihan.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCekLatihan:
                cek_dialog().show();
                break;
            case R.id.btnNextLatihan:
                soalNext();
                break;
            case R.id.gbrLatihan:
                presenter.moveGambar(LatihanActivity.this, fileName);
                break;
            default:
                break;
        }
    }

    @Override
    public void tampilData(String soal, String opsi_1, String opsi_2, String opsi_3, String jawaban, String fileName) {
        txtSoalLatihan.setText(soal);
        opsi_1_latihan.setText(opsi_1);
        opsi_2_latihan.setText(opsi_2);
        opsi_3_latihan.setText(opsi_3);
        this.jawaban = jawaban;
        this.fileName = fileName;
    }

    @Override
    public void tampilGambar(Bitmap bitmap) {
        gbrLatihan.setImageBitmap(bitmap);
    }

    @Override
    public void soalNext() {
        opsi_latihan.clearCheck();
        btnCekLatihan.setEnabled(false);
        presenter.getSoal(kategori);
    }

    @Override
    public void cekJawaban(int gambar, String pesan) {
        emoji.setImageResource(gambar);
        txtCekLatihan.setText(pesan);
    }

    @Override
    public void moveIntent(Intent intent) {
        startActivity(intent);
    }

    private Dialog cek_dialog(){
        final Dialog dialog = new Dialog(LatihanActivity.this);
        dialog.setContentView(R.layout.cek_dialog);
        dialog.setTitle("Cek Jawaban");

        int selectedId = opsi_latihan.getCheckedRadioButtonId();

        emoji = (ImageView) dialog.findViewById(R.id.gbrCekLatihan);
        txtCekLatihan = (TextView) dialog.findViewById(R.id.txtCekLatihan);
        jawaban_latihan = (RadioButton) findViewById(selectedId);
        dialogButton = (Button) dialog.findViewById(R.id.btnOkCekLatihan);

        jawaban_user = jawaban_latihan.getText().toString();

        if(jawaban_user.equals(jawaban)){
            cekJawaban(R.drawable.benar,"Jawaban Anda Benar");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    soalNext();
                }
            });
        }else{
            cekJawaban(R.drawable.salah,"Jawaban Anda Salah");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        return dialog;
    }

}
