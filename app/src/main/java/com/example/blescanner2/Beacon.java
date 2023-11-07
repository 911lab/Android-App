package com.example.blescanner2;

public class Beacon {
    private String address;
    private double rssi;
    private String now;
    private double distance;

    public Beacon(String address, int rssi, String now, double distance) {
        this.address = address;
        this.rssi = rssi;
        this.now = now;
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public double getRssi() {
        return rssi;
    }

    public String getNow() {
        return now;
    }

    public double getDistance() {
        return distance;
    }
}
