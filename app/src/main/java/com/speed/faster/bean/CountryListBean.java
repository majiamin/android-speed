package com.speed.faster.bean;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


//国家
public class CountryListBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<CountryBean> countryBeanList;//存国家信息

    public ArrayList<CountryBean> getCountryBeanList() {
        return countryBeanList;
    }

    public void setCountryBeanList(ArrayList<CountryBean> countryBeanList) {
        this.countryBeanList = countryBeanList;
    }
}
