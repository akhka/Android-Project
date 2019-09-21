package com.example.popularmovies.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Response;
import com.example.popularmovies.utils.ApiInterface;
import com.example.popularmovies.utils.CONSTANTS;
import com.example.popularmovies.utils.RecyclerViewOnItemClickListener;
import com.example.popularmovies.utils.RetrofitHandler;
import com.example.popularmovies.utils.adapters.MovieListAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnItemClickListener {

    private Retrofit retrofit;
    private ApiInterface api;

    private String SELECTED = "popular";
    private List<Movie> movies;

    private RecyclerView recyclerView_movie_list;
    MovieListAdapter adapter;
    private RecyclerView.LayoutManager manager;

    private String jsonResponse;

    //todo: go to CONSTANTS.java to replace the TMDB key


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.popular_item_menu:{
                SELECTED = "popular";
                updateUI();
            }
            case R.id.top_item_menu:{
                SELECTED = "top_rated";
                updateUI();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        recyclerView_movie_list = findViewById(R.id.recyclerView_movie_list);
        manager = new GridLayoutManager(this, 2);

        initializeRetrofit();

        getData();

    }



    public void initializeRetrofit(){
        retrofit = new RetrofitHandler().getRtrofit(retrofit);
        api = retrofit.create(ApiInterface.class);
    }

    public void getData(){
        Call<com.example.popularmovies.model.Response> call = api.getResponse(SELECTED, CONSTANTS.TMDB_API_KEY);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Response result = response.body();
                jsonResponse = result.toString();
                System.out.println(jsonResponse);
                movies = result.getMovies();
                adapter = new MovieListAdapter((ArrayList<Movie>) movies);
                adapter.setOnItemClickListener(MainActivity.this);
                recyclerView_movie_list.setAdapter(adapter);
                recyclerView_movie_list.setLayoutManager(manager);

                for (Movie movie : movies){
                    System.out.println(movie.getTitle() + ", " + CONSTANTS.BASE_URL + movie.getPosterUrl());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void updateUI(){
        getData();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClickListener(int position) {
        Intent detailIntent = new Intent(this, DetailsActivity.class);
        Movie clickedMovie = movies.get(position);
        detailIntent.putExtra("id", clickedMovie.getId());
        detailIntent.putExtra("original_title", clickedMovie.getTitle());
        detailIntent.putExtra("poster_path", clickedMovie.getPosterUrl());
        detailIntent.putExtra("overview", clickedMovie.getOverview());
        detailIntent.putExtra("vote_average", clickedMovie.getVoteAverage());
        detailIntent.putExtra("release_date", clickedMovie.getReleaseDate());

        startActivity(detailIntent);
    }
}
