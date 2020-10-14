package com.alz2019.tracker.dao;

import com.alz2019.tracker.model.Region;

import java.util.List;

public interface RegionDao {
    /**
     * Fetches data from JSON. Updates regional stats
     */
    void fetchData();

    /**
     * Retrieves and returns all stats
     *
     * @return list of regions with stats
     */
    List<Region> getAllStats();
}
