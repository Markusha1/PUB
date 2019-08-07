package com.example.pub.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.pub.Models.Budget;
import com.example.pub.Presenters.BudgetListPresenter;
import com.example.pub.R;
import com.example.pub.RecyclerView.BudgetAdapter;
import com.example.pub.views.BudgetListView;

import java.util.List;




public class BudgetsActivity extends MvpAppCompatActivity implements BudgetListView {
    private static final String TAG = "MVPapplucation";
    private RecyclerView recyclerView;
    private BudgetAdapter adapter;
    private static final String BUDGETITEM = "newBudget";
    private static final String DETAILS = "details";
    private static final int REQUEST_BUDGET = 100;
    private List<Budget> mList;
    @InjectPresenter
    BudgetListPresenter presenter;
    @ProvidePresenter
    BudgetListPresenter providePresenter(){
        return new BudgetListPresenter(getApplicationContext());
    }


    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        mList = presenter.loadBudgets();
        setContentView(R.layout.list_budgets);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BudgetAdapter(mList, presenter);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.fab).setOnClickListener(v -> {
            Log.d(TAG, "Fab нажата");
            presenter.newBudgetScreen();
        });
    }

    public void onResume(){
        super.onResume();
        adapter.setList(presenter.loadBudgets());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void openDetail(Budget budget) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DETAILS, budget);
        startActivity(intent);
    }

    @Override
    public void setBudgets() {
        adapter = new BudgetAdapter(mList, presenter);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void goToAddScreen(Budget budget) {
        Intent intent = new Intent(this, NewBudgetActivity.class);
        intent.putExtra(BUDGETITEM, budget);
        startActivityForResult(intent,REQUEST_BUDGET);
    }

    @Override
    public void edit(Budget budget) {
        Intent i = new Intent(this, NewBudgetActivity.class);
        i.putExtra(BUDGETITEM, budget);
        startActivityForResult(i, REQUEST_BUDGET);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Budget budget = (Budget) data.getSerializableExtra("budget");
            boolean existed = false;
            for (int i = 0; i < mList.size(); i++) {
                if (budget.getIdentifier().equals(mList.get(i).getIdentifier())) {
                    presenter.update(budget);
                    existed = true;
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
            if (!existed) presenter.addBudget(budget);
            }
    }
}