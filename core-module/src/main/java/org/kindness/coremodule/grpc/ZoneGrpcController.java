package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import org.kindness.common.grpc.zone.*;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public final class ZoneGrpcController extends ZoneServiceGrpc.ZoneServiceImplBase {
    @Override
    public void getZoneData(ZoneDataRequest request, StreamObserver<ZoneDataResponse> responseObserver) {
        super.getZoneData(request, responseObserver);
    }

    @Override
    public void getAllZonesData(AllZonesDataRequest request, StreamObserver<AllZonesDataResponse> responseObserver) {
        super.getAllZonesData(request, responseObserver);
    }
}
