package com.justynastanek.movieworldwebapp.controller;

import com.justynastanek.movieworldwebapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/movie/")
public class MovieDetailsController {

    private ItemService itemService;
    private PersonalListService personalListService;
    private ItemBasedFilteringRecsService itemBasedFilteringRecsService;
    private RatingService ratingService;
    private UserService userService;

    @Autowired
    public MovieDetailsController(ItemService itemService, PersonalListService personalListService,
                                  ItemBasedFilteringRecsService itemBasedFilteringRecsService,
                                  RatingService ratingService, UserService userService) {
        this.itemService = itemService;
        this.personalListService = personalListService;
        this.itemBasedFilteringRecsService = itemBasedFilteringRecsService;
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @GetMapping("{movieId}/")
    public String getMovieDetails(Principal principal, @PathVariable String movieId, Model model) {

        long id = userService.getUserByUsername(principal.getName());

        model.addAttribute("id", id);
        model.addAttribute("genreList",itemService.getAllGenres());
        model.addAttribute("movie", itemService.getMovieById(movieId));
        model.addAttribute("isOnList", (personalListService.isOnUserList(id, movieId)) ? "Remove from list" : "Add to list");
        model.addAttribute("yourRating", ratingService.getUserRating(id, movieId));
        model.addAttribute("similarItemsList", itemBasedFilteringRecsService.getItemBasedRecommendations(movieId));

        return "movie-details";
    }
}
