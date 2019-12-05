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




/** Handles each individual cardview that goes into the recyclerview
 * @author Dylan
 */
class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    //The list of dogs that are going to be displayed
    private List<Dog> dogs;
    //Determines whether or not the rescue is attempting to view their specific dogs
    private boolean viewDogs = false;


    public CaptionedImagesAdapter() {

    }


    /**
     *
     * @param dogs - the list of dogs
     */
    void setDogs(List<Dog> dogs) {
        this.dogs = dogs;

    }


    /**
     *
     * @return the size of the dog list
     */
    @Override
    public int getItemCount() {
        return dogs.size();
    }

    /**
     *
     * @param position - position of adapter
     * @return the position
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }



    /**
     * Inflate the layout
     * @param parent - handled by android
     * @param viewType -handled by android
     * @return - the viewholder for the adapter
     */
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }




    /**
     * Initialization for each cardView
     * @param holder - handled by android
     * @param position - handled by android (position of adapter)
     *
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Dog d = dogs.get(position);
        TextView tv = holder.cardView.findViewById(R.id.cardText);


        tv.setText(d.getName());

        //If the dog uploaded a photo in this session, b will not be null. Use that photo if
        //it is not null. Else, use the photo stored in the database. This is done because, in some
        //instances, the photo will not be uploaded to the database quick enough, so we must use
        //the photo from the current session.
        if (d.bitmapImage != null) {
            Glide.with(holder.cardView.getContext())
                    // .using(new FirebaseImageLoader())
                    .load(d.bitmapImage)
                    .into((ImageView) holder.cardView.findViewById(R.id.cardView));

        } else {


            Glide.with(holder.cardView.getContext())
                    // .using(new FirebaseImageLoader())
                    .load(d.imageStorageReference)
                    .into((ImageView) holder.cardView.findViewById(R.id.cardView));
        }

        final CardView cardView = holder.cardView;

        //Set the onClickListener for each card. Send the ID to the CollectionEnlarge activity and start the activity.
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cardView.getContext(), CollectionEnlarge.class);

                // Instead of sending the position, send the database id in the intent.
                String id = CaptionedImagesAdapter.this.dogs.get(position).getId();
                intent.putExtra(CollectionEnlarge.EXTRA_DOG_ID, id);
                intent.putExtra("viewDogs", viewDogs);


                cardView.getContext().startActivity(intent);


            }


        });


    }

    /**
     *
     * @param viewDogs - boolean determining whether or not the rescue is trying to view their dogs
     */
    public void setViewDogs(boolean viewDogs) {
        this.viewDogs = viewDogs;
    }

    /**
     * sets the CardView and imageview for the ViewHolder
     */
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
