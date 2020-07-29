package com.justynastanek.movieworldwebapp.repository;

import com.justynastanek.movieworldwebapp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

    @Query("select count(distinct e.user) from Event e where e.eventTimestamp > :date")
    int getVisitorsNumberLast30Days(@Param("date")LocalDateTime date);

    @Query("select count(distinct e.sessionId) from Event e where e.eventTimestamp > :date")
    int getSessionsNumberLast30Days(@Param("date")LocalDateTime date);

    @Query("select count(e) from Event e where e.eventType = 'buy' and e.eventTimestamp > :date")
    int getSoldItemsLast30Days(@Param("date") LocalDateTime date);

    @Query("select concat(e.item.title, ' (', count(e), ')') from Event e where e.eventType = 'buy' " +
            "and e.eventTimestamp > :date group by e.item.title having count(e) > 0 order by count(e) desc")
    List<String> getMostSoldItemsLast30Days(@Param("date") LocalDateTime date);
}
