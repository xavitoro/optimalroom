package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import org.optimalwaytechtest.application.contracts.*;
import org.optimalwaytechtest.application.usecases.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing reservations.
 */
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final CreateReservationUseCase createReservation;
    private final ListReservationsUseCase listReservations;
    private final CancelReservationUseCase cancelReservation;
    private final ReservationWebMapper mapper;

    public ReservationController(CreateReservationUseCase createReservation,
                                 ListReservationsUseCase listReservations,
                                 CancelReservationUseCase cancelReservation,
                                 ReservationWebMapper mapper) {
        this.createReservation = createReservation;
        this.listReservations = listReservations;
        this.cancelReservation = cancelReservation;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@Valid @RequestBody CreateReservationRequest request) {
        CreateReservationCommand cmd = new CreateReservationCommand(
                request.roomId(),
                request.userId(),
                request.start(),
                request.end()
        );
        ReservationDto dto = createReservation.handle(cmd);
        return mapper.toResponse(dto);
    }

    @GetMapping
    public ReservationPageResponse list(@RequestParam(required = false) UUID roomId,
                                        @RequestParam(required = false) UUID userId,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        Instant start = null;
        Instant end = null;
        if (date != null) {
            start = date.atStartOfDay().toInstant(ZoneOffset.UTC);
            end = start.plus(1, ChronoUnit.DAYS);
        }
        ListReservationsQuery query = new ListReservationsQuery(
                roomId,
                userId,
                start,
                end,
                null,
                page,
                size
        );
        ReservationPageDto dto = listReservations.handle(query);
        List<ReservationResponse> items = dto.items().stream().map(mapper::toResponse).toList();
        return new ReservationPageResponse(items, dto.total(), page, size);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        CancelReservationCommand cmd = new CancelReservationCommand(id);
        cancelReservation.handle(cmd);
    }
}

