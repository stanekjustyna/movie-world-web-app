package com.justynastanek.movieworldwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRating {

    private long userId;
    private String movieId;
    private int rating;
}
