package com.example.rahmadewi.esim1.feature.artikel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ArtikelActivity extends MvpActivity<ArtikelPresenter> implements ArtikelView{

    List<String> p;
    List<String> src;

    int i = 0;

    @BindView(R.id.txtJudulArtikel)
    TextView txtJudul;

    @BindView(R.id.bgArtikel)
    LinearLayout b;

    String sub_topik;

    @Override
    protected ArtikelPresenter cretePresenter() {
        return new ArtikelPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);

        p = new ArrayList<>();
        src = new ArrayList<>();

        sub_topik = getIntent().getStringExtra("item");

        presenter.getData(sub_topik);

    }

    @Override
    public void parseH(String data) {
        TextView textView = new TextView(getApplicationContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(20);
        textView.setText(data);
        textView.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
        b.addView(textView);
    }

    @Override
    public void parseP(String data) {
        TextView textView = new TextView(getApplicationContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setLinksClickable(true);
        textView.setAutoLinkMask(Linkify.WEB_URLS);
        textView.setText(Html.fromHtml(data));
        textView.setTextSize(16);
        textView.setLineSpacing(1.3f, 1.3f);
        textView.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
        b.addView(textView);
        p.add(data);
    }

    @Override
    public void parseOl(String data) {
        TextView textView = new TextView(getApplicationContext());

        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setLinksClickable(true);
        textView.setAutoLinkMask(Linkify.WEB_URLS);
        textView.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
        textView.setText(data);
        textView.setTextSize(16);
        textView.setLineSpacing(1.3f, 1.3f);
        b.addView(textView);
    }

    @Override
    public void parseImg(Bitmap bitmap, String nama) {
        final ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        imageView.setAdjustViewBounds(true);
        imageView.requestLayout();
        imageView.setId(i);
        i++;

        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.moveToIntent(ArtikelActivity.this, src.get(imageView.getId()));
            }
        });

        b.addView(imageView);
        src.add(nama);
    }

    @Override
    public void parseUl(String data) {
        TextView textView = new TextView(getApplicationContext());

        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setLinksClickable(true);
        textView.setAutoLinkMask(Linkify.WEB_URLS);
        textView.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
        textView.setText(Html.fromHtml(data, null, null));
        textView.setTextSize(16);
        textView.setLineSpacing(1.3f,1.3f);
        b.addView(textView);
    }

    @Override
    public void moveIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void setTexView(String data) {
        txtJudul.setText(data);
    }

}
