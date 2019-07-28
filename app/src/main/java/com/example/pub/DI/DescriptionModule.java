package com.example.pub.DI;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DescriptionModule {

    @Singleton
    @Provides
    Calendar provideCalendar(){
        return Calendar.getInstance();
    }

    @Singleton
    @Provides
    GoogleApiClient provideGoogleApi(Context context){
        return new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
    }
}
