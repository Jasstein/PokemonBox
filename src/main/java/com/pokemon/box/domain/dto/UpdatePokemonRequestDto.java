package com.pokemon.box.domain.dto;

import com.pokemon.box.domain.entity.PokemonGender;
import com.pokemon.box.domain.entity.PokemonNature;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdatePokemonRequestDto(
        @NotBlank(message = ERROR_MESSAGE_NAME_LENGTH)
        @Length(max = 12, message = ERROR_MESSAGE_NAME_LENGTH)
        String name,
        @NotNull(message = ERROR_MESSAGE_NATURE)
        PokemonNature nature,
        @NotBlank(message = ERROR_MESSAGE_MOVE1)
        @Length(max = 25, message = ERROR_MESSAGE_MOVE_LENGTH)
        String move1,
        @Length(max = 25, message = ERROR_MESSAGE_MOVE_LENGTH)
        String move2,
        @Length(max = 25, message = ERROR_MESSAGE_MOVE_LENGTH)
        String move3,
        @Length(max = 25, message = ERROR_MESSAGE_MOVE_LENGTH)
        String move4
) {

    private static final String ERROR_MESSAGE_NAME_LENGTH =
            "Name length must be between 1 and 12 characters.";
    private static final String ERROR_MESSAGE_NATURE =
            "Pokemon nature must be provided.";
    private static final String ERROR_MESSAGE_MOVE1 =
            "Pokemon move 1 must be provided.";
    private static final String ERROR_MESSAGE_MOVE_LENGTH =
            "Pokemon move must be between 1 and 25 characters.";
}
