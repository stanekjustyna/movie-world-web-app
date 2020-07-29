package com.justynastanek.movieworldwebapp.controller;

import com.justynastanek.movieworldwebapp.dto.RequestEvent;
import com.justynastanek.movieworldwebapp.dto.RequestList;
import com.justynastanek.movieworldwebapp.dto.RequestRating;
import com.justynastanek.movieworldwebapp.service.EventService;
import com.justynastanek.movieworldwebapp.service.PersonalListService;
import com.justynastanek.movieworldwebapp.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/collect/")
public class CollectorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectorController.class);
    private RatingService ratingService;
    private PersonalListService personalListService;
    private EventService eventService;

    @Autowired
    public CollectorController(RatingService ratingService, PersonalListService personalListService, EventService eventService) {
        this.ratingService = ratingService;
        this.personalListService = personalListService;
        this.eventService = eventService;
    }

    @PostMapping(value = "rating/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void logRating(@RequestBody RequestRating rating) {
        boolean isSaved = ratingService.saveRating(rating);
        if (isSaved) {
            LOGGER.info("Rating: " + rating.getRating() + " for Item: " + rating.getMovieId()
                    + " by User: " + rating.getUserId() + " added/updated.");
        }
    }

    @PostMapping(value = "list/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addToList(@RequestBody RequestList requestList) {
        boolean isSaved = personalListService.addItem(requestList);
        if (isSaved) {
            LOGGER.info("User: " + requestList.getUserId() + " added/deleted Item: "
                        + requestList.getMovieId() + " to/from list.");
        }
    }

    @PostMapping(value = "event/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addEvent(@RequestBody RequestEvent requestEvent, HttpSession session) {
        eventService.addEvent(requestEvent, session.getId());
        LOGGER.info("Event: " + requestEvent.getEventType() + " for Item: "
                    + requestEvent.getMovieId() + " by User: " + requestEvent.getUserId());

    }
}
