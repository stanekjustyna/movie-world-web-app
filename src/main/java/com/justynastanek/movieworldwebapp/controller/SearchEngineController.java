package com.justynastanek.movieworldwebapp.controller;

import com.justynastanek.movieworldwebapp.service.ItemService;
import com.justynastanek.movieworldwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/results/")
public class SearchEngineController {

    private ItemService itemService;
    private UserService userService;

    @Autowired
    public SearchEngineController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @PostMapping("/title-year/")
    @GetMapping("/title-year/")
    public String getSearchResultsByTitleAndYear(@RequestParam String title, @RequestParam String year, Model model, Principal principal) {

        long id = userService.getUserByUsername(principal.getName());

        model.addAttribute("id", id);
        model.addAttribute("genreList",itemService.getAllGenres());
        model.addAttribute("foundMovies", itemService.getItemByTitleAndYear(title, year));

        return "movie-found";
    }

    @PostMapping("/title/")
    @GetMapping("/title")
    public String getSearchResultsByTitle(@RequestParam String title, Model model, Principal principal) {

        long id = userService.getUserByUsername(principal.getName());

        model.addAttribute("id", id);
        model.addAttribute("genreList",itemService.getAllGenres());
        model.addAttribute("foundMovies", itemService.getItemByTitle(title));

        return "movie-found";
    }
}
