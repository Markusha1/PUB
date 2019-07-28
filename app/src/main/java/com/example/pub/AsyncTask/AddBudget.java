package com.example.pub.AsyncTask;

import android.os.AsyncTask;

import com.example.pub.Models.Budget;
import com.example.pub.Models.BudgetListRepository;

public class AddBudget extends AsyncTask<Budget,Void,Void> {
    private BudgetListRepository repository;

    public AddBudget(BudgetListRepository repository){
        this.repository = repository;
    }
    @Override
    protected Void doInBackground(Budget...budget) {
        repository.addBudget(budget[0]);
        return null;
    }
}
