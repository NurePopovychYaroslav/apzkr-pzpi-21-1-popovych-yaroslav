package ua.clamor1s.eLock.dto.response;

import java.time.LocalDateTime;

public record CampusResponse(String name,
                             String location,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             String createdBy,
                             Long id) {
}
