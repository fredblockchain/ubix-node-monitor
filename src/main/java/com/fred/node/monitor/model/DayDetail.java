package com.fred.node.monitor.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class DayDetail {

    private LocalDate date;
    private long total;
    private ArrayList<Reward> rewards;


    public DayDetail(LocalDate date, long total, ArrayList<Reward> rewards) {
        this.date = date;
        this.total = total;
        this.rewards = rewards;
    }

    public ArrayList<Reward> getRewards() {
        return rewards;
    }

    public long getTotal() {
        return total;
    }

    public LocalDate getDate() {
        return date;
    }
}
