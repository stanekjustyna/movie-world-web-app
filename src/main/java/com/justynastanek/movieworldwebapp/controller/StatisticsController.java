package com.justynastanek.movieworldwebapp.controller;

import com.justynastanek.movieworldwebapp.service.EventService;
import com.justynastanek.movieworldwebapp.service.RatingService;
import com.justynastanek.movieworldwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/analytics/")
public class StatisticsController {

    private EventService eventService;
    private RatingService ratingService;
    private UserService userService;

    @Autowired
    public StatisticsController(EventService eventService, RatingService ratingService, UserService userService) {
        this.eventService = eventService;
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @GetMapping
    public String getStatistics(Model model, Principal principal) {
        long id = userService.getUserByUsername(principal.getName());
        model.addAttribute("id", id);
        model.addAttribute("visitors", eventService.getVisitorsNumberLast30Days());
        model.addAttribute("sessions", eventService.getSessionsNumberLast30Days());
        model.addAttribute("sold", eventService.getSoldItemsLast30Days());
        model.addAttribute("conversion", String.format("%.2f", eventService.getConversionRateLast30Days() * 100) + "%");
        model.addAttribute("distribution",ratingService.getRatingDistributionLast30Days());
        model.addAttribute("popular", eventService.getMostoldItemsLast30Days());
        return "analytics";
    }
}
