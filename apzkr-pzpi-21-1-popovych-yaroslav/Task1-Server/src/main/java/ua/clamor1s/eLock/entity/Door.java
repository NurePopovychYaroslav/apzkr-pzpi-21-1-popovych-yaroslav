package ua.clamor1s.eLock.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "door")
public class Door extends AbstractEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "door_id_seq")
    @SequenceGenerator(name = "door_id_seq", sequenceName = "door_id_seq", allocationSize = 1, initialValue = 1)
    Long id;

    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_from_id")
    @ToString.Exclude
    Area from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_to_id")
    @ToString.Exclude
    Area to;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "door_permission",
            joinColumns = @JoinColumn(name = "door_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @ToString.Exclude
    Set<Permission> permissions = new HashSet<>();

    public void addPermission(Permission permission) {
        permissions.add(permission);
        permission.getDoors().add(this);
    }

    public void removePermission(Permission permission) {
        permissions.remove(permission);
        permission.getDoors().remove(this);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Door)) return false;
        return id != null && id.equals(((Door) obj).getId());
    }
}
