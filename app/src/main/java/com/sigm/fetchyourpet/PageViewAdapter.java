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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.List;

public class PageViewAdapter extends PagerAdapter {

    private ArrayList<Dog> dogList;
    private LayoutInflater layoutInflater;
    private Context context;
    private Dog d;


    public PageViewAdapter(ArrayList<Dog> dogs, Context context) {
        this.dogList = dogs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dogList.size();
    }



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_matches_item, container, false);

        d = dogList.get(position);
        d.disliked =false;

        ImageView image;
        image = view.findViewById(R.id.image);

        TextView age, size,name,breed,similarity;
        final ImageButton email, maps,search,like,dislike;
        CardView c;

        c = view.findViewById(R.id.cardView);
        email = view.findViewById(R.id.email);
        maps = view.findViewById(R.id.location);
        search = view.findViewById(R.id.google);
        breed = view.findViewById(R.id.breed);
        like = view.findViewById(R.id.like);
        dislike = view.findViewById(R.id.dislike);
        similarity = view.findViewById(R.id.similarityScore);

        Log.d("test",dogList.get(position).getName() + " " + dogList.get(position).favorited.toString());
        if(dogList.get(position).favorited){
            like.setImageResource(R.drawable.like_heart);
        }else {
            like.setImageResource(R.drawable.default_heart_icon);
        }
        if(dogList.get(position).disliked){
            dislike.setImageResource(R.drawable.dislike_red);
        }else {
            dislike.setImageResource(R.drawable.thumbs_down_icon);
        }


        like.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                d = dogList.get(position);

                String action = "";

                if(!d.favorited){
                    like.setImageResource(R.drawable.like_heart);
                    action = dogList.get(position).getName() + " was added to your favorites!";
                    if(!PotentialAdopter.currentAdopter.favoritedDogsArray.contains(d)) {
                        PotentialAdopter.currentAdopter.favoritedDogsArray.add(d);
                        Log.d("test","Favorited dog added " + d.getName());
                        d.favorited = true;

                    }
                }else {
                    like.setImageResource(R.drawable.default_heart_icon);
                    action = d.getName() + " was removed from your favorites.";
                    if(PotentialAdopter.currentAdopter.favoritedDogsArray.contains(d)) {
                        PotentialAdopter.currentAdopter.favoritedDogsArray.remove(d);
                        Log.d("test","Favorited dog removed " + d.getName());
                        d.favorited = false;

                    }
                }

                Toast t = Toast.makeText(context, action,
                        Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                t.show();



            }
        });
        dislike.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String action = "";
                d = dogList.get(position);


                if(!d.disliked){
                    dislike.setImageResource(R.drawable.dislike_red);
                    action = dogList.get(position).getName() + " will not be included in the future.";
                    if(!PotentialAdopter.currentAdopter.dislikedDogsArray.contains(dogList.get(position))) {
                        PotentialAdopter.currentAdopter.dislikedDogsArray.add(dogList.get(position));
                        Log.d("test","Disliked dog added " + dogList.get(position).getName());
                    }
                }else {
                    dislike.setImageResource(R.drawable.thumbs_down_icon);
                    action = dogList.get(position).getName() + " will be included in the future";
                    if(PotentialAdopter.currentAdopter.dislikedDogsArray.contains(dogList.get(position))) {
                        PotentialAdopter.currentAdopter.dislikedDogsArray.remove(dogList.get(position));
                        Log.d("test","Disliked dog removed " + dogList.get(position).getName());

                    }
                }
                d.disliked = !d.disliked;

                Toast t = Toast.makeText(context, action,
                        Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                t.show();


            }
        });



        c.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Dog.currentDog = d;

                Intent i = new Intent(context, CollectionEnlarge.class);
                String id = PageViewAdapter.this.dogList.get(position).getId();
                CollectionEnlarge.viewMatches=true;
                i.putExtra(CollectionEnlarge.EXTRA_DOG_ID, id);

                context.startActivity(i);

            }
        });

        email.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                intent("email");

            }
        });
        maps.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                intent("maps");

            }
        });

        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                intent("google");

            }
        });



        if(d.bitmapImage != null){
            Glide.with(context)
                    // .using(new FirebaseImageLoader())
                    .load(d.bitmapImage)
                    .into(image);

        }
        else {


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
        int similarityScore = (int)(d.getSimilarityScore() *100);
        String sim = similarityScore + "% Match!";
        similarity.setText(sim);
       String breedText = d.getBreed()+", " + d.getSex();
        breed.setText(breedText);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("param", models.get(position).getTitle());
//                context.startActivity(intent);
//                // finish();
//            }
//        });

        container.addView(view, 0);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public void intent(final String reason){


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

                                if(reason.equals("email")) {


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
                                }else if(reason.equals("maps")){

                                    String s;
                                    s = r.getStreet() + ", " + r.getCity().replaceAll(" ","+") + ", " + r.getState();


                                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + s);
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    context.startActivity(mapIntent);




                                }else if(reason.equals("google")){
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
