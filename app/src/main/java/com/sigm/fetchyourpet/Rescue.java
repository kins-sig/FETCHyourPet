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
    private static ArrayList<Rescue> rescues = new ArrayList<>();
    private int rescueID, zip;
    private String name, street, city, state, email, password;
    private Bitmap photo;
    public static Rescue currentRescue;


    public Rescue(Bitmap b, String name, String street, String city, String state, int zip, String email, String password) {
        rescueID = counter;
        counter++;
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
    public Rescue(){

    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getPhoto(){
        return photo;
    }
    public String getOrganization(){
        return name;
    }

    //            emailView.setText(r.getEmail());
    //            passwordLabel.setText("NEW PASSWORD");
    //            zipView.setText(r.getZip());
    //            streetView.setText(r.getStreet());
    //            stateView.setText(r.getState());
    //            cityView.setText(r.getCity());
    public String getEmail(){
        return this.email;
    }
    public String getStreet(){
        return this.street;
    }
    public String getCity(){
        return this.city;
    }

    public String getState() {
        return state;
    }
    public String getZip(){
        return Integer.toString(this.zip);
    }
}
