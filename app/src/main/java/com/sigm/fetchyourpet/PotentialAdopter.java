package com.sigm.fetchyourpet;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class PotentialAdopter {



    private int adopterID, zip, phone, numDogsRatedSinceLastRefresh;
    private Bitmap photo;
    private String firstName, lastname, email, password;
    int[] favorite_dog_ids;
    private static int counter = 0;
    private static ArrayList<PotentialAdopter> adopters = new ArrayList<>();
    private static PotentialAdopter currentAdopter;

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
    public PotentialAdopter(){

    }

    public void setCurrentAdopter(PotentialAdopter p){
        currentAdopter = p;
    }
    public PotentialAdopter getCurrentAdopter(){
        return currentAdopter;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getZip() {
        return Integer.toString(zip);
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setBitmap(Bitmap b){
        this.photo=b;
    }



}
