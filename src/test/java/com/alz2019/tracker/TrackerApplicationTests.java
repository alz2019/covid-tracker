package com.alz2019.tracker;

import com.alz2019.tracker.dao.RegionDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TrackerApplication.class)
class TrackerApplicationTests {
    @Autowired
    RegionDao dao;

    @Test
    void contextLoads() {
    }

    @Test
    void testListSize() {
        assertNotEquals(0, dao.getAllStats().size());
    }

    @Test
    void testListNotContains() {
        assertTrue(dao.getAllStats().stream().noneMatch(region -> region.getName().contains("Гомельская")));
    }
}
