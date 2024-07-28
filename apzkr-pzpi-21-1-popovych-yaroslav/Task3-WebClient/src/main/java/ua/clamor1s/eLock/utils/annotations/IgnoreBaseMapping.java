package ua.clamor1s.eLock.utils.annotations;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


@Mapping(target = "updatedAt", ignore = true)
@Mapping(target = "updatedBy", ignore = true)
@Mapping(target = "createdAt", ignore = true)
@Mapping(target = "createdBy", ignore = true)
@Mapping(target = "version", ignore = true)
@Mapping(target = "id", ignore = true)
@Target(ElementType.METHOD)
public @interface IgnoreBaseMapping {
}
