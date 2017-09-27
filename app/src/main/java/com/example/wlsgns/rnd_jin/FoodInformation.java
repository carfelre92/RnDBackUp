package com.example.wlsgns.rnd_jin;

/**
 * Created by wlsgn on 2017-09-27.
 */

public class FoodInformation {

    public FoodInformation(String name, String info){
        this.name = name;
        this.info = info;
    }


    public String name;
    public String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
