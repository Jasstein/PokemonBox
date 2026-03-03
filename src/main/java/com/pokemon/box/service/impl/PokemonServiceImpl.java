package com.pokemon.box.service.impl;

import com.pokemon.box.domain.CreatePokemonRequest;
import com.pokemon.box.domain.UpdatePokemonRequest;
import com.pokemon.box.domain.entity.Pokemon;
import com.pokemon.box.exception.PokemonNotFoundException;
import com.pokemon.box.repository.PokemonRepository;
import com.pokemon.box.service.PokemonService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public Pokemon createPokemon(CreatePokemonRequest request) {
        Instant now = Instant.now();
        Pokemon pokemon = new Pokemon(
                null,
                request.species(),
                request.name(),
                request.gender(),
                request.nature(),
                request.move1(),
                request.move2(),
                request.move3(),
                request.move4(),
                now,
                now
        );

        return pokemonRepository.save(pokemon);
    }

    @Override
    public List<Pokemon> listPokemon() {
        return pokemonRepository.findAll(Sort.by(Sort.Direction.ASC, "created"));
    }

    @Override
    public Pokemon updatePokemon(UUID pokemonId, UpdatePokemonRequest request) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException(pokemonId));

        pokemon.setName(request.name());
        pokemon.setNature(request.nature());
        pokemon.setMove1(request.move1());
        pokemon.setMove2(request.move2());
        pokemon.setMove3(request.move3());
        pokemon.setMove4(request.move4());
        pokemon.setUpdated(Instant.now());

        return pokemonRepository.save(pokemon);
    }

    @Override
    public void deletePokemon(UUID pokemonId) {
        pokemonRepository.deleteById(pokemonId);
    }
}
