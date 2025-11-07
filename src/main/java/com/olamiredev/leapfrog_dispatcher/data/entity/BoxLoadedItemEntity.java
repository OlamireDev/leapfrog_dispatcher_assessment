package com.olamiredev.leapfrog_dispatcher.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name ="box_loaded_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BoxLoadedItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoxEntity box;

    @ManyToOne(fetch = FetchType.LAZY)
    private ItemEntity item;

    private int quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
