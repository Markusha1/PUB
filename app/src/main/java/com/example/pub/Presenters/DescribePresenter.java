package com.example.pub.Presenters;


import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pub.Models.Detail;
import com.example.pub.views.DescribeView;

import java.io.File;


@InjectViewState
public class DescribePresenter extends MvpPresenter<DescribeView> {
    private Detail mDetail;

    public void initDetail(Detail detail){
        mDetail = detail;
        if(mDetail.getDescription() != null) getViewState().setText(mDetail.getDescription());
        if(mDetail.getCategory() != null) getViewState().setCategoryName(mDetail.getCategory());
        if(mDetail.getDate() != null) getViewState().setDate(mDetail.getDate());
        if(mDetail.getTime() != null) getViewState().setTime(mDetail.getTime());
        if(mDetail.getMoney() != null) getViewState().setMoney(mDetail.getMoney());
        if(mDetail.getMyLocation() != null) getViewState().setLocation(mDetail.getMyLocation());
        if(mDetail.getPhotoPath() != null) getViewState().loadPhoto(mDetail.getPhotoPath());
    }

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


    public void showDatePicker(){
        getViewState().showCalendar();
    }

    public void showTimePicker(){
        getViewState().showClock();
    }

    public void detailCategory(String category){
        mDetail.setCategory(category);
        getViewState().setCategoryName(category);
    }

    public void cameraOnClick(){
        getViewState().takePhoto(mDetail);
    }

    public void gallaryOnClick(){
        getViewState().takePhotoFromGallery();
    }

    public void geoOnClick(){
        getViewState().getLocation();
    }

    public File getPhotoFile(Context context) {
        File filesDir = context.getFilesDir();
        return new File(filesDir, mDetail.getPhotoFileName());
    }
}
