package com.fred.node.monitor.model;

import java.util.ArrayList;

public class DailySummary {

    private ArrayList<DaySummary> days;

    public DailySummary(ArrayList<DaySummary> days) {
        this.days = days;
    }

    public ArrayList<DaySummary> getDays() {
        return days;
    }
}
