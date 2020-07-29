package com.justynastanek.movieworldwebapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Item {

    @Id
    private String movieId;
    private String title;
    private String year;
    private String genre;
    private String language;
    private String poster;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double avgRating;

    @OneToMany(mappedBy = "item")
    private List<Rating> ratings = new ArrayList<>();
}
