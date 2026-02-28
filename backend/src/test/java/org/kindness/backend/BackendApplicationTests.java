package org.kindness.backend;

import org.junit.jupiter.api.Test;
import org.kindness.api.dao.BaseDao;
import org.kindness.api.model.impl.Zone;
import org.kindness.backend.dao.JdbcZoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private JdbcZoneDao zoneDao;

    @Test
    void contextLoads() {
        Zone zone = new Zone("Mingi nimi", 52);

        zoneDao.insert(zone);

        List<Zone> allZones = zoneDao.findAll();
        Zone savedZone = allZones.getLast();

        assertNotNull("Id should be presented", savedZone.getZoneId());
        assertEquals("Names should be same", zone.getName(), savedZone.getName());
        assertEquals("Capacity should be same", zone.getCapacity(), savedZone.getCapacity());
    }

}
