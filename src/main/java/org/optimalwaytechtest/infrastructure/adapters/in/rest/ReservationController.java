package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Reservations", description = "Manage room reservations")
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
    @Operation(summary = "Create a reservation", description = "Creates a reservation for a room and user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reservation created"),
            @ApiResponse(responseCode = "400", description = "Invalid reservation data")
    })
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
    @Operation(summary = "List reservations", description = "Returns reservations filtered by parameters")
    public ReservationPageResponse list(
            @Parameter(description = "Filter by room identifier")
            @RequestParam(required = false) UUID roomId,
            @Parameter(description = "Filter by user identifier")
            @RequestParam(required = false) UUID userId,
            @Parameter(description = "Filter by reservation date")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
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
    @Operation(summary = "Cancel a reservation", description = "Cancels the reservation with the given identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reservation cancelled"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public void delete(@PathVariable UUID id) {
        CancelReservationCommand cmd = new CancelReservationCommand(id);
        cancelReservation.handle(cmd);
    }
}

