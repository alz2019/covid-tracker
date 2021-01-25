package com.alz2019.tracker.service;

import com.alz2019.tracker.model.Region;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
class RegionServiceImplTest {
    RegionServiceImpl regionService;

    @Test
    public void testContains() throws IOException {
        String s = "{\n" +
                "  \"Items\": [\n" +
                "    {\n" +
                "      \"LocationName\": \"Тестовая область\",\n" +
                "      \"Lat\": 55.3673997,\n" +
                "      \"Lng\": 36.1563454,\n" +
                "      \"Observations\": 0,\n" +
                "      \"Confirmed\": 133645,\n" +
                "      \"Recovered\": 94369,\n" +
                "      \"Deaths\": 2611,\n" +
                "      \"IsoCode\": \"RU-MOS\",\n" +
                "      \"New\": false\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        regionService = Mockito.spy(new RegionServiceImpl());
        doReturn(s).when(regionService).getRawJson();
        regionService.fetchData();
        Assertions.assertTrue(regionService.getAllStats().stream().map(Region::getName).anyMatch(n -> n.equals("Тестовая область")));
    }
}