package com.olamiredev.leapfrog_dispatcher.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name ="item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "^[A-Za-z0-9_-]+$")
    private String name;

    private BigDecimal weight;

    @Column( unique = true )
    @Pattern(regexp = "^[A-Z0-9_]+$")
    private String code;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
