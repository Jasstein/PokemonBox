package com.pokemon.box.exception;

import java.util.UUID;

public class PokemonNotFoundException extends RuntimeException {

    private final UUID id;

    public PokemonNotFoundException(UUID id) {
        super(String.format("Pokemon with ID %s does not exist.", id));
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
