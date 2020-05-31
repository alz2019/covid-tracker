package com.alz2019.tracker.model;

public class RegionalStats {
    private String name;
    private int confirmed;
    private int recovered;
    private int dead;
    private int active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDead() {
        return dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public int getActive() {
        return active;
    }

    public void computeActive() {
        this.active = this.confirmed - this.recovered - this.dead;
    }

}
