package com.example.pub.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import com.example.pub.Models.Detail;
import com.example.pub.Models.DetailListRepository;
import com.example.pub.Presenters.DetailPresenter;
import com.example.pub.R;
import com.example.pub.RecyclerView.DetailAdapter;
import com.example.pub.Views.DetailView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;


public class DetailActivity extends MvpAppCompatActivity implements DetailView {
    private static final int REQUEST_BUDGET = 100;
    private TextView mBalance, mInitBalance;
    private RecyclerView mRecyclerView;
    private DetailAdapter adapter;
    private PieChart pieChart;
    @Inject
    public DetailListRepository mRepository;
    @InjectPresenter
    public DetailPresenter presenter;
    @ProvidePresenter
    DetailPresenter providePresenter() {
        return new DetailPresenter(getApplicationContext());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_detail);
        findViewById(R.id.init_balance_view);
        presenter.initBudget(getIntent().getSerializableExtra("details"));
        mRecyclerView = findViewById(R.id.recycler_view2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailAdapter(presenter);
        mRecyclerView.setAdapter(adapter);
        mBalance = findViewById(R.id.balance);
        mBalance.setText(presenter.getBalance());
        mInitBalance = findViewById(R.id.init_balance);
        pieChart = findViewById(R.id.pie_chart);
        showPieChart();
        presenter.setInitBalances();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        getSupportActionBar().setTitle(presenter.getTitle());
        findViewById(R.id.float_button).setOnClickListener(v -> presenter.addNewDetail());
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        showPieChart();
    }

    @Override
    public void setInitBalance(String initBalance) {
        mInitBalance.setText(initBalance);
    }

    @Override
    public void setCurrentBalance(String currentBalance) {
        mBalance.setText(currentBalance);
    }

    @Override
    public void createDetail() {
        Intent i = new Intent(this, CalculActivity.class);
        startActivityForResult(i, REQUEST_BUDGET);
    }

    @Override
    public void showPieChart() {
        List<Detail> list = presenter.loadDetails();
        ArrayList<PieEntry> categories = new ArrayList<>();
        pieChart.setExtraOffsets(5, 5, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.5f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.animateY(1000, Easing.EaseInOutCirc);
        if (list.size() == 0) {
            Description d = new Description();
            d.setText("Нет данных");
            d.setTextSize(40f);
            pieChart.setDescription(d);
            pieChart.getDescription().setPosition(580,720);
            categories.add(new PieEntry(1, ""));
            PieDataSet dataSet = new PieDataSet(categories, "");
            dataSet.setColors(R.color.emptyChar);
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);
            pieChart.setData(data);

        } else {
            for (int i = 0; i < statistics(list).keySet().toArray().length; i++) {
                String label = (String) statistics(list).keySet().toArray()[i];
                categories.add(new PieEntry((Float) statistics(list).get(statistics(list).keySet().toArray()[i]), label));
            }
            PieDataSet dataSet = new PieDataSet(categories, "Категории");
            dataSet.setSliceSpace(3);
            dataSet.setSelectionShift(5);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(39f);
            pieChart.getDescription().setEnabled(false);
            pieChart.setData(data);
        }
        }

        @Override
        public void openDescription (Detail detail){
            Intent i = new Intent(this, DescribeActivity.class);
            i.putExtra("describedetail", detail);
            startActivityForResult(i, REQUEST_BUDGET);
        }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("pause", "onPause: ok");
    }

    @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && data != null) {
                Detail d = (Detail) data.getSerializableExtra("newdetail");
                boolean existed = false;
                for (int i = 0; i < presenter.loadDetails().size(); i++) {
                    if (d.getId() == (presenter.loadDetails().get(i).getId())) {
                        presenter.updateDetail(d);
                        existed = true;
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                if (!existed) {
                    if (requestCode == 1)
                        d.setDate(android.text.format.DateFormat.format("dd.MM.yy", new Date()).toString());
                    presenter.add(d);
                }
                presenter.setCurrentBalance(d.getMoney());
            }
        }

        public HashMap statistics (List < Detail > details) {
            HashMap<String, Float> counter = new HashMap<>();
            for (Detail detail : details) {
                String key = detail.getCategory();
                if (!counter.containsKey(key)) counter.put(key, 1f);
                else counter.put(key, counter.get(key)+ 1);
            }
            return counter;
        }


    }

