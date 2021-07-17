package up.info.tp3.service;

import android.telecom.Call;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import up.info.tp3.data.Pokemon;

public interface PokemonService {
    final String BASE_URL = "https://pokeapi.co/api/v2/";
/*
    @GET("pokemon/{id}")
    Call<Pokemon> getPokemonById(@Path("id") String id);
*/

}