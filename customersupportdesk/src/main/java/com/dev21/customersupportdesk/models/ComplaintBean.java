package com.dev21.customersupportdesk.models;

import java.io.Serializable;

/**
 * Created by Prajwal on 02/04/17.
 */

public class ComplaintBean implements Serializable {
    private String complaintTitle;
    private String complaintDescription;
    private String complaintImage;
    private String complaintResponseMessage;
    private String complaintID;
    private String complaintFrom;
    private String userID;
    private String customerMobNumber;
    private String complaintStatus;
    private String complaintTimeStamp;

    public ComplaintBean() {

    }

    public ComplaintBean(String complaintDescription, String complaintID, String complaintFrom) {
        this.complaintDescription = complaintDescription;
        this.complaintID = complaintID;
        this.complaintFrom = complaintFrom;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getComplaintFrom() {
        return complaintFrom;
    }

    public void setComplaintFrom(String complaintFrom) {
        this.complaintFrom = complaintFrom;
    }

    public String getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        this.complaintTitle = complaintTitle;
    }

    public String getComplaintTimeStamp() {
        return complaintTimeStamp;
    }

    public void setComplaintTimeStamp(String complaintTimeStamp) {
        this.complaintTimeStamp = complaintTimeStamp;
    }

    public String getCustomerMobNumber() {
        return customerMobNumber;
    }

    public void setCustomerMobNumber(String customerMobNumber) {
        this.customerMobNumber = customerMobNumber;
    }

    public String getComplaintImage() {
        return complaintImage;
    }

    public void setComplaintImage(String complaintImage) {
        this.complaintImage = complaintImage;
    }

    public String getComplaintResponseMessage() {
        return complaintResponseMessage;
    }

    public void setComplaintResponseMessage(String complaintResponseMessage) {
        this.complaintResponseMessage = complaintResponseMessage;
    }
}
