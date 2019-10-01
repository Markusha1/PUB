package com.example.pub.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.pub.R;
import com.example.pub.models.Budget;
import com.example.pub.models.Goal;
import com.example.pub.presenters.BudgetListPresenter;
import com.example.pub.recyclerView.BudgetAdapter;
import com.example.pub.views.BudgetListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BudgetsActivity extends MvpAppCompatActivity implements BudgetListView {
    private static final String TAG = "MVPapplucation";
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.empty_list) ImageView picture;
    @BindView(R.id.empty_text) TextView text;
    private BudgetAdapter adapter;
    private static final String BUDGETITEM = "newBudget";
    private static final String GOALITEM = "newGoal";
    private static final String DETAILS = "details";
    private static final int REQUEST_BUDGET = 100;
    private List<Budget> mList;
    @InjectPresenter
    BudgetListPresenter presenter;
    @ProvidePresenter
    BudgetListPresenter providePresenter(){
        return new BudgetListPresenter(this);
    }


    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        setContentView(R.layout.list_budgets);
        ButterKnife.bind(this);
        mList = presenter.loadBudgets();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BudgetAdapter(mList, presenter);
        recyclerView.setAdapter(adapter);
        mFab.setOnClickListener(v -> {
            Log.d(TAG, "Fab нажата");
            presenter.newBudgetScreen();
        });
        presenter.checkList();
    }

    public void onResume(){
        super.onResume();
        presenter.checkList();
        adapter.setList(presenter.loadBudgets());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    //открвает экран с деталями бюджета
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

    //открывает экран создания бюджета
    @Override
    public void goToAddScreen(Budget budget) {
        Intent intent = new Intent(this, NewBudgetActivity.class);
        intent.putExtra(BUDGETITEM, budget);
        startActivityForResult(intent,REQUEST_BUDGET);
    }

    //пока эта функция разрабатывается
    @Override
    public void goToNewGoal(Goal goal){
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(GOALITEM, goal);
        startActivityForResult(intent,REQUEST_BUDGET);
    }

    //открывет экран изменения бюджета
    @Override
    public void edit(Budget budget) {
        Intent i = new Intent(this, NewBudgetActivity.class);
        i.putExtra(BUDGETITEM, budget);
        startActivityForResult(i, REQUEST_BUDGET);
    }

    //показывает картинку пустого списка, если тот таковым является
    @Override
    public void showEmptyList(){
        picture.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
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