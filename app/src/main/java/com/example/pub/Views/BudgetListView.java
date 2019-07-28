package com.example.pub.Views;


import com.arellomobile.mvp.MvpView;
import com.example.pub.Models.Budget;



public interface BudgetListView extends MvpView {
    void openDetail(Budget budget);
    void setBudgets();
    void goToAddScreen(Budget budget);
    void edit(Budget budget);
}
