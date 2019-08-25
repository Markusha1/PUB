package com.example.pub.di;



import com.example.pub.presenters.BudgetListPresenter;
import com.example.pub.presenters.DetailPresenter;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {RoomModule.class, AppModule.class})
public interface AppComponent {

    void inject(BudgetListPresenter presenter);

    void inject(DetailPresenter presenter);
}
