package com.example.pub.views;

import com.arellomobile.mvp.MvpView;
import com.example.pub.models.Detail;

public interface DescribeView extends MvpView {
    void setText(String text);
    void setDate(String date);
    void setTime(String time);
    void showCalendar();
    void showClock();
    void sendResult(Detail detail);
    void setCategoryName(String name);
    void setMoney(String money);
    void setLocation(String oldLocation);
    void takePhoto(Detail d);
    void getLocation();
    void loadPhoto(String uri);
    void takePhotoFromGallery();
}
