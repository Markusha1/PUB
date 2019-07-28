package com.example.pub.Models;

import com.example.pub.DataBase.DetailDao;

import java.util.List;

import javax.inject.Inject;

public class DetailListRepository implements DetailRepository{
    private DetailDao detailDao;

    @Inject
    public DetailListRepository(DetailDao detailDao){
        this.detailDao = detailDao;
    }

    @Override
    public void updateDetail(Detail detail) {
        detailDao.update(detail);
    }

    @Override
    public void addDetail(Detail detail) {
        detailDao.addDetail(detail);
    }

    @Override
    public void deleteDetail(Detail detail) {
        detailDao.deleteDetail(detail);
    }

    @Override
    public Budget getDetail(String id) {
        return null;
    }

    @Override
    public List<Detail> getAll(String id) {
        return detailDao.getAll(id);
    }
}
