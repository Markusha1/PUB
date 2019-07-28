package com.example.pub.DI;

import com.example.pub.activities.DescribeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DescriptionModule.class})
public interface DescribeComponent {

    void inject(DescribeActivity activity);
}
