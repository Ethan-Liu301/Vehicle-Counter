package com.example.npatel.vehicalcounter;

public class vehical_count {

    private String date;
    private int twc, fwc;

    public vehical_count(String date, int twc, int fwc) {
        this.date = date;
        this.twc = twc;
        this.fwc = fwc;

    }

    public String gDate() {
        return date;
    }

    public int gtwc() {
        return twc;
    }

    public int gfwc() {
        return fwc;
    }
}


