package com.justynastanek.movieworldwebapp.controller;

import com.justynastanek.movieworldwebapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class MainRecommendationController {

    private ItemService itemService;
    private RatingService ratingService;
    private UserService userService;
    private PersonalListService personalListService;
    private UserBasedFilteringRecsService userBasedFilteringRecsService;

    @Autowired
    public MainRecommendationController(ItemService itemService, RatingService ratingService,
                                        UserService userService, PersonalListService personalListService,
                                        UserBasedFilteringRecsService userBasedFilteringRecsService) {
        this.itemService = itemService;
        this.ratingService = ratingService;
        this.userService = userService;
        this.personalListService = personalListService;
        this.userBasedFilteringRecsService = userBasedFilteringRecsService;
    }

    @GetMapping
    public String getIndex(Model model, Principal principal) {

        long id = userService.getUserByUsername(principal.getName());

        model.addAttribute("genreList",itemService.getAllGenres());

        model.addAttribute("mostPopularList", ratingService.get10MostPopularItems());

        model.addAttribute("userBasedRecs", userBasedFilteringRecsService.getUserBasedRecommendations(id));

        model.addAttribute("id", id);

        model.addAttribute("myList", personalListService.getUserList(id));

        return "index";
    }
}
