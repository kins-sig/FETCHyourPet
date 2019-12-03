package com.sigm.fetchyourpet;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PotentialAdopter {


    public static PotentialAdopter currentAdopter;
    private static int counter = 0;
    private static ArrayList<PotentialAdopter> adopters = new ArrayList<>();
    int[] favorite_dog_ids;
    private int phone, numDogsRatedSinceLastRefresh;
    private Bitmap photo;
    private String firstName, email, password, image, zip, username, adopterID,traits;
    ArrayList<Dog> dislikedDogsArray = new ArrayList<>();
    String dislikedDogs = "";
    boolean alreadySet = false;

    public PotentialAdopter(Bitmap b, String fname, String zip, String email, String path) {
        this.photo = b;
        this.firstName = fname;
        this.email = email;
        this.zip = zip;
        this.image = path;

        adopters.add(this);
    }

    public PotentialAdopter() {
    }

    public PotentialAdopter getCurrentAdopter() {
        return currentAdopter;
    }

    public void setCurrentAdopter(PotentialAdopter p) {
        currentAdopter = p;
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
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdopterID() {
        return adopterID;
    }

    public void setAdopterID(String adopterID) {
        this.adopterID = adopterID;
    }

    public void setTraits(String s){
        this.traits = s;
    }

    public String getTraits(){
        return this.traits;
    }


    public Matrix get_user_traits(){

        double[] data = new double[30];
        char[] chars = traits.trim().toCharArray();
        int i = 0;
        for(char c : chars){
            data[i] = Character.getNumericValue(c);
            i++;

        }
        return  new Matrix(data);



    }

    public String getDislikedDogs() {
        return dislikedDogs;
    }

    public void setDislikedDogs(String s) {
        this.dislikedDogs = s;
    }

    public void setDislikedDogsArray(){
        if(!alreadySet) {
            alreadySet = true;
            String s = dislikedDogs.trim();
            String[] disliked = s.split(" ");
            int i = 0;

            for(String id:disliked){
                for(Dog d:Dog.dogList){
                    if(id.trim().equals(d.getId())){
                        dislikedDogsArray.add(d);
                        Log.d("test",d.getName());

                    }
                }

            }


        }

    }

    public void clearDislikes(){
        dislikedDogsArray.clear();

        Map<String, Object> update = new HashMap<>();
        String s = "";

        update.put("dislikedDogs", s);

        MainActivity.firestore
                .collection("adopter")
                .document(PotentialAdopter.currentAdopter.getAdopterID())
                .update(update);
    }
}
