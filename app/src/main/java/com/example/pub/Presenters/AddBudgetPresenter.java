package com.example.pub.Presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pub.Models.Budget;
import com.example.pub.views.AddBudgetView;

import java.util.Date;


@InjectViewState
public class AddBudgetPresenter extends MvpPresenter<AddBudgetView> {
    private Budget mBudget;
    private static final String BUDGETITEM = "newBudget";

    public void initBudget(Intent intent){
        mBudget = (Budget) intent.getSerializableExtra(BUDGETITEM);
        if (mBudget.getBudgetText()!=null && mBudget.getBudgetMoney()!=null){
            getViewState().setMoney(mBudget.getBudgetMoney());
            getViewState().setTitle(mBudget.getBudgetText());
        }
    }

    public void getBudget(String title, String money){
        if (title.length() == 0){
            getViewState().showErrorTittle();
        }
        else {
            mBudget.setBudgetText(title.substring(0, 1).toUpperCase().concat(title.substring(1)));
            mBudget.setBudgetDate(android.text.format.DateFormat.format("dd.MM.yy", new Date()).toString());
            if (money.length() == 0) {
                mBudget.setBudgetMoney("0");
            }
            else {
                mBudget.setBudgetMoney(money);
            }
            getViewState().saveBudget(mBudget);
        }
    }
}
