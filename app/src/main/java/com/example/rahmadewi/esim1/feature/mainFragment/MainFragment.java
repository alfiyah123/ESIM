package com.example.rahmadewi.esim1.feature.mainFragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpFragment;
import com.example.rahmadewi.esim1.check.MyService;
import com.example.rahmadewi.esim1.models.gambar.Gambar;
import com.example.rahmadewi.esim1.models.kategori.Kategori;
import com.example.rahmadewi.esim1.models.materi.Materi;
import com.example.rahmadewi.esim1.models.rambu.Rambu;
import com.example.rahmadewi.esim1.models.soal.Soal;
import com.example.rahmadewi.esim1.models.topik.Topik;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends MvpFragment<MainFragmentPresenter> implements MainViewFragment, View.OnClickListener{

    Button sima, simb1, simb2,simc;
    ImageView gbrCoba;
    List<String> kategori = new ArrayList<>();
    List<String> id = new ArrayList<>();
    List<String> gambar = new ArrayList<>();

    private static final String DB_NAME = "esim.db";

    ProgressDialog pDialog;
    ProgressDialog pDialogImage;

    //String url = "http://192.168.1.101/esim1/";
    String url = "http://esimsurabaya.tk/";

    private AQuery aq;
    int i=0;

    @Override
    protected MainFragmentPresenter createPresenter() {
        return new MainFragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_main, container, false);
        onViewCreated(x,savedInstanceState);
        presenter.createInstance(getActivity(), DB_NAME);

        aq = new AQuery(getActivity(), x);

        sima = (Button) x.findViewById(R.id.btnSimA);
        simb1 = (Button) x.findViewById(R.id.btnSimB1);
        simb2 = (Button) x.findViewById(R.id.btnSimB2);
        simc = (Button) x.findViewById(R.id.btnSimC);
        gbrCoba = (ImageView) x.findViewById(R.id.gbrCoba);

        sima.setOnClickListener(this);
        simb1.setOnClickListener(this);
        simb2.setOnClickListener(this);
        simc.setOnClickListener(this);

        Boolean isFirstRun = getActivity().getSharedPreferences("PREFERENCE",Context.MODE_PRIVATE).getBoolean("isfirstrun", true);

        if(isFirstRun){
            if(!MyService.isConnected(getActivity())){
                getDataFail("No Internet Connection");
            } else {
                Dialog dialog;
                final String[] items = {"SIM A", "SIM B1", "SIM B2", "SIM C"};
                kategori.add("UMUM");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilih Kategori SIM : ");
                builder.setMultiChoiceItems(items, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedItemId,
                                                boolean isSelected) {
                                if (isSelected) {
                                    String jenisSIM = items[selectedItemId];
                                    kategori.add(jenisSIM);
                                } else if (kategori.contains(selectedItemId)) {
                                    kategori.remove(Integer.valueOf(selectedItemId));
                                }
                            }
                        })
                        .setPositiveButton("Unduh Data", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                pDialog = new ProgressDialog(getActivity());
                                pDialog.setIndeterminate(false);
                                pDialog.setCancelable(false);
                                pDialog.setMessage("Please wait ...");
                                pDialog.show();
                                presenter.unduhDataKategori();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();

                getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit().putBoolean("isfirstrun", false).commit();
            }
        }

        return x;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void showLoading(String pesan) {
        pDialog.setMessage(pesan);
    }

    @Override
    public void hideLoading() {
        pDialog.dismiss();
    }

    @Override
    public void getDataSuccessKategori(Kategori kategori) {
        if(kategori.getCode() == 200){
            for(int i=0; i<kategori.getResult().size();i++){
                presenter.simpanDataKategori(kategori.getResult().get(i));
            }
        }
        presenter.unduhDataTopik();
    }

    @Override
    public void getDataSuccessTopik(Topik topik) {
        if(topik.getCode() == 200){
            for(int i=0; i<topik.getResult().size();i++){
                presenter.simpanDataTopik(topik.getResult().get(i));
            }
        }
        String[] kategoriArray = listToArray(kategori);
        presenter.unduhDataSoal(kategoriArray);
    }

    @Override
    public void getDataSuccessSoal(Soal soal) {
        if(soal.getCode() == 200){
            for(int i=0; i<soal.getResult().size();i++){
                if(!gambar.contains(url+"uploads/soal/"+soal.getResult().get(i).getGambar())) {
                    gambar.add(url + "uploads/soal/" + soal.getResult().get(i).getGambar());
                }
                presenter.simpanDataSoal(soal.getResult().get(i));
            }
        }
        String[] kategoriArray = listToArray(kategori);
        presenter.unduhDataMateri(kategoriArray);
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
        String[] idArray = listToArray(id);
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
        presenter.unduhDataRambu();
    }

    @Override
    public void getDataFail(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
        System.out.println("Eror Unduh : "+message);
    }

    @Override
    public String[] listToArray(List<String> data) {
        String hasil[] = new String[data.size()];
        for(int i=0; i<hasil.length; i++){
            hasil[i] = data.get(i);
        }
        return hasil;
    }

    @Override
    public void moveToMenu(Intent intent) {
        startActivity(intent);
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

            aq.id(R.id.gbrCoba).image(url, false, false, 0, 0, new BitmapAjaxCallback() {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSimA:
                presenter.getKategori("SIM A",getActivity());
                break;
            case R.id.btnSimB1:
                presenter.getKategori("SIM B1",getActivity());
                break;
            case R.id.btnSimB2:
                presenter.getKategori("SIM B2",getActivity());
                break;
            case R.id.btnSimC:
                presenter.getKategori("SIM C",getActivity());
                break;
            default:
                break;
        }
    }
}
