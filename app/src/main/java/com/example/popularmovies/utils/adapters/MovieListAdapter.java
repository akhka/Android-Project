package com.example.popularmovies.utils.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.Constants;
import com.example.popularmovies.utils.RecyclerViewOnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private ArrayList<Movie> movies;
    private RecyclerViewOnItemClickListener mListener;

    public MovieListAdapter(ArrayList<Movie> items) {
        movies = items;
    }

    public void setOnItemClickListener(RecyclerViewOnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movie_recycler_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = movies.get(i);

        Picasso.get().load(Constants.IMAGE_URL + movie.getPosterUrl()).into(viewHolder.imageView_movie_list);

        viewHolder.textView_list_title.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView_movie_list;
        TextView textView_list_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_movie_list = itemView.findViewById(R.id.imageView_movie_list);
            textView_list_title = itemView.findViewById(R.id.textView_list_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClickListener(position);
                        }
                    }
                }
            });
        }
    }
}
