package com.example.pub.DI;

import com.example.pub.Models.Calculator;
import com.example.pub.Models.Detail;
import com.example.pub.Presenters.CalculatorPresenter;

import dagger.Component;

@Component
public interface DetailComponent {

    Detail getDetail();

    Calculator getCalculator();

    void inject(CalculatorPresenter presenter);
}
