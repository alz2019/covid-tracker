package com.alz2019.tracker.service;

import com.alz2019.tracker.model.RegionalStats;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidService {
    private List<RegionalStats> allStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "0 59 10 * * ?")
    public void fetch() throws IOException {
        JSONArray regions = downloadData();
        updateRegionalStats(regions);
    }

    private JSONArray downloadData() throws IOException {
        String rawJSON = new NetworkDAO().request("https://covid19.rosminzdrav.ru/wp-json/api/mapdata/");
        JSONObject root = new JSONObject(rawJSON);
        return root.getJSONArray("Items");
    }

    private void updateRegionalStats(JSONArray regions) {
        List<RegionalStats> newStats = new ArrayList<>();
        for (int i = 0; i < regions.length(); i++) {
            JSONObject jsonRegion = regions.getJSONObject(i);
            RegionalStats regionalStats = new RegionalStats();
            regionalStats.setName(jsonRegion.getString("LocationName"));
            regionalStats.setConfirmed(jsonRegion.getInt("Confirmed"));
            regionalStats.setRecovered(jsonRegion.getInt("Recovered"));
            regionalStats.setDead(jsonRegion.getInt("Deaths"));
            regionalStats.computeActive();
            newStats.add(regionalStats);
        }
        sortRegionsByConfirmedCases(newStats);
        this.allStats = newStats;
    }

    private void sortRegionsByConfirmedCases(List<RegionalStats> list) {
        list.sort((o1, o2) -> {
            if (o1.getConfirmed() == o2.getConfirmed())
                return 0;
            return o1.getConfirmed() < o2.getConfirmed() ? 1 : -1;
        });
    }

    public List<RegionalStats> getAllStats() {
        return allStats;
    }
}
