package com.justynastanek.movieworldwebapp.repository;

import com.justynastanek.movieworldwebapp.dto.ItemPopularity;
import com.justynastanek.movieworldwebapp.dto.RatedItem;
import com.justynastanek.movieworldwebapp.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepo extends JpaRepository<Rating, LocalDateTime> {

    @Query("select new com.justynastanek.movieworldwebapp.dto.ItemPopularity(i.movieId, " +
            "count(r) as ratings, i.poster, i.title) from Rating r join r.item i group by i.movieId order by ratings desc")
    List<ItemPopularity> getMovieListOrderedByPopularity();

    @Query("select new com.justynastanek.movieworldwebapp.dto.ItemPopularity(i.movieId, " +
            "count(r) as ratings, i.poster, i.title) from Rating r join r.item i where i.genre like concat('%', :genre, '%') " +
            "group by i.movieId order by ratings desc")
    List<ItemPopularity> getMovieListByGenreOrderedByPopularity(@Param("genre") String genre);

    @Query("select r from Rating r join r.user u join r.item i where u.userId = :userId and i.movieId = :movieId")
    Optional<Rating> findOneByUserIdAndMovieId(@Param("userId") long userId, @Param("movieId") String movieId);

    @Transactional
    @Modifying
    @Query("update Rating r set r.rating = :rating, r.ratingTimestamp = current_timestamp() " +
            "where r.user.userId = :userId and r.item.movieId = :movieId")
    int updateRating(@Param("userId") long userId, @Param("movieId") String movieId, @Param("rating") int rating);

    @Query("select r.rating from Rating r where r.user.userId = :id and r.item.movieId = :movieId")
    Optional<Integer> getUserItemRating(@Param("id") long userId, @Param("movieId") String movieId);

    @Query("select r.rating, count(r) from Rating r where r.ratingTimestamp > :date group by r.rating")
    List<List<Integer>> getRatingDistributionLast30Days(@Param("date") LocalDateTime date);

    @Query("select avg(r.rating) from Rating r where r.user.userId = :userId group by r.user")
    Double findAvgUserRating(@Param("userId") long userId);

    @Query("select count(r.rating) from Rating r where r.user.userId = :userId")
    Integer countUserRatings(@Param("userId") long id);

    @Query("select avg(r.rating) from Rating r where r.user.userId = :userId and r.item.genre " +
            "like concat('%', :genre, '%')")
    Double getAvgUserRatingByGenre(@Param("userId") long userId, @Param("genre") String genre);

    @Query("select avg(r.rating) from Rating r where r.item.genre like concat('%', :genre, '%')")
    Double getAvgRatingByGenre(@Param("genre") String genre);

    @Query("select count(r.rating) from Rating r where r.item.genre like concat('%', :genre, '%') " +
            "and r.user.userId = :id")
    Integer countUserRatingsByGenres(@Param("genre") String genre, @Param("id") long id);

    @Query("select new com.justynastanek.movieworldwebapp.dto.RatedItem(r.item.title, r.item.avgRating, r.rating) " +
            "from Rating r where r.user.userId = :id order by r.ratingTimestamp desc")
    List<RatedItem> getRatedTitles(@Param("id") long id);
}
