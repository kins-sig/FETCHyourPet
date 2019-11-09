package com.sigm.fetchyourpet;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Rescue {



    // INT Org ID
    // String Org Name
    // INT Zip
    // INT Phone num
    // String Email
    private static int counter = 0;
    private int rescueID;
    private String name;
    private String street;
    private String city;
    private String state;
    private int zip;
    private String email;
    private String password;
    private Bitmap photo;
    private static ArrayList<Rescue> rescues = new ArrayList<>();;


    public Rescue(Bitmap b, String name, String street, String city, String state, int zip, String email, String password){
        rescueID = counter; counter++;
        this.photo = b;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
        this.password = password;

        rescues.add(this);
    }





}
