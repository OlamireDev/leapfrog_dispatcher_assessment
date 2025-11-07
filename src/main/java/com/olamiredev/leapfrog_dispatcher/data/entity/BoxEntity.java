package com.olamiredev.leapfrog_dispatcher.data.entity;

import com.olamiredev.leapfrog_dispatcher.data.discrete.BoxDispatchState;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="box")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BoxEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(20)")
    private String txRef;

    @Column(nullable = false)
    @DecimalMax("500.00")
    @DecimalMin("0.00")
    private BigDecimal weight;

    @Column(nullable = false)
    @Max(100)
    @Min(0)
    private int batteryLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoxDispatchState state;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<BoxLoadedItemEntity> loadedItemEntities;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
