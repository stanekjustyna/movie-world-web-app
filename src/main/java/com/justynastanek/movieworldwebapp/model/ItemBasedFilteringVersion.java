package com.justynastanek.movieworldwebapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ItemBasedFilteringVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long version;

    private LocalDateTime timestamp;
}
