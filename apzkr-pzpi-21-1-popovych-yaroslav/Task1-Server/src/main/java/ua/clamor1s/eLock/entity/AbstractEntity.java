package ua.clamor1s.eLock.entity;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

    @LastModifiedDate
    LocalDateTime updatedAt;

    @LastModifiedBy
    String updatedBy;

    @CreatedDate
    LocalDateTime createdAt;

    @CreatedBy
    String createdBy;

    @Version
    Long version;
}