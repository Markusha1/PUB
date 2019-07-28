package com.example.pub.DI;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext(){
        return context;
    }
}
