package id.zainalfahrudin.topmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.zainalfahrudin.topmovie.R;
import id.zainalfahrudin.topmovie.model.ModelListMovie;

/**
 * Created by zainal on 14/05/16.
 */
public class AdapterListMovie extends ArrayAdapter {

    public static final String HTTP_BASE_URL = "http://image.tmdb.org/t/p/";
    private Context context;
    private List<ModelListMovie.ResultsEntity> data = new ArrayList<>();
    private int resourceId;
    private LayoutInflater inflater ;

    public AdapterListMovie(Context context, int resourceId, List<ModelListMovie.ResultsEntity> data) {
        super(context, resourceId, data);
        this.resourceId = resourceId;
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        ImageView image;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            row = inflater.inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);
            holder.image.setImageResource(R.drawable.ic_image_white_48dp);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag(); //Easy to recycle view
        }

        Glide.with(context)
                .load(HTTP_BASE_URL +"w500"+ data.get(position).getPoster_path())
                .fitCenter()
                .centerCrop()
                .into(holder.image);

        return row;
    }
}
