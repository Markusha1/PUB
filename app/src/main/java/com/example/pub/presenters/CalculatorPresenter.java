package com.example.pub.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import com.example.pub.di.DaggerDetailComponent;
import com.example.pub.di.DetailComponent;
import com.example.pub.models.Calculator;
import com.example.pub.models.Detail;
import com.example.pub.views.CalculateView;


import javax.inject.Inject;

@InjectViewState
public class CalculatorPresenter extends MvpPresenter<CalculateView> {
    @Inject
    Detail detail;
    @Inject
    Calculator calculator;

    public CalculatorPresenter(){
        DetailComponent c = DaggerDetailComponent.create();
        c.inject(this);
    }

    public void incomeOnClick(){
        getViewState().showPlus();
    }

    public void consumeOnClick(){
        getViewState().showMinus();
    }

    public void addMenuClick(String cost, boolean flag){
        if(cost.length() != 0) {
            if(!flag && !cost.equals("0")) detail.setMoney(String.valueOf(-1*Float.parseFloat(cost)));
            else detail.setMoney(cost);
            detail.setCategory("Прочее");
            detail.setDescription(null);
            getViewState().addDetail(detail);
        }
    }

    public void describeDetail(String money){
        detail.setMoney(money);
        detail.setMyLocation("");
        detail.setPhotoPath("");
        getViewState().openDescription(detail);
    }

    public void makeResult(Detail detail){
        getViewState().sendResult(detail);
    }

    public void zero_Click(){
        getViewState().setSign(calculator.zero_Click());
    }

    public void one_Click(){
        getViewState().setSign(calculator.one_Click());
    }

    public void two_Click(){
        getViewState().setSign(calculator.two_Click());
    }

    public void three_Click(){
        getViewState().setSign(calculator.three_Click());
    }

    public void four_Click(){
        getViewState().setSign(calculator.four_Click());
    }

    public void five_Click(){
        getViewState().setSign(calculator.five_Click());
    }

    public void six_Click(){
        getViewState().setSign(calculator.six_Click());
    }

    public void seven_Click(){
        getViewState().setSign(calculator.seven_Click());
    }

    public void eight_Click(){
        getViewState().setSign(calculator.eight_Click());
    }

    public void nine_Click(){
        getViewState().setSign(calculator.nine_Click());
    }

    public void dot_Click(){
        getViewState().setSign(calculator.dot_Click());
    }

    public void equal_Click(){
        getViewState().showOperator(calculator.equal_Click());
        if (String.valueOf(calculator.getResult()).contains("Infinity")){
            getViewState().setSign("Ошибка");
        }
        else getViewState().setSign(String.valueOf(calculator.getResult()));
    }
    public void add_Click(String s){
        getViewState().showOperator(calculator.add_Click(s));
        getViewState().setSign("");
    }
    public void sub_Click(String s){
        getViewState().showOperator(calculator.sub_Click(s));
        getViewState().setSign("");
    }
    public void division_Click(String s){
        getViewState().showOperator(calculator.division_Click(s));
        getViewState().setSign("");
    }
    public void multi_Click(String s){
        getViewState().showOperator(calculator.multi_Click(s));
        getViewState().setSign("");
    }
    public void clean_short_Click(String s){
         if (s.length() == 2 && s.contains("-")){
             getViewState().setSign("");
         }
         getViewState().setSign(calculator.clean_short_Click(s));
    }
    public void clean_long_Click(){
        getViewState().setSign("");
        calculator.clean_long_Click();
    }
}
