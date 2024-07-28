package ua.clamor1s.eLock.dto.response;

import java.time.LocalDateTime;

public record DoorResponse(Long id,
                           String name,
                           Long areaFromId,
                           Long areaToId,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           String createdBy) {
}
