package com.sigm.fetchyourpet;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Much like the CaptionedImagesAdapter, this handles each dog's layout in ViewMatches
 * @author Dylan
 */

public class PageViewAdapter extends PagerAdapter {

    private ArrayList<Dog> dogList;
    private LayoutInflater layoutInflater;
    private Context context;
    private Dog d;


    /**
     *
     * @param dogs - the list of dogs to be displayed
     * @param context - the current context
     */
    public PageViewAdapter(ArrayList<Dog> dogs, Context context) {
        this.dogList = dogs;
        this.context = context;
    }

    /**
     * @return the size of dogList
     */
    @Override
    public int getCount() {
        return dogList.size();
    }


    /**
     *
     * @param view - handled by PagerAdapter
     * @param object -handled by PagerAdapter
     * @return - handled by PagerAdapter
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    /**
     * Initialization and setting the views for each Dog
     * @param container - the container the Dog layout will reside in
     * @param position - the position of the current Dog
     * @return - the created view
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_matches_item, container, false);

        d = dogList.get(position);
        d.disliked = false;

        ImageView image;
        image = view.findViewById(R.id.image);

        TextView age, size, name, breed, similarity;
        final ImageButton email, maps, search, like, dislike;
        CardView c;

        c = view.findViewById(R.id.cardView);
        email = view.findViewById(R.id.email);
        maps = view.findViewById(R.id.location);
        search = view.findViewById(R.id.google);
        breed = view.findViewById(R.id.breed);
        like = view.findViewById(R.id.like);
        dislike = view.findViewById(R.id.dislike);
        similarity = view.findViewById(R.id.similarityScore);

        //Set the button images depending on the dog's boolean values
        if (dogList.get(position).favorited) {
            like.setImageResource(R.drawable.like_heart);
        } else {
            like.setImageResource(R.drawable.default_heart_icon);
        }
        if (dogList.get(position).disliked) {
            dislike.setImageResource(R.drawable.dislike_red);
        } else {
            dislike.setImageResource(R.drawable.thumbs_down_icon);
        }

        //set the OnClickListeners for all of the buttons
        like.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d = dogList.get(position);

                String action = "";

                if (!d.favorited) {
                    like.setImageResource(R.drawable.like_heart);
                    action = dogList.get(position).getName() + " was added to your favorites!";
                    if (!PotentialAdopter.currentAdopter.favoritedDogsArray.contains(d)) {
                        PotentialAdopter.currentAdopter.favoritedDogsArray.add(d);
                        Log.d("test", "Favorited dog added " + d.getName());
                        d.favorited = true;

                    }
                } else {
                    like.setImageResource(R.drawable.default_heart_icon);
                    action = d.getName() + " was removed from your favorites.";
                    if (PotentialAdopter.currentAdopter.favoritedDogsArray.contains(d)) {
                        PotentialAdopter.currentAdopter.favoritedDogsArray.remove(d);
                        d.favorited = false;

                    }
                }

                Toast t = Toast.makeText(context, action,
                        Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                t.show();


            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String action = "";
                d = dogList.get(position);


                if (!d.disliked) {
                    dislike.setImageResource(R.drawable.dislike_red);
                    action = dogList.get(position).getName() + " will not be included in the future.";
                    if (!PotentialAdopter.currentAdopter.dislikedDogsArray.contains(dogList.get(position))) {
                        PotentialAdopter.currentAdopter.dislikedDogsArray.add(dogList.get(position));
                        Log.d("test", "Disliked dog added " + dogList.get(position).getName());
                    }
                } else {
                    dislike.setImageResource(R.drawable.thumbs_down_icon);
                    action = dogList.get(position).getName() + " will be included in the future";
                    if (PotentialAdopter.currentAdopter.dislikedDogsArray.contains(dogList.get(position))) {
                        PotentialAdopter.currentAdopter.dislikedDogsArray.remove(dogList.get(position));
                        Log.d("test", "Disliked dog removed " + dogList.get(position).getName());

                    }
                }
                d.disliked = !d.disliked;

                Toast t = Toast.makeText(context, action,
                        Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                t.show();


            }
        });


        c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dog.currentDog = d;

                Intent i = new Intent(context, CollectionEnlarge.class);
                String id = PageViewAdapter.this.dogList.get(position).getId();
                CollectionEnlarge.viewMatches = true;
                i.putExtra(CollectionEnlarge.EXTRA_DOG_ID, id);

                context.startActivity(i);

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent("email");

            }
        });
        maps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent("maps");

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent("google");

            }
        });

        //If the rescue uploaded a photo in this session, b will not be null. Use that photo if
        //it is not null. Else, use the photo stored in the database. This is done because, in some
        //instances, the photo will not be uploaded to the database quick enough, so we must use
        //the photo from the current session.
        if (d.bitmapImage != null) {
            Glide.with(context)
                    // .using(new FirebaseImageLoader())
                    .load(d.bitmapImage)
                    .into(image);

        } else {


            Glide.with(context)
                    // .using(new FirebaseImageLoader())
                    .load(d.imageStorageReference)
                    .into(image);
        }


        name = view.findViewById(R.id.name);
        age = view.findViewById(R.id.age);
        size = view.findViewById(R.id.size);

        // imageView.setImageResource(dogs.get(position).getImage());
        String s = dogList.get(position).getName();
        String agetext = "Age: " + dogList.get(position).getAge();
        age.setText(agetext);
        size.setText(d.getSize());
        name.setText(d.getName());
        //converting the similarity score to a whole number (and converting to a percentage)
        int similarityScore = (int) (d.getSimilarityScore() * 100);
        String sim = similarityScore + "% Match!";
        similarity.setText(sim);
        String breedText = d.getBreed() + ", " + d.getSex();
        breed.setText(breedText);

        container.addView(view, 0);
        return view;
    }


    /**
     *
     * @param container - handled by PagerAdapter
     * @param position - handled by PagerAdapter
     * @param object - handled by PagerAdapter
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    /**
     *
     * @param reason - contains a single word indicating which button was clicked/what action
     *               is to be performed.
     */
    public void intent(final String reason) {

        //To perform these button clicks, we must first access the rescue organization's data from
        //the corresponding dog that was clicked.
        MainActivity.firestore.collection("rescue")
                .whereEqualTo("rescueID", d.getRescueID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {

                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Rescue r = document.toObject(Rescue.class);

                                //launches an email intent with the TO address filled in
                                if (reason.equals("email")) {


                                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                                    emailIntent.setData(Uri.parse("mailto:" + r.getEmail()));


                                    try {
                                        context.startActivity(emailIntent);
                                    } catch (ActivityNotFoundException e) {
                                        //TODO: Handle case where no email app is available

                                        Toast t = Toast.makeText(context, "Could not open an email app",
                                                Toast.LENGTH_SHORT);
                                        t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                                        t.show();
                                    }
                                }//launches google maps with the location of the rescue already set
                                else if (reason.equals("maps")) {

                                    String s;
                                    s = r.getStreet() + ", " + r.getCity().replaceAll(" ", "+") + ", " + r.getState();


                                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + s);
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    context.startActivity(mapIntent);


                                }//launches an internet engine located on the phone with the organization name in
                                //the search bar.
                                else if (reason.equals("google")) {
                                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                                    intent.putExtra(SearchManager.QUERY, r.getOrganization()); // query contains search string
                                    context.startActivity(intent);

                                }


                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }


                });

    }


}
