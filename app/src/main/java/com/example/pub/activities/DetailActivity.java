package com.example.pub.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.pub.R;
import com.example.pub.models.Detail;
import com.example.pub.presenters.DetailPresenter;
import com.example.pub.recyclerView.DetailAdapter;
import com.example.pub.utilities.Constants;
import com.example.pub.views.DetailView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends MvpAppCompatActivity implements DetailView {
    @BindView(R.id.balance) TextView mBalance;
    @BindView(R.id.init_balance) TextView mInitBalance;
    @BindView(R.id.recycler_view2) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private static final int REQUEST_BUDGET = 100;
    private DetailAdapter adapter;
    private PieChart pieChart;
    private int[] colors = {R.color.pie_one, R.color.pie_two,R.color.pie_three,R.color.pie_four,R.color.pie_five,R.color.pie_six,R.color.pie_seven,R.color.pie_eight,R.color.pie_nine,R.color.pie_ten,R.color.pie_eleven,R.color.pie_twelve,R.color.pie_thirteen,R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark,R.color.background,R.color.text2,};
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
        ButterKnife.bind(this);
        presenter.initBudget(getIntent().getSerializableExtra("details"));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailAdapter(presenter);
        mRecyclerView.setAdapter(adapter);
        mBalance.setText(presenter.getBalance());
        pieChart = findViewById(R.id.pie_chart);
        showPieChart();
        presenter.setInitBalances();
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
        pieChart.setDescription(null);
        if (list.size() == 0) {
            pieChart.setCenterText("Нет данных");
            categories.add(new PieEntry(1, ""));
            PieDataSet dataSet = new PieDataSet(categories, "");
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);
            pieChart.setData(data);

        } else {
            for (int i = 0; i < statistics(list).keySet().toArray().length; i++) {
                String label = (String) statistics(list).keySet().toArray()[i];
                categories.add(new PieEntry((Float) statistics(list).get(statistics(list).keySet().toArray()[i]), label));
            }
            pieChart.setCenterText("");
            PieDataSet dataSet = new PieDataSet(categories, "Категории");
            dataSet.setSliceSpace(3);
            dataSet.setSelectionShift(5);
            dataSet.setColors(ColorTemplate.createColors(getResources(), colors));
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(39f);
            pieChart.setData(data);
        }
    }

    @Override
    public void openDescription (Detail detail){
        Intent i = new Intent(this, DescribeActivity.class);
        i.putExtra(Constants.DESCRIPTION_DETAIL, detail);
        startActivityForResult(i, REQUEST_BUDGET);
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }


    @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && data != null) {
                Detail d = (Detail) data.getSerializableExtra(Constants.NEW_DETAIL);
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

