package com.justynastanek.movieworldwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatedItem {

    private String title;
    private double avgRating;
    private int userRating;
}
