package com.fred.node.monitor;

import com.fred.node.monitor.model.Reward;

import java.util.Comparator;

public class TransactionComparator implements Comparator<Reward> {

    @Override
    public int compare(Reward reward, Reward r1) {
        return reward.getDate().compareTo(r1.getDate());
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
