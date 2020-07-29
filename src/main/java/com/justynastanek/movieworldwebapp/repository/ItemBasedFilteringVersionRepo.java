package com.justynastanek.movieworldwebapp.repository;


import com.justynastanek.movieworldwebapp.model.ItemBasedFilteringVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemBasedFilteringVersionRepo extends JpaRepository<ItemBasedFilteringVersion, Long> {

    ItemBasedFilteringVersion findTopByOrderByVersionDesc();
}
