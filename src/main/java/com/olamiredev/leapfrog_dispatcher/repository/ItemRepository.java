package com.olamiredev.leapfrog_dispatcher.repository;

import com.olamiredev.leapfrog_dispatcher.data.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
