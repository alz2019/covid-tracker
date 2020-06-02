package com.alz2019.tracker.model;

public class Region {
    private String name;
    private int confirmed;
    private int recovered;
    private int dead;
    private int active;

    private Region() {};

    public static class RegionBuilder {
        private String name;
        private int confirmed;
        private int recovered;
        private int dead;

        public RegionBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RegionBuilder withConfirmed(int confirmed) {
            this.confirmed = confirmed;
            return this;
        }

        public RegionBuilder withRecovered(int recovered) {
            this.recovered = recovered;
            return this;
        }

        public RegionBuilder withDead(int dead) {
            this.dead = dead;
            return this;
        }

        public Region build() {
            Region region = new Region();
            region.name = this.name;
            region.confirmed = this.confirmed;
            region.recovered = this.recovered;
            region.dead = this.dead;
            region.active = this.confirmed - this.recovered - this.dead;

            return region;
        }
    }

    public String getName() {
        return name;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getDead() {
        return dead;
    }

    public int getActive() {
        return active;
    }

}
