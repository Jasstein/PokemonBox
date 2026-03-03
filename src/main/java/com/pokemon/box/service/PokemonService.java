package com.pokemon.box.service;

import com.pokemon.box.domain.CreatePokemonRequest;
import com.pokemon.box.domain.UpdatePokemonRequest;
import com.pokemon.box.domain.entity.Pokemon;

import java.util.List;
import java.util.UUID;

public interface PokemonService {

    Pokemon createPokemon(CreatePokemonRequest request);

    List<Pokemon> listPokemon();

    Pokemon updatePokemon(UUID pokemonId, UpdatePokemonRequest request);

    void deletePokemon(UUID pokemonId);
}
