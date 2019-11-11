package com.sigm.fetchyourpet;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class PotentialAdopter {



    private int adopterID, zip, phone, numDogsRatedSinceLastRefresh;
    private Bitmap photo;
    private String firstName, lastname, email, password;
    int[] favorite_dog_ids;
    private static int counter = 0;
    private static ArrayList<PotentialAdopter> adopters = new ArrayList<>();;



    public PotentialAdopter(Bitmap b, String fname, String lname, int zip, String email, String password){
        adopterID = counter; counter++;
        this.photo = b;
        this.firstName = fname;
        this.lastname = lname;
        this.email = email;
        this.password = password;
        this.email = email;
        this.password = password;
        this.zip = zip;

        adopters.add(this);
    }


}
