package com.dev21.customersupportdesk.models;

/**
 * Created by Prajwal on 31/03/17.
 */

public class UserDetail {
    private String userName;
    private String userEmailID;
    private String userMobileNum;
    private String createdAt;
    private String profileCompleted;

    public UserDetail() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmailID() {
        return userEmailID;
    }

    public void setUserEmailID(String userEmailID) {
        this.userEmailID = userEmailID;
    }

    public String getUserMobileNum() {
        return userMobileNum;
    }

    public void setUserMobileNum(String userMobileNum) {
        this.userMobileNum = userMobileNum;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(String profileCompleted) {
        this.profileCompleted = profileCompleted;
    }
}
