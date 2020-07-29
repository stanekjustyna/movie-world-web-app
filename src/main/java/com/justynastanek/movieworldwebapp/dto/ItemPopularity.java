package com.justynastanek.movieworldwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPopularity {

    private String movieId;
    private long ratingNo;
    private String poster;
    private String title;
}
