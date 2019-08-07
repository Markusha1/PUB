package com.example.pub.Presenters;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pub.DI.AppComponent;
import com.example.pub.DI.AppModule;
import com.example.pub.DI.DaggerAppComponent;
import com.example.pub.DI.RoomModule;
import com.example.pub.Models.Budget;
import com.example.pub.Models.BudgetListRepository;
import com.example.pub.Models.Categories;
import com.example.pub.Models.Detail;
import com.example.pub.Models.DetailListRepository;
import com.example.pub.R;
import com.example.pub.views.DetailView;
import com.example.pub.views.DetailViewHolder;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;



@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {
    private Budget mBudget;
    @Inject
    public DetailListRepository repository;
    @Inject
    public BudgetListRepository mRepository;


    public DetailPresenter(Context context){
        AppComponent component = DaggerAppComponent.builder().appModule(new AppModule(context)).roomModule(new RoomModule()).build();
        component.inject(this);
    }

    public void initBudget(Serializable s){
       mBudget = (Budget) s;
    }

    public List<Detail> loadDetails(){
        return repository.getAll(mBudget.getIdentifier());
    }

    public void addNewDetail(){
        getViewState().createDetail();
    }

    public void updateDetail(Detail d){
        repository.updateDetail(d);
    }

    public void add(Detail detail){
        detail.setId(mBudget.getIdentifier());
        repository.addDetail(detail);
    }

    public void onBindHolder(DetailViewHolder holder, int position){
        holder.setMoney(loadDetails().get(position).getMoney());
        holder.setDate(loadDetails().get(position).getDate());
        holder.setCategory(loadDetails().get(position).getCategory());
        holder.setImage(Categories.loadList().get(loadDetails().get(position).getCategory()));
    }
    public int getCount(){
        return loadDetails().size();
    }

    public void detailSelected(int position){
        getViewState().openDescription(loadDetails().get(position));
    }

    public void setCurrentBalance(String money){
        float balance = Float.parseFloat(mBudget.getBudgetMoney()) + Float.parseFloat(money);
        mBudget.setBudgetMoney(String.valueOf(balance));
        getViewState().setCurrentBalance(String.valueOf(balance));
        mRepository.updateBudget(mBudget);
    }

    public void setInitBalances(){
        float initBalance = Float.parseFloat(mBudget.getBudgetMoney());
        List<Detail> list = loadDetails();
        for (Detail d : list){
            initBalance-=  Float.parseFloat(d.getMoney());

        }
        getViewState().setInitBalance(String.valueOf(initBalance));
    }
    public String getBalance(){
        return mBudget.getBudgetMoney();
    }

    public Budget getBudget(){
        return mBudget;
    }

    public void selectDetail(int position, View view){
        Detail detail = loadDetails().get(position);
        PopupMenu menu = new PopupMenu(view.getContext(), view);
        menu.inflate(R.menu.delete_detail_item);
        menu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete) {
                loadDetails().remove(position);
                repository.deleteDetail(detail);
                mBudget.setBudgetMoney(String.valueOf(Float.parseFloat(mBudget.getBudgetMoney()) - Float.parseFloat(detail.getMoney())));
                getViewState().setCurrentBalance(mBudget.getBudgetMoney());
                mRepository.updateBudget(mBudget);
                getViewState().updateAdapter();
                setUpPieChart();
                return true;
            }
            return false;
        });
        menu.show();
    }

    public String getTitle(){
        return mBudget.getBudgetText();
    }

    private void setUpPieChart(){
        getViewState().showPieChart();
    }
}
