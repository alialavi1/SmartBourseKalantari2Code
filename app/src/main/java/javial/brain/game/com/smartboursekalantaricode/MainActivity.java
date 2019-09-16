package javial.brain.game.com.smartboursekalantaricode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javial.brain.game.com.smartboursekalantaricode._interface.MyEvents;
import javial.brain.game.com.smartboursekalantaricode.adapter.Movie_Custom_Adapter;
import javial.brain.game.com.smartboursekalantaricode.model.MovieListModel;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {

    RecyclerView liv;
    OkHttpClient okHttpClient;
    Request request;
    String url_MovieList = "http://www.omdbapi.com/?apikey=3e974fca&s=batman";
    String url_MovieDetail = "http://www.omdbapi.com/?apikey=3e974fca&i={imdbID}";
    String serverResponce;
    JSONObject jsonObject;
    JSONArray jsonArray;
    //public

    ImdbIdItemClickReceived imdbIdItemClickReceived;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Hawk.init(this)
                .setEncryption(new NoEncryption())
                .build();

        liv = findViewById(R.id.liv);

        imdbIdItemClickReceived = new ImdbIdItemClickReceived();
        intentFilter = new IntentFilter("ITEM_CLICKED");
        registerReceiver(imdbIdItemClickReceived,intentFilter);

        //if(Hawk.put("FIRST",false))Hawk.put("MOVIE_NU",0);

        ArrayList<MovieListModel> movies = new ArrayList<>();
        try {
            if(hasInternetConnection())
                getMovieList();
            else
                getOfflineMovieList();

        } catch (IOException e) {
           e.printStackTrace();
        }

        /*Movie_Custom_Adapter adapter = new Movie_Custom_Adapter(this,movies);
        liv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        liv.setAdapter(adapter);*/

    }

    /*public interface MyEvents{
        public void itemClicked(String id);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imdbIdItemClickReceived);
    }

    private void getOfflineMovieList() {
        serverResponce = Hawk.get("MOVIE","NOT_FOUND");
        if(serverResponce.compareTo("NOT_FOUND")!=0){
            final ArrayList<MovieListModel> movieListModels = new ArrayList<>();
            if(serverResponce!=null) {
                try {
                    jsonObject = new JSONObject(serverResponce);
                    jsonArray = jsonObject.getJSONArray("Search");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(jsonArray.length()>0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MovieListModel movieListModel = new MovieListModel();

                        JSONObject c = null;
                        try {
                            c = jsonArray.getJSONObject(i);

                            movieListModel.setImdbId(c.getString("imdbID"));
                            movieListModel.setType(c.getString("Type"));
                            movieListModel.setTitle(c.getString("Title"));
                            movieListModel.setYear(c.getString("Year"));
                            movieListModel.setPoster(c.getString("Poster"));

                            // adding Movie to Movie list
                            movieListModels.add(movieListModel);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadMovieToREcyclerView(movieListModels);
                        }
                    });

                }else{
                }

            }
        }else Toast.makeText(this, "برای اولین ورود نیاز به اینترنت دارید", Toast.LENGTH_SHORT).show();
    }

    public void getMovieList() throws IOException {  // ok
        final ArrayList<MovieListModel> movieListModels = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url_MovieList)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                serverResponce = response.body().string();

                // save Movie for Offline
                Hawk.put("MOVIE",serverResponce);

                if(serverResponce!=null) {
                    try {
                        jsonObject = new JSONObject(serverResponce);
                        jsonArray = jsonObject.getJSONArray("Search");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(jsonArray.length()>0){
                        for (int i = 0; i < jsonArray.length(); i++) {
                            MovieListModel movieListModel = new MovieListModel();

                            JSONObject c = null;
                            try {
                                c = jsonArray.getJSONObject(i);

                                movieListModel.setImdbId(c.getString("imdbID"));
                                movieListModel.setType(c.getString("Type"));
                                movieListModel.setTitle(c.getString("Title"));
                                movieListModel.setYear(c.getString("Year"));
                                movieListModel.setPoster(c.getString("Poster"));

                                // adding Movie to Movie list
                                movieListModels.add(movieListModel);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadMovieToREcyclerView(movieListModels);
                            }
                        });

                    }else{
                    }

                }

            }
        });
    }

    private void loadMovieToREcyclerView(ArrayList<MovieListModel> movieListModels) {
        Movie_Custom_Adapter adapter = new Movie_Custom_Adapter(MainActivity.this, movieListModels);
        liv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        liv.setAdapter(adapter);
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

    public class ImdbIdItemClickReceived extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String imdbId = intent.getStringExtra("ID");
            openInfoActivity(imdbId);
        }
    }

    public  void openInfoActivity(String id){
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        intent.putExtra("IMDBID" , id);
        startActivity(intent);
    }

}
