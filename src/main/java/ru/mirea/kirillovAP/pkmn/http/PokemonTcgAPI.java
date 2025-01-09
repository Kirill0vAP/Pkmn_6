package ru.mirea.kirillovAP.pkmn.http;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.*;

public interface PokemonTcgAPI {
    @GET("/v2/card")
    Call<JsonNode> getPokemon(@Query(value = "q", encoded = true) String query);
}