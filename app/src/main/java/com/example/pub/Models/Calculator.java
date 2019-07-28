package com.example.pub.Models;

import java.util.Stack;

import javax.inject.Inject;

public class Calculator implements CalculatorInt{
    private String expression;
    private Stack<Double> digits;
    private Stack<String> operats;
    private double value;
    private double result;

    @Inject
    public Calculator(){
        expression = "";
        digits = new Stack<>();
        operats = new Stack<>();
    }

    @Override
    public String zero_Click() {
        return expression += "0";
    }

    @Override
    public String one_Click() { return expression += "1"; }

    @Override
    public String two_Click() {
        return expression += "2";
    }

    @Override
    public String three_Click() {
        return expression += "3";
    }

    @Override
    public String four_Click() {
        return expression += "4";
    }

    @Override
    public String five_Click() { return expression += "5"; }

    @Override
    public String six_Click(){ return expression += "6"; }

    @Override
    public String seven_Click() { return expression += "7"; }

    @Override
    public String eight_Click() {
        return expression += "8";
    }

    @Override
    public String nine_Click() { return expression += "9"; }

    @Override
    public String dot_Click() {
        return expression += ".";
    }

    @Override
    public String equal_Click() {
        if(expression.length() != 0) {
            digits.push(Double.parseDouble(expression));
            comput();
            digits.clear();
            operats.clear();
            expression = "";
        }
            return "=";
    }

    @Override
    public String add_Click(String s) {
        if (s.length() != 0) {
            digits.push(Double.parseDouble(s));
            expression = "";
            comput();
            operats.push("+");
        }
        return "+";
    }

    @Override
    public String sub_Click(String s){
        if (s.length() != 0) {
            digits.push(Double.parseDouble(s));
            expression = "";
            comput();
            operats.push("-");
        }
        return "-";
    }

    @Override
    public String division_Click(String s) {
        if (s.length() != 0) {
            digits.push(Double.parseDouble(s));
            expression = "";
            comput();
            operats.push("/");
        }
        return "/";
    }

    @Override
    public String multi_Click(String s) {
        if (s.length() != 0) {
            digits.push(Double.parseDouble(s));
            expression = "";
            comput();
            operats.push("*");
        }
        return "*";
    }

    @Override
    public void clean_short_Click() {
        if (expression.length() <= 1) {
            expression = "";
            value = 1;
        }
        else {
            expression = expression.substring(0, expression.length() - 1);
        }
    }

    @Override
    public void clean_long_Click() {
        digits.clear();
        operats.clear();
        expression = "";
    }

    private void comput() {
        if (digits.size() >= 2){
            while (digits.size() > 1) {
                value = digits.pop();
                switch (operats.pop()) {
                    case "+":
                        result = value + digits.pop();
                        digits.push(result);
                        break;
                    case "-":
                        result = digits.pop() - value;
                        digits.push(result);
                        break;
                    case "/":
                        result = digits.pop() / value;
                        digits.push(result);
                        break;
                    case "*":
                        result = value * digits.pop();
                        digits.push(result);
                        break;
                }
            }
        }
    }
    @Override
    public double getResult(){
        return result;
    }
}
