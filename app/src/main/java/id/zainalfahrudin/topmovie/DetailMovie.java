package id.zainalfahrudin.topmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.zainalfahrudin.topmovie.adapter.AdapterListMovie;

/**
 * Created by zainal on 14/05/16.
 */
public class DetailMovie extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitleMovie);
        TextView tvRelease = (TextView) findViewById(R.id.tvRelase);
        TextView tvVote = (TextView) findViewById(R.id.tvVote);
        TextView tvSinop = (TextView) findViewById(R.id.tvSinopsis);
        ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);

        Intent intent = getIntent();
        if (intent!=null){

            String title = intent.getStringExtra("title");
            String release = intent.getStringExtra("release");
            String vote = intent.getStringExtra("vote");
            String posterUrl = intent.getStringExtra("poster");
            String sinop = intent.getStringExtra("sinopsis");

            Log.d("TAG", "onCreate: "+title+"\n"+release+"\n"+vote+"\n"+sinop);


            tvTitle.setText(title);
            tvRelease.setText(release);
            tvVote.setText(vote+"/10");
            tvSinop.setText(sinop);

            Glide.with(this)
                    .load(AdapterListMovie.HTTP_BASE_URL +"w500"+ posterUrl)
                    .fitCenter()
                    .centerCrop()
                    .into(ivPoster);

        }
    }
}
