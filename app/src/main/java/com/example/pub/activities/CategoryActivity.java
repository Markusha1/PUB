package com.example.pub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pub.Presenters.CategoryPresenter;
import com.example.pub.R;
import com.example.pub.RecyclerView.CategoryAdapter;
import com.example.pub.Views.CategoryView;

public class CategoryActivity extends MvpAppCompatActivity implements CategoryView {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    @InjectPresenter
    public CategoryPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(presenter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setCategory(String s) {
        Intent intent = new Intent();
        intent.putExtra("category", s);
        setResult(RESULT_OK, intent);
        finish();
    }
}
