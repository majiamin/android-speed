package com.speed.faster.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by mjm  on 2018/7/14 10:03
 * 登录对象
 */


public class LoginBean implements Serializable {


    /**
     * diamond : 0
     * isCheat : false
     * hasComment : false
     * id : 3030450
     * registerDevice : E3FBCA22-6C3F-41F9-B67A-2CB2B641DS8B
     * devices : ["E3FBCA22-6C3F-41F9-B67A-2CB2B641DS8B"]
     * vipExp : 2018-07-14T08:36:02.166Z
     * updatedAt : 2018-07-14T07:36:02.170Z
     * createdAt : 2018-07-14T07:36:02.166Z
     * username : 053003030450
     * password : jAJz6eJ
     * appId : 1375965573
     * lastVisited : 2018-07-14T07:36:02.170Z
     * superVipExp : null
     * email : null
     * isVip : true
     * isSuperVip : false
     * isNew : true
     */
    private static final long serialVersionUID = -7060210544600464481L;
    private int diamond;
    private boolean isCheat;
    private boolean hasComment;
    private int id;
    private String registerDevice;
    private String vipExp;
    private String updatedAt;
    private String createdAt;
    private String username;
    private String password;
    private String appId;
    private String lastVisited;
    private String superVipExp;
    private String email;
    private boolean isVip;
    private boolean isSuperVip;
    private boolean isNew;
    private List<String> devices;

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public boolean isIsCheat() {
        return isCheat;
    }

    public void setIsCheat(boolean isCheat) {
        this.isCheat = isCheat;
    }

    public boolean isHasComment() {
        return hasComment;
    }

    public void setHasComment(boolean hasComment) {
        this.hasComment = hasComment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegisterDevice() {
        return registerDevice;
    }

    public void setRegisterDevice(String registerDevice) {
        this.registerDevice = registerDevice;
    }

    public String getVipExp() {
        return vipExp;
    }

    public void setVipExp(String vipExp) {
        this.vipExp = vipExp;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(String lastVisited) {
        this.lastVisited = lastVisited;
    }

    public String getSuperVipExp() {
        return superVipExp;
    }

    public void setSuperVipExp(String  superVipExp) {
        this.superVipExp = superVipExp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public boolean isIsSuperVip() {
        return isSuperVip;
    }

    public void setIsSuperVip(boolean isSuperVip) {
        this.isSuperVip = isSuperVip;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }
}

