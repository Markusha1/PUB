package com.example.pub.DI;



import com.example.pub.Presenters.BudgetListPresenter;
import com.example.pub.Presenters.DetailPresenter;
import com.example.pub.activities.BudgetsActivity;
import com.example.pub.activities.DetailActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {RoomModule.class, AppModule.class})
public interface AppComponent {

    void inject(BudgetListPresenter presenter);

    void inject(DetailPresenter presenter);
}
