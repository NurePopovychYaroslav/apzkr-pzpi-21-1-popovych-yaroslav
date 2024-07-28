package ua.clamor1s.eLock.dto.response;

import java.time.LocalDateTime;

public record GroupResponse(Long id,
                            String name,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            String createdBy) {
}
