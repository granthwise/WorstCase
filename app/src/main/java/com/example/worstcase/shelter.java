package com.example.worstcase;

//This was made

public class shelter {
    public int number;
    public double lat;
    public double longi;
    public int food;
    public int water;
    public int medicine;
    public boolean capacity;
    public String name;

    public shelter(int number, double lat, double longi, int food, int water, int medicine, String tempCapacity, String name) {
        this.number = number;
        this.lat = lat;
        this.longi = longi;
        this.food = food;
        this.water = water;
        this.medicine = medicine;
        if (tempCapacity == "true") {
            capacity = true;
        }
        else {
            capacity = false;
        }
        this.name = name;
    }

    public double getLat(){
        return lat;
    }

    public double getLongi(){
        return longi;
    }

    public int getFood() {
        return food;
    }

    public int getWater() {
        return water;
    }

    public int getMedicine() {
        return medicine;
    }

    public String getStringFood() {
        if (food == 0) {
            return "None";
        }
        else if (food == 1) {
            return "Low";
        }
        else {
            return "Full";
        }
    }

    public String getStringWater() {
        if (food == 0) {
            return "None";
        }
        else if (food == 1) {
            return "Low";
        }
        else {
            return "Full";
        }
    }

    public String getStringMedicine() {
        if (food == 0) {
            return "None";
        }
        else if (food == 1) {
            return "Low";
        }
        else {
            return "Full";
        }
    }



    public boolean getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }
}
