package com.alz2019.tracker.data;

import com.alz2019.tracker.model.Region;

import java.io.IOException;
import java.util.List;

public interface RegionDao {
    void fetchData();

    List<Region> getAllStats();
}
