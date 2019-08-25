package com.example.pub.views;

import com.arellomobile.mvp.MvpView;
import com.example.pub.models.Budget;

public interface AddBudgetView extends MvpView {
    void showErrorTittle();
    void saveBudget(Budget budget);
    void setTitle(String title);
    void setMoney(String money);
}
