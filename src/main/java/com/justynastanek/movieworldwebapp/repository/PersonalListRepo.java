package com.justynastanek.movieworldwebapp.repository;

import com.justynastanek.movieworldwebapp.model.PersonalList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalListRepo extends JpaRepository<PersonalList, Long> {


    @Query("select p from PersonalList p join p.user u join p.item i where u.userId = :userId and i.movieId = :movieId")
    Optional<PersonalList> findOneByUserIdAndMovieId(@Param("userId") long userId, @Param("movieId") String movieId);

    @Transactional
    @Modifying
    @Query("update PersonalList p set p.onList = :onList, p.modTimestamp = current_timestamp() " +
            "where p.user.userId = :userId and p.item.movieId = :movieId")
    int updatePersonalList(@Param("userId") long userId, @Param("movieId") String movieId, @Param("onList") boolean onList);

    @Query("select p from PersonalList p where p.user.userId = :id order by p.modTimestamp desc")
    Optional<List<PersonalList>> findAllByUserId(@Param("id") long id);

    @Query("select p.onList from PersonalList p where p.user.userId = :id and p.item.movieId = :movieId")
    Optional<Boolean> isOnUserList(@Param("id") long userId, @Param("movieId") String movieId);
}
