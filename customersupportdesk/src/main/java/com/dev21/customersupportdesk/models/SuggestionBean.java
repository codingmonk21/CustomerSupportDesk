package com.dev21.customersupportdesk.models;

import java.io.Serializable;

/**
 * Created by Prajwal on 02/04/17.
 */

public class SuggestionBean implements Serializable {
    private String suggestionTitle;
    private String suggestionDescription;
    private String suggestionImage;
    private String suggestionID;
    private String userID;
    private String suggestionFrom;
    private String customerMobNumber;
    private String suggestionStatus;
    private String suggestionResponseMessage;
    private String suggestionTimeStamp;

    public void setSuggestionDescription(String suggestionDescription) {
        this.suggestionDescription = suggestionDescription;
    }

    public void setSuggestionFrom(String suggestionFrom) {
        this.suggestionFrom = suggestionFrom;
    }

    public String getSuggestionDescription() {
        return this.suggestionDescription;
    }

    public String getSuggestionFrom() {
        return this.suggestionFrom;
    }

    public String getSuggestionID() {
        return suggestionID;
    }

    public void setSuggestionID(String suggestionID) {
        this.suggestionID = suggestionID;
    }

    public String getSuggestionTitle() {
        return suggestionTitle;
    }

    public void setSuggestionTitle(String suggestionTitle) {
        this.suggestionTitle = suggestionTitle;
    }

    public String getSuggestionStatus() {
        return suggestionStatus;
    }

    public void setSuggestionStatus(String suggestionStatus) {
        this.suggestionStatus = suggestionStatus;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSuggestionTimeStamp() {
        return suggestionTimeStamp;
    }

    public void setSuggestionTimeStamp(String suggestionTimeStamp) {
        this.suggestionTimeStamp = suggestionTimeStamp;
    }

    public String getCustomerMobNumber() {
        return customerMobNumber;
    }

    public void setCustomerMobNumber(String customerMobNumber) {
        this.customerMobNumber = customerMobNumber;
    }

    public String getSuggestionImage() {
        return suggestionImage;
    }

    public void setSuggestionImage(String suggestionImage) {
        this.suggestionImage = suggestionImage;
    }

    public String getSuggestionResponseMessage() {
        return suggestionResponseMessage;
    }

    public void setSuggestionResponseMessage(String suggestionResponseMessage) {
        this.suggestionResponseMessage = suggestionResponseMessage;
    }
}
