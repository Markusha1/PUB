package com.example.pub.Views;

import com.arellomobile.mvp.MvpView;
import com.example.pub.Models.Budget;

public interface AddBudgetView extends MvpView {
    void showErrorTittle();
    void saveBudget(Budget budget);
    void setTitle(String title);
    void setMoney(String money);
}
