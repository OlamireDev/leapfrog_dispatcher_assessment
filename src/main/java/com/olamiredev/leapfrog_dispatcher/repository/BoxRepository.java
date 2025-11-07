package com.olamiredev.leapfrog_dispatcher.repository;

import com.olamiredev.leapfrog_dispatcher.data.entity.BoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoxRepository extends JpaRepository<BoxEntity, String> {

    @Query("select box from BoxEntity box where box.batteryLevel >= 25.00 and box.state NOT IN('LOADED', 'DELIVERING', 'DELIVERED', 'RETURNING')")
    List<BoxEntity> findAllLoadableBoxes();

    @Query("select box from BoxEntity box where box.txRef = ?1 and box.state NOT IN('LOADED', 'DELIVERING', 'DELIVERED', 'RETURNING')")
    Optional<BoxEntity> findAvailableBoxByTxRef(String txRef);

}
