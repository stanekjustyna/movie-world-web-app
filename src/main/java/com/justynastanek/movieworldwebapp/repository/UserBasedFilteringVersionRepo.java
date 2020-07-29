package com.justynastanek.movieworldwebapp.repository;

import com.justynastanek.movieworldwebapp.model.UserBasedFilteringVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBasedFilteringVersionRepo extends JpaRepository<UserBasedFilteringVersion, Long> {

    UserBasedFilteringVersion findTopByOrderByVersionDesc();

}
