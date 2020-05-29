package com.alz2019.tracker.service;

import com.alz2019.tracker.model.RegionStats;
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
    public List<RegionStats> getAllStats() {
        return allStats;
    }

    private List<RegionStats> allStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "0 59 10 * * ?")
    public void fetch() throws IOException {
        NetworkDAO networkDAO = new NetworkDAO();
        List<RegionStats> newStats = new ArrayList<>();
        String rawJSON = networkDAO.request("https://raw.githubusercontent.com/mediazona/data-corona-Russia/master/data.json");
        JSONObject root = new JSONObject(rawJSON);
        JSONArray regions = root.getJSONArray("data");
        for (int i = 0; i < regions.length(); i++) {
            JSONObject jsonRegion = regions.getJSONObject(i);
            RegionStats regionStats = new RegionStats();
            String name = jsonRegion.getString("name");
            JSONArray confirmedCases = jsonRegion.getJSONArray("confirmed");
            JSONArray deadCases = jsonRegion.getJSONArray("dead");
            JSONArray recoveredCases = jsonRegion.getJSONArray("recovered");
            int confirmed = 0, dead = 0, recovered = 0, active = 0;
            for (int j = 0; j < confirmedCases.length(); j++) {
                confirmed += confirmedCases.getInt(j);
                dead += deadCases.getInt(j);
                recovered += recoveredCases.getInt(j);
            }
            active = confirmed - dead - recovered;
            regionStats.setName(name);
            regionStats.setConfirmed(confirmed);
            regionStats.setActive(active);
            newStats.add(regionStats);
        }
        sortByConfirmedCases(newStats);
        this.allStats = newStats;
    }

    private void sortByConfirmedCases(List<RegionStats> list) {
        list.sort((o1, o2) -> {
            if (o1.getConfirmed() == o2.getConfirmed())
                return 0;
            return o1.getConfirmed() < o2.getConfirmed() ? 1 : -1;
        });
    }
}
