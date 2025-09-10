package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import org.optimalwaytechtest.application.contracts.ReservationDto;
import org.springframework.stereotype.Component;

/**
 * Mapper between application DTOs and web responses.
 */
@Component
class ReservationWebMapper {

    ReservationResponse toResponse(ReservationDto dto) {
        return new ReservationResponse(
                dto.id(),
                dto.roomId(),
                dto.userId(),
                dto.start(),
                dto.end(),
                dto.status(),
                dto.createdAt()
        );
    }
}

