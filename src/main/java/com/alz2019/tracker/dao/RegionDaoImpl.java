package com.alz2019.tracker.dao;

import com.alz2019.tracker.exception.RegionDaoException;
import com.alz2019.tracker.model.Region;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@Service
public class RegionDaoImpl implements RegionDao {
    private List<Region> allStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "0 35 10 * * ?")
    public void fetchData() {
        JSONArray regions = downloadData();

        updateRegionalStats(regions);
    }

    private JSONArray downloadData() {
        String rawJson = "";
        try {
            rawJson = new NetworkDao().request("https://covid19.rosminzdrav.ru/wp-json/api/mapdata/");
        } catch (IOException e) {
            throw new RegionDaoException("The requested data is not available", e);
        }
        JSONObject root = new JSONObject(rawJson);
        return root.getJSONArray("Items");
    }

    private void updateRegionalStats(JSONArray regions) {
        List<Region> newStats = new ArrayList<>();
        for (int i = 0; i < regions.length(); i++) {
            JSONObject jsonRegion = regions.getJSONObject(i);
            Region regionalStats = new Region
                    .RegionBuilder()
                    .withName(jsonRegion.getString("LocationName"))
                    .withConfirmed(jsonRegion.getInt("Confirmed"))
                    .withRecovered(jsonRegion.getInt("Recovered"))
                    .withDead(jsonRegion.getInt("Deaths"))
                    .build();
            newStats.add(regionalStats);
        }
        this.allStats = sortRegionsByConfirmedCases(newStats);
    }

    private List<Region> sortRegionsByConfirmedCases(List<Region> regions) {
        return regions.stream()
                .filter(region -> !region.getName().equals("No region speified"))
                .sorted(comparingInt(Region::getConfirmed).reversed())
                .collect(toList());
    }

    public List<Region> getAllStats() {
        return allStats;
    }
}
