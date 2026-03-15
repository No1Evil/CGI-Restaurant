package org.kindness.webapp.web;

import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.zone.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zone")
@RequiredArgsConstructor
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class ZoneController {
    private final ZoneServiceGrpc.ZoneServiceBlockingStub zoneService;

    @PostMapping("/get")
    public ResponseEntity<?> getZone(@RequestParam long id) {
        ZoneRequest request = ZoneRequest.newBuilder()
                .setZoneId(id)
                .build();

        ZoneResponse response = zoneService.getZone(request);

        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response.getMessage());
        }

        return ResponseEntity.ok(response.getData());
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAllZones() {
        ZoneCollectionRequest request = ZoneCollectionRequest.newBuilder().build();
        ZoneCollectionResponse response = zoneService.getZoneCollection(request);

        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response.getMessage());
        }

        return ResponseEntity.ok(response.getDataList());
    }
}