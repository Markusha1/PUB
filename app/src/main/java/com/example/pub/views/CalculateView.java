package com.example.pub.views;

import com.arellomobile.mvp.MvpView;
import com.example.pub.models.Detail;

public interface CalculateView extends MvpView {
    void openDescription(Detail detail);
    void showPlus();
    void showMinus();
    void addDetail(Detail detail);
    void sendResult(Detail detail);
    void setSign(String s);
    void showOperator(String s);
}
