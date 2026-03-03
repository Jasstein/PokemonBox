package com.pokemon.box;

import com.pokemon.box.domain.entity.Pokemon;
import com.pokemon.box.domain.entity.PokemonGender;
import com.pokemon.box.domain.entity.PokemonNature;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.regex.Pattern;

@SpringBootApplication
public class PokemonBoxApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(PokemonBoxApplication.class, args);
	//	sendRequest();

	}

	public static void sendRequest() throws IOException, InterruptedException {
		//create Pokemon
		String postURL = "http://localhost:8080/api/v1/box";
		HttpClient client = HttpClient.newHttpClient();
		Instant now = Instant.now();
		Pokemon pikachu = new Pokemon(null, "Pikachu", "Pika", PokemonGender.MALE, PokemonNature.ADAMANT,
				"Thundershock", "Thunderbolt", "Thunder", "Volt Tackle", now, now);
		String json = new StringBuilder()
				.append("{")
				.append("\"species\":\""+pikachu.getSpecies()+"\",")
				.append("\"name\":\""+pikachu.getName()+"\",")
				.append("\"gender\":\""+pikachu.getGender()+"\",")
				.append("\"nature\":\""+pikachu.getNature()+"\",")
				.append("\"move1\":\""+pikachu.getMove1()+"\",")
				.append("\"move2\":\""+pikachu.getMove2()+"\",")
				.append("\"move3\":\""+pikachu.getMove3()+"\",")
				.append("\"move4\":\""+pikachu.getMove4()+"\"")
				.append("}").toString();

		HttpRequest request = HttpRequest.newBuilder()
				.header("Content-Type", "application/json")
				.uri(URI.create(postURL))
				.POST(HttpRequest.BodyPublishers.ofString(json))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		//create second Pokemon
		now = Instant.now();
		Pokemon charizard = new Pokemon(null, "Charizard", "Char", PokemonGender.MALE, PokemonNature.MODEST,
				"Flamethrower", "Fire Blast", "Fire Spin", "Fly", now, now);
		String json2 = new StringBuilder()
				.append("{")
				.append("\"species\":\""+charizard.getSpecies()+"\",")
				.append("\"name\":\""+charizard.getName()+"\",")
				.append("\"gender\":\""+charizard.getGender()+"\",")
				.append("\"nature\":\""+charizard.getNature()+"\",")
				.append("\"move1\":\""+charizard.getMove1()+"\",")
				.append("\"move2\":\""+charizard.getMove2()+"\",")
				.append("\"move3\":\""+charizard.getMove3()+"\",")
				.append("\"move4\":\""+charizard.getMove4()+"\"")
				.append("}").toString();
		request = HttpRequest.newBuilder()
				.header("Content-Type", "application/json")
				.uri(URI.create(postURL))
				.POST(HttpRequest.BodyPublishers.ofString(json2))
				.build();
		response = client.send(request, HttpResponse.BodyHandlers.ofString());



	//	System.out.println("Status code: " + response.statusCode());
	//	System.out.println("Body: " + response.body());

		//get Pokemon list
		String URL_GET = "http://localhost:8080/api/v1/box";
		URL url = new URL(URL_GET);
		HttpRequest getRequest = HttpRequest.newBuilder()
				.GET()
				.timeout(Duration.ofSeconds(10))
				.uri(URI.create(URL_GET))
				.build();
		HttpResponse<String> getResponse = client.send(getRequest,HttpResponse.BodyHandlers.ofString());
		System.out.println(getResponse.statusCode());
		System.out.println(getResponse.body());

		//delete [] from response
		String charToDel = "[]";
		String pat = "[" + Pattern.quote(charToDel) + "]";
		String responseBody = getResponse.body().replaceAll(pat,"");

		//get list of Pokemon in JSONObject
		String[] parts = responseBody.split("(?<=}),(?=\\{)");
		for (String part : parts) {
			System.out.println(part);
			JSONObject jsonObject = new JSONObject(part);
			System.out.println(jsonObject.get("species"));
		}


	}

}
