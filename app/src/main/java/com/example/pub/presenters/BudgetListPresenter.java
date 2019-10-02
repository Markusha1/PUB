package com.example.pub.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pub.di.AppComponent;
import com.example.pub.di.AppModule;
import com.example.pub.di.DaggerAppComponent;
import com.example.pub.di.RoomModule;
import com.example.pub.models.Budget;
import com.example.pub.models.BudgetListRepository;
import com.example.pub.models.Goal;
import com.example.pub.views.BudgetListView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class BudgetListPresenter extends MvpPresenter<BudgetListView> {
    @Inject
    BudgetListRepository mRepository;

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
    //Функция в разработке
    public void newGoalScreen(){
        Goal goal = new Goal();
        getViewState().goToNewGoal(goal);
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

    public void checkList(){
        if (loadBudgets().size() == 0) getViewState().showEmptyList();
    }

}
