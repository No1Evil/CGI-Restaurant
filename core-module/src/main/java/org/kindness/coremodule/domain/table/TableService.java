package org.kindness.coremodule.domain.table;

import lombok.RequiredArgsConstructor;
import org.kindness.common.model.impl.Table;
import org.kindness.module.persistence.dao.impl.JdbcTableDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TableService {
    private final JdbcTableDao tableDao;

    public Table getTable(Long tableId){
        if (tableId == null) throw new IllegalStateException("Table id cannot be null");
        return tableDao.findById(tableId)
                .orElseThrow(() -> new IllegalStateException("No table found"));
    }

    public List<Table> getAllTables(){
        return tableDao.findAll();
    }
}
