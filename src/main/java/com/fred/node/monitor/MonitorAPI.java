package com.fred.node.monitor;

import com.fred.node.chain.StatusService;
import com.fred.node.chain.data.TransactionService;
import com.fred.node.monitor.model.DailySummary;
import com.fred.node.monitor.model.DayDetail;
import com.fred.node.monitor.model.SyncStatus;
import com.fred.node.ubix.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequestMapping("/")
public class MonitorAPI {

    @Autowired
    @Qualifier("statusService")
    private StatusService statusService;

    @Autowired
    @Qualifier("transactionService")
    private TransactionService dataStore;

    @Autowired
    @Qualifier("rewardsService")
    private RewardsService rewardsService;

    @Autowired
    @Qualifier("monitorService")
    private MonitorService monitorService;


    @GetMapping("/daily/summary")
    public DailySummary getLastSevenDays(@RequestParam(defaultValue = "7") long days) {
        return this.monitorService.getLastDays(days);
    }

    @GetMapping("/daily/detail")
    public DayDetail getDayDetail(@RequestParam(defaultValue = "0") long day) {

        return this.monitorService.getDayDetail(day);
    }

    @GetMapping("/monitor/status")
    public SyncStatus checkStatus() {
        //TODO add nodeID in url
        return statusService.getStatusReport("UBX");
    }
}
