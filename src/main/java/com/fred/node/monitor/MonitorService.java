package com.fred.node.monitor;

import com.fred.node.chain.StatusService;
import com.fred.node.chain.data.TransactionService;
import com.fred.node.monitor.model.*;
import com.fred.node.monitor.price.PriceService;
import com.fred.node.ubix.RewardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Service
public class MonitorService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private long offsetMinutes;

    @Autowired
    @Qualifier("statusService")
    private StatusService statusService;

    @Autowired
    @Qualifier("transactionService")
    private TransactionService dataStore;

    @Autowired
    @Qualifier("rewardsService")
    private RewardsService rewardsService;

    @Qualifier("priceService")
    public PriceService priceService;

    public MonitorService(@Value("${global.time.utc.offset}") long offsetMinutes) {
        this.offsetMinutes = offsetMinutes;
    }

    public DailySummary getLastDays(long days) {
        ArrayList<DaySummary> res = new ArrayList<DaySummary>();

        for(int i = 0; i<days; i++) {
            DayDetail dd = this.rewardsService.getInfoForDay(i);
            long total = 0;
            for(Reward r: dd.getRewards()) {
                total += r.getValue();
            }
            DaySummary ds = new DaySummary(dd.getDate(), total);
            res.add(ds);
        }

        return new DailySummary(res);
    }

    public DayDetail getDayDetail(long day) {

        DayDetail res = this.rewardsService.getInfoForDay(day);
        for(Reward r : res.getRewards()) {
            r.setDate(r.getDate().plus(this.offsetMinutes, ChronoUnit.MINUTES) );
        }

        return res;
    }

    public SyncStatus checkStatus(String nodeID) {
        SyncStatus status = statusService.getStatusReport(nodeID);
        status.setLastSync(status.getLastSync()+this.offsetMinutes*60);
        status.setLastSuccessSync(status.getLastSuccessSync()+this.offsetMinutes*60);
        return status;
    }

}
