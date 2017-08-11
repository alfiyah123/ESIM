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
    TextView txtJawabanPembahasan1, txtJawabanPembahasan2, txtJawabanPembahasan3;
    ImageView gbrPembahasan;
    LinearLayout bgPembahasan, bgJawaban1, bgJawaban2, bgJawaban3;

    @BindView(R.id.scrPembahasan)
    LinearLayout scrPembahasan;

    ArrayList<Integer> id_soal = new ArrayList<>();
    ArrayList<String> jawabanUser = new ArrayList<>();
    boolean[] validasi = new boolean[30];
    String[][] opsi;

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
        jawabanUser = myBundle.getStringArrayList("jawabanUser");

        Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("opsi");
        if(objectArray!=null){
            opsi = new String[objectArray.length][];
            for(int i=0;i<objectArray.length;i++){
                opsi[i]=(String[]) objectArray[i];
                System.out.println("ini opsinya "+i+" : "+opsi[i][0]+" | "+opsi[i][1]+" | "+opsi[i][2]);
            }
        }

        if(id_soal != null) {
            for (int i = 0; i < id_soal.size(); i++) {
                scrPembahasan.addView(getData(id_soal.get(i)));
            }
        }
    }

    @Override
    public void setDataLayout(String soal, String jawaban) {
        txtSoalPembahasan.setText(no+". "+soal);
        txtJawabanPembahasan1.setText("A. "+opsi[no-1][0]);
        txtJawabanPembahasan2.setText("B. "+opsi[no-1][1]);
        txtJawabanPembahasan3.setText("C. "+opsi[no-1][2]);

        String jawaban1 = txtJawabanPembahasan1.getText().toString();
        String jawaban2 = txtJawabanPembahasan2.getText().toString();

        if(jawaban1.equals("A. "+jawaban)){
            colorValidasi(bgJawaban1,204,255,153);
        }else if(jawaban2.equals("B. "+jawaban)){
            colorValidasi(bgJawaban2,204,255,153);
        }else{
            colorValidasi(bgJawaban3,204,255,153);
        }

        if(!validasi[no-1]){
            if(jawaban1.equals("A. "+jawabanUser.get(no-1))){
                colorValidasi(bgJawaban1, 255,153,153);
            }else if(jawaban2.equals("B. "+jawabanUser.get(no-1))){
                colorValidasi(bgJawaban2, 255,153,153);
            }else{
                colorValidasi(bgJawaban3, 255,153,153);
            }
        }
        no++;
    }

    @Override
    public void setImg(Bitmap bitmap) {
        gbrPembahasan.setImageBitmap(bitmap);
    }

    private void colorValidasi(LinearLayout linearLayout, int red, int green, int blue){
        linearLayout.setBackgroundColor(Color.rgb(red,green,blue));
    }

    private View getData(int id_soal){
        View v = getLayoutInflater().inflate(R.layout.content_pembahasan, null);

        txtSoalPembahasan= (TextView)v.findViewById(R.id.txtSoalPembahasan);
        txtJawabanPembahasan1= (TextView)v.findViewById(R.id.txtJawabanPembahasan1);
        txtJawabanPembahasan2= (TextView)v.findViewById(R.id.txtJawabanPembahasan2);
        txtJawabanPembahasan3= (TextView)v.findViewById(R.id.txtJawabanPembahasan3);
        gbrPembahasan= (ImageView)v.findViewById(R.id.gbrPembahasan);
        bgPembahasan = (LinearLayout)v.findViewById(R.id.bgPembahasan);
        bgJawaban1 = (LinearLayout) v.findViewById(R.id.bgJawaban1);
        bgJawaban2 = (LinearLayout) v.findViewById(R.id.bgJawaban2);
        bgJawaban3 = (LinearLayout) v.findViewById(R.id.bgJawaban3);

        presenter.getData(id_soal);

        return v;
    }
}
