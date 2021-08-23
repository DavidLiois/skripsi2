package com.android.mobileattendance;

public class attendanceinfomodel {
    private String Date2, JamDatang, MulaiIstirahat, SelesaiIstirahat, JamPulang;

    public attendanceinfomodel(String date2, String jamDatang, String mulaiIstirahat, String selesaiIstirahat, String jamPulang) {
        Date2 = date2;
        JamDatang = jamDatang;
        MulaiIstirahat = mulaiIstirahat;
        SelesaiIstirahat = selesaiIstirahat;
        JamPulang = jamPulang;
    }

    public attendanceinfomodel(){}

    public String getDate2() {
        return Date2;
    }

    public void setDate2(String date2) {
        Date2 = date2;
    }

    public String getJamDatang() {
        return JamDatang;
    }

    public void setJamDatang(String jamDatang) {
        JamDatang = jamDatang;
    }

    public String getMulaiIstirahat() {
        return MulaiIstirahat;
    }

    public void setMulaiIstirahat(String mulaiIstirahat) {
        MulaiIstirahat = mulaiIstirahat;
    }

    public String getSelesaiIstirahat() {
        return SelesaiIstirahat;
    }

    public void setSelesaiIstirahat(String selesaiIstirahat) {
        SelesaiIstirahat = selesaiIstirahat;
    }

    public String getJamPulang() {
        return JamPulang;
    }

    public void setJamPulang(String jamPulang) {
        JamPulang = jamPulang;
    }
}
