package ua.clamor1s.eLock.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import ua.clamor1s.eLock.entity.AbstractEntity;
import ua.clamor1s.eLock.utils.DateTimeUtils;

import java.util.Objects;

public interface CreatableUpdatableMapper<T extends AbstractEntity> {
    @AfterMapping
    default void fillCreatableUpdatable(@MappingTarget T entity) {
        if (Objects.isNull(entity.getCreatedAt())) {
            entity.setCreatedAt(DateTimeUtils.mapToLocalDateAsUTC());
        }
        entity.setUpdatedAt(DateTimeUtils.mapToLocalDateAsUTC());
    }
}
