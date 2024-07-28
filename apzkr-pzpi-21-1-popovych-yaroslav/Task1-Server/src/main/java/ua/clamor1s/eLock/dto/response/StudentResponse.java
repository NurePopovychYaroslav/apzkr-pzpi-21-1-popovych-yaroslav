package ua.clamor1s.eLock.dto.response;

import java.time.LocalDateTime;

public record StudentResponse(Long id,
                              String firstName,
                              String lastName,
                              String email,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt,
                              String createdBy) {
}
