package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.reservation.*;
import org.kindness.common.model.impl.Reservation;
import org.kindness.common.model.impl.User;
import org.kindness.coremodule.domain.reservation.ReservationService;
import org.kindness.coremodule.domain.user.UserService;
import org.kindness.coremodule.util.GrpcServiceUtil;
import org.kindness.common.model.util.TimestampConverter;
import org.springframework.grpc.server.service.GrpcService;

import java.util.Collections;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public final class ReservationGrpcController extends ReservationServiceGrpc.ReservationServiceImplBase {
    private final ReservationService reservationService;
    private final UserService userService;

    @Override
    public void reserveTable(ReserveTableRequest request, StreamObserver<TableReservationResponse> observer) {
        var responseBuilder = TableReservationResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            var reservationStart = TimestampConverter.toInstant(request.getReservationStart());
            var reservationEnd = TimestampConverter.toInstant(request.getReservationEnd());

            var reservation = Reservation.builder()
                    .userId(request.getUserId())
                    .tableId(request.getTableId())
                    .startsAt(reservationStart)
                    .endsAt(reservationEnd)
                    .build();

            Long reservationId = reservationService.createReservation(reservation);
            responseBuilder.setReservationId(reservationId);
        });
    }

    @Override
    public void cancelReservation(CancelReservationRequest request, StreamObserver<CancelReservationResponse> observer) {
        var responseBuilder = CancelReservationResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            long reservationId = request.getReservationId();
            long userId = request.getUserId();
            reservationService.cancelReservation(reservationId, userId);
        });
    }

    @Override
    public void getReservation(RequestReservation request, StreamObserver<ReservationResponse> observer) {
        var responseBuilder = ReservationResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            long userId = request.getUserId();
            long reservationId = request.getReservationId();

            User user = userService.getUserData(userId);
            boolean isPrivileged = !"USER".equalsIgnoreCase(user.getRole());

            Reservation reservation;
            if (isPrivileged) {
                reservation = reservationService.getReservation(reservationId)
                        .orElseThrow(() -> new IllegalStateException("Reservation not found"));
            } else {
                reservation = reservationService.getUserReservation(reservationId, userId)
                        .orElseThrow(() -> new IllegalStateException("Reservation not found or access denied"));
            }

            responseBuilder.setData(convert(reservation));
        });
    }

    @Override
    public void getReservationCollection(RequestReservationCollection request, StreamObserver<ReservationCollectionResponse> observer) {
        var responseBuilder = ReservationCollectionResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            long userId = request.getUserId();

            User user = userService.getUserData(userId);
            boolean isPrivileged = "ADMIN".equalsIgnoreCase(user.getRole());

            List<Reservation> reservations;
            if (isPrivileged) {
                reservations = reservationService.getAllReservations();
            } else {
                reservations = reservationService.getAllUserReservations(userId);
            }

            responseBuilder.addAllData(convert(reservations));
        });
    }



    private static List<ReservationData> convert(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            return Collections.emptyList();
        }
        return reservations.stream().map(ReservationGrpcController::convert).toList();
    }

    private static ReservationData convert(Reservation res) {
        return ReservationData.newBuilder()
                .setId(res.getId())
                .setUserId(res.getUserId())
                .setTableId(res.getTableId())
                .setReservationStart(TimestampConverter.fromInstant(res.getStartsAt()))
                .setReservationEnd(TimestampConverter.fromInstant(res.getEndsAt()))
                .build();
    }
}
