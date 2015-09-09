package com.example.fanilo.weatherapp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by fanilo on 9/7/15.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApp {
}
