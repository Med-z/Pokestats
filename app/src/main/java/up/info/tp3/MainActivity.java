package up.info.tp3;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import up.info.tp3.data.Pokemon;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "Pokestat"; // Identifiant pour les messages de log
    private static final String URL_API = "https://pokeapi.co/api/v2/pokemon/";
    private Button exitBut;
    private Button searchBut;
    private EditText textField;
    private static ArrayList<Pokemon> pokemon  = new ArrayList<>();;
    //private RequestQueue queue;
    //private Pokemon pokeinfos;
    private Set<String> searchedPokemonName;
    // Listes des permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exitBut = findViewById(R.id.exitBut);
        searchBut = findViewById(R.id.searchBut);
        Log.v(TAG, "quit button");
        textField = findViewById(R.id.inputPokemon);
        this.searchedPokemonName = new HashSet<String>();


        exitBut.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   finish();
               }
           }

        );

        searchBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*getApplicationContext(),*/
                //Pokemon pokeinfos = makeRequest(textField.getText().toString());
                //makeRequest(textField.getText().toString());


                Toast toast = Toast.makeText(getApplicationContext(), "Travail de recherche en cours ", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                toast.show();


                reload_historic();
                display_historic();


                Intent activity = new Intent(MainActivity.this, StatPokeActivity.class);
                activity.putExtra("Pokemon",textField.getText().toString());
                startActivity(activity);
            }
        });
        verifyStoragePermissions(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        write_historic_in_file();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*
        int mavaleur;
        if(savedInstanceState.containsKey("mavaleur"))
            mavaleur = savedInstanceState.getInt(("mavaleur"));
        else
            mavaleur = DEFAULT_MAVALEUR;

         */
    }


    // Fonction qui recharge un historique
    public void reload_historic() {
        this.searchedPokemonName.add(textField.getText().toString());
// Récuperation de l'objet unique qui s'occupe de la sauvegarde
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.edit().putStringSet("historyPokemonName", this.searchedPokemonName).commit();
// Récuperation de l'ancienne valeur ou d'une valeur par défaut
        this.searchedPokemonName = sharedPref.getStringSet("historyPokemonName", new TreeSet<String>());
    }

    // Fonction qui affiche l'historique àpartir de l'attribut searchedPokemonName
// Il faut donc avoir chargé l'historique avant!
    public void display_historic() {
        Log.d(TAG,"Historique ("+ (new Date())+ ") size= "+searchedPokemonName.size()+": ");
        for (String item : searchedPokemonName) {
            Log.d(TAG,"\t- " + item);
        }
    }

    public void write_historic_in_file() {

        //File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"pokestat_historic.txt");

        //File fileout = new File(folder, "pokestat_historic.txt");
        
        try (FileOutputStream fos = new FileOutputStream(folder)) {
            PrintStream ps = new PrintStream(fos);
            ps.println("Start of my historic");

            for (String item : searchedPokemonName) {
                ps.println("\t- " + item);
            }
            ps.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG,"File not found",e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"Error I/O",e);
        }
        /*
        try {
            File folder = new File(Environment.getExternalStorageDirectory(),Environment.DIRECTORY_DOWNLOADS);
            File fileout = new File(folder, "pokestat_historic.txt");

            PrintStream ps = new PrintStream(fileout);
            ps.println("Start of my historic");

            for (String item : searchedPokemonName) {
                ps.println("\t- " + item);
            }
            ps.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG,"File not found",e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"Error I/O",e);
        }*/
    }


    public static void verifyStoragePermissions(Activity activity) {
// Vérifie si nous avons les droits d'écriture
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
// Aie, il faut les demander àl'utilisateur
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    /*
    private void addInList(Pokemon poke){
        pokemon.add(poke);
    }

    private static Pokemon getFirstPoke(){
        return pokemon.get(0);
    }

    private void makeRequest(final String s){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL_API + s.toLowerCase();
     

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
        private Pokemon tmpPoke;

        @Override
        public void onResponse(JSONObject response) {
            if (null != response) {
                try {
                    Log.println(Log.DEBUG,MainActivity.TAG,"got res : " + response.toString());
                    JSONArray stats = response.getJSONArray("stats");
                    Log.v(TAG, response.getString("name"));
                    Log.v(TAG, response.getString("height"));

                    this.tmpPoke  = new Pokemon(
                            response.getString("name"),
                            response.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name"),
                            response.getInt("height"),
                            response.getInt("base_experience"),
                            response.getInt("weight")
                    );

                    addInList(tmpPoke);

                    } catch (JSONException e) {
                        Log.println(Log.ERROR,MainActivity.TAG,"while parsing json : " + e.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR,MainActivity.TAG,"Not a valid name !!!");
                finish();
            }

        });
        queue.add(request);
    }

*/
}