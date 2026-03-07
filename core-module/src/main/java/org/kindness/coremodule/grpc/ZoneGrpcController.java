package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.zone.*;
import org.kindness.common.model.impl.Zone;
import org.kindness.coremodule.domain.zone.ZoneService;
import org.kindness.coremodule.util.GrpcServiceUtil;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public final class ZoneGrpcController extends ZoneServiceGrpc.ZoneServiceImplBase {
    private final ZoneService zoneService;

    @Override
    public void getZone(ZoneRequest request, StreamObserver<ZoneResponse> observer) {
        var responseBuilder = ZoneResponse.newBuilder();
        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            Zone zone = zoneService.getZone(request.getZoneId());
            responseBuilder.mergeFrom(convert(zone));
        });
    }

    @Override
    public void getZoneCollection(ZoneCollectionRequest request, StreamObserver<ZoneCollectionResponse> observer) {
        var responseBuilder = ZoneCollectionResponse.newBuilder();
        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            List<Zone> zones = zoneService.getAllZones();
            responseBuilder.addAllData(convert(zones));
        });
    }

    private static List<ZoneData> convert(List<Zone> zones){
        return zones.stream().map(ZoneGrpcController::convert).toList();
    }

    private static ZoneData convert(Zone zone){
        return ZoneData.newBuilder()
                .setZoneId(zone.getZoneId())
                .setName(zone.getName())
                .build();
    }
}
