package com.justynastanek.movieworldwebapp.repository;

import com.justynastanek.movieworldwebapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<Item, String> {

    @Query("select distinct genre from Item")
    List<String> findDistinctGenres();

    Optional<Item> findByMovieId(String id);

    @Query("select i from Item i where (i.title like concat('%', :title, '%') and i.year like concat('%', :year, '%'))")
    List<Item> findItemByTitleAndYear(@Param("title") String title, @Param("year") String year);

    @Query("select i from Item i where i.title like concat('%', :title, '%')")
    List<Item> findItemByTitle(@Param("title") String title);
}
