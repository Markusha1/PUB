package com.example.pub.Presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pub.DI.AppComponent;
import com.example.pub.DI.AppModule;
import com.example.pub.DI.DaggerAppComponent;
import com.example.pub.DI.RoomModule;
import com.example.pub.Models.Budget;
import com.example.pub.Models.BudgetListRepository;
import com.example.pub.views.BudgetListView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class BudgetListPresenter extends MvpPresenter<BudgetListView> {
    @Inject
    public BudgetListRepository mRepository;

    public BudgetListPresenter(Context context) {
        AppComponent component = DaggerAppComponent.builder().appModule(new AppModule(context)).roomModule(new RoomModule()).build();
        component.inject(this);
    }

    public List<Budget> loadBudgets() {
        return mRepository.getAll();
    }

    public void newBudgetScreen() {
        Budget mBudget = new Budget();
        getViewState().goToAddScreen(mBudget);
    }

    public void deleteBudget(Budget budget) {
        mRepository.deleteBudget(budget);
    }

    public void editBudget(Budget budget) {
        getViewState().edit(budget);
    }

    public void update(Budget budget) {
        mRepository.updateBudget(budget);
    }

    public void addBudget(Budget budget) {
        mRepository.addBudget(budget);
    }

    public void onClickBudget(Budget budget) {
        getViewState().openDetail(budget);
    }

}
