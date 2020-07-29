package com.justynastanek.movieworldwebapp.controller;

import com.justynastanek.movieworldwebapp.service.ItemService;
import com.justynastanek.movieworldwebapp.service.RatingService;
import com.justynastanek.movieworldwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/taste/")
public class UserTasteController {

    private UserService userService;
    private RatingService ratingService;
    private ItemService itemService;

    @Autowired
    public UserTasteController(UserService userService, RatingService ratingService, ItemService itemService) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.itemService = itemService;
    }

    @GetMapping
    public String getUserTaste(Model model, Principal principal) {
        long id = userService.getUserByUsername(principal.getName());
        model.addAttribute("id", id);
        model.addAttribute("avgRating", String.format("%.2f", ratingService.getAvgUserRating(id)));
        model.addAttribute("countRating", ratingService.countUserRatings(id));
        model.addAttribute("genres", itemService.getAllGenres());
        model.addAttribute("avgUserRatingByGenre", ratingService.getAvgUserRatingByGenres(id));
        model.addAttribute("avgRatingByGenre", ratingService.getAvgRatingByGenres());
        model.addAttribute("numberOfUserRatingsByGenre", ratingService.countUserRatingsByGenres(id));
        model.addAttribute("ratedMovies", ratingService.getRatedTitles(id));
        return "user-taste";
    }
}
