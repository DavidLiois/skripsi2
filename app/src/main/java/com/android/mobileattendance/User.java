package com.android.mobileattendance;

public class User {
    public String id, jabatan, divisi, username, fullname, jeniskelamin, usernote, photo, pob, dob, alamat, email, phonenumber, active;
    public String temp;

    public User(String id, String jabatan, String divisi, String username, String fullname, String jeniskelamin, String usernote, String photo, String pob, String dob, String alamat, String email, String phonenumber, String active, String temp) {
        this.id = id;
        this.jabatan = jabatan;
        this.divisi = divisi;
        this.username = username;
        this.fullname = fullname;
        this.jeniskelamin = jeniskelamin;
        this.usernote = usernote;
        this.photo = photo;
        this.pob = pob;
        this.dob = dob;
        this.alamat = alamat;
        this.email = email;
        this.phonenumber = phonenumber;
        this.active = active;
        this.temp = temp;
    }

    public User(){}

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getJeniskelamin() {
        return jeniskelamin;
    }

    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public String getUsernote() {
        return usernote;
    }

    public void setUsernote(String usernote) {
        this.usernote = usernote;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
