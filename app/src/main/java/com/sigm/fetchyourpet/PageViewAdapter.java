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

    private Dog[] dogList;
    private LayoutInflater layoutInflater;
    private Context context;
    private Dog d;


    public PageViewAdapter(Dog[] dogs, Context context) {
        this.dogList = dogs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dogList.length;
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

        d = dogList[position];

        ImageView image;
        image = view.findViewById(R.id.image);

        TextView age, size,name,breed;
        final ImageButton email, maps,search,like,dislike;
        CardView c;

        c = view.findViewById(R.id.cardView);
        email = view.findViewById(R.id.email);
        maps = view.findViewById(R.id.location);
        search = view.findViewById(R.id.google);
        breed = view.findViewById(R.id.breed);
        like = view.findViewById(R.id.like);
        dislike = view.findViewById(R.id.dislike);


        if(d.favorited){
            like.setImageResource(R.drawable.like_heart);
        }else {
            like.setImageResource(R.drawable.default_heart_icon);
        }


        like.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String action = "";

                if(d.favorited){
                    like.setImageResource(R.drawable.default_heart_icon);
                     action = dogList[position].getName() + " was unfavorited.";
                }else {
                    like.setImageResource(R.drawable.like_heart);
                    action = dogList[position].getName() + " was favorited";
                }
                d.favorited = !d.favorited;

                Toast t = Toast.makeText(context, action,
                        Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                t.show();



            }
        });
        dislike.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String action = "";

                if(d.disliked){
                   // dislike.setImageResource(R.drawable.default_heart_icon);
                    action = dogList[position].getName() + " will not be included in the future.";
                }else {
                    //dislike.setImageResource(R.drawable.like_heart);
                    action = dogList[position].getName() + " will be included in the future";
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
                String id = PageViewAdapter.this.dogList[position].getId();

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
        String s = dogList[position].getName();
        String agetext = "Age: " + dogList[position].getAge();
        age.setText(agetext);
        size.setText(d.getSize());
        name.setText(d.getName());
        breed.setText(d.getBreed() +", " + d.getSex());

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
