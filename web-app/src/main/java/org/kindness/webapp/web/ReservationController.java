package org.kindness.webapp.web;

import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.reservation.*;
import org.kindness.common.model.util.TimestampConverter;
import org.kindness.webapp.configuration.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class ReservationController {

    private final ReservationServiceGrpc.ReservationServiceBlockingStub reservationService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ReservationDto dto, @AuthenticationPrincipal UserPrincipal principal) {
        long userId = principal.getId();

        ReserveTableRequest request = ReserveTableRequest.newBuilder()
                .setUserId(userId)
                .setTableId(dto.tableId())
                .setReservationStart(TimestampConverter.fromLocalDateTime(dto.start))
                .setReservationEnd(TimestampConverter.fromLocalDateTime(dto.end))
                .build();

        TableReservationResponse response = reservationService.reserveTable(request);

        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response.getMessage());
        }

        return ResponseEntity.ok(response.getReservationId());
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam long reservationId,
                                    @AuthenticationPrincipal UserPrincipal principal) {
        long userId = principal.getId();

        CancelReservationRequest request = CancelReservationRequest.newBuilder()
                .setReservationId(reservationId)
                .setUserId(userId)
                .build();

        CancelReservationResponse response = reservationService.cancelReservation(request);

        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(response.getMessage());
        }

        return ResponseEntity.ok("Successful reservation");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getReservation(@RequestParam long reservationId,
                                            @AuthenticationPrincipal UserPrincipal principal) {
        long userId = principal.getId();

        RequestReservation request = RequestReservation.newBuilder()
                .setReservationId(reservationId)
                .setUserId(userId)
                .build();

        ReservationResponse response = reservationService.getReservation(request);

        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getMessage());
        }

        return ResponseEntity.ok(response.getData());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReservations(@AuthenticationPrincipal UserPrincipal principal) {
        long userId = principal.getId();

        RequestReservationCollection request = RequestReservationCollection.newBuilder()
                .setUserId(userId)
                .build();

        ReservationCollectionResponse response = reservationService.getReservationCollection(request);

        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response.getMessage());
        }

        return ResponseEntity.ok(response.getDataList());
    }

    public record ReservationDto(long tableId, LocalDateTime start, LocalDateTime end) {}
}