package com.pokemon.box.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "species", nullable = false, updatable = false, length = 12)
    private String species;

    @Column(name = "name", nullable = false, length = 12)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, updatable = false)
    private PokemonGender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature", nullable = false)
    private PokemonNature nature;

    @Column(name = "move1", nullable = false, length = 25)
    private String move1;

    @Column(name = "move2", length = 25)
    private String move2;

    @Column(name = "move3", length = 25)
    private String move3;

    @Column(name = "move4", length = 25)
    private String move4;

    @Column(name = "created", nullable = false, updatable = false)
    private Instant created;

    @Column(name = "updated", nullable = false)
    private Instant updated;

    public Pokemon() {
    }

    public Pokemon(UUID id, String species, String name, PokemonGender gender, PokemonNature nature,
                   String move1, String move2, String move3, String move4, Instant created, Instant updated) {
        this.id = id;
        this.species = species;
        this.name = name;
        this.gender = gender;
        this.nature = nature;
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.move4 = move4;
        this.created = created;
        this.updated = updated;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PokemonGender getGender() {
        return gender;
    }

    public void setGender(PokemonGender gender) {
        this.gender = gender;
    }

    public PokemonNature getNature() {
        return nature;
    }

    public void setNature(PokemonNature nature) {
        this.nature = nature;
    }

    public String getMove1() {
        return move1;
    }

    public void setMove1(String move1) {
        this.move1 = move1;
    }

    public String getMove2() {
        return move2;
    }

    public void setMove2(String move2) {
        this.move2 = move2;
    }

    public String getMove3() {
        return move3;
    }

    public void setMove3(String move3) {
        this.move3 = move3;
    }

    public String getMove4() {
        return move4;
    }

    public void setMove4(String move4) {
        this.move4 = move4;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(id, pokemon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", species='" + species + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", nature=" + nature +
                ", move1='" + move1 + '\'' +
                ", move2='" + move2 + '\'' +
                ", move3='" + move3 + '\'' +
                ", move4='" + move4 + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
