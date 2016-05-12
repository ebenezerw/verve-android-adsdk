package com.vervewireless.mastersampleapplication;

import android.app.LauncherActivity;

public class SampleItem extends LauncherActivity.ListItem {

    private Class target;
    private String name;

    public SampleItem(String name) {
        this.name = name;
    }

    public SampleItem(Class target, String name) {
        this.target = target;
        this.name = name;
    }

    public Class getTarget() {
        return target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
