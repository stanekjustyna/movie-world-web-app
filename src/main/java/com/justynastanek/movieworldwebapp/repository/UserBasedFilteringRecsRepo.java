package com.justynastanek.movieworldwebapp.repository;

import com.justynastanek.movieworldwebapp.model.User;
import com.justynastanek.movieworldwebapp.model.UserBasedFilteringRecs;
import com.justynastanek.movieworldwebapp.model.UserBasedFilteringVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBasedFilteringRecsRepo extends JpaRepository<UserBasedFilteringRecs, Long> {


    List<UserBasedFilteringRecs> findAllByUserAndVersion(User user, UserBasedFilteringVersion version);
}
