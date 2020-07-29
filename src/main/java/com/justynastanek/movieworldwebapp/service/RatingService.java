package com.justynastanek.movieworldwebapp.service;

import com.justynastanek.movieworldwebapp.dto.ItemPopularity;
import com.justynastanek.movieworldwebapp.dto.RatedItem;
import com.justynastanek.movieworldwebapp.dto.RequestRating;
import com.justynastanek.movieworldwebapp.model.Rating;
import com.justynastanek.movieworldwebapp.repository.ItemRepo;
import com.justynastanek.movieworldwebapp.repository.RatingRepo;
import com.justynastanek.movieworldwebapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {

    private RatingRepo ratingRepository;
    private UserRepo userRepository;
    private ItemRepo itemRepository;
    private ItemService itemService;

    @Autowired
    public RatingService(RatingRepo ratingRepository, UserRepo userRepository,
                         ItemRepo itemRepository, ItemService itemService) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    public List<ItemPopularity> get10MostPopularItems() {

        List<ItemPopularity> mostPopularList = ratingRepository.getMovieListOrderedByPopularity();

        return (mostPopularList.size() <= 10) ? mostPopularList : mostPopularList.subList(0, 10);
    }

    public List<ItemPopularity> get10MostPopularItemsByGenre(String genre) {

        List<ItemPopularity> mostPopularList = ratingRepository.getMovieListByGenreOrderedByPopularity(genre);

        return (mostPopularList.size() <= 10) ? mostPopularList : mostPopularList.subList(0, 10);
    }

    public boolean saveRating(RequestRating requestRating) {

        if (userRepository.findByUserId(requestRating.getUserId()).isPresent()) {
            Rating rating = new Rating();
            rating.setUser(userRepository.findByUserId(requestRating.getUserId()).get());
            rating.setItem(itemRepository.findByMovieId(requestRating.getMovieId()).get());
            rating.setRating(requestRating.getRating());
            rating.setRatingTimestamp(LocalDateTime.now());

            if (ratingRepository.findOneByUserIdAndMovieId(requestRating.getUserId(), requestRating.getMovieId()).isEmpty()) {
                ratingRepository.save(rating);
                return true;
            } else {
                ratingRepository.updateRating(rating.getUser().getUserId(), rating.getItem().getMovieId(), rating.getRating());
                return true;
            }
        }

        return false;
    }

    public int getUserRating(long userId, String movieId) {
        return (ratingRepository.getUserItemRating(userId, movieId).isPresent()) ?
                ratingRepository.getUserItemRating(userId, movieId).get() : 0;
    }

    public Map<Integer, Integer> getRatingDistributionLast30Days() {
        Map<Integer, Integer> result = new HashMap<>();
        for(List<Integer> rating : ratingRepository.getRatingDistributionLast30Days(LocalDateTime.now().minusDays(30))) {
            result.put(rating.get(0), rating.get(1));
        }

        return result;
    }

    public double getAvgUserRating(long id) {
        return ratingRepository.findAvgUserRating(id);
    }

    public int countUserRatings(long id) {
        return ratingRepository.countUserRatings(id);
    }

    public List<Double> getAvgUserRatingByGenres(long id) {

        List<Double> avgUserRatingByGenre = new ArrayList<>();

        for(String genre : itemService.getAllGenres()) {
            avgUserRatingByGenre.add(ratingRepository.getAvgUserRatingByGenre(id, genre));
        }

        return avgUserRatingByGenre;
    }

    public List<Double> getAvgRatingByGenres() {

        List<Double> avgRatingByGenre = new ArrayList<>();

        for(String genre : itemService.getAllGenres()) {
            avgRatingByGenre.add(ratingRepository.getAvgRatingByGenre(genre));
        }

        return avgRatingByGenre;
    }

    public List<Integer> countUserRatingsByGenres(long id) {

        List<Integer> numberOfUserRatingsByGenres = new ArrayList<>();

        for(String genre : itemService.getAllGenres()) {
            numberOfUserRatingsByGenres.add(ratingRepository.countUserRatingsByGenres(genre, id));
        }

        return numberOfUserRatingsByGenres;
    }

    public List<RatedItem> getRatedTitles(long id) {
        return ratingRepository.getRatedTitles(id);
    }
}
