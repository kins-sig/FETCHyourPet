package com.sigm.fetchyourpet;

import android.graphics.Bitmap;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Rescue {


    // INT Org ID
    // String Org Name
    // INT Zip
    // INT Phone num
    // String Email
    private static int counter = 0;
    private static ArrayList<Rescue> rescues = new ArrayList<>();
    private int rescueID;
    private String organization, street, city, state, email, username,image,zip;
    private Bitmap photo;
    public static Rescue currentRescue;
    public List<Dog> dogs = new ArrayList<>();
    private StorageReference imageStorageReference;



    public Rescue(Bitmap b, String name, String street, String city, String state, String zip, String email, String path) {
        rescueID = counter;
        counter++;
        this.photo = b;
        this.organization = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
        this.image=path;

        rescues.add(this);
    }
    public Rescue(){

    }
    public String getImage(){
        return this.image;
    }
    public void setImage(String s){
        this.image=s;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setOrganization(String name) {
        this.organization = name;
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


    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getPhoto(){
        return photo;
    }
    public String getOrganization(){
        return this.organization;
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
        return (this.zip);
    }

    public StorageReference getImageStorageReference() {
        return imageStorageReference;
    }

    public void setImageStorageReference(StorageReference imageStorageReference) {
        this.imageStorageReference = imageStorageReference;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
