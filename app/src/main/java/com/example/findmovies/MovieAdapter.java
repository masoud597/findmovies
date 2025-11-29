package com.example.findmovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<MovieModel> movies = new ArrayList<>();

    public void updateMovies(List<MovieModel> newMovies) {
        if (newMovies != null) {
            movies.clear();
            movies.addAll(newMovies);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        MovieModel movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPoster;
        private final TextView txtTitle, txtRating, txtYear;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtYear = itemView.findViewById(R.id.txtYear);
        }

        void bind(MovieModel movie) {
            if (movie.getPoster() != null && !movie.getPoster().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(movie.getPoster())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imgPoster);
            }
            txtTitle.setText((movie.getTitle() != null) ? movie.getTitle() : "Movie Title");
            txtRating.setText((movie.getImdb_rating() != null) ? movie.getImdb_rating() : "-/10");
            txtYear.setText((movie.getYear() != null) ? movie.getYear() : "-");
        }
    }
}
