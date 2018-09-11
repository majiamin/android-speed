package com.speed.faster.bean;


import java.io.Serializable;

/**
 * Created by mjm  on 2018/7/13 11:22
 * 国家选择
 */


public class CountryBean implements Serializable {

    /**
     * name : 英国
     * code : GB
     * enable : true
     */

    private String name;
    private String code;
    private boolean enable;
    private  boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
