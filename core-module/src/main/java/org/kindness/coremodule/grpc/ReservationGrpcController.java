package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import org.kindness.common.grpc.reservation.*;
import org.kindness.common.model.impl.Reservation;
import org.kindness.coremodule.domain.reservation.ReservationService;
import org.kindness.coremodule.util.TimestampConverter;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public final class ReservationGrpcController extends ReservationServiceGrpc.ReservationServiceImplBase {
    private ReservationService reservationService;

    @Override
    public void reserveTable(ReserveTableRequest request, StreamObserver<TableReservationResponse> observer) {
        long tableId = request.getTableId();
        var reservationStart = TimestampConverter.convert(request.getReservationStart());
        var reservationEnd = TimestampConverter.convert(request.getReservationEnd());
        long userId = request.getUserId();

        var reservation = Reservation.builder()
                .userId(userId)
                .tableId(tableId)
                .reservationStart(reservationStart)
                .reservationEnd(reservationEnd)
                .build();

        var responseBuilder = TableReservationResponse.newBuilder();
        try {
            reservationService.createReservation(reservation);
            responseBuilder.setSuccess(true);
        } catch (IllegalStateException e){
            responseBuilder.setMessage(e.getMessage());
            responseBuilder.setSuccess(false);
        }

        observer.onNext(responseBuilder.build());
        observer.onCompleted();
    }

    @Override
    public void cancelReservation(CancelReservationRequest request, StreamObserver<CancelReservationResponse> observer) {
        long reservationId = request.getReservationId();

        var responseBuilder = CancelReservationResponse.newBuilder();
        try {
            reservationService.cancelReservation(reservationId);
            responseBuilder.setSuccess(true);
        } catch (IllegalStateException e){
            responseBuilder.setMessage(e.getMessage());
            responseBuilder.setSuccess(false);
        }

        observer.onNext(responseBuilder.build());
        observer.onCompleted();
    }
}
