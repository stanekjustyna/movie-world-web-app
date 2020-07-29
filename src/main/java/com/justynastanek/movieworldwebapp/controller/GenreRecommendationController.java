package com.justynastanek.movieworldwebapp.controller;

import com.justynastanek.movieworldwebapp.service.ItemService;
import com.justynastanek.movieworldwebapp.service.RatingService;
import com.justynastanek.movieworldwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/genre/")
public class GenreRecommendationController {

    private ItemService itemService;
    private RatingService ratingService;
    private UserService userService;

    @Autowired
    public GenreRecommendationController(ItemService itemService,
                                         RatingService ratingService, UserService userService) {
        this.itemService = itemService;
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @GetMapping("{genre}/")
    public String getIndex(Model model, Principal principal, @PathVariable String genre) {

        long id = userService.getUserByUsername(principal.getName());

        model.addAttribute("genreList",itemService.getAllGenres());

        model.addAttribute("mostPopularList", ratingService.get10MostPopularItemsByGenre(genre));

        model.addAttribute("id", id);

        return "movie-genre";
    }

}

