package org.kindness.coremodule.domain.zone;

import lombok.RequiredArgsConstructor;
import org.kindness.common.model.impl.Zone;
import org.kindness.module.persistence.dao.impl.JdbcZoneDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ZoneService {
    private final JdbcZoneDao zoneDao;

    public Zone getZone(Long id){
        if (id == null) throw new IllegalStateException("Zone id cannot be null");
        return zoneDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("No zone found"));
    }

    public List<Zone> getAllZones(){
        return zoneDao.findAll();
    }
}
