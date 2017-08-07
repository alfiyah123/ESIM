package com.example.rahmadewi.esim1.feature.rambu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.adapter.RambuAdapter;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;

import butterknife.BindView;

public class RambuActivity extends MvpActivity<RambuPresenter> implements RambuView {

    private int index = 0;
    private int PAGE_SIZE = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private String item;

    RambuAdapter rambuAdapter;

    @BindView(R.id.cardList)
    RecyclerView recList;

    @BindView(R.id.loading)
    LinearLayout a;

    @BindView(R.id.more_progress)
    ProgressBar b;

    @BindView(R.id.txtJudulRambu)
    TextView txtJudul;

    final LinearLayoutManager llm = new LinearLayoutManager(this);

    @Override
    protected RambuPresenter cretePresenter() {
        return new RambuPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rambu);

        item = getIntent().getStringExtra("item");

        txtJudul.setText(item);

        recList.setHasFixedSize(true);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        rambuAdapter = new RambuAdapter(presenter.initData(item, PAGE_SIZE));
        recList.setAdapter(rambuAdapter);

        recList.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recList.getChildCount();
                totalItemCount = llm.getItemCount();
                firstVisibleItem = llm.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount
                            && firstVisibleItem >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        a.setVisibility(View.VISIBLE);
                        b.setVisibility(View.VISIBLE);
                        isLoading = true;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                presenter.loadData(item, index, PAGE_SIZE);
                                rambuAdapter.notifyDataSetChanged();
                            }
                        }, 2000);
                    }
                }
            }
        });
    }

    @Override
    public void endData(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateIndex(int index) {
        this.index = index;
    }

    @Override
    public void hideLoading() {
        isLoading = false;
        a.setVisibility(View.GONE);
        b.setVisibility(View.GONE);
    }
}
