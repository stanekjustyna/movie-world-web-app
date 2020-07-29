package com.justynastanek.movieworldwebapp.service;

import com.justynastanek.movieworldwebapp.model.Item;
import com.justynastanek.movieworldwebapp.repository.UserBasedFilteringRecsRepo;
import com.justynastanek.movieworldwebapp.repository.UserBasedFilteringVersionRepo;
import com.justynastanek.movieworldwebapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBasedFilteringRecsService {

    private UserBasedFilteringVersionRepo userBasedFilteringVersionRepo;
    private UserBasedFilteringRecsRepo userBasedFilteringRecsRepo;
    private UserRepo userRepo;

    @Autowired
    public UserBasedFilteringRecsService(UserBasedFilteringVersionRepo userBasedFilteringVersionRepo,
                                         UserBasedFilteringRecsRepo userBasedFilteringRecsRepo, UserRepo userRepo) {
        this.userBasedFilteringVersionRepo = userBasedFilteringVersionRepo;
        this.userBasedFilteringRecsRepo = userBasedFilteringRecsRepo;
        this.userRepo = userRepo;
    }

    public List<Item>  getUserBasedRecommendations(long id) {
        return userBasedFilteringRecsRepo.findAllByUserAndVersion(userRepo.findByUserId(id).get(),
                userBasedFilteringVersionRepo.findTopByOrderByVersionDesc())
                .stream().map(e -> e.getItem()).collect(Collectors.toList());
    }
}
