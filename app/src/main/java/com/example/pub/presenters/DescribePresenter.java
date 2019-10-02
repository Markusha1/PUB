package com.example.pub.presenters;


import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pub.models.Detail;
import com.example.pub.views.DescribeView;

import java.io.File;


@InjectViewState
public class DescribePresenter extends MvpPresenter<DescribeView> {
    private Detail mDetail;
    //устанавливает новые или уже существующие параметры описания
    public void initDetail(Detail detail){
        mDetail = detail;
        if(mDetail.getDescription() != null) getViewState().setText(mDetail.getDescription());
        if(mDetail.getCategory() != null) getViewState().setCategoryName(mDetail.getCategory());
        if(mDetail.getDate() != null) getViewState().setDate(mDetail.getDate());
        if(mDetail.getTime() != null) getViewState().setTime(mDetail.getTime());
        if(mDetail.getMoney() != null) getViewState().setMoney(mDetail.getMoney());
        if(!mDetail.getMyLocation().equals("")) getViewState().setLocation(mDetail.getMyLocation());
        if(!mDetail.getPhotoPath().equals("")) getViewState().loadPhoto(mDetail.getPhotoPath());
    }
    //принять изменения в описании
    public void onApplyClick(String descr, String category, String date, String time, String money, String location, String photoPath){
        mDetail.setDate(date);
        mDetail.setTime(time);
        mDetail.setMyLocation(location);
        if(photoPath == null) mDetail.setPhotoPath("");
        else mDetail.setPhotoPath(photoPath);
        if(category.equals("Категория")) mDetail.setCategory("Прочее");
        else mDetail.setCategory(category);
        mDetail.setDescription(descr);
        if(money.length() == 0) mDetail.setMoney("0");
        else mDetail.setMoney(String.valueOf(Float.parseFloat(money)));
        getViewState().sendResult(mDetail);
    }

    //показать календарь
    public void showDatePicker(){
        getViewState().showCalendar();
    }
    //показать часы
    public void showTimePicker(){
        getViewState().showClock();
    }
    //открывает выбор категории
    public void detailCategory(String category){
        mDetail.setCategory(category);
        getViewState().setCategoryName(category);
    }
    //включить камеру
    public void cameraOnClick(){
        getViewState().takePhoto(mDetail);
    }
    //открыть галерею
    public void gallaryOnClick(){
        getViewState().takePhotoFromGallery();
    }
    //получить координаты
    public void geoOnClick(){
        getViewState().getLocation();
    }
    //получить уникальное имя файла
    public File getPhotoFile(Context context) {
        File filesDir = context.getFilesDir();
        return new File(filesDir, mDetail.getPhotoFileName());
    }
}
