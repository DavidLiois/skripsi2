package com.android.mobileattendance;

public class presensi {
    private String Date,clockIn,clockOut,istirahat,afterBreak;
    public presensi(String Date, String clockIn, String clockOut, String istirahat, String afterBreak) {
        this.Date = Date;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.istirahat = istirahat;
        this.afterBreak = afterBreak;
    }

    public presensi(){}

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getClockIn() {
        return clockIn;
    }

    public void setClockIn(String clockIn) {
        this.clockIn = clockIn;
    }

    public String getClockOut() {
        return clockOut;
    }

    public void setClockOut(String clockOut) {
        this.clockOut = clockOut;
    }

    public String getIstirahat() {
        return istirahat;
    }

    public void setIstirahat(String istirahat) {
        this.istirahat = istirahat;
    }

    public String getAfterBreak() {
        return afterBreak;
    }

    public void setAfterBreak(String afterBreak) {
        this.afterBreak = afterBreak;
    }
}
