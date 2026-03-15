package org.kindness.webapp.web;

import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.table.*;
import org.kindness.common.model.util.TimestampConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/table")
@RequiredArgsConstructor
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class TableController {
    private final TableServiceGrpc.TableServiceBlockingStub tableService;

    @PostMapping("/get")
    public ResponseEntity<?> getTable(@RequestParam long id) {
        RequestTable request = RequestTable.newBuilder().setTableId(id).build();
        TableResponse response = tableService.getTable(request);

        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getMessage());
        }
        return ResponseEntity.ok(response.getData());
    }

    @PostMapping("/available")
    public ResponseEntity<?> getAvailableTables(@RequestBody AvailableTablesDto dto) {
        Instant start = Instant.parse(dto.startTime);
        Instant end = Instant.parse(dto.endTime);

        RequestAvailableTables request = RequestAvailableTables.newBuilder()
                .setRestaurantId(dto.restaurantId)
                .setZoneId(dto.zoneId)
                .setCapacity(dto.capacity)
                .setStartTime(TimestampConverter.fromInstant(start))
                .setEndTime(TimestampConverter.fromInstant(end))
                .build();

        TableCollectionResponse response = tableService.getAvailableTables(request);

        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.getMessage());
        }

        return ResponseEntity.ok(response.getDataList());
    }

    public record AvailableTablesDto(Long restaurantId, Long zoneId, Integer capacity,
                                     String startTime, String endTime) {}
}