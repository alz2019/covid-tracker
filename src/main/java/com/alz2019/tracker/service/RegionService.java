package com.alz2019.tracker.service;

import com.alz2019.tracker.model.Region;

import java.util.List;

public interface RegionService {
    /**
     * Retrieves and returns all stats
     *
     * @return list of regions with stats
     */
    List<Region> getAllStats();
}