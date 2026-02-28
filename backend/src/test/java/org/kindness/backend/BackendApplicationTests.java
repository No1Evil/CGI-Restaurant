package org.kindness.backend;

import org.junit.jupiter.api.Test;
import org.kindness.api.dao.BaseDao;
import org.kindness.api.model.impl.User;
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
    private BaseDao<Zone> zoneDao;

    @Autowired
    private BaseDao<User> userDao;

    @Test
    void checkZoneQueries() {
        Zone zone = new Zone("Mingi nimi", 52);

        zoneDao.insert(zone);

        List<Zone> allZones = zoneDao.findAll();
        Zone savedZone = allZones.getLast();

        assertNotNull("Id should be presented", savedZone.getZoneId());
        assertEquals("Name should be same", zone.getName(), savedZone.getName());
        assertEquals("Capacity should be same", zone.getCapacity(), savedZone.getCapacity());
    }

    @Test
    void checkUserQueries() {
        User user = new User("MingiNimi", "teineNimi", "blah,blah@gmail.com", "wahnotYet");

        userDao.insert(user);

        List<User> allUsers = userDao.findAll();
        User savedUser = allUsers.getLast();

        assertNotNull("Id should be presented", savedUser.getUserId());
        assertEquals("1Name should be same", user.getFirstName(), savedUser.getFirstName());
        assertEquals("2Name should be same", user.getSecondName(), savedUser.getSecondName());
        assertEquals("Email should be same", user.getEmail(), savedUser.getEmail());
    }

}
