package id.zainalfahrudin.topmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class HomeActivity extends AppCompatActivity {

    private GridView gvMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gvMovie = (GridView) findViewById(R.id.gvImageMovie);
    }
}
