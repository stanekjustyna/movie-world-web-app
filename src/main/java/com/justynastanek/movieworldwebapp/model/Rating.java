package com.justynastanek.movieworldwebapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Rating {

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movieId")
    private Item item;

    private int rating;

    @Id
    private LocalDateTime ratingTimestamp;
}
