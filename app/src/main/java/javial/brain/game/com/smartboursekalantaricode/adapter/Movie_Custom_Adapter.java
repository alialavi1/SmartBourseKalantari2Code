package javial.brain.game.com.smartboursekalantaricode.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javial.brain.game.com.smartboursekalantaricode.MainActivity;
import javial.brain.game.com.smartboursekalantaricode.R;
import javial.brain.game.com.smartboursekalantaricode._interface.MyEvents;
import javial.brain.game.com.smartboursekalantaricode.model.MovieListModel;

public class Movie_Custom_Adapter extends RecyclerView.Adapter<Movie_Custom_Adapter.ViowHolder> {
    private Context context;
    private ArrayList<MovieListModel> movies;
    private ImageView img;
    private ConstraintLayout lay;
    private int position;
    private String imdbId;
    private String Type;
    private String Year;


    public Movie_Custom_Adapter(Context context, ArrayList<MovieListModel> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_item,viewGroup,false);
        ViowHolder viowHolder = new ViowHolder(view);
        return viowHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViowHolder viowHolder, int position) {

        MovieListModel movieListModel = movies.get(position);

        Glide.with(context)
                .load(movieListModel.getPoster())
                .into(viowHolder.iv_poster);

        viowHolder.tv_imdbId.setText(movieListModel.getImdbId());
        viowHolder.tv_type.setText(movieListModel.getType());
        viowHolder.tv_title.setText(movieListModel.getTitle());
        viowHolder.tv_year.setText(movieListModel.getYear());

        viowHolder.lay.setTag(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_poster;
        TextView tv_imdbId, tv_type, tv_title, tv_year;
        ConstraintLayout lay;

        ProgressBar progressBar;
        ViowHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster = itemView.findViewById(R.id.iv_poster);

            tv_imdbId = itemView.findViewById(R.id.tv_imdbId);
            tv_type= itemView.findViewById(R.id.tv_type);
            tv_title= itemView.findViewById(R.id.tv_title);
            tv_year= itemView.findViewById(R.id.tv_year);

            lay= itemView.findViewById(R.id.lay);
            lay.setOnClickListener(this);
        }



        @Override
        public void onClick(final View v) {

            lay = (ConstraintLayout) v;
            if (v.getId() == R.id.lay) {
                Toast.makeText(context, "position " + movies.get(Integer.parseInt(lay.getTag().toString())).getImdbId() , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent("ITEM_CLICKED");
                intent. putExtra("ID", movies.get(Integer.parseInt(lay.getTag().toString())).getImdbId());
                context.sendBroadcast(intent);


            }
        }

    }
}
