package ua.clamor1s.eLock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "campus")
public class Campus extends AbstractEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campus_id_seq")
    @SequenceGenerator(name = "campus_id_seq", sequenceName = "campus_id_seq", allocationSize = 1, initialValue = 1)
    Long id;

    @Column(nullable = false)
    String name;

    String location;

    @OneToMany(mappedBy = "campus")
    @ToString.Exclude
    List<Area> areas;

    public void addArea(Area area) {
        areas.add(area);
        area.setCampus(this);
    }

    public void removeArea(Area area) {
        areas.remove(area);
        area.setCampus(null);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Campus)) return false;
        return id != null && id.equals(((Campus) obj).getId());
    }
}
