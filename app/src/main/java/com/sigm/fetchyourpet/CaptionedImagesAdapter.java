package com.sigm.fetchyourpet;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    boolean front = false;
    float textsize = 10;
    private List<Dog> dogs;
    private int image;
    private String user;
    private boolean viewDogs = false;



    public CaptionedImagesAdapter() {

    }

    public CaptionedImagesAdapter(List<Dog> images) {
        this.dogs = images;
    }


    void setDogs(List<Dog> images) {
        this.dogs = images;

    }

    void setUser(String s) {
        user = s;
    }


    @Override
    public int getItemCount() {
        return dogs.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Dog d = dogs.get(position);
        TextView tv = holder.cardView.findViewById(R.id.cardText);


        tv.setText(d.getName());

        Glide
                .with(holder.cardView.getContext())
                .load(d.getImage())
                .into((ImageView) holder.cardView.findViewById(R.id.cardView));


        final CardView cardView = holder.cardView;


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cardView.getContext(), CollectionEnlarge.class);

                // Instead of sending the position, send the database id in the intent.
                long id = CaptionedImagesAdapter.this.dogs.get(position).getId();
                intent.putExtra(CollectionEnlarge.EXTRA_DOG_ID, id);
                intent.putExtra("viewDogs",viewDogs);


                cardView.getContext().startActivity(intent);


            }


        });


    }

    public void setViewDogs(boolean viewDogs) {
        this.viewDogs = viewDogs;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
            imageView = v.findViewById(R.id.cardView);


        }
    }
}
