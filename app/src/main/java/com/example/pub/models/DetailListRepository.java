package com.example.pub.models;

import com.example.pub.database.DetailDao;

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
    public List<Detail> getAll(String id) {
        return detailDao.getAll(id);
    }
}
