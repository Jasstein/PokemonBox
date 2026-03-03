package com.pokemon.box.domain;

import com.pokemon.box.domain.entity.PokemonGender;
import com.pokemon.box.domain.entity.PokemonNature;

public record CreatePokemonRequest(
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
