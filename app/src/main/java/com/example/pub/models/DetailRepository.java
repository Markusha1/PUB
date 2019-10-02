package com.example.pub.models;

import java.util.List;

public interface DetailRepository {
    void updateDetail(Detail detail);
    void addDetail(Detail detail);
    void deleteDetail(Detail detail);
    List<Detail> getAll(String id);
}
