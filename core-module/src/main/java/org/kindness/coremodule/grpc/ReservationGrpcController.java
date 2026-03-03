package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import org.kindness.common.grpc.reservation.ReservationServiceGrpc;
import org.kindness.common.grpc.reservation.ReserveTableRequest;
import org.kindness.common.grpc.reservation.TableReservationResponse;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public final class ReservationGrpcController extends ReservationServiceGrpc.ReservationServiceImplBase {

    @Override
    public void reserveTable(ReserveTableRequest request, StreamObserver<TableReservationResponse> responseObserver) {
        super.reserveTable(request, responseObserver);
    }
}
