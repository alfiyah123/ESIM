package com.example.rahmadewi.esim1.feature.hasilSimulasi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;
import com.example.rahmadewi.esim1.check.SessionManager;
import com.example.rahmadewi.esim1.feature.main.MainActivity;
import com.example.rahmadewi.esim1.feature.pembahasan.PembahasanActivity;
import com.example.rahmadewi.esim1.models.nilai.Nilai;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class HasilActivity extends MvpActivity<HasilPresenter> implements HasilView, View.OnClickListener {

    @BindView(R.id.txtScore)
    TextView txtScore;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.txtJmlBenar)
    TextView txtJmlBenar;
    @BindView(R.id.txtJmlSalah)
    TextView txtJmlSalah;
    @BindView(R.id.bgr)
    LinearLayout a;

    @BindView(R.id.material_design_android_floating_action_menu)
    FloatingActionMenu materialDesignFAM;
    @BindView(R.id.btnHome)
    FloatingActionButton btnHome;
    @BindView(R.id.btnPembahasan)
    FloatingActionButton btnPembahasan;
    @BindView(R.id.btnDashboard)
    FloatingActionButton btnDashboard;
    @BindView(R.id.btnFacebook)
    FloatingActionButton btnFacebook;

    int score, benar, salah;
    String kategori;

    boolean[] validasi = new boolean[30];
    ArrayList<Integer> id_soal = new ArrayList<>();

    ProgressDialog pDialog;

    SessionManager session;

    @Override
    protected HasilPresenter cretePresenter() {
        return new HasilPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        session = new SessionManager(getApplicationContext());

        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();
        score = myBundle.getInt("score");
        benar = myBundle.getInt("benar");
        salah = myBundle.getInt("salah");
        id_soal = myBundle.getIntegerArrayList("id_soal");
        validasi = myBundle.getBooleanArray("validasi");
        kategori = myBundle.getString("kategori");

        if(benar > 21){
            txtStatus.setText("LULUS");
        }else{
            txtStatus.setText("TIDAK LULUS");
        }

        presenter.simpanNilai(score, kategori);

        txtScore.setText("" + score);
        txtJmlBenar.setText("Jawaban Benar : " + benar);
        txtJmlSalah.setText("Jawaban Salah : " + salah);

        final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Bitmap screenshot = presenter.getScreenShot(rootView);
        presenter.store(screenshot, "screen.png", getApplicationContext());

        btnHome.setOnClickListener(this);
        btnPembahasan.setOnClickListener(this);
        btnDashboard.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);

        materialDesignFAM.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if(opened) {
                    a.setAlpha(0.20f);
                    opened = false;
                }else{
                    a.setAlpha(1.0f);
                    opened = true;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHome:
                presenter.moveIntent(HasilActivity.this, MainActivity.class, null, null);
                break;
            case R.id.btnPembahasan:
                presenter.moveIntent(HasilActivity.this, PembahasanActivity.class, id_soal, validasi);
                break;
            case R.id.btnDashboard:
                HashMap<String, String> userData = session.getUserDetails();
                String username = userData.get(SessionManager.KEY_USERNAME);
                presenter.shareDashboard(username, score, kategori);
                break;
            case R.id.btnFacebook:
                presenter.shareImage();
                break;
            default:
                break;
        }
    }

    @Override
    public void moveToIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void shareEror(String pesan) {
        Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        pDialog = new ProgressDialog(HasilActivity.this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please wait ...");
        pDialog.show();
    }

    @Override
    public void hideLoading() {
        pDialog.dismiss();
    }

    @Override
    public void getDataSuccess(Nilai nilai) {
        if(nilai.getCode() == 200){
            buildDialog("Nilai berhasil dikirim ke dashboard", "Berhasil");
        }else{
            buildDialog("Nilai tidak terkirim ke dashboard", "Gagal");
        }
    }

    @Override
    public void getDataFail(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void buildDialog(String pesan, String judul){
        new AlertDialog.Builder(HasilActivity.this)
                .setTitle(judul)
                .setMessage(pesan)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
