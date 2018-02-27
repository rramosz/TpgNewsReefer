package ec.com.tpg.tpgnews.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.menu_principal;
import ec.com.tpg.tpgnews.model.Picture;
import ec.com.tpg.tpgnews.view.PictureDetailActvity;

/**
 * Created by Ricky Ramos. on 6/2/2018.
 */

public class PictureAdapterRecyclerView extends RecyclerView.Adapter<PictureAdapterRecyclerView.PictureViewHolder> {

    private ArrayList<Picture> pictures;
    private int resource;
    private Activity activity;

    public PictureAdapterRecyclerView(ArrayList<Picture> pictures, int resources, Activity activity) {
        this.pictures = pictures;
        this.resource = resources;
        this.activity = activity;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
          Picture picture= pictures.get(position);
          holder.user_name_card.setText(picture.getUser_name());
          holder.time_card.setText(picture.getTime());
          holder.like_card.setText(picture.getLike_number());
        //Para visualizar la imagen desde internet.
        Picasso.with(activity).load(picture.getPicture()).into(holder.picture_card);
        holder.picture_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Intent intent = new Intent(activity, PictureDetailActvity.class);  Se comento para invocar al menu
                Intent intent = new Intent(activity, menu_principal.class);
                /*Toast.makeText(activity, "This is my Toast message!",
                        Toast.LENGTH_LONG).show();*/
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    Explode explode=new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, activity.getString(R.string.transition_name_picture) ).toBundle());
                }
                else {
                    activity.startActivity(intent);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder {

        private ImageView picture_card;
        private TextView user_name_card;
        private TextView time_card;
        private TextView like_card;

        public PictureViewHolder(View itemView) {
            super(itemView);
            picture_card= (ImageView) itemView.findViewById(R.id.picture_card_image);
            user_name_card= (TextView) itemView.findViewById(R.id.user_name);
            time_card= (TextView) itemView.findViewById(R.id.second_word);
            like_card= (TextView) itemView.findViewById(R.id.text_contador_like);
        }
    }

}
