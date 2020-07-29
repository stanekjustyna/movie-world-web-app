package com.justynastanek.movieworldwebapp.service;

import com.justynastanek.movieworldwebapp.model.Item;
import com.justynastanek.movieworldwebapp.repository.ItemBasedFilteringRecsRepo;
import com.justynastanek.movieworldwebapp.repository.ItemBasedFilteringVersionRepo;
import com.justynastanek.movieworldwebapp.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemBasedFilteringRecsService {

    private ItemBasedFilteringVersionRepo itemBasedFilteringVersionRepo;
    private ItemBasedFilteringRecsRepo itemBasedFilteringRecsRepo;
    private ItemRepo itemRepository;

    @Autowired
    public ItemBasedFilteringRecsService(ItemBasedFilteringVersionRepo itemBasedFilteringVersionRepo,
                                         ItemBasedFilteringRecsRepo itemBasedFilteringRecsRepo,
                                         ItemRepo itemRepository) {
        this.itemBasedFilteringVersionRepo = itemBasedFilteringVersionRepo;
        this.itemBasedFilteringRecsRepo = itemBasedFilteringRecsRepo;
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemBasedRecommendations(String id) {
        return itemBasedFilteringRecsRepo.findAllByBaseItemAndVersion(itemRepository.findByMovieId(id).get(),
                itemBasedFilteringVersionRepo.findTopByOrderByVersionDesc())
                .stream().map(e -> e.getSimilarItem()).collect(Collectors.toList());
    }
}
