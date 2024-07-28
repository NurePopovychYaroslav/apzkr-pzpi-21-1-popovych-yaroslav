package ua.clamor1s.eLock.dto.response;

import java.time.LocalDateTime;

public record AreaResponse(String name,
                           Long campusId,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           String createdBy,
                           Long id) {
}
