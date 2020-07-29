package com.justynastanek.movieworldwebapp.repository;

import com.justynastanek.movieworldwebapp.model.Item;
import com.justynastanek.movieworldwebapp.model.ItemBasedFilteringRecs;
import com.justynastanek.movieworldwebapp.model.ItemBasedFilteringVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemBasedFilteringRecsRepo extends JpaRepository<ItemBasedFilteringRecs, Long> {

    List<ItemBasedFilteringRecs> findAllByBaseItemAndVersion(Item baseItem, ItemBasedFilteringVersion version);
}
