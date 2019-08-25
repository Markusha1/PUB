package com.example.pub.views;


import com.arellomobile.mvp.MvpView;
import com.example.pub.models.Budget;
import com.example.pub.models.Goal;


public interface BudgetListView extends MvpView {
    void openDetail(Budget budget);
    void setBudgets();
    void showEmptyList();
    void goToAddScreen(Budget budget);
    void goToNewGoal(Goal goal);
    void edit(Budget budget);
}
