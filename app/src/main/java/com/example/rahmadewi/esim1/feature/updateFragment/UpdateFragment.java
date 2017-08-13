package com.example.rahmadewi.esim1.feature.updateFragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpFragment;
import com.example.rahmadewi.esim1.models.gambar.Gambar;
import com.example.rahmadewi.esim1.models.materi.Materi;
import com.example.rahmadewi.esim1.models.rambu.Rambu;
import com.example.rahmadewi.esim1.models.soal.Soal;
import com.example.rahmadewi.esim1.models.topik.Topik;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateFragment extends MvpFragment<UpdatePresenter> implements UpdateView, View.OnClickListener {

    ProgressDialog pDialogKategori;
    ProgressDialog update;
    ProgressDialog pDialogImage;

    LinearLayout layout;
    Button btnUpdate;
    ImageView gbrCoba;

    String[] kategori = {"SIM A", "SIM B1", "SIM B2", "SIM C"};

    ArrayList<String> text = new ArrayList<>();

    int id_topik, id_rambu;
    ArrayList<Integer> id_soal = new ArrayList<>();
    ArrayList<Integer> id_materi = new ArrayList<>();
    ArrayList<String> kategoriSoal = new ArrayList<>();
    ArrayList<String> kategoriMateri = new ArrayList<>();

    int[] id_soal_array, id_materi_array;
    String[] kategori_soal_array, kategori_materi_array;

    ArrayList<String> id = new ArrayList<>();
    List<String> gambar = new ArrayList<>();

    //String url = "http://192.168.1.101/esim1/";
    String url = "http://esimsurabaya.tk/";
    int i=0;

    private AQuery aq;

    public static Fragment newInstance(){
        return new UpdateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_update, container, false);
        onViewCreated(x,savedInstanceState);

        aq = new AQuery(getActivity(), x);

        layout = (LinearLayout) x.findViewById(R.id.bgDashboardUpdate);
        btnUpdate = (Button) x.findViewById(R.id.btnUpdate);
        gbrCoba = (ImageView) x.findViewById(R.id.gbrCoba1);

        presenter.getMaxIdTopikServer();

        btnUpdate.setOnClickListener(this);

        return x;
    }

    @Override
    protected UpdatePresenter createPresenter() {
        return new UpdatePresenter(this);
    }

    @Override
    public void showLoading(String pesan) {
        update.setMessage(pesan);
    }

    @Override
    public void hideLoading() {
        update.hide();
    }

    @Override
    public void showLoadingCek() {
        pDialogKategori = new ProgressDialog(getActivity());
        pDialogKategori.setIndeterminate(false);
        pDialogKategori.setCancelable(false);
        pDialogKategori.setMessage("Cek Update");
        pDialogKategori.show();
    }

    @Override
    public void getDataFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getIdSuksesSoal(Soal soal) {
        presenter.getMaxIdSoalDb(kategori, soal, text, id_soal, kategoriSoal);
        presenter.getMaxIdMateriServer(kategori);
    }

    @Override
    public void getIdSuksesMateri(Materi materi) {
        presenter.getMaxIdMateriDb(kategori,materi,text, id_materi, kategoriMateri);
        presenter.getMaxIdRambuServer();
    }

    @Override
    public void getIdSuksesTopik(Topik topik) {
        id_topik = presenter.getMaxIdTopikDb(topik, text);
        presenter.getMaxIdSoalServer(kategori);
    }

    @Override
    public void getIdSuksesRambu(Rambu rambu) {
        id_rambu = presenter.getMaxIdRambuDb(rambu, text);
    }

    @Override
    public void setText() {
        if(text.size() != 0) {
            for (int i = 0; i < text.size(); i++) {
                TextView a = new TextView(getActivity());
                a.setText(text.get(i));
                layout.addView(a);
            }
        }else{
            TextView a = new TextView(getActivity());
            a.setText("No data update available");
            layout.addView(a);
        }
    }

    @Override
    public void hideLoadingCek() {
        pDialogKategori.hide();
    }

    @Override
    public void getDataSuccessTopik(Topik topik) {
        if(topik.getCode() == 200){
            for(int i=0; i<topik.getResult().size();i++){
                presenter.simpanDataTopik(topik.getResult().get(i));
            }
        }
        kategori_soal_array = listToArrayString(kategoriSoal);
        id_soal_array = listToArrayInt(id_soal);
        presenter.unduhDataSoal(id_soal_array, kategori_soal_array);
    }

    @Override
    public void getDataSuccessSoal(Soal soal) {
        if(soal.getCode() == 200){
            for(int i=0; i<soal.getResult().size();i++){
                if(!gambar.contains(url+"uploads/soal/"+soal.getResult().get(i).getGambar())) {
                    gambar.add(url + "uploads/soal/" + soal.getResult().get(i).getGambar());
                }
                //System.out.println("baru soal : "+soal.getResult().get(i).getSoal());
                presenter.simpanDataSoal(soal.getResult().get(i));
            }
        }
        kategori_materi_array = listToArrayString(kategoriMateri);
        id_materi_array = listToArrayInt(id_materi);
        presenter.unduhDataMateri(id_materi_array, kategori_materi_array);
    }

    @Override
    public void getDataSuccessMateri(Materi materi) {
        if(materi.getCode() == 200){
            for(int i=0; i<materi.getResult().size();i++){
                id.add(materi.getResult().get(i).getIdMateri());
                System.out.println("id_materi : "+id.get(i));
                presenter.simpanDataMateri(materi.getResult().get(i));
            }
        }
        String[] idArray = listToArrayString(id);
        presenter.unduhDataGambar(idArray);
    }

    @Override
    public void getDataSuccessRambu(Rambu rambu) {
        if(rambu.getCode() == 200){
            for(int i=0; i<rambu.getResult().size();i++){
                gambar.add(url+"uploads/rambu/"+rambu.getResult().get(i).getGambar());
                presenter.simpanDataRambu(rambu.getResult().get(i));
            }
        }
        showLoadingImage();
        presenter.unduhGambar(gambar.get(0));
    }

    @Override
    public void getDataSuccessGambar(Gambar gambar) {
        System.out.println("unduh sukses");
        if(gambar.getCode() == 200){
            for(int i=0; i<gambar.getResult().size();i++){
                this.gambar.add(url+"source/"+gambar.getResult().get(i).getNamaGambar());
                presenter.simpanDataGambar(gambar.getResult().get(i));
            }
        }
        presenter.unduhDataRambu(id_rambu);
    }

    @Override
    public void showLoadingImage() {
        System.out.println("mulai unduh gambar");
        pDialogImage = new ProgressDialog(getActivity());
        pDialogImage.setMessage("Downloading Image from Internet. Please wait...");
        pDialogImage.setIndeterminate(false);
        pDialogImage.setCancelable(false);
        pDialogImage.show();
    }

    @Override
    public void updateProgressImage(String message) {
        pDialogImage.setMessage(message);
    }

    @Override
    public void hideLoadingImage() {
        pDialogImage.hide();
    }

    @Override
    public void simpanGambar(String fileName, String url) {
        final File target = new File(new File(Environment.getExternalStorageDirectory().getPath() + "/SIM_IMAGES1/"), fileName);

        if(!url.equals("lalalalala")) {
            updateProgressImage("Gambar " + (i + 1) + "/" + gambar.size());

            aq.id(R.id.gbrCoba1).image(url, false, false, 0, 0, new BitmapAjaxCallback() {
                @Override
                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] b = baos.toByteArray();
                    String EnString = Base64.encodeToString(b, Base64.DEFAULT);

                    try {
                        if (target.createNewFile()) {
                            target.createNewFile();
                        }
                        if (EnString != null) {
                            FileOutputStream fos = new FileOutputStream(target);
                            byte[] decodedString = android.util.Base64.decode(EnString,
                                    android.util.Base64.DEFAULT);
                            fos.write(decodedString);
                            fos.flush();
                            fos.close();
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    i++;
                    if (i == (gambar.size())) {
                        presenter.unduhGambar("lalalalala");
                    } else {
                        presenter.unduhGambar(gambar.get(i));
                    }
                }
            });
        }else{
            hideLoadingImage();
            Toast.makeText(getActivity(), "Gambar Berhasil diunduh", Toast.LENGTH_LONG).show();
        }
    }

    private String[] listToArrayString(ArrayList<String> data) {
        String hasil[] = new String[data.size()];
        for(int i=0; i<hasil.length; i++){
            hasil[i] = data.get(i);
        }
        return hasil;
    }

    private int[] listToArrayInt(ArrayList<Integer> data) {
        int hasil[] = new int[data.size()];
        for(int i=0; i<hasil.length; i++){
            hasil[i] = data.get(i);
        }
        return hasil;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdate:
                update = new ProgressDialog(getActivity());
                update.setIndeterminate(false);
                update.setCancelable(false);
                update.setMessage("Update in progress ...");
                update.show();
                presenter.unduhDataTopik(id_topik);
                break;
            default:
                break;
        }
    }
}
