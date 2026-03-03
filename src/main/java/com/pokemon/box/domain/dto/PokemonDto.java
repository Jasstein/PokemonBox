package com.pokemon.box.domain.dto;

import com.pokemon.box.domain.entity.PokemonGender;
import com.pokemon.box.domain.entity.PokemonNature;

import java.time.Instant;
import java.util.UUID;

public record PokemonDto(
        UUID id,
        String species,
        String name,
        PokemonGender gender,
        PokemonNature nature,
        String move1,
        String move2,
        String move3,
        String move4
) {
}
