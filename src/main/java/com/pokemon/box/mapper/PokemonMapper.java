package com.pokemon.box.mapper;

import com.pokemon.box.domain.CreatePokemonRequest;
import com.pokemon.box.domain.UpdatePokemonRequest;
import com.pokemon.box.domain.dto.CreatePokemonRequestDto;
import com.pokemon.box.domain.dto.PokemonDto;
import com.pokemon.box.domain.dto.UpdatePokemonRequestDto;
import com.pokemon.box.domain.entity.Pokemon;

public interface PokemonMapper {

    CreatePokemonRequest fromDto(CreatePokemonRequestDto dto);

    UpdatePokemonRequest fromDto(UpdatePokemonRequestDto dto);

    PokemonDto toDto(Pokemon pokemon);
}
