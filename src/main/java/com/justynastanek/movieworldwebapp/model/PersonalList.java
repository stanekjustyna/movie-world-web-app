package com.justynastanek.movieworldwebapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class PersonalList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long personalListId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movieId")
    private Item item;

    private boolean onList;

    private LocalDateTime modTimestamp;
}
