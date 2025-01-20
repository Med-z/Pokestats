package up.info.tp3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import up.info.tp3.data.Pokemon;

public class StatPokeActivity extends AppCompatActivity {

    private Button returnBut;
    private Button websiteBut;
    private TextView pokemonName;
    private String pokeresult;
    private Pokemon poke;
    private TextView displayPokemonType;
    private TextView displayPokemonSize;
    private TextView displayPokemonWeight;
    private ImageView imgPokemon;

    private static final String URL_API = "https://pokeapi.co/api/v2/pokemon/";
    private static final String url = "https://www.pokepedia.fr/";
    private  String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_poke);
        returnBut = findViewById(R.id.returnBut);
        websiteBut = findViewById(R.id.websiteBut);
        this.pokemonName = findViewById(R.id.pokemonName);
        this.displayPokemonType = findViewById(R.id.displayPokemonType);
        this.displayPokemonSize = findViewById(R.id.displayPokemonSize);
        this.displayPokemonWeight = findViewById(R.id.displayPokemonWeight);
        imgPokemon = (ImageView) findViewById(R.id.pokemonImage);


        Intent intent = getIntent();

        pokeresult = (String) intent.getSerializableExtra("Pokemon");
        Log.v("pokename",pokeresult);

        pokemonName.setText(pokeresult.toUpperCase());

        this.webUrl = url+ pokemonName.getText().toString().toLowerCase();

        returnBut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
               finish();

             }
         }
        );

        websiteBut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(Intent.ACTION_VIEW);
                 intent.setData(Uri.parse(webUrl));
                 startActivity(intent);
             }
         }
        );

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy policy = null;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        Document doc = null;
        try {

            if (pokeresult.equals("mehdi")) {
                displayMehdiResult();
            } else if (pokeresult.equals("yacine") ){
                displayYacineResult();
            } else if(pokeresult.equals("zakaria")) {
                displayZakiResult();
            }else if(pokeresult.equals("fadi")){
                displayFadiResult();
            }else {

                displayPokemonInfo();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    private void displayPokemonInfo() throws IOException {
        Document doc = Jsoup.connect(webUrl).get();

        final Element tab = doc.select(".tableaustandard.ficheinfo thead").first();
        String[] parts = tab.child(0).select("span").text().split(" ");
        String numPokedex = parts[0]; // 004
        String[] part2 = parts[1].split(" "); // 034556
        numPokedex += part2[0];

        final Element tab1 = doc.select(".tableaustandard.ficheinfo tbody").first();
        //Element tab = doc.select("illustration").first();
        Elements img = tab1.getElementsByTag("img");
        imgPokemon.setVisibility(View.VISIBLE);
        Element el =  img.get(0);
        String src = el.absUrl("src");

        Drawable drw =LoadImageFromWebOperations(src);
        Bitmap bitmap = ((BitmapDrawable) drw).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 550, 550, true));
        imgPokemon.setImageDrawable(d);


        String type  = tab1.child(6).select("td a").attr("title").split(" ")[0];
        displayPokemonType.setText(type);

        String height = tab1.child(8).select("td").text().split("m")[0].replace(",",".") + "m";
        String weight = tab1.child(9).select("td").text().split("g")[0].replace(",",".") + "g";
        displayPokemonSize.setText("" + height);
        displayPokemonWeight.setText("" + weight);

    }


    private void displayMehdiResult(){
        displayPokemonType.setText("Le fondateur");
        displayPokemonSize.setText("1.82 m");
        displayPokemonWeight.setText("70 kg");
    }

    private void displayZakiResult(){
        displayPokemonType.setText("Le rejeté");
        displayPokemonSize.setText("1.73 m");
        displayPokemonWeight.setText("66 kg");
    }

    private void displayYacineResult(){
        displayPokemonType.setText("L'égaré");
        displayPokemonSize.setText("1.76 m");
        displayPokemonWeight.setText("65 kg");
    }

    private void displayFadiResult(){
        displayPokemonType.setText("Le disparu");
        displayPokemonSize.setText("1.71 m");
        displayPokemonWeight.setText("60 kg");
    }

    private Drawable LoadImageFromWebOperations(String strPhotoUrl)
    {
        try
        {
            InputStream is = (InputStream) new URL(strPhotoUrl).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            return null;
        }
    }


}
