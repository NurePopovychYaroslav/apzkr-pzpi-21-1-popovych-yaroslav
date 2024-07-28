package ua.clamor1s.eLock.dto.response;

import java.time.LocalDateTime;

public record UserResponse(String email,
                           String firstName,
                           String lastName,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
}
