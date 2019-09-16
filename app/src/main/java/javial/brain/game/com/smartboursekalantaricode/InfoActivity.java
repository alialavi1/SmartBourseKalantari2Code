package javial.brain.game.com.smartboursekalantaricode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javial.brain.game.com.smartboursekalantaricode.model.MovieDetailModel;
import javial.brain.game.com.smartboursekalantaricode.model.MovieListModel;
import javial.brain.game.com.smartboursekalantaricode.model.RatingsModel;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InfoActivity extends AppCompatActivity {

    String imdbID_Code;
    String url_MovieInfoList = "http://www.omdbapi.com/?apikey=3e974fca&i=";
    String serverResponce;
    JSONObject jsonObject;
    JSONArray jsonArray;

    private TextView  tv_Director, tv_Writer, tv_Actors, tv_Plot, tv_Language, tv_Country, tv_Awards, tv_Metascore, tv_imdbRating, tv_imdbVotes, tv_imdbID;
    private TextView tv_Title, tv_Year, tv_Rated , tv_Released, tv_Runtime, tv_Genre, tv_Type, tv_DVD, tv_BoxOffice, tv_Production, tv_Website, tv_Response;
    private ImageView iv_Poster;

    private TextView rating1,rating2,rating3;
    private LinearLayout lay_ratingS1,lay_ratingS2,lay_ratingS3;
    private LinearLayout lay_ratingV1,lay_ratingV2,lay_ratingV3;

    private TextView tv_Source1,tv_Source2,tv_Source3;
    private TextView tv_Value1,tv_Value2,tv_Value3;

    private int id;
    private String Title;
    private String Year;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private String Poster;
    private String Metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String Type;
    private String DVD;
    private String BoxOffice;
    private String Production;
    private String Website;
    private String Response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info_item);

        Hawk.init(this)
                .setEncryption(new NoEncryption())
                .build();

        imdbID_Code = getIntent().getStringExtra("IMDBID");
        //imdbID ="tt0372784";

        tv_Director = findViewById(R.id.tv_Director);
        tv_Writer = findViewById(R.id.tv_Writer);
        tv_Actors = findViewById(R.id.tv_Actors);
        tv_Plot = findViewById(R.id.tv_Plot);
        tv_Language = findViewById(R.id.tv_Language);
        tv_Country = findViewById(R.id.tv_Country);
        tv_Awards = findViewById(R.id.tv_Awards);
        iv_Poster = findViewById(R.id.iv_poster);
        tv_Metascore = findViewById(R.id.tv_Metascore);
        tv_imdbRating = findViewById(R.id.tv_imdbRating);
        tv_imdbVotes = findViewById(R.id.tv_imdbVotes);
        tv_imdbID = findViewById(R.id.tv_imdbID);

        tv_Title = findViewById(R.id.tv_Title);
        tv_Year = findViewById(R.id.tv_year);
        tv_Rated = findViewById(R.id.tv_Rated);
        tv_Released = findViewById(R.id.tv_Released);
        tv_Runtime = findViewById(R.id.tv_Runtime);
        tv_Genre = findViewById(R.id.tv_Genre);
        tv_Type = findViewById(R.id.tv_type);
        tv_DVD = findViewById(R.id.tv_DVD);
        tv_BoxOffice = findViewById(R.id.tv_BoxOffice);
        tv_Production = findViewById(R.id.tv_Production);
        tv_Website = findViewById(R.id.tv_Website);
        tv_Response = findViewById(R.id.tv_Response);

        tv_Source1 = findViewById(R.id.tv_Source1);
        tv_Source2 = findViewById(R.id.tv_Source2);
        tv_Source3 = findViewById(R.id.tv_Source3);

        tv_Value1 = findViewById(R.id.tv_Value1);
        tv_Value2 = findViewById(R.id.tv_Value2);
        tv_Value3 = findViewById(R.id.tv_Value3);

        rating1 = findViewById(R.id.rating1);
        rating2 = findViewById(R.id.rating2);
        rating3 = findViewById(R.id.rating3);
        lay_ratingS1 = findViewById(R.id.lay_ratingS1);
        lay_ratingS2 = findViewById(R.id.lay_ratingS2);
        lay_ratingS3 = findViewById(R.id.lay_ratingS3);
        lay_ratingV1 = findViewById(R.id.lay_ratingV1);
        lay_ratingV2 = findViewById(R.id.lay_ratingV2);
        lay_ratingV3 = findViewById(R.id.lay_ratingV3);

        tv_Director.setText("");
        tv_Actors.setText("");
        tv_Plot.setText("");
        tv_Plot.setText("");
        tv_Country.setText("");
        tv_Awards.setText("");
        iv_Poster.setImageDrawable(null);
        tv_Metascore.setText("");
        tv_imdbRating.setText("");
        tv_imdbVotes.setText("");
        tv_imdbID.setText("");

        tv_Title.setText("");
        tv_Year.setText("");
        tv_Rated.setText("");
        tv_Released.setText("");
        tv_Runtime.setText("");
        tv_Genre.setText("");
        tv_DVD.setText("");
        tv_BoxOffice.setText("");
        tv_Production.setText("");
        tv_Website.setText("");
        tv_Response.setText("");

        tv_Source1.setText("");
        tv_Source2.setText("");
        tv_Source3.setText("");

        tv_Value1.setText("");
        tv_Value2.setText("");
        tv_Value3.setText("");

        try {
            if(hasInternetConnection()) {
                url_MovieInfoList = url_MovieInfoList.concat(imdbID_Code);
                getMovieRatings();
            }else
                getOfflineMovieInfo();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getOfflineMovieInfo() {
        serverResponce = Hawk.get("MOVIE_Detail".concat(imdbID_Code),"NOT_FOUND");
        if(serverResponce.compareTo("NOT_FOUND")!=0){
            final ArrayList<RatingsModel> ratingsModels = new ArrayList<>();
            if(serverResponce!=null){
                try {
                    jsonObject = new JSONObject(serverResponce);
                    jsonArray = jsonObject.getJSONArray("Ratings");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(jsonArray.length()>0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RatingsModel ratingsModel = new RatingsModel();

                        JSONObject c = null;
                        try {
                            c = jsonArray.getJSONObject(i);

                            ratingsModel.setSource(c.getString("Source"));
                            ratingsModel.setValue(c.getString("Value"));

                            // adding Rating to Movie list
                            ratingsModels.add(ratingsModel);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        Title = jsonObject.getString("Title");
                        Year = jsonObject.getString("Year");
                        Rated = jsonObject.getString("Rated");
                        Released = jsonObject.getString("Released");
                        Runtime = jsonObject.getString("Runtime");

                        Genre = jsonObject.getString("Genre");
                        Director = jsonObject.getString("Director");
                        Writer = jsonObject.getString("Writer");
                        Actors = jsonObject.getString("Actors");
                        Plot = jsonObject.getString("Plot");

                        Language = jsonObject.getString("Language");
                        Country = jsonObject.getString("Country");
                        Awards = jsonObject.getString("Awards");
                        Poster = jsonObject.getString("Poster");
                        Metascore = jsonObject.getString("Metascore");

                        imdbRating = jsonObject.getString("imdbRating");
                        imdbVotes = jsonObject.getString("imdbVotes");
                        imdbID = jsonObject.getString("imdbID");
                        Type = jsonObject.getString("Type");
                        DVD = jsonObject.getString("DVD");

                        BoxOffice = jsonObject.getString("BoxOffice");
                        Production = jsonObject.getString("Production");
                        Website = jsonObject.getString("Website");
                        Response = jsonObject.getString("Response");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InfoActivity.this, "---"+ratingsModels.get(0).getSource(), Toast.LENGTH_SHORT).show();
                                if(ratingsModels.size()>0) {
                                    rating1.setVisibility(View.VISIBLE);
                                    lay_ratingS1.setVisibility(View.VISIBLE);
                                    lay_ratingV1.setVisibility(View.VISIBLE);

                                    tv_Source1.setText(ratingsModels.get(0).getSource());
                                    tv_Value1.setText(ratingsModels.get(0).getValue());
                                }
                                if(ratingsModels.size()>1) {
                                    rating2.setVisibility(View.VISIBLE);
                                    lay_ratingS2.setVisibility(View.VISIBLE);
                                    lay_ratingV2.setVisibility(View.VISIBLE);

                                    tv_Source2.setText(ratingsModels.get(1).getSource());
                                    tv_Value2.setText(ratingsModels.get(1).getValue());
                                }
                                if(ratingsModels.size()>2) {
                                    rating3.setVisibility(View.VISIBLE);
                                    lay_ratingS3.setVisibility(View.VISIBLE);
                                    lay_ratingV3.setVisibility(View.VISIBLE);

                                    tv_Source3.setText(ratingsModels.get(2).getSource());
                                    tv_Value3.setText(ratingsModels.get(2).getValue());
                                }

                                Toast.makeText(InfoActivity.this, "---"+Title, Toast.LENGTH_SHORT).show();

                                tv_Title.setText(Title);
                                tv_Year.setText(Year);
                                tv_Rated.setText(Rated);
                                tv_Released.setText(Released);
                                tv_Runtime.setText(Runtime);

                                tv_Genre.setText(Genre);
                                tv_Director.setText(Director);
                                tv_Writer.setText(Writer);
                                tv_Actors.setText(Actors);
                                tv_Plot.setText(Plot);

                                tv_Language.setText(Language);
                                tv_Country.setText(Country);
                                tv_Awards.setText(Awards);
                                Glide.with(InfoActivity.this)
                                        .load(Poster)
                                        .into(iv_Poster);
                                tv_Metascore.setText(Metascore);

                                tv_imdbRating.setText(imdbRating);
                                tv_imdbVotes.setText(imdbVotes);
                                tv_imdbID.setText(imdbID);
                                tv_Type.setText(Type);
                                tv_DVD.setText(DVD);

                                tv_BoxOffice.setText(BoxOffice);
                                tv_Production.setText(Production);
                                tv_Website.setText(Website);
                                tv_Response.setText(Response);

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{
                }
            }
        }else {
            Toast.makeText(this, "برای اولین ورود نیاز به اینترنت دارید", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void getMovieRatings() throws IOException {  // ok
        //final ArrayList<MovieDetailModel> movieDetailModels = new ArrayList<>();
        final ArrayList<RatingsModel> ratingsModels = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url_MovieInfoList)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                serverResponce = response.body().string();

                // save MovieDetail for Offline
                Hawk.put("MOVIE_Detail".concat(imdbID_Code),serverResponce);

                if(serverResponce!=null) {
                    try {
                        jsonObject = new JSONObject(serverResponce);
                        jsonArray = jsonObject.getJSONArray("Ratings");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(jsonArray.length()>0){
                        for (int i = 0; i < jsonArray.length(); i++) {
                            RatingsModel ratingsModel = new RatingsModel();

                            JSONObject c = null;
                            try {
                                c = jsonArray.getJSONObject(i);

                                ratingsModel.setSource(c.getString("Source"));
                                ratingsModel.setValue(c.getString("Value"));

                                // adding Rating to Movie list
                                ratingsModels.add(ratingsModel);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            Title = jsonObject.getString("Title");
                            Year = jsonObject.getString("Year");
                            Rated = jsonObject.getString("Rated");
                            Released = jsonObject.getString("Released");
                            Runtime = jsonObject.getString("Runtime");

                            Genre = jsonObject.getString("Genre");
                            Director = jsonObject.getString("Director");
                            Writer = jsonObject.getString("Writer");
                            Actors = jsonObject.getString("Actors");
                            Plot = jsonObject.getString("Plot");

                            Language = jsonObject.getString("Language");
                            Country = jsonObject.getString("Country");
                            Awards = jsonObject.getString("Awards");
                            Poster = jsonObject.getString("Poster");
                            Metascore = jsonObject.getString("Metascore");

                            imdbRating = jsonObject.getString("imdbRating");
                            imdbVotes = jsonObject.getString("imdbVotes");
                            imdbID = jsonObject.getString("imdbID");
                            Type = jsonObject.getString("Type");
                            DVD = jsonObject.getString("DVD");

                            BoxOffice = jsonObject.getString("BoxOffice");
                            Production = jsonObject.getString("Production");
                            Website = jsonObject.getString("Website");
                            Response = jsonObject.getString("Response");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(InfoActivity.this, "---"+ratingsModels.get(0).getSource(), Toast.LENGTH_SHORT).show();
                                    if(ratingsModels.size()>0) {
                                        rating1.setVisibility(View.VISIBLE);
                                        lay_ratingS1.setVisibility(View.VISIBLE);
                                        lay_ratingV1.setVisibility(View.VISIBLE);

                                        tv_Source1.setText(ratingsModels.get(0).getSource());
                                        tv_Value1.setText(ratingsModels.get(0).getValue());
                                    }
                                    if(ratingsModels.size()>1) {
                                        rating2.setVisibility(View.VISIBLE);
                                        lay_ratingS2.setVisibility(View.VISIBLE);
                                        lay_ratingV2.setVisibility(View.VISIBLE);

                                        tv_Source2.setText(ratingsModels.get(1).getSource());
                                        tv_Value2.setText(ratingsModels.get(1).getValue());
                                    }
                                    if(ratingsModels.size()>2) {
                                        rating3.setVisibility(View.VISIBLE);
                                        lay_ratingS3.setVisibility(View.VISIBLE);
                                        lay_ratingV3.setVisibility(View.VISIBLE);

                                        tv_Source3.setText(ratingsModels.get(2).getSource());
                                        tv_Value3.setText(ratingsModels.get(2).getValue());
                                    }

                                    Toast.makeText(InfoActivity.this, "---"+Title, Toast.LENGTH_SHORT).show();

                                    tv_Title.setText(Title);
                                    tv_Year.setText(Year);
                                    tv_Rated.setText(Rated);
                                    tv_Released.setText(Released);
                                    tv_Runtime.setText(Runtime);

                                    tv_Genre.setText(Genre);
                                    tv_Director.setText(Director);
                                    tv_Writer.setText(Writer);
                                    tv_Actors.setText(Actors);
                                    tv_Plot.setText(Plot);

                                    tv_Language.setText(Language);
                                    tv_Country.setText(Country);
                                    tv_Awards.setText(Awards);
                                    Glide.with(InfoActivity.this)
                                            .load(Poster)
                                            .into(iv_Poster);
                                    tv_Metascore.setText(Metascore);

                                    tv_imdbRating.setText(imdbRating);
                                    tv_imdbVotes.setText(imdbVotes);
                                    tv_imdbID.setText(imdbID);
                                    tv_Type.setText(Type);
                                    tv_DVD.setText(DVD);

                                    tv_BoxOffice.setText(BoxOffice);
                                    tv_Production.setText(Production);
                                    tv_Website.setText(Website);
                                    tv_Response.setText(Response);

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }else{
                    }
                }

            }

        });
    }

    public boolean hasInternetConnection(){
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected())
        {
            return true;
        }
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected())
        {
            return true;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
        {
            return true;
        }
        return false;
    }
}
