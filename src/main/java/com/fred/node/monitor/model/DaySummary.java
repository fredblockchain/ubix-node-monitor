package com.fred.node.monitor.model;

import java.time.LocalDate;
import java.util.Date;

public class DaySummary {
    private LocalDate day;
    private double reward;

    public DaySummary(LocalDate day, double reward) {
        this.day = day;
        this.reward = reward;
    }

    public double getReward() {
        return reward;
    }

    public LocalDate getDay() {
        return day;
    }

}
