package com.example.pub.Models;

public interface CalculatorInt {
     String zero_Click();

     String one_Click();

     String two_Click();

     String three_Click();

     String four_Click();

     String five_Click();

     String six_Click();

     String seven_Click();

     String eight_Click();

     String nine_Click();

    String dot_Click();

    String equal_Click();

    String add_Click(String s);

    String sub_Click(String s);

    String division_Click(String s);

    String multi_Click(String s);

    String clean_short_Click(String expression);

    void clean_long_Click();

    double getResult();
}
