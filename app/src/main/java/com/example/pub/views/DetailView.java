package com.example.pub.views;

import com.arellomobile.mvp.MvpView;
import com.example.pub.models.Detail;


public interface DetailView extends MvpView {
    void setCurrentBalance(String currentBalance);
    void setInitBalance(String initBalance);
    void createDetail();
    void openDescription(Detail detail);
    void updateAdapter();
    void showPieChart();
}
