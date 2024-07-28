package ua.clamor1s.eLock.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public interface DateTimeUtils {

    static LocalDateTime mapToLocalDateAsUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
