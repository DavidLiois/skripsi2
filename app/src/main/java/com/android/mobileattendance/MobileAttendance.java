package com.android.mobileattendance;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class MobileAttendance {
    @SerializedName("StaffId")
    private int StaffId;
    @SerializedName("jabatan")
    private String jabatan;
    @SerializedName("divisi")
    private String divisi;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("username")
    private String username;
    @SerializedName("jeniskelamin")
    private String jeniskelamin;
    @SerializedName("pob")
    private String pob;
    @SerializedName("dob")
    private String dob;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("email")
    private String email;
    @SerializedName("phonenumber")
    private String phonenumber;
    @SerializedName("usernote")
    private String usernote;

    @SerializedName("image")
    private Image image;

    @SerializedName("password")
    private String password;
    @SerializedName("passwordA")
    private String passwordA;
    @SerializedName("passwordQ")
    private String passwordQ;

    @SerializedName("ipnumber")
    private String ipnumber;
    @SerializedName("longtitude")
    private Double longtitude;
    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("Datang")
    private Boolean Datang;
    @SerializedName("JamDatang")
    private String JamDatang;
    @SerializedName("Terlambat")
    private Boolean Terlambat;
    @SerializedName("JamTerlambat")
    private String JamTerlambat;
    @SerializedName("Istirahat")
    private Boolean Istirahat;
    @SerializedName("MulaiIstirahat")
    private String MulaiIstirahat;
    @SerializedName("SelesaiIstirahat")
    private String SelesaiIstirahat;
    @SerializedName("Pulang")
    private Boolean Pulang;
    @SerializedName("JamPulang")
    private String JamPulang;
    @SerializedName("Absen")
    private Boolean Absen;
    @SerializedName("AlasanAbsen")
    private String AlasanAbsen;
    @SerializedName("Longtitude")
    private Double Longtitude;
    @SerializedName("Latitude")
    private Double Latitude;

    @SerializedName("jatahcuti")
    private String jatahcuti;
    @SerializedName("izincuti")
    private String izincuti;
    @SerializedName("alasanizincuti")
    private String alasanizincuti;
    @SerializedName("mulaicuti")
    private String mulaicuti;
    @SerializedName("selesaicuti")
    private String selesaicuti;

    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;



}
