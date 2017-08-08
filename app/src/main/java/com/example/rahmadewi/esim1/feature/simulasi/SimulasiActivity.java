package com.example.rahmadewi.esim1.feature.simulasi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class SimulasiActivity extends MvpActivity<SimulasiPresenter> implements SimulasiView, View.OnClickListener {

    @BindView(R.id.txtWaktu)
    TextView txtWaktu;
    @BindView(R.id.txtSoalSimulasi)
    TextView txtSoalSimulasi;
    @BindView(R.id.txtJmlSoal)
    TextView txtJmlSoal;
    @BindView(R.id.gbrSimulasi)
    ImageView gbrSimulasi;
    @BindView(R.id.opsi_1_simulasi)
    RadioButton opsi_1_simulasi;
    @BindView(R.id.opsi_2_simulasi)
    RadioButton opsi_2_simulasi;
    @BindView(R.id.opsi_3_simulasi)
    RadioButton opsi_3_simulasi;
    @BindView(R.id.opsiSimulasi)
    RadioGroup opsi_simulasi;
    @BindView(R.id.btnNextSimulasi)
    Button btnNextSimulasi;

    List<String> jawaban = new ArrayList<>();
    List<String> jawabanUser = new ArrayList<>();
    ArrayList<Integer> id_soal = new ArrayList<>();
    List<Integer> jumlah;
    List<String> topik;

    boolean[] validasi = new boolean[30];

    int no=1;
    int jmlSoal = 30;

    String fileName, kategori;

    CountDownTimer countDownTimer;          // built in android class CountDownTimer
    long totalTimeCountInMilliseconds;      // total count down time in milliseconds
    long timeBlinkInMilliseconds;           // start time of start blinking
    boolean blink;

    @Override
    protected SimulasiPresenter cretePresenter() {
        return new SimulasiPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulasi);

        kategori = getIntent().getStringExtra("kategori");

        totalTimeCountInMilliseconds = 900 * 1000;      // time count for 3 minutes = 180 seconds
        timeBlinkInMilliseconds = 60 * 1000;
        //totalTimeCountInMilliseconds = 180 * 1000;

        topik = presenter.setDataTopik();
        jumlah = presenter.setJumlahData();

        presenter.getIdSoal(topik,kategori,jumlah);
        presenter.getSoal(id_soal.get(0));
        waktu();

        btnNextSimulasi.setOnClickListener(this);
        gbrSimulasi.setOnClickListener(this);

        opsi_simulasi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = opsi_simulasi.getCheckedRadioButtonId();
                if(id != 0){
                    btnNextSimulasi.setEnabled(true);
                }
            }
        });

    }

    @Override
    public void updateId(ArrayList<Integer> id_soal) {
        this.id_soal = id_soal;
        System.out.println("data id soal : "+ Arrays.asList(this.id_soal));
    }

    @Override
    public void tampilData(String soal, String opsi_1, String opsi_2, String opsi_3, String jawaban, String fileName) {
        txtSoalSimulasi.setText(soal);
        opsi_1_simulasi.setText(opsi_1);
        opsi_2_simulasi.setText(opsi_2);
        opsi_3_simulasi.setText(opsi_3);
        this.jawaban.add(jawaban);
        this.fileName = fileName;
    }

    @Override
    public void tampilGambar(Bitmap bitmap) {
        gbrSimulasi.setImageBitmap(bitmap);
    }

    @Override
    public void moveIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gbrSimulasi:
                presenter.moveGambar(SimulasiActivity.this, fileName);
                break;
            case R.id.btnNextSimulasi:
                nextSoal();
                break;
            default:
                break;
        }
    }

    private void nextSoal(){
        int selectedId = opsi_simulasi.getCheckedRadioButtonId();
        RadioButton jawaban_simulasi = (RadioButton) findViewById(selectedId);
        jawabanUser.add(jawaban_simulasi.getText().toString());

        if(no != id_soal.size()){
            presenter.getSoal(id_soal.get(no));
            if(no == (id_soal.size()-1)){
                btnNextSimulasi.setText("Selesai");
            }
        }else{
            for(int l=0; l<jawabanUser.size(); l++){
                System.out.println("Kunci : "+jawaban.get(l)+" | Jawaban User : "+jawabanUser.get(l));
            }
            countDownTimer.cancel();
            hitungNilai();
        }

        no++;
        opsi_simulasi.clearCheck();
        btnNextSimulasi.setEnabled(false);
        txtJmlSoal.setText("Soal " + no + " dari " + jmlSoal);
    }

    @Override
    public void waktu(){
        txtWaktu.setTextAppearance(getApplicationContext(), R.style.normalText);
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

                if ( leftTimeInMilliseconds < timeBlinkInMilliseconds ) {
                    txtWaktu.setTextAppearance(getApplicationContext(), R.style.blinkText);
                    if ( blink ) {
                        txtWaktu.setVisibility(View.VISIBLE);
                    } else {
                        txtWaktu.setVisibility(View.INVISIBLE);
                    }
                    blink = !blink;
                }
                txtWaktu.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
            }
            @Override
            public void onFinish() {
                hitungNilai();
            }
        }.start();
    }

    @Override
    public void hitungNilai() {
        double score;
        int benar = 0;
        int salah;
        for(int i=0; i<jawabanUser.size(); i++) {
            if (jawabanUser.get(i).equals(jawaban.get(i))) {
                benar = benar + 1;
                validasi[i] = true;
            } else {
                validasi[i] = false;
                System.out.println("ini id soal :"+id_soal.get(i));
                String topik = presenter.getTopik(id_soal.get(i));
                presenter.setDataKesalahan(kategori, topik);
            }
        }
        salah = jmlSoal - benar;
        score = benar * 3.33;

        System.out.println("benar :"+benar);
        System.out.println("salah :"+salah);
        System.out.println("nilai :"+score);

        presenter.moveNilai(SimulasiActivity.this, kategori, score, benar, salah, id_soal, validasi);
        finish();
    }
}
