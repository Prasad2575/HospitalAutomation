package com.hospital.model;

public class HospitalInfo {

    private int sNo;
    private String name;
    private int beds;
    private double rating;
    private String phone;
    private String url;

    public HospitalInfo(int sNo, String name, int beds, double rating, String phone, String url) {
        this.sNo = sNo;
        this.name = name;
        this.beds = beds;
        this.rating = rating;
        this.phone = phone;
        this.url = url;
    }

    public int getsNo() { return sNo; }
    public String getName() { return name; }
    public int getBeds() { return beds; }
    public double getRating() { return rating; }
    public String getPhone() { return phone; }
    public String getUrl() { return url; }

    @Override
    public String toString() {
        return sNo + ") " + name +
                "\n   Beds   : " + beds +
                "\n   Rating : " + rating +
                "\n   Phone  : " + phone +
                "\n   URL    : " + url;
    }
}