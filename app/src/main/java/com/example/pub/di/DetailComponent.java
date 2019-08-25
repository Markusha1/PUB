package com.example.pub.di;

import com.example.pub.models.Calculator;
import com.example.pub.models.Detail;
import com.example.pub.presenters.CalculatorPresenter;

import dagger.Component;

@Component
public interface DetailComponent {

    Detail getDetail();

    Calculator getCalculator();

    void inject(CalculatorPresenter presenter);
}
