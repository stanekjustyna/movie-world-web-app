package com.justynastanek.movieworldwebapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ItemBasedFilteringRecs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "baseMovieId")
    private Item baseItem;

    @ManyToOne
    @JoinColumn(name = "similarMovieId")
    private Item similarItem;

    private double similarity;

    @ManyToOne
    @JoinColumn(name = "version")
    private ItemBasedFilteringVersion version;

}
