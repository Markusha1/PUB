package com.example.pub.models;

import java.util.List;

public interface BudgetRepository {
    void updateBudget(Budget budget);
    void addBudget(Budget budget);
    void deleteBudget(Budget budget);
    Budget getBudget(String id);
    List<Budget> getAll();

}