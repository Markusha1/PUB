package com.example.pub.presenters;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pub.models.Categories;
import com.example.pub.views.CategoryView;
import com.example.pub.views.ViewHolderInt;

@InjectViewState
public class CategoryPresenter extends MvpPresenter<CategoryView> {

    public CategoryPresenter(){
    }

    public void onBindViewHolder(ViewHolderInt holder, int position){
            holder.setTitle(Categories.loadDetailList().keySet().toArray()[position].toString());
            holder.setImage(Categories.loadDetailList().get(Categories.loadDetailList().keySet().toArray()[position]));
    }

    public int getCount(){
        return Categories.loadDetailList().size();
    }

    public void categoryIsSelected(int position) {
        getViewState().setCategory(Categories.loadDetailList().keySet().toArray()[position].toString());
    }
}
