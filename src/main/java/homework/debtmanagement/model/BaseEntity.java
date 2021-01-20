package homework.debtmanagement.model;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "ENTITY_CREATED", nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "ENTITY_UPDATED", nullable = false)
    private LocalDateTime updated;

}
