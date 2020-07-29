package com.justynastanek.movieworldwebapp.service;

import com.justynastanek.movieworldwebapp.model.Item;
import com.justynastanek.movieworldwebapp.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private ItemRepo itemRepository;

    @Autowired
    public ItemService(ItemRepo itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<String> getAllGenres() {
        return itemRepository.findDistinctGenres().stream()
                .distinct().filter(e -> !e.equals("")&&!e.contains("|")).collect(Collectors.toList());
    }

    public Item getMovieById(String id) {
        return itemRepository.findByMovieId(id).get();
    }

    public List<Item> getItemByTitleAndYear(String title, String year) {
        List <Item> foundItems = itemRepository.findItemByTitleAndYear(title, year)
                .stream().sorted(Comparator.comparing(Item::getYear).reversed()).collect(Collectors.toList());;
        return (foundItems.size() <= 10) ? foundItems : foundItems.subList(0, 10);
    }

    public List<Item> getItemByTitle(String title) {
        List <Item> foundItems = itemRepository.findItemByTitle(title)
                .stream().sorted(Comparator.comparing(Item::getYear).reversed()).collect(Collectors.toList());;
        return (foundItems.size() <= 10) ? foundItems : foundItems.subList(0, 10);
    }
}
