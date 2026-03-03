package com.pokemon.box.controller;

import com.pokemon.box.domain.CreatePokemonRequest;
import com.pokemon.box.domain.UpdatePokemonRequest;
import com.pokemon.box.domain.dto.CreatePokemonRequestDto;
import com.pokemon.box.domain.dto.PokemonDto;
import com.pokemon.box.domain.dto.UpdatePokemonRequestDto;
import com.pokemon.box.domain.entity.Pokemon;
import com.pokemon.box.domain.entity.PokemonGender;
import com.pokemon.box.domain.entity.PokemonNature;
import com.pokemon.box.mapper.PokemonMapper;
import com.pokemon.box.service.PokemonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/box")
public class PokemonController {

    private final PokemonService  pokemonService;
    private final PokemonMapper pokemonMapper;

    public PokemonController(PokemonService pokemonService, PokemonMapper pokemonMapper) {
        this.pokemonService = pokemonService;
        this.pokemonMapper = pokemonMapper;
    }

    @PostMapping
    public ResponseEntity<PokemonDto> createPokemon(
            @Valid @RequestBody CreatePokemonRequestDto createPokemonRequestDto
    ){
        CreatePokemonRequest createPokemonRequest = pokemonMapper.fromDto(createPokemonRequestDto);
        Pokemon pokemon = pokemonService.createPokemon(createPokemonRequest);
        PokemonDto createdPokemonDto = pokemonMapper.toDto(pokemon);
        return new ResponseEntity<>(createdPokemonDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PokemonDto>> listPokemon(){
        List<Pokemon> pokemonList = pokemonService.listPokemon();
        List<PokemonDto> pokemonDtos = pokemonList.stream().map(pokemonMapper::toDto).toList();
        return ResponseEntity.ok(pokemonDtos);
    }

    @PutMapping("/{pokemonId}")
    public ResponseEntity<PokemonDto> updatePokemon(
            @PathVariable UUID pokemonId,
            @Valid @RequestBody UpdatePokemonRequestDto updatePokemonRequestDto
    ){
        UpdatePokemonRequest  updatePokemonRequest = pokemonMapper.fromDto(updatePokemonRequestDto);
        Pokemon pokemon = pokemonService.updatePokemon(pokemonId, updatePokemonRequest);
        PokemonDto pokemonDto = pokemonMapper.toDto(pokemon);
        return ResponseEntity.ok(pokemonDto);
    }

    @DeleteMapping(path = "/{pokemonId}")
    public ResponseEntity<Void> deletePokemon(@PathVariable UUID pokemonId){
        pokemonService.deletePokemon(pokemonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
