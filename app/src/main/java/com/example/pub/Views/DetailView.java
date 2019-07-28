package com.example.pub.Views;

import com.arellomobile.mvp.MvpView;
import com.example.pub.Models.Detail;


public interface DetailView extends MvpView {
    void setCurrentBalance(String currentBalance);
    void setInitBalance(String initBalance);
    void createDetail();
    void openDescription(Detail detail);
    void updateAdapter();
    void showPieChart();
}
