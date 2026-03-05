package com.pokemon.box.controller;

import com.pokemon.box.domain.entity.Pokemon;
import com.pokemon.box.domain.entity.PokemonGender;
import com.pokemon.box.domain.entity.PokemonNature;
import com.pokemon.box.service.PokemonService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
@RequestMapping(path = "/")
public class PokemonDisplay {

    List<Pokemon> pokemonList;

    @RequestMapping("/home")
    public String box(Model model){
        List<Pokemon> pokemonList = new ArrayList<Pokemon>();
        model.addAttribute("pokemonList",pokemonList);
        return "boxScreen";
    }

    @PostMapping("/home")
    public String box(Model model, @RequestParam String species, @RequestParam String name,
                      @RequestParam String gender, @RequestParam String nature, @RequestParam String move1,
                      @RequestParam String move2, @RequestParam String move3, @RequestParam String move4) throws IOException, InterruptedException {

        String url = "http://localhost:8080/api/v1/box";

        //Create Pokemon
        Instant now = Instant.now();
        Pokemon pokemon = new Pokemon(null, species, name, PokemonGender.valueOf(gender), PokemonNature.valueOf(nature),
                move1, move2, move3, move4, now, now);
        HttpClient client = HttpClient.newHttpClient();
        String json = new StringBuilder()
                .append("{")
                .append("\"species\":\""+pokemon.getSpecies()+"\",")
                .append("\"name\":\""+pokemon.getName()+"\",")
                .append("\"gender\":\""+pokemon.getGender()+"\",")
                .append("\"nature\":\""+pokemon.getNature()+"\",")
                .append("\"move1\":\""+pokemon.getMove1()+"\",")
                .append("\"move2\":\""+pokemon.getMove2()+"\",")
                .append("\"move3\":\""+pokemon.getMove3()+"\",")
                .append("\"move4\":\""+pokemon.getMove4()+"\"")
                .append("}").toString();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //get Pokemon list
        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.ofSeconds(10))
                .uri(URI.create(url))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest,HttpResponse.BodyHandlers.ofString());

        //delete [] from response
        String charToDel = "[]";
        String pat = "[" + Pattern.quote(charToDel) + "]";
        String responseBody = getResponse.body().replaceAll(pat,"");

        pokemonList = new ArrayList<Pokemon>();
        //get list of Pokemon in JSONObject
        String[] parts = responseBody.split("(?<=}),(?=\\{)");
        for (String part : parts) {
        //    System.out.println(part);
            JSONObject jsonObject = new JSONObject(part);
       //     System.out.println(jsonObject.get("species"));
            Pokemon pokemonStored = new Pokemon(
                    UUID.fromString((String) jsonObject.get("id")),
                    (String) jsonObject.get("species"),
                    (String) jsonObject.get("name"),
                    PokemonGender.valueOf((String) jsonObject.get("gender")),
                    PokemonNature.valueOf((String) jsonObject.get("nature")),
                    (String) jsonObject.get("move1"),
                    (String) jsonObject.get("move2"),
                    (String) jsonObject.get("move3"),
                    (String) jsonObject.get("move4"),
                    now,
                    now);
            pokemonList.add(pokemonStored);
        }

        model.addAttribute("pokemonList",pokemonList);
        model.addAttribute("update","Update");

        return "boxScreen";
    }

    @PostMapping("/update")
    public String box(Model model, @RequestParam String pokemonId, @RequestParam String name,
                      @RequestParam String nature, @RequestParam String move1,
                      @RequestParam String move2, @RequestParam String move3, @RequestParam String move4) throws IOException, InterruptedException {
        Instant now = Instant.now();
        String url = "http://localhost:8080/api/v1/box";
        String urlPut = url + "/" + pokemonId;
        HttpClient client = HttpClient.newHttpClient();
        Pokemon pokemon = pokemonList.get(0);
        for(int i = 0; i < pokemonList.size(); i++){
            if(pokemonList.get(i).getId().toString().equals(pokemonId)){
                pokemon = pokemonList.get(i);
                break;
            }
        }
        pokemon.setName(name);
        pokemon.setNature(PokemonNature.valueOf(nature));
        pokemon.setMove1(move1);
        pokemon.setMove2(move2);
        pokemon.setMove3(move3);
        pokemon.setMove4(move4);

        String json = new StringBuilder()
                .append("{")
                .append("\"name\":\""+pokemon.getName()+"\",")
                .append("\"nature\":\""+pokemon.getNature()+"\",")
                .append("\"move1\":\""+pokemon.getMove1()+"\",")
                .append("\"move2\":\""+pokemon.getMove2()+"\",")
                .append("\"move3\":\""+pokemon.getMove3()+"\",")
                .append("\"move4\":\""+pokemon.getMove4()+"\"")
                .append("}").toString();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(urlPut))
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //get Pokemon list
        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.ofSeconds(10))
                .uri(URI.create(url))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest,HttpResponse.BodyHandlers.ofString());

        //delete [] from response
        String charToDel = "[]";
        String pat = "[" + Pattern.quote(charToDel) + "]";
        String responseBody = getResponse.body().replaceAll(pat,"");

        pokemonList = new ArrayList<Pokemon>();
        //get list of Pokemon in JSONObject
        String[] parts = responseBody.split("(?<=}),(?=\\{)");
        for (String part : parts) {
            JSONObject jsonObject = new JSONObject(part);
            Pokemon pokemonStored = new Pokemon(
                    UUID.fromString((String) jsonObject.get("id")),
                    (String) jsonObject.get("species"),
                    (String) jsonObject.get("name"),
                    PokemonGender.valueOf((String) jsonObject.get("gender")),
                    PokemonNature.valueOf((String) jsonObject.get("nature")),
                    (String) jsonObject.get("move1"),
                    (String) jsonObject.get("move2"),
                    (String) jsonObject.get("move3"),
                    (String) jsonObject.get("move4"),
                    now,
                    now);
            pokemonList.add(pokemonStored);
        }

        model.addAttribute("pokemonList",pokemonList);
        model.addAttribute("update","Update");

        return "boxScreen";
    }

    @RequestMapping("/create")
    public String create(Model model){
        return "createScreen";
    }

    @RequestMapping("/update")
    public String update(Model model, @RequestParam String pokemonId){
        Pokemon pokemon = pokemonList.get(0);
        for(int i = 0; i < pokemonList.size(); i++){
            if(pokemonList.get(i).getId().toString().equals(pokemonId)){
                pokemon = pokemonList.get(i);
                break;
            }
        }
        model.addAttribute("updatePokemon", "Update " + pokemon.getName());
        model.addAttribute("pokemonId",pokemon.getId());
        model.addAttribute("species","Species: " + pokemon.getSpecies());
        model.addAttribute("name", pokemon.getName());
        model.addAttribute("gender","Gender: " + pokemon.getGender().toString());
        model.addAttribute(pokemon.getNature().toString(),"selected");
        model.addAttribute("move1", pokemon.getMove1());
        model.addAttribute("move2", pokemon.getMove2());
        model.addAttribute("move3", pokemon.getMove3());
        model.addAttribute("move4", pokemon.getMove4());
        return "updateScreen";
    }

    @RequestMapping("/delete")
    public String delete(Model model, @RequestParam String pokemonId) throws IOException, InterruptedException {
        String url = "http://localhost:8080/api/v1/box";
        String urlDelete = url + "/" + pokemonId;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder()
                .DELETE()
                .uri(URI.create(urlDelete))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //get Pokemon list
        Instant now = Instant.now();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.ofSeconds(10))
                .uri(URI.create(url))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest,HttpResponse.BodyHandlers.ofString());

        //delete [] from response
        String charToDel = "[]";
        String pat = "[" + Pattern.quote(charToDel) + "]";
        String responseBody = getResponse.body().replaceAll(pat,"");

        if(!(responseBody.equals(""))) {
            List<Pokemon> pokemonList = new ArrayList<Pokemon>();
            //get list of Pokemon in JSONObject
            String[] parts = responseBody.split("(?<=}),(?=\\{)");
            for (String part : parts) {
                    System.out.println(part);
                JSONObject jsonObject = new JSONObject(part);
                //     System.out.println(jsonObject.get("species"));
                Pokemon pokemonStored = new Pokemon(
                        UUID.fromString((String) jsonObject.get("id")),
                        (String) jsonObject.get("species"),
                        (String) jsonObject.get("name"),
                        PokemonGender.valueOf((String) jsonObject.get("gender")),
                        PokemonNature.valueOf((String) jsonObject.get("nature")),
                        (String) jsonObject.get("move1"),
                        (String) jsonObject.get("move2"),
                        (String) jsonObject.get("move3"),
                        (String) jsonObject.get("move4"),
                        now,
                        now);
                pokemonList.add(pokemonStored);
            }
            model.addAttribute("pokemonList",pokemonList);
        }

        model.addAttribute("update","Update");

        return "boxScreen";
    }

}
