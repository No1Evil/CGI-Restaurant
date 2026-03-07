package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import org.kindness.common.grpc.reservation.*;
import org.kindness.common.model.impl.Reservation;
import org.kindness.coremodule.domain.reservation.ReservationService;
import org.kindness.coremodule.util.GrpcServiceUtil;
import org.kindness.coremodule.util.TimestampConverter;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public final class ReservationGrpcController extends ReservationServiceGrpc.ReservationServiceImplBase {
    private ReservationService reservationService;

    @Override
    public void reserveTable(ReserveTableRequest request, StreamObserver<TableReservationResponse> observer) {
        var responseBuilder = TableReservationResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            var reservationStart = TimestampConverter.convert(request.getReservationStart());
            var reservationEnd = TimestampConverter.convert(request.getReservationEnd());

            var reservation = Reservation.builder()
                    .userId(request.getUserId())
                    .tableId(request.getTableId())
                    .reservationStart(reservationStart)
                    .reservationEnd(reservationEnd)
                    .build();

            reservationService.createReservation(reservation);
        });
    }

    @Override
    public void cancelReservation(CancelReservationRequest request, StreamObserver<CancelReservationResponse> observer) {
        var responseBuilder = CancelReservationRequest.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            long reservationId = request.getReservationId();
            reservationService.cancelReservation(reservationId);
        });
    }
}
