package com.example.rahmadewi.esim1.feature.dashboardFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpFragment;
import com.example.rahmadewi.esim1.check.MyService;
import com.example.rahmadewi.esim1.models.nilai.Nilai;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Map;

public class DashboardFragment extends MvpFragment<DashboardPresenter> implements DashboardView {

    TextView nUser;
    TextView nKategori;
    LineChart nilaiUser;
    LinearLayout layout;
    Spinner spinner;
    Button btnCari;
    Context context;

    ProgressDialog pDialog;

    private static final String[]paths = {"SIM A", "SIM B1", "SIM B2", "SIM C"};
    String kategori1 = "SIM A";

    public static Fragment newInstance(){
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_dashboard, container, false);
        onViewCreated(x,savedInstanceState);

        context = getActivity();

        spinner = (Spinner) x.findViewById(R.id.spinner);
        layout = (LinearLayout) x.findViewById(R.id.bgDashboard);
        btnCari = (Button) x.findViewById(R.id.buttonCari);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        kategori1 = "SIM A";
                        break;
                    case 1:
                        kategori1 = "SIM B1";
                        break;
                    case 2:
                        kategori1 = "SIM B2";
                        break;
                    case 3:
                        kategori1 = "SIM C";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MyService.isConnected(getActivity())){
                    getDataFail("No Internet Connection");
                }else {
                    presenter.getDataNilai(kategori1);
                }
            }
        });


        return x;
    }

    @Override
    protected DashboardPresenter createPresenter() {
        return new DashboardPresenter(this);
    }

    @Override
    public void onShowLoading() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        pDialog.show();
    }

    @Override
    public void hideLoading() {
        pDialog.dismiss();
    }

    @Override
    public void getDataFail(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void layoutGrafik(ArrayList<String> user, ArrayList<Float> nilai, ArrayList<String> userUnik) {
        LinearLayout parent = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 20);
        parent.setOrientation(LinearLayout.HORIZONTAL);
        parent.setPadding(20, 20, 20, 20);
        parent.setLayoutParams(layoutParams);
        parent.setBackgroundColor(Color.parseColor("#FFFFFF"));

        nKategori = new TextView(context);
        nKategori.setTextSize(20.0f);
        nKategori.setPaintFlags(nKategori.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        nKategori.setTypeface(null, Typeface.BOLD_ITALIC);
        nKategori.setText("KATEGORI " + kategori1);

        parent.addView(nKategori);
        layout.addView(parent);

        int j = 0;
        for (int k = 0; k < userUnik.size(); k++) {
            int l = j;
            LinearLayout parent1 = new LinearLayout(context);
            parent1.setOrientation(LinearLayout.VERTICAL);
            parent1.setPadding(20, 20, 20, 20);
            parent1.setLayoutParams(layoutParams);
            parent1.setBackgroundColor(Color.parseColor("#FFFFFF"));

            nUser = new TextView(context);
            nUser.setText("" + userUnik.get(k));
            nUser.setTextSize(18.0f);
            nUser.setLayoutParams(layoutParams);
            nUser.setTypeface(null, Typeface.BOLD);
            nilaiUser = new LineChart(context);
            ArrayList<Entry> yvalues = new ArrayList<Entry>();
            ArrayList<String> xVals1 = new ArrayList<String>();
            ArrayList<Integer> dataY = new ArrayList<>();

            int jumlah = 0;
            for (int i = 0; i < user.size(); i++) {
                System.out.println("user : " + user.get(i));
                if (user.get(i).equals(userUnik.get(k))) {
                    jumlah = jumlah + 1;
                }
            }
            System.out.println("Jumlah Unik = " + jumlah);

            for (int c = l; c < (jumlah + l); c++) {
                yvalues.add(new Entry(nilai.get(c).floatValue(), ((c - l))));
                dataY.add(nilai.get(c).intValue());
                xVals1.add("" + ((c - l)));

                j++;
            }

            LineDataSet dataNilai = new LineDataSet(yvalues, "Grafik Nilai Simulasi");
            int[] colors = new int[dataY.size()];

            for(int i=0; i<colors.length; i++){
                if(dataY.get(i) >= 70){
                    colors[i] = Color.rgb(51,255,51);
                }else{
                    colors[i] = Color.rgb(255,51,51);
                }
            }
            dataNilai.setDrawCubic(true);
            dataNilai.setCircleColors(colors);
            LineData data = new LineData(xVals1, dataNilai);

            nilaiUser.setMinimumHeight(300);
            nilaiUser.setData(data);
            nilaiUser.animateY(5000);
            parent1.addView(nUser);
            parent1.addView(nilaiUser);
            layout.addView(parent1);
        }

    }

    @Override
    public void hapusView() {
        layout.removeAllViews();
    }

}
