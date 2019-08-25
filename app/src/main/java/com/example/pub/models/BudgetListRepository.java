package com.example.pub.models;


import com.example.pub.database.BudgetDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BudgetListRepository implements BudgetRepository {
    private BudgetDao budgetDao;

    @Inject
    public BudgetListRepository(BudgetDao budgetDao){
        this.budgetDao = budgetDao;
    }

    @Override
    public void updateBudget(Budget budget) {
        budgetDao.updateBudget(budget);
    }

    @Override
    public void addBudget(Budget budget) {
        budgetDao.insertBudget(budget);
    }

    @Override
    public void deleteBudget(Budget budget) {
        budgetDao.deleteBudget(budget);
    }

    @Override
    public Budget getBudget(String id) {
        return budgetDao.getBudget(id);
    }

    @Override
    public List<Budget> getAll() {
        return budgetDao.getAll();
    }
}
