package id.zainalfahrudin.topmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import id.zainalfahrudin.topmovie.adapter.AdapterListMovie;
import id.zainalfahrudin.topmovie.model.ModelListMovie;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    public static final String HTTP_API_THEMOVIEDB = "http://api.themoviedb.org/3/movie/";
    private GridView gvMovie;
    private static OkHttpClient client  = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String strResponse;
    private String TAG = "HomeActivity";
    private List<ModelListMovie.ResultsEntity> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gvMovie = (GridView) findViewById(R.id.gvImageMovie);

        getListMovie();
    }

    private void getListMovie() {
        try {
            get(HTTP_API_THEMOVIEDB+"popular?api_key="+BuildConfig.MOVIE_DB_API_KEY, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    HomeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(HomeActivity.this, "Error when getting list movie", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    strResponse = response.body().string();
                    Log.d(TAG, "onResponse: "+strResponse);
                    HomeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {
                                ModelListMovie modelListMovie = new Gson().fromJson(strResponse, ModelListMovie.class);
                                final List<ModelListMovie.ResultsEntity> resultsEntity = modelListMovie.getResults();
                                AdapterListMovie adapter = new AdapterListMovie(getApplicationContext(),R.layout.item_gridview,resultsEntity);
                                gvMovie.setAdapter(adapter);
                                gvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        int id_movie = resultsEntity.get(position).getId();
                                        Intent intent = new Intent(HomeActivity.this,DetailMovie.class);
                                        intent.putExtra("id",id_movie);
                                        intent.putExtra("title",resultsEntity.get(position).getOriginal_title());
                                        intent.putExtra("vote",String.valueOf(resultsEntity.get(position).getVote_average()));
                                        intent.putExtra("poster",resultsEntity.get(position).getPoster_path());
                                        intent.putExtra("release",resultsEntity.get(position).getRelease_date());
                                        intent.putExtra("sinopsis",resultsEntity.get(position).getOverview());
                                        startActivity(intent);
                                    }
                                });
                            }else{
                                Toast.makeText(HomeActivity.this, "failed response", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(HomeActivity.this, "failed get list movie", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.nav_about){
            final MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
            builder.content(getString(R.string.writer))
                    .cancelable(true)
                    .contentGravity(GravityEnum.CENTER)
                    .contentColorRes(R.color.black)
                    .title(getString(R.string.about))
                    .positiveText("BACK")
                    .positiveColor(getResources().getColor(R.color.colorPrimary))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .build();
            builder.show();
        }else{
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Call get(String url, Callback callback) throws IOException {
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }


}