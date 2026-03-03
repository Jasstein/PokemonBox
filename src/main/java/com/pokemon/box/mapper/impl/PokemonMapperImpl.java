package com.pokemon.box.mapper.impl;

import com.pokemon.box.domain.CreatePokemonRequest;
import com.pokemon.box.domain.UpdatePokemonRequest;
import com.pokemon.box.domain.dto.CreatePokemonRequestDto;
import com.pokemon.box.domain.dto.PokemonDto;
import com.pokemon.box.domain.dto.UpdatePokemonRequestDto;
import com.pokemon.box.domain.entity.Pokemon;
import com.pokemon.box.mapper.PokemonMapper;
import org.springframework.stereotype.Component;

@Component
public class PokemonMapperImpl implements PokemonMapper {

    @Override
    public CreatePokemonRequest fromDto(CreatePokemonRequestDto dto) {
        return new CreatePokemonRequest(
                dto.species(),
                dto.name(),
                dto.gender(),
                dto.nature(),
                dto.move1(),
                dto.move2(),
                dto.move3(),
                dto.move4()
        );
    }

    @Override
    public UpdatePokemonRequest fromDto(UpdatePokemonRequestDto dto) {
        return new UpdatePokemonRequest(
                dto.name(),
                dto.nature(),
                dto.move1(),
                dto.move2(),
                dto.move3(),
                dto.move4()
        );
    }

    @Override
    public PokemonDto toDto(Pokemon pokemon) {
        return new PokemonDto(
                pokemon.getId(),
                pokemon.getSpecies(),
                pokemon.getName(),
                pokemon.getGender(),
                pokemon.getNature(),
                pokemon.getMove1(),
                pokemon.getMove2(),
                pokemon.getMove3(),
                pokemon.getMove4()
        );
    }
}
