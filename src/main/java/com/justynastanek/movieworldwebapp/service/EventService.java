package com.justynastanek.movieworldwebapp.service;

import com.justynastanek.movieworldwebapp.dto.RequestEvent;
import com.justynastanek.movieworldwebapp.model.Event;
import com.justynastanek.movieworldwebapp.model.Item;
import com.justynastanek.movieworldwebapp.repository.EventRepo;
import com.justynastanek.movieworldwebapp.repository.ItemRepo;
import com.justynastanek.movieworldwebapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private EventRepo eventRepository;
    private UserRepo userRepository;
    private ItemRepo itemRepository;

    @Autowired
    public EventService(EventRepo eventRepository, UserRepo userRepository, ItemRepo itemRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public void addEvent(RequestEvent requestEvent, String sessionId) {
        Event event = new Event();
        event.setEventType(requestEvent.getEventType());
        event.setUser(userRepository.findByUserId(requestEvent.getUserId()).get());
        Optional<Item> item = itemRepository.findByMovieId(requestEvent.getMovieId());
        event.setItem((item.isPresent()) ? item.get() : null);
        event.setEventTimestamp(LocalDateTime.now());
        event.setSessionId(sessionId);
        event.setGenre(requestEvent.getGenre());
        eventRepository.save(event);
    }

    public int getVisitorsNumberLast30Days() {
        return eventRepository.getVisitorsNumberLast30Days(LocalDateTime.now().minusDays(30));
    }

    public int getSessionsNumberLast30Days() {
        return eventRepository.getSessionsNumberLast30Days(LocalDateTime.now().minusDays(30));
    }

    public int getSoldItemsLast30Days() {
        return eventRepository.getSoldItemsLast30Days(LocalDateTime.now().minusDays(30));
    }

    public double getConversionRateLast30Days() {
        return (getSoldItemsLast30Days() * 1.0) / getSessionsNumberLast30Days();
    }

    public List<String> getMostoldItemsLast30Days() {

        List<String> mostSoldList = eventRepository.getMostSoldItemsLast30Days(LocalDateTime.now().minusDays(30));

        return (mostSoldList.size() > 10) ? mostSoldList.subList(0, 10) : mostSoldList;

    }

}
