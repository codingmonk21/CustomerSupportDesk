package com.dev21.customersupportdesk.models;

import java.io.Serializable;

/**
 * Created by Prajwal on 02/04/17.
 */

public class FeedbackBean implements Serializable {
    private String feedbackTitle;
    private String feedbackDescription;
    private String feedbackImage;
    private String feedbackID;
    private String userID;
    private String feedbackFrom;
    private String customerMobNumber;
    private String feedbackStatus;
    private String feedbackResponseMessage;
    private String feedbackTimestamp;

    public String getFeedbackFrom() {
        return feedbackFrom;
    }

    public void setFeedbackFrom(String feedbackFrom) {
        this.feedbackFrom = feedbackFrom;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getFeedbackDescription() {
        return feedbackDescription;
    }

    public void setFeedbackDescription(String feedbackDescription) {
        this.feedbackDescription = feedbackDescription;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFeedbackTimestamp() {
        return feedbackTimestamp;
    }

    public void setFeedbackTimestamp(String feedbackTimestamp) {
        this.feedbackTimestamp = feedbackTimestamp;
    }

    public String getFeedbackImage() {
        return feedbackImage;
    }

    public void setFeedbackImage(String feedbackImage) {
        this.feedbackImage = feedbackImage;
    }

    public String getCustomerMobNumber() {
        return customerMobNumber;
    }

    public void setCustomerMobNumber(String customerMobNumber) {
        this.customerMobNumber = customerMobNumber;
    }

    public String getFeedbackResponseMessage() {
        return feedbackResponseMessage;
    }

    public void setFeedbackResponseMessage(String feedbackResponseMessage) {
        this.feedbackResponseMessage = feedbackResponseMessage;
    }
}
