package com.android.mobileattendance;

public class EIleave {
    private String startDate,endDate,reason,image;

    public EIleave(String startDate, String endDate, String reason, String image) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.image = image;
    }

    public EIleave(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
