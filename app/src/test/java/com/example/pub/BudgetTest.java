package com.example.pub;

import org.junit.*;
import static org.junit.Assert.*;

import com.example.pub.Models.Budget;

import java.util.Date;

public class BudgetTest {
    private Budget budget = new Budget();

    @Before
    public void initBudget(){
        budget.setBudgetMoney("120");
        budget.setBudgetDate(String.valueOf(new Date()));
        budget.setBudgetText("Some Title");
    }

    @After
    public void nullBudget(){
        budget = null;
    }

    @Test
    public void testGetText(){
        assertEquals("Some Title", budget.getBudgetText());
    }

    @Test
    public void testGetMoney(){
        assertEquals("120", budget.getBudgetMoney());
    }

    @Test
    public void testGetDate(){
        assertEquals(String.valueOf(new Date()), budget.getBudgetDate());
    }
}
