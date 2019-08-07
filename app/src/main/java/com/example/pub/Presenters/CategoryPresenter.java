package com.example.pub.Presenters;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pub.Models.Categories;
import com.example.pub.views.CategoryView;
import com.example.pub.views.ViewHolderInt;

@InjectViewState
public class CategoryPresenter extends MvpPresenter<CategoryView> {

    public CategoryPresenter(){
    }

    public void onBindViewHolder(ViewHolderInt holder, int position){
        holder.setTitle(Categories.loadList().keySet().toArray()[position].toString());
        holder.setImage(Categories.loadList().get(Categories.loadList().keySet().toArray()[position]));
    }

    public int getCount(){
        return Categories.loadList().size();
    }

    public void categoryIsSelected(int position){
        getViewState().setCategory(Categories.loadList().keySet().toArray()[position].toString());
    }
}
