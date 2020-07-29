package com.justynastanek.movieworldwebapp.service;

import com.justynastanek.movieworldwebapp.dto.RequestList;
import com.justynastanek.movieworldwebapp.model.Item;
import com.justynastanek.movieworldwebapp.model.PersonalList;
import com.justynastanek.movieworldwebapp.repository.ItemRepo;
import com.justynastanek.movieworldwebapp.repository.PersonalListRepo;
import com.justynastanek.movieworldwebapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonalListService {

    private PersonalListRepo personalListRepository;
    private UserRepo userRepository;
    private ItemRepo itemRepository;

    @Autowired
    public PersonalListService(PersonalListRepo personalListRepository, UserRepo userRepository, ItemRepo itemRepository) {
        this.personalListRepository = personalListRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public boolean addItem(RequestList requestList) {

        if (userRepository.findByUserId(requestList.getUserId()).isPresent()) {
            PersonalList personalList = new PersonalList();
            personalList.setUser(userRepository.findByUserId(requestList.getUserId()).get());
            personalList.setItem(itemRepository.findByMovieId(requestList.getMovieId()).get());
            personalList.setModTimestamp(LocalDateTime.now());

            Optional<PersonalList> personalListPosition =
            personalListRepository.findOneByUserIdAndMovieId(requestList.getUserId(), requestList.getMovieId());

            if (personalListPosition.isEmpty()) {
                personalList.setOnList(true);
                personalListRepository.save(personalList);
                return true;
            } else {
                personalList.setOnList(!personalListPosition.get().isOnList());
                personalListRepository.updatePersonalList(
                        personalList.getUser().getUserId(), personalList.getItem().getMovieId(), personalList.isOnList());
                return true;
            }
        }

        return false;
    }

    public List<Item> getUserList(long id) {
        Optional<List<PersonalList>> userList = personalListRepository.findAllByUserId(id);

        if (userList.isPresent()) {

            List<Item> userActiveList
                    = userList.get().stream().filter(e -> e.isOnList() == true).map(e -> e.getItem())
                    .collect(Collectors.toList());

            if (userActiveList.size() > 10) {
                return userActiveList.subList(0, 10);
            }

            return userActiveList;
        }

        return null;
    }

    public boolean isOnUserList(long userId, String movieId) {

        return
                (personalListRepository.isOnUserList(userId, movieId).isEmpty()) ?
                        false : personalListRepository.isOnUserList(userId, movieId).get();
    }
}
