package com.dev21.customersupportdesk.models;

/**
 * Created by Prajwal on 03/04/17.
 */

public class CommonBean {
    private String postedBy;
    private String postTitle;
    private String postDescription;
    private String postImage;
    private String postedTime;
    private String postedUserID;
    private String postStatus;
    private String postResponseMessage;
    private boolean isExpanded;
    private String customerMobNumber;
    private String actionID;
    private String actionType;

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getPostedUserID() {
        return postedUserID;
    }

    public void setPostedUserID(String postedUserID) {
        this.postedUserID = postedUserID;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public String getActionID() {
        return actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getCustomerMobNumber() {
        return customerMobNumber;
    }

    public void setCustomerMobNumber(String customerMobNumber) {
        this.customerMobNumber = customerMobNumber;
    }

    public String getPostResponseMessage() {
        return postResponseMessage;
    }

    public void setPostResponseMessage(String postResponseMessage) {
        this.postResponseMessage = postResponseMessage;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
