package com.android.mobileattendance;

public class Data {
    private String StaffId,Fullname,Position,Division,Present,Cuti,Absent;
    public String temp;
    public Data(String StaffId, String Fullname, String Position, String Division, String Present, String Cuti, String Absent, String temp){
        this.StaffId = StaffId;
        this.Fullname = Fullname;
        this.Position = Position;
        this.Division = Division;
        this.Present = Present;
        this.Cuti = Cuti;
        this.Absent = Absent;
        this.temp = temp;
    }

    public Data(){}

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String staffId) {
        StaffId = staffId;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getPresent() {
        return Present;
    }

    public void setPresent(String present) {
        Present = present;
    }

    public String getAbsent() {
        return Absent;
    }

    public void setAbsent(String absent) {
        Absent = absent;
    }

    public String getCuti() {
        return Cuti;
    }

    public void setCuti(String cuti) {
        Cuti = cuti;
    }
}
